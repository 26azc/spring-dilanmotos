import { useEffect, useState } from "react";

export default function Usuarios() {
    const [usuarios, setUsuarios] = useState([]);
    const [nuevo, setNuevo] = useState({ nombre: '', correo: '', contrasena: '' });
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const cargar = async () => {
        try {
            const r = await fetch('http://localhost:8080/api/usuarios');
            const d = await r.json();
            setUsuarios(d);
        } catch (e) { console.error("Error:", e); }
    };

    useEffect(() => { cargar(); }, []);

    const guardar = async (e) => {
        e.preventDefault();
        
        // URL corregida: siempre a la API de usuarios
        const url = editMode 
            ? `http://localhost:8080/api/usuarios/${selectedId}` 
            : 'http://localhost:8080/api/usuarios';
        
        const method = editMode ? 'PUT' : 'POST';

        try {
            const res = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevo)
            });
            
            if (res.ok) {
                setNuevo({ nombre: '', correo: '', contrasena: '' });
                setEditMode(false);
                setSelectedId(null);
                cargar();
                alert(editMode ? "Usuario actualizado" : "Usuario guardado");
            }
        } catch (error) { alert("Error de conexión"); }
    };

    const iniciarEdicion = (u) => {
        window.scrollTo(0, 0);
        // Detectamos el ID (puede venir como idUsuario o id_usuario)
        const id = u.idUsuario || u.id_usuario;
        if (id) {
            setEditMode(true);
            setSelectedId(id);
            setNuevo({ nombre: u.nombre, correo: u.correo, contrasena: '' });
        }
    };

    const eliminar = async (id) => {
        if (!id || !window.confirm("¿Eliminar usuario?")) return;
        await fetch(`http://localhost:8080/api/usuarios/${id}`, { method: 'DELETE' });
        cargar();
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel">
                <h3 className="text-primary">{editMode ? 'Editar Usuario' : 'Gestión de Usuarios'}</h3>
                <hr />
                <form onSubmit={guardar}>
                    <input className="input-bs" placeholder="Nombre" value={nuevo.nombre} onChange={e => setNuevo({...nuevo, nombre: e.target.value})} required />
                    <input className="input-bs" placeholder="Correo" value={nuevo.correo} onChange={e => setNuevo({...nuevo, correo: e.target.value})} required />
                    <input className="input-bs" type="password" placeholder="Nueva Contraseña" value={nuevo.contrasena} onChange={e => setNuevo({...nuevo, contrasena: e.target.value})} />
                    <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100`}>
                        {editMode ? 'Guardar Cambios' : 'Registrar'}
                    </button>
                    {editMode && <button type="button" className="btn-bs btn-danger w-100 mt-2" onClick={() => {setEditMode(false); setNuevo({nombre:'', correo:'', contrasena:''})}}>Cancelar</button>}
                </form>
            </div>

            <div className="card-panel">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>ID</div><div>Nombre</div><div>Correo</div><div>Estado</div><div className="text-center">Acciones</div>
                    </div>
                    {usuarios.map(u => (
                        <div className="custom-table-row" key={u.idUsuario || u.id_usuario}>
                            <div>#{u.idUsuario || u.id_usuario}</div>
                            <div>{u.nombre}</div>
                            <div>{u.correo}</div>
                            <div style={{color: 'green', fontWeight: 'bold'}}>ACTIVO</div>
                            <div style={{display: 'flex', justifyContent: 'center', gap: '8px'}}>
                                <button className="btn-bs btn-success btn-sm" onClick={() => iniciarEdicion(u)}><i className="fa-solid fa-pen"></i></button>
                                <button className="btn-bs btn-danger btn-sm" onClick={() => eliminar(u.idUsuario || u.id_usuario)}><i className="fa-solid fa-trash"></i></button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}