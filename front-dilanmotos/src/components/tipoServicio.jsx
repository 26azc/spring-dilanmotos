import React, { useState, useEffect } from 'react';
import '../global.css';

const TipoServicio = () => {
    // ESTADOS
    const [tipoServicios, setTipoServicios] = useState([]); // lista de servicios
    const [busqueda, setBusqueda] = useState(""); // barra de búsqueda 
    const [nuevoTipo, setNuevoTipo] = useState({ nombre: "", descripcion: "" }); // formulario
    const [editMode, setEditMode] = useState(false); // controla si estamos editando

    const API_URL = "http://localhost:8080/api/tipo-servicio";

    // Cargamos los datos al iniciar 
    useEffect(() => {
        cargarServicios();
    }, []);

    // GET - Cargar servicios
    const cargarServicios = async () => {
        try {
            const url = busqueda ? `${API_URL}?search=${busqueda}` : API_URL;
            const response = await fetch(url);
            const data = await response.json();
            setTipoServicios(data);
        } catch (error) {
            console.log("Error al conectar con la API:", error);
        }
    };

    // POST / PUT - Guardar o Actualizar
    const handleGuardar = async (e) => {
        e.preventDefault();
        const metodo = editMode ? 'PUT' : 'POST';
        
        // CORRECCIÓN: Usamos id_tipo_servicio (sincronizado con Java y DB)
        const idActual = nuevoTipo.id_tipo_servicio;
        const url = editMode ? `${API_URL}/${idActual}` : API_URL;

        try {
            const response = await fetch(url, {
                method: metodo,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoTipo)
            });

            if (response.ok) {
                alert(editMode ? "Servicio actualizado!" : "Servicio guardado con éxito!!");
                setNuevoTipo({ nombre: "", descripcion: "" });
                setEditMode(false);
                cargarServicios();
            }
        } catch (error) {
            console.log("Error al procesar la solicitud:", error);
        }
    };

    // Preparar el formulario para editar
    const iniciarEdicion = (servicio) => {
        setEditMode(true);
        setNuevoTipo(servicio); // Carga los datos en el formulario
    };

    // DELETE - Eliminar
    const handleEliminar = async (id) => {
        // Validación preventiva para evitar el error 'undefined'
        if (!id) {
            console.error("ID no recibido en handleEliminar");
            return;
        }

        if (window.confirm("¿Estás seguro de eliminar este servicio?")) {
            try {
                const response = await fetch(`${API_URL}/${id}`, {
                    method: 'DELETE'
                });
               
                if (response.status === 204 || response.ok) {
                    // Filtramos usando el nombre de variable correcto
                    setTipoServicios(tipoServicios.filter(ts => ts.id_tipo_servicio !== id));
                    alert("Registro eliminado con éxito!");
                } else {
                    alert("No se pudo eliminar el registro seleccionado");
                }
            } catch (error) {
                console.log("Error al eliminar", error);
            }
        }
    };

    return (
        <div className="container mt-4">
            <h2 className="text-primary">Gestión de Tipos de Servicio</h2>
            <div className="main-content-inner">
                {/* Formulario de Registro/Edición */}
                <div className="card-panel">
                    <h3 className="text-primary">{editMode ? 'Editar Tipo de Servicio' : 'Registrar Nuevo Servicio'}</h3>
                    <hr />
                    <form onSubmit={handleGuardar}>
                        <input
                            className="input-bs"
                            placeholder="Nombre del Servicio (Ej: Mantenimiento)"
                            value={nuevoTipo.nombre}
                            onChange={e => setNuevoTipo({ ...nuevoTipo, nombre: e.target.value })}
                            required
                        />
                        <input
                            className="input-bs"
                            placeholder="Descripción"
                            value={nuevoTipo.descripcion}
                            onChange={e => setNuevoTipo({ ...nuevoTipo, descripcion: e.target.value })}
                            required
                        />

                        <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100`}>
                            {editMode ? 'Guardar Cambios' : 'Registrar'}
                        </button>

                        {editMode && (
                            <button
                                type="button"
                                className="btn-bs btn-danger w-100 mt-2"
                                onClick={() => {
                                    setEditMode(false);
                                    setNuevoTipo({ nombre: '', descripcion: '' });
                                }}
                            >
                                Cancelar
                            </button>
                        )}
                    </form>
                </div>

                {/* Tabla de Datos */}
                <div className="card-panel mt-4">
                    <div className="custom-table-container">
                        <div className="custom-table-header">                        
                            <div>Nombre</div>
                            <div>Descripción</div>
                            <div className="text-center">Acciones</div>
                        </div>

                        {tipoServicios.map(t => (
                            <div className="custom-table-row" key={t.id_tipo_servicio}>                               
                                <div>{t.nombre}</div>
                                <div>{t.descripcion}</div>
                                <div style={{ display: 'flex', justifyContent: 'center', gap: '8px' }}>
                                    <button
                                        className="btn-bs btn-success btn-sm"
                                        onClick={() => iniciarEdicion(t)}
                                    >
                                        <i className="fa-solid fa-pen"></i>
                                    </button>
                                    <button
                                        className="btn-bs btn-danger btn-sm"
                                        onClick={() => handleEliminar(t.id_tipo_servicio)}
                                    >
                                        <i className="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                        ))}
                        {tipoServicios.length === 0 && <p className="text-center p-3">No hay servicios registrados.</p>}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TipoServicio;