import { useEffect, useState } from "react";

export default function Motos() {
    const [motos, setMotos] = useState([]);
    const [marcas, setMarcas] = useState([]);
    const [nuevo, setNuevo] = useState({ modelo: '', cilindraje: '', idMarca: '', tipoReparacion: '' });
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const cargarDatos = async () => {
        const [resM, resMa] = await Promise.all([
            fetch('http://localhost:8080/api/motos'),
            fetch('http://localhost:8080/api/marcas') // Asegúrate de tener este endpoint
        ]);
        setMotos(await resM.json());
        setMarcas(await resMa.json());
    };

    useEffect(() => { cargarDatos(); }, []);

    const guardar = async (e) => {
        e.preventDefault();
        const url = editMode ? `http://localhost:8080/api/motos/${selectedId}` : 'http://localhost:8080/api/motos';
        
        const payload = {
            modelo: nuevo.modelo,
            cilindraje: parseFloat(nuevo.cilindraje),
            tipoReparacion: nuevo.tipoReparacion,
            marca: { idMarca: parseInt(nuevo.idMarca) },
            usuario: { idUsuario: 1 } // Usuario por defecto para pruebas
        };

        const res = await fetch(url, {
            method: editMode ? 'PUT' : 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert("Moto guardada");
            setNuevo({ modelo: '', cilindraje: '', idMarca: '', tipoReparacion: '' });
            setEditMode(false);
            cargarDatos();
        }
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel">
                <h3 className="text-primary">🏍️ Registro de Motos</h3>
                <form onSubmit={guardar}>
                    <div className="row">
                        <div className="col-md-6">
                            <label>Marca</label>
                            <select className="input-bs" value={nuevo.idMarca} onChange={e => setNuevo({...nuevo, idMarca: e.target.value})} required>
                                <option value="">Seleccione marca</option>
                                {marcas.map(m => <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>)}
                            </select>
                        </div>
                        <div className="col-md-6">
                            <label>Modelo (Año/Nombre)</label>
                            <input className="input-bs" value={nuevo.modelo} onChange={e => setNuevo({...nuevo, modelo: e.target.value})} required />
                        </div>
                    </div>
                    <label>Cilindraje (cc)</label>
                    <input className="input-bs" type="number" value={nuevo.cilindraje} onChange={e => setNuevo({...nuevo, cilindraje: e.target.value})} required />
                    
                    <label>Tipo de Reparación / Estado</label>
                    <input className="input-bs" value={nuevo.tipoReparacion} onChange={e => setNuevo({...nuevo, tipoReparacion: e.target.value})} required />

                    <button type="submit" className="btn-bs btn-primary w-100 mt-3">Guardar Moto</button>
                </form>
            </div>

            <div className="card-panel mt-4">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Marca</div><div>Modelo</div><div>Cilindraje</div><div className="text-center">Acciones</div>
                    </div>
                    {motos.map(m => (
                        <div className="custom-table-row" key={m.idMoto}>
                            <div className="fw-bold">{m.marca?.nombre}</div>
                            <div>{m.modelo}</div>
                            <div>{m.cilindraje}cc</div>
                            <div className="text-center">
                                <button className="btn-bs btn-danger btn-sm" onClick={() => {
                                    if(window.confirm("¿Eliminar?")) fetch(`http://localhost:8080/api/motos/${m.idMoto}`, {method:'DELETE'}).then(()=>cargarDatos())
                                }}>
                                    <i className="fa-solid fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}