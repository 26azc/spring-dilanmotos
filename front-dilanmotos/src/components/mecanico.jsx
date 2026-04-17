import React, { useState, useEffect } from 'react';
import '../global.css';

const Mecanico = () => {
    const [mecanicos, setMecanicos] = useState([]);
    const [busqueda, setBusqueda] = useState("");
    const [nuevoMecanico, setNuevoMecanico] = useState({nombre: "", especialidad: "", telefono: ""});
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null); 

    const API_URL = "http://localhost:8080/api/mecanicos";

    useEffect(() => {
        cargarMecanicos(); 
    }, []); 

    const cargarMecanicos = async () => {
        try {
       
            const url = busqueda ? `${API_URL}?search=${busqueda}` : API_URL;
            const response = await fetch(url);
            const data = await response.json();
            setMecanicos(data);
        } catch (error) {
            console.error("Error al conectar con la API:", error);
        }
    };

    const handleGuardar = async (e) => {
        e.preventDefault();
        const url = editMode ? `${API_URL}/${selectedId}` : API_URL;
        const method = editMode ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoMecanico)
            });

            if (response.ok) {
                alert(editMode ? "Datos actualizados" : "Mecánico registrado con éxito");
                resetForm();
                cargarMecanicos();
            }
        } catch (error) {
            console.error("Error en la solicitud:", error);
        }
    };

    const iniciarEdicion = (m) => {
        window.scrollTo(0, 0);
        const id = m.idMecanico || m.id_mecanico; 
        if (id) {
            setEditMode(true);
            setSelectedId(id);
            setNuevoMecanico({ 
                nombre: m.nombre, 
                especialidad: m.especialidad, 
                telefono: m.telefono 
            });
        }
    };

    const handleEliminar = async (id) => {
        if (!id || id === "undefined") return;
        if (window.confirm("¿Seguro que desea eliminar este registro?")) {
            try {
                const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
                if (response.ok) {
                    cargarMecanicos();
                    alert("Registro eliminado");
                }
            } catch (error) {
                console.error("Error al eliminar:", error);
            }
        }
    };

    const resetForm = () => {
        setNuevoMecanico({ nombre: "", especialidad: "", telefono: "" });
        setEditMode(false);
        setSelectedId(null);
    };

    return (
        <div className="container mt-4">
            <h2 className="text-primary">Panel de Control de Mecánicos</h2>
            <div className="card-panel">
                <form onSubmit={handleGuardar}>
                    <input className="input-bs" placeholder="Nombre completo" value={nuevoMecanico.nombre} onChange={e => setNuevoMecanico({ ...nuevoMecanico, nombre: e.target.value })} required />
                    <input className="input-bs" placeholder="Especialidad" value={nuevoMecanico.especialidad} onChange={e => setNuevoMecanico({ ...nuevoMecanico, especialidad: e.target.value })} required />
                    <input className="input-bs" placeholder="Teléfono" value={nuevoMecanico.telefono} onChange={e => setNuevoMecanico({ ...nuevoMecanico, telefono: e.target.value })} required />
                    <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100`}>
                        {editMode ? 'Actualizar Mecánico' : 'Registrar Nuevo'}
                    </button>
                    {editMode && <button type="button" className="btn-bs btn-danger w-100 mt-2" onClick={resetForm}>Cancelar</button>}
                </form>
            </div>
            <div className="card-panel mt-4">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Nombre</div><div>Especialidad</div><div>Teléfono</div><div className="text-center">Acciones</div>
                    </div>
                    {mecanicos.map(m => (
                        <div className="custom-table-row" key={m.idMecanico || m.id_mecanico}>
                            <div>{m.nombre}</div><div>{m.especialidad}</div><div>{m.telefono}</div>
                            <div style={{ display: 'flex', justifyContent: 'center', gap: '8px' }}>
                                <button className="btn-bs btn-success btn-sm" onClick={() => iniciarEdicion(m)}><i className="fa-solid fa-pen"></i></button>
                                <button className="btn-bs btn-danger btn-sm" onClick={() => handleEliminar(m.idMecanico || m.id_mecanico)}><i className="fa-solid fa-trash"></i></button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Mecanico;