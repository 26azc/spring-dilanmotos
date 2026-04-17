import { useEffect, useState } from "react";

export default function Motos() {
    const [motos, setMotos] = useState([]); // Inicializado como array siempre
    const [marcas, setMarcas] = useState([]);
    const [nuevo, setNuevo] = useState({ modelo: '', cilindraje: '', idMarca: '', tipoReparacion: '' });
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const cargarDatos = async () => {
        try {
            const [resM, resMa] = await Promise.all([
                fetch('http://localhost:8080/api/motos'),
                fetch('http://localhost:8080/api/marcas')
            ]);

            // Verificamos que las respuestas sean exitosas antes de convertir a JSON
            if (resM.ok && resMa.ok) {
                const dataMotos = await resM.json();
                const dataMarcas = await resMa.json();
                
                // Blindaje: Si el backend no devuelve un array, forzamos uno vacío
                setMotos(Array.isArray(dataMotos) ? dataMotos : []);
                setMarcas(Array.isArray(dataMarcas) ? dataMarcas : []);
            }
        } catch (error) {
            console.error("Error cargando datos:", error);
            setMotos([]); // Evita que .map() falle si el servidor está caído
        }
    };

    useEffect(() => { cargarDatos(); }, []);

    const prepararEdicion = (moto) => {
        setEditMode(true);
        setSelectedId(moto.idMoto);
        setNuevo({
            modelo: moto.modelo,
            cilindraje: moto.cilindraje,
            idMarca: moto.marca?.idMarca || '',
            tipoReparacion: moto.tipoReparacion || ''
        });
    };

    const guardar = async (e) => {
        e.preventDefault();
        const url = editMode ? `http://localhost:8080/api/motos/${selectedId}` : 'http://localhost:8080/api/motos';
        
        const payload = {
            modelo: nuevo.modelo,
            cilindraje: parseFloat(nuevo.cilindraje),
            tipoReparacion: nuevo.tipoReparacion,
            marca: { idMarca: parseInt(nuevo.idMarca) },
            usuario: { idUsuario: 1 } // Mantener el ID del usuario logueado aquí
        };

        try {
            const res = await fetch(url, {
                method: editMode ? 'PUT' : 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (res.ok) {
                alert(editMode ? "Moto actualizada" : "Moto guardada");
                setNuevo({ modelo: '', cilindraje: '', idMarca: '', tipoReparacion: '' });
                setEditMode(false);
                setSelectedId(null);
                cargarDatos();
            }
        } catch (error) {
            alert("Error al conectar con el servidor");
        }
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel">
                <h3 className="text-primary">🏍️ {editMode ? 'Editar Moto' : 'Registro de Motos'}</h3>
                <form onSubmit={guardar}>
                    <div className="row">
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Marca</label>
                            <select className="input-bs" value={nuevo.idMarca} onChange={e => setNuevo({...nuevo, idMarca: e.target.value})} required>
                                <option value="">Seleccione marca</option>
                                {marcas.map(m => <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>)}
                            </select>
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Modelo (Año/Nombre)</label>
                            <input className="input-bs" value={nuevo.modelo} onChange={e => setNuevo({...nuevo, modelo: e.target.value})} required />
                        </div>
                    </div>
                    
                    <div className="row">
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Cilindraje (cc)</label>
                            <input className="input-bs" type="number" value={nuevo.cilindraje} onChange={e => setNuevo({...nuevo, cilindraje: e.target.value})} required />
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Estado / Reparación</label>
                            <input className="input-bs" value={nuevo.tipoReparacion} onChange={e => setNuevo({...nuevo, tipoReparacion: e.target.value})} required />
                        </div>
                    </div>

                    <div className="d-flex gap-2">
                        <button type="submit" className={`btn-bs w-100 ${editMode ? 'btn-warning' : 'btn-primary'}`}>
                            {editMode ? 'Actualizar Cambios' : 'Guardar Moto'}
                        </button>
                        {editMode && (
                            <button type="button" className="btn-bs btn-secondary" onClick={() => {
                                setEditMode(false);
                                setNuevo({ modelo: '', cilindraje: '', idMarca: '', tipoReparacion: '' });
                            }}>Cancelar</button>
                        )}
                    </div>
                </form>
            </div>

            <div className="card-panel mt-4">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Marca</div><div>Modelo</div><div>Cilindraje</div><div className="text-center">Acciones</div>
                    </div>
                    {/* El operador && asegura que solo mapee si hay datos */}
                    {motos.length > 0 ? (
                        motos.map(m => (
                            <div className="custom-table-row" key={m.idMoto}>
                                <div className="fw-bold">{m.marca?.nombre || 'S/M'}</div>
                                <div>{m.modelo}</div>
                                <div>{m.cilindraje}cc</div>
                                <div className="text-center d-flex justify-content-center gap-2">
                                    <button className="btn-bs btn-warning btn-sm" onClick={() => prepararEdicion(m)}>
                                        <i className="fa-solid fa-pen"></i>
                                    </button>
                                    <button className="btn-bs btn-danger btn-sm" onClick={() => {
                                        if(window.confirm("¿Eliminar esta moto?")) {
                                            fetch(`http://localhost:8080/api/motos/${m.idMoto}`, {method:'DELETE'})
                                            .then(() => cargarDatos());
                                        }
                                    }}>
                                        <i className="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="p-3 text-center text-muted">No hay motos registradas.</div>
                    )}
                </div>
            </div>
        </div>
    );
}