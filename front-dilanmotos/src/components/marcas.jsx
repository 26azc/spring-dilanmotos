import { useEffect, useState } from "react";

export default function Marcas() {
    const [marcas, setMarcas] = useState([]);
    const [nuevo, setNuevo] = useState({ nombre: '' });
    // Usaremos 'selectedId' para rastrear qué marca editamos
    const [selectedId, setSelectedId] = useState(null);

    const API_URL = 'http://localhost:8080/api/marcas';

    const cargarDatos = async () => {
        try {
            const res = await fetch(API_URL);
            const data = await res.json();
            setMarcas(Array.isArray(data) ? data : []);
        } catch (e) { 
            console.error("Error al obtener marcas:", e); 
        }
    };

    useEffect(() => { cargarDatos(); }, []);

    // Carga los datos de la fila en el formulario para editar
    const prepararEdicion = (m) => {
        setSelectedId(m.idMarca); // Activamos modo edición guardando el ID
        setNuevo({ nombre: m.nombre });
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    const guardar = async (e) => {
        e.preventDefault();
        try {
            // Si selectedId tiene valor, es un PUT; si es null, es un POST
            const url = selectedId 
                ? `${API_URL}/${selectedId}` 
                : API_URL;
        
            const method = selectedId ? 'PUT' : 'POST';

            const res = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevo)
            });

            if (res.ok) {
                setNuevo({ nombre: '' });
                setSelectedId(null); // Salimos del modo edición
                cargarDatos();
                alert(selectedId ? "Marca actualizada con éxito" : "Marca registrada con éxito");
            }
        } catch (e) { 
            alert("Error de conexión con el servidor"); 
        }
    };

    const eliminar = async (id) => {
        if (window.confirm('¿Deseas eliminar esta marca permanentemente?')) {
            try {
                const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
                
                if (res.status === 204 || res.ok) {
                    cargarDatos();
                } else if (res.status === 409) {
                    alert("No se puede eliminar: Esta marca tiene registros asociados.");
                }
            } catch (e) { 
                console.error("Error al eliminar:", e); 
            }
        }
    };

    const cancelarEdicion = () => {
        setSelectedId(null);
        setNuevo({ nombre: '' });
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel shadow-sm">
                <h3 className="text-primary mb-4">🏷️ Gestión de Marcas</h3>
                
                <form onSubmit={guardar}>
                    <div className="row">
                        <div className="col-md-12">
                            <label className="fw-bold mb-2">
                                {selectedId ? "Modificar Nombre de Marca" : "Nuevo Nombre de Marca"}
                            </label>
                            <input 
                                className="input-bs" 
                                placeholder="Ej: Honda, Yamaha, Apple..." 
                                value={nuevo.nombre} 
                                onChange={e => setNuevo({ nombre: e.target.value })} 
                                required 
                            />
                        </div>
                    </div>
                    
                    <div className="d-flex gap-2 mt-3">
                        <button type="submit" className={`btn-bs w-100 ${selectedId ? 'btn-warning' : 'btn-primary'}`}>
                            <i className={`fa-solid ${selectedId ? 'fa-save' : 'fa-plus'}`}></i> 
                            {selectedId ? " Guardar Cambios" : " Registrar Marca"}
                        </button>
                        
                        {selectedId && (
                            <button type="button" className="btn-bs btn-secondary w-50" onClick={cancelarEdicion}>
                                Cancelar
                            </button>
                        )}
                    </div>
                </form>
            </div>

            <div className="card-panel mt-4 shadow-sm">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>ID</div>
                        <div>Nombre</div>
                        <div className="text-center">Acciones</div>
                    </div>
                    
                    {marcas.length > 0 ? (
                        marcas.map(m => (
                            <div className="custom-table-row" key={m.idMarca}>
                                <div className="text-muted">#{m.idMarca}</div>
                                <div className="fw-bold">{m.nombre}</div>
                                <div className="text-center d-flex justify-content-center gap-2">
                                    <button 
                                        className="btn-bs btn-success btn-sm" 
                                        title="Editar"
                                        onClick={() => prepararEdicion(m)}
                                    >
                                        <i className="fa-solid fa-pen"></i>
                                    </button>
                                    <button 
                                        className="btn-bs btn-danger btn-sm" 
                                        title="Eliminar"
                                        onClick={() => eliminar(m.idMarca)}
                                    >
                                        <i className="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="p-4 text-center text-muted">
                            No hay marcas en la base de datos.
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}