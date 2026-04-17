import { useEffect, useState } from "react";

export default function Usuarios() {
    const [usuarios, setUsuarios] = useState([]);
    const [nuevo, setNuevo] = useState({ nombre: '', correo: '', contrasena: '', idReferencia: 1 });
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const cargar = async () => {
        try {
            const r = await fetch('http://localhost:8080/api/usuarios');
            const d = await r.json();
            setUsuarios(d);
        } catch (e) { console.error("Error al cargar:", e); }
    };

    useEffect(() => { cargar(); }, []);

    const guardar = async (e) => {
        e.preventDefault();
        
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
                setNuevo({ nombre: '', correo: '', contrasena: '', idReferencia: 1 });
                setEditMode(false);
                setSelectedId(null);
                cargar();
                alert(editMode ? "Usuario actualizado" : "Usuario registrado exitosamente");
            } else {
                const errorData = await res.text();
                alert("Error del servidor: " + errorData);
            }
        } catch (error) { alert("Error de conexión con el servidor"); }
    };

    const iniciarEdicion = (u) => {
        window.scrollTo(0, 0);
        // Blindaje contra nombres de propiedades variables
        const id = u.idUsuario !== undefined ? u.idUsuario : u.id_usuario;
        if (id !== undefined) {
            setEditMode(true);
            setSelectedId(id);
            setNuevo({ nombre: u.nombre, correo: u.correo, contrasena: '', idReferencia: 1 });
        }
    };

    const eliminar = async (id) => {
        if (!id || id === "undefined") {
            alert("Error: ID de usuario no válido");
            return;
        }
        if (!window.confirm("¿Está seguro de eliminar este usuario?")) return;
        
        try {
            const res = await fetch(`http://localhost:8080/api/usuarios/${id}`, { method: 'DELETE' });
            if (res.ok) {
                cargar();
                alert("Usuario eliminado");
            }
        } catch (e) { alert("Error al eliminar"); }
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel">
                <h3 className="text-primary">{editMode ? 'Editar Usuario' : 'Gestión de Usuarios'}</h3>
                <hr />
                <form onSubmit={guardar}>
                    <input className="input-bs" placeholder="Nombre completo" value={nuevo.nombre} onChange={e => setNuevo({...nuevo, nombre: e.target.value})} required />
                    <input className="input-bs" placeholder="Correo electrónico" value={nuevo.correo} onChange={e => setNuevo({...nuevo, correo: e.target.value})} required />
                    <input className="input-bs" type="password" placeholder={editMode ? "Nueva contraseña (opcional)" : "Contraseña"} value={nuevo.contrasena} onChange={e => setNuevo({...nuevo, contrasena: e.target.value})} required={!editMode} />
                    
                    <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100`}>
                        {editMode ? 'Actualizar Datos' : 'Registrar Usuario'}
                    </button>
                    {editMode && <button type="button" className="btn-bs btn-danger w-100 mt-2" onClick={() => {setEditMode(false); setNuevo({nombre:'', correo:'', contrasena:'', idReferencia: 1})}}>Cancelar</button>}
                </form>
            </div>

            <div className="card-panel mt-4">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>ID</div><div>Nombre</div><div>Correo</div><div>Estado</div><div className="text-center">Acciones</div>
                    </div>
                    {usuarios.map(u => {
                        const currentId = u.idUsuario !== undefined ? u.idUsuario : u.id_usuario;
                        return (
                            <div className="custom-table-row" key={currentId}>
                                <div>#{currentId}</div>
                                <div>{u.nombre}</div>
                                <div>{u.correo}</div>
                                <div style={{color: 'green', fontWeight: 'bold'}}>ACTIVO</div>
                                <div style={{display: 'flex', justifyContent: 'center', gap: '8px'}}>
                                    <button className="btn-bs btn-success btn-sm" onClick={() => iniciarEdicion(u)}><i className="fa-solid fa-pen"></i></button>
                                    <button className="btn-bs btn-danger btn-sm" onClick={() => eliminar(currentId)}><i className="fa-solid fa-trash"></i></button>
                                </div>
                            </div>
                        );
                    })}
                </div>
            </div>
        </div>
    );
}