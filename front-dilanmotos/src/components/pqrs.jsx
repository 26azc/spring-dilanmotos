import { useEffect, useState } from "react";

export default function Pqrs() {
    const [solicitudes, setSolicitudes] = useState([]);
    const [nuevo, setNuevo] = useState({ 
        tipo: 'Peticion', asunto: '', descripcion: '', 
        estado: 'PENDIENTE', respuesta_admin: '', idUsuario: 1 
    });
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const API_URL = 'http://localhost:8080/api/pqrs';

    const cargarDatos = async () => {
        try {
            const res = await fetch(API_URL);
            const data = await res.json();
            setSolicitudes(Array.isArray(data) ? data : []);
        } catch (error) { console.error("Error cargando PQRS:", error); }
    };

    useEffect(() => { cargarDatos(); }, []);

    const guardar = async (e) => {
        e.preventDefault();
        const url = editMode ? `${API_URL}/${selectedId}` : API_URL;
        
        try {
            const res = await fetch(url, {
                method: editMode ? 'PUT' : 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevo)
            });

            if (res.ok) {
                alert(editMode ? "✅ PQRS Atendida" : "✅ PQRS Enviada");
                setNuevo({ tipo: 'Peticion', asunto: '', descripcion: '', estado: 'PENDIENTE', respuesta_admin: '', idUsuario: 1 });
                setEditMode(false);
                cargarDatos();
            } else {
                alert("Error 500: Revisa la consola de Java.");
            }
        } catch (err) { alert("Error de conexión"); }
    };

    const iniciarEdicion = (p) => {
        window.scrollTo(0, 0);
        setEditMode(true);
        setSelectedId(p.idPqrs);
        setNuevo({
            tipo: p.tipo,
            asunto: p.asunto,
            descripcion: p.descripcion,
            estado: p.estado,
            respuesta_admin: p.respuesta_admin || '',
            idUsuario: p.idUsuario
        });
    };

    const formatearFecha = (fechaRaw) => {
        if (!fechaRaw) return "En proceso...";
        // LocalDateTime llega como string "2026-04-15T..."
        return fechaRaw.replace('T', ' ').substring(0, 16);
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel">
                <h3 className="text-primary">{editMode ? 'Atender Solicitud' : 'Nueva PQRS'}</h3>
                <hr />
                <form onSubmit={guardar}>
                    <div className="row">
                        <div className="col-md-6">
                            <label className="fw-bold">Tipo de Solicitud</label>
                            <select className="input-bs" value={nuevo.tipo} onChange={e => setNuevo({...nuevo, tipo: e.target.value})} disabled={editMode}>
                                <option value="Peticion">Petición</option>
                                <option value="Queja">Queja</option>
                                <option value="Reclamo">Reclamo</option>
                                <option value="Sugerencia">Sugerencia</option>
                            </select>
                        </div>
                        {editMode && (
                            <div className="col-md-6">
                                <label className="fw-bold text-danger">Cambiar Estado</label>
                                <select className="input-bs border-danger" value={nuevo.estado} onChange={e => setNuevo({...nuevo, estado: e.target.value})}>
                                    <option value="PENDIENTE">PENDIENTE</option>
                                    <option value="RECIBIDO">RECIBIDO</option>
                                    <option value="EN PROCESO">EN PROCESO</option>
                                    <option value="RESPONDIDA">RESPONDIDA</option>
                                    <option value="CERRADA">CERRADA</option>
                                </select>
                            </div>
                        )}
                    </div>

                    <label className="mt-2 fw-bold">Asunto</label>
                    <input className="input-bs" value={nuevo.asunto} onChange={e => setNuevo({...nuevo, asunto: e.target.value})} required disabled={editMode} />
                    
                    <label className="fw-bold">Descripción detallada</label>
                    <textarea className="input-bs" rows="3" value={nuevo.descripcion} onChange={e => setNuevo({...nuevo, descripcion: e.target.value})} required disabled={editMode} />

                    {editMode && (
                        <div className="mt-3 p-3 bg-light border border-success rounded">
                            <label className="fw-bold text-success">Respuesta del Administrador</label>
                            <textarea className="input-bs border-success" rows="3" value={nuevo.respuesta_admin} onChange={e => setNuevo({...nuevo, respuesta_admin: e.target.value})} placeholder="Escribe la respuesta formal aquí..." />
                        </div>
                    )}

                    <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100 mt-3`}>
                        {editMode ? 'Guardar Cambios y Notificar' : 'Enviar Mi Solicitud'}
                    </button>
                    {editMode && <button type="button" className="btn-bs btn-secondary w-100 mt-2" onClick={() => {setEditMode(false); setNuevo({tipo:'Peticion', asunto:'', descripcion:'', estado:'PENDIENTE', idUsuario: 1})}}>Cancelar</button>}
                </form>
            </div>

            <div className="card-panel mt-4">
                <h5>Historial de Solicitudes</h5>
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Fecha</div><div>Asunto</div><div>Estado</div><div className="text-center">Acciones</div>
                    </div>
                    {solicitudes.map(p => (
                        <div className="custom-table-row" key={p.idPqrs}>
                            <div className="small">{formatearFecha(p.fecha)}</div>
                            <div className="fw-bold">{p.asunto}</div>
                            <div>
                                <span className={`badge ${p.estado === 'PENDIENTE' ? 'bg-warning' : p.estado === 'RESPONDIDA' ? 'bg-success' : 'bg-primary'}`}>
                                    {p.estado}
                                </span>
                            </div>
                            <div className="text-center">
                                <button className="btn-bs btn-success btn-sm me-2" onClick={() => iniciarEdicion(p)}>
                                    <i className="fa-solid fa-pen-to-square"></i>
                                </button>
                                <button className="btn-bs btn-danger btn-sm" onClick={async () => {
                                    if(window.confirm("¿Eliminar registro?")) {
                                        await fetch(`${API_URL}/${p.idPqrs}`, {method:'DELETE'});
                                        cargarDatos();
                                    }
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