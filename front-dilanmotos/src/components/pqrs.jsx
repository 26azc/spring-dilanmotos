import React, { useState, useEffect } from 'react';

const PqrsManager = () => {
    const [listaPqrs, setListaPqrs] = useState([]);
    const [formData, setFormData] = useState({ tipo: 'Peticion', asunto: '', descripcion: '' });
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    // 1. CARGAR DATOS DESDE EL BACKEND
    const cargarPqrs = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/pqrs');
            if (!res.ok) throw new Error("Error al obtener datos");
            const data = await res.json();
            setListaPqrs(data);
        } catch (error) {
            console.error("Error en fetch cargarPqrs:", error);
        }
    };

    useEffect(() => {
        cargarPqrs();
    }, []);

    // 2. GUARDAR O ACTUALIZAR
    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const url = editMode 
            ? `http://localhost:8080/api/pqrs/${selectedId}` 
            : 'http://localhost:8080/api/pqrs';
        
        const method = editMode ? 'PUT' : 'POST';

        // Construcción del objeto para enviar (Ajustado para evitar errores 400)
        const objetoParaEnviar = {
            ...formData,
            id_usuario: 1, // Cambiar por ID del usuario logueado en el futuro
            fecha_envio: editMode ? undefined : new Date().toISOString(),
            estado: editMode ? 'EN PROCESO' : 'PENDIENTE',
            comentario_usuario: "Enviado desde el panel de gestión"
        };

        try {
            const res = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(objetoParaEnviar)
            });

            if (res.ok) {
                alert(editMode ? "✅ PQRS actualizada correctamente" : "✅ PQRS enviada con éxito");
                setFormData({ tipo: 'Peticion', asunto: '', descripcion: '' });
                setEditMode(false);
                setSelectedId(null);
                cargarPqrs();
            } else {
                const errorLog = await res.text();
                console.error("Error del servidor:", errorLog);
                alert("Hubo un problema con los datos. Revisa la consola de IntelliJ.");
            }
        } catch (error) {
            alert("Error crítico: No se pudo conectar con el servidor.");
        }
    };

    // 3. ELIMINAR (Solución al error 'undefined')
    const handleEliminar = async (item) => {
        // Buscamos el ID en ambas nomenclaturas posibles
        const id = item.id_pqrs || item.idPqrs;

        if (!id) {
            alert("Error: No se encontró un ID válido para este registro.");
            console.log("Objeto recibido:", item);
            return;
        }

        if (!window.confirm(`¿Seguro que quieres eliminar la PQRS #${id}?`)) return;
        
        try {
            const res = await fetch(`http://localhost:8080/api/pqrs/${id}`, { 
                method: 'DELETE' 
            });

            if (res.ok) {
                cargarPqrs();
            } else {
                alert("El servidor denegó la eliminación.");
            }
        } catch (error) {
            console.error("Error al eliminar:", error);
        }
    };

    // 4. INICIAR EDICIÓN
    const iniciarEdicion = (item) => {
        window.scrollTo(0, 0);
        const id = item.id_pqrs || item.idPqrs;

        if (id) {
            setEditMode(true);
            setSelectedId(id);
            setFormData({
                tipo: item.tipo,
                asunto: item.asunto,
                descripcion: item.descripcion
            });
        }
    };

    return (
        <div className="main-content-inner">
            {/* PANEL DE FORMULARIO */}
            <div className="card-panel">
                <h3 className="text-primary">
                    <i className={`fa-solid ${editMode ? 'fa-edit' : 'fa-plus-circle'} me-2`}></i>
                    {editMode ? 'Editar Solicitud' : 'Nueva PQRS'}
                </h3>
                <hr />
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label className="fw-bold">Tipo</label>
                        <select 
                            className="input-bs" 
                            value={formData.tipo} 
                            onChange={e => setFormData({...formData, tipo: e.target.value})}
                        >
                            <option value="Peticion">Peticion</option>
                            <option value="Queja">Queja</option>
                            <option value="Reclamo">Reclamo</option>
                            <option value="Sugerencia">Sugerencia</option>
                        </select>
                    </div>

                    <div className="mb-3">
                        <label className="fw-bold">Asunto</label>
                        <input 
                            className="input-bs" 
                            placeholder="Resumen de tu solicitud"
                            value={formData.asunto} 
                            onChange={e => setFormData({...formData, asunto: e.target.value})} 
                            required 
                        />
                    </div>

                    <div className="mb-3">
                        <label className="fw-bold">Descripción</label>
                        <textarea 
                            className="input-bs" 
                            rows="4" 
                            placeholder="Explica detalladamente..."
                            value={formData.descripcion} 
                            onChange={e => setFormData({...formData, descripcion: e.target.value})} 
                            required 
                        />
                    </div>

                    <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100`}>
                        <i className={`fa-solid ${editMode ? 'fa-save' : 'fa-paper-plane'} me-2`}></i>
                        {editMode ? 'Guardar Cambios' : 'Enviar Solicitud'}
                    </button>

                    {editMode && (
                        <button 
                            type="button" 
                            className="btn-bs btn-danger w-100 mt-2" 
                            onClick={() => {
                                setEditMode(false); 
                                setSelectedId(null);
                                setFormData({tipo:'Peticion', asunto:'', descripcion:''});
                            }}
                        >
                            Cancelar
                        </button>
                    )}
                </form>
            </div>

            {/* TABLA DE RESULTADOS */}
            <div className="card-panel mt-4">
                <h5 className="text-muted mb-3">Historial de PQRS</h5>
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Fecha</div>
                        <div>Tipo</div>
                        <div>Asunto</div>
                        <div>Estado</div>
                        <div className="text-center">Acciones</div>
                    </div>

                    {listaPqrs.length > 0 ? (
                        listaPqrs.map((item) => {
                            const currentId = item.id_pqrs || item.idPqrs;
                            return (
                                <div className="custom-table-row" key={currentId || Math.random()}>
                                    <div>{item.fecha_envio ? new Date(item.fecha_envio).toLocaleDateString() : 'N/A'}</div>
                                    <div>{item.tipo}</div>
                                    <div className="text-truncate" style={{maxWidth: '200px'}}>{item.asunto}</div>
                                    <div className="fw-bold text-primary">{item.estado || 'RECIBIDO'}</div>
                                    <div style={{display: 'flex', justifyContent: 'center', gap: '8px'}}>
                                        <button 
                                            className="btn-bs btn-success btn-sm" 
                                            onClick={() => iniciarEdicion(item)}
                                            title="Editar"
                                        >
                                            <i className="fa-solid fa-pen"></i>
                                        </button>
                                        <button 
                                            className="btn-bs btn-danger btn-sm" 
                                            onClick={() => handleEliminar(item)}
                                            title="Eliminar"
                                        >
                                            <i className="fa-solid fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            );
                        })
                    ) : (
                        <div className="p-4 text-center text-muted">No hay solicitudes registradas.</div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default PqrsManager;