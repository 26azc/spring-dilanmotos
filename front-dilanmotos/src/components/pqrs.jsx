import { useEffect, useState } from "react";

export default function Pqrs() {
    // 💡 Obtenemos el ID del usuario logueado
    const idLogueado = localStorage.getItem("idUsuario");

    const [solicitudes, setSolicitudes] = useState([]);
    const [nuevo, setNuevo] = useState({ 
        tipo: 'Peticion', 
        asunto: '', 
        descripcion: '', 
        comentario_usuario: '', 
        estado: 'PENDIENTE',
        respuesta_admin: '',
        calificacion_servicio: '-',
        comentario_servicio: '-'
    });
    
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const API_URL = 'http://localhost:8080/api/pqrs';

    const cargarDatos = async () => {
        try {
            const res = await fetch(API_URL);
            if (res.ok) {
                const data = await res.json();
                setSolicitudes(Array.isArray(data) ? data : []);
            }
        } catch (error) {
            console.error("Error al cargar PQRS:", error);
        }
    };

    useEffect(() => {
        cargarDatos();
    }, []);

    const guardar = async (e) => {
        e.preventDefault();

        if (!idLogueado) {
            alert("⚠️ Debes iniciar sesión para gestionar solicitudes.");
            return;
        }

        // 🛡️ BLINDAJE: Construimos el JSON exacto para evitar errores de nulos en la DB
        const payload = {
            idUsuario: parseInt(idLogueado),
            tipo: nuevo.tipo,
            asunto: nuevo.asunto,
            descripcion: nuevo.descripcion,
            comentario_usuario: nuevo.comentario_usuario.trim() || "Sin comentario",
            estado: nuevo.estado || 'PENDIENTE',
            respuesta_admin: nuevo.respuesta_admin || "Sin respuesta",
            calificacion_servicio: nuevo.calificacion_servicio || "-",
            comentario_servicio: nuevo.comentario_servicio || "-"
        };

        const url = editMode ? `${API_URL}/${selectedId}` : API_URL;
        const method = editMode ? 'PUT' : 'POST';

        try {
            const res = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (res.ok) {
                alert(editMode ? "✅ Solicitud actualizada con éxito" : "✅ Solicitud enviada correctamente");
                resetForm();
                cargarDatos();
            } else {
                const errorData = await res.text();
                console.error("Fallo del servidor:", errorData);
                alert("❌ Error 500: Revisa que todos los campos coincidan en Java y la DB.");
            }
        } catch (err) {
            alert("❌ Error de conexión. ¿Está encendido el Backend?");
        }
    };

    const resetForm = () => {
        setNuevo({ 
            tipo: 'Peticion', asunto: '', descripcion: '', 
            comentario_usuario: '', estado: 'PENDIENTE',
            respuesta_admin: '', calificacion_servicio: '-', comentario_servicio: '-'
        });
        setEditMode(false);
        setSelectedId(null);
    };

    const iniciarEdicion = (p) => {
        window.scrollTo(0, 0);
        setEditMode(true);
        setSelectedId(p.idPqrs);
        setNuevo({
            tipo: p.tipo,
            asunto: p.asunto,
            descripcion: p.descripcion,
            comentario_usuario: p.comentario_usuario || '',
            estado: p.estado,
            respuesta_admin: p.respuesta_admin || '',
            calificacion_servicio: p.calificacion_servicio || '-',
            comentario_servicio: p.comentario_servicio || '-'
        });
    };

    const formatearFecha = (fechaRaw) => {
        if (!fechaRaw) return "Pendiente";
        return fechaRaw.replace('T', ' ').substring(0, 16);
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel">
                <h3 className="text-primary">
                    <i className="fa-solid fa-file-signature me-2"></i>
                    {editMode ? 'Gestionar Solicitud' : 'Nueva PQRS'}
                </h3>
                <hr />
                <form onSubmit={guardar}>
                    <div className="row">
                        <div className="col-md-6">
                            <label className="fw-bold">Tipo de Trámite</label>
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
                    <input className="input-bs" value={nuevo.asunto} onChange={e => setNuevo({...nuevo, asunto: e.target.value})} required disabled={editMode} placeholder="Ej: Problemas con mi factura" />
                    
                    <label className="fw-bold">Detalle de la Solicitud</label>
                    <textarea className="input-bs" rows="3" value={nuevo.descripcion} onChange={e => setNuevo({...nuevo, descripcion: e.target.value})} required disabled={editMode} placeholder="Explica detalladamente..." />

                    <label className="fw-bold mt-2 text-secondary">Comentario del Usuario</label>
                    <input className="input-bs" value={nuevo.comentario_usuario} onChange={e => setNuevo({...nuevo, comentario_usuario: e.target.value})} required disabled={editMode} placeholder="Observación final del usuario..." />

                    {editMode && (
                        <div className="mt-3 p-3 bg-light border border-success rounded">
                            <label className="fw-bold text-success">Respuesta Oficial del Administrador</label>
                            <textarea className="input-bs border-success" rows="3" value={nuevo.respuesta_admin} onChange={e => setNuevo({...nuevo, respuesta_admin: e.target.value})} placeholder="Escribe la solución aquí..." />
                        </div>
                    )}

                    <div className="d-flex gap-2 mt-3">
                        <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} flex-grow-1`}>
                            {editMode ? 'Actualizar y Responder' : 'Enviar Solicitud'}
                        </button>
                        {editMode && <button type="button" className="btn-bs btn-secondary" onClick={resetForm}>Cancelar</button>}
                    </div>
                </form>
            </div>

            <div className="card-panel mt-4">
                <h5>Historial de Gestión de PQRS</h5>
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Enviado</div>
                        <div>Tipo</div>
                        <div>Asunto</div>
                        <div>Estado</div>
                        <div className="text-center">Acciones</div>
                    </div>
                    {solicitudes.length > 0 ? solicitudes.map(p => (
                        <div className="custom-table-row" key={p.idPqrs}>
                            <div className="small">{formatearFecha(p.fecha)}</div>
                            <div className="fw-bold">{p.tipo}</div>
                            <div>{p.asunto}</div>
                            <div>
                                <span className={`badge ${p.estado === 'PENDIENTE' ? 'bg-warning' : p.estado === 'RESPONDIDA' ? 'bg-success' : 'bg-primary'}`}>
                                    {p.estado}
                                </span>
                            </div>
                            <div className="text-center d-flex gap-1 justify-content-center">
                                <button className="btn-bs btn-success btn-sm" onClick={() => iniciarEdicion(p)} title="Editar/Atender">
                                    <i className="fa-solid fa-pen-to-square"></i>
                                </button>
                                <button className="btn-bs btn-danger btn-sm" onClick={async () => {
                                    if(window.confirm("¿Deseas eliminar este registro de forma permanente?")) {
                                        await fetch(`${API_URL}/${p.idPqrs}`, {method:'DELETE'});
                                        cargarDatos();
                                    }
                                }}>
                                    <i className="fa-solid fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    )) : (
                        <div className="text-center p-4">No hay solicitudes registradas.</div>
                    )}
                </div>
            </div>
        </div>
    );
}