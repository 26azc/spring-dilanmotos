import { useEffect, useState } from "react";

export default function Usuarios() {
const [usuarios, setUsuarios] = useState([]);
    const [busqueda, setBusqueda] = useState('');
    const [nuevoUsuario, setNuevoUsuario] = useState({ nombre: '', correo: '', contrasena: '' });
    const [mensaje, setMensaje] = useState(null); // Para alertas de éxito/error
    

useEffect(() => {
        cargarUsuarios();
    }, []);

    //axios o fetch para cargar usuarios

    const cargarUsuarios = async (termino = '') => {
        try {
            const url = termino ? `http://localhost:8080/api/usuarios?search=${termino}` : 'http://localhost:8080/api/usuarios';
            const response = await fetch(url);
            const data = await response.json();
            setUsuarios(data);
        } catch (error) {
            console.error("Error cargando usuarios:", error);
        }
    };

    const guardarUsuario = async (e) => {
        e.preventDefault(); 
        try {
            const response = await fetch('http://localhost:8080/api/usuarios', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoUsuario)
            });

            if (response.ok) {
                setMensaje({ tipo: 'success', texto: '¡Usuario creado exitosamente!' });
                setNuevoUsuario({ nombre: '', correo: '', contrasena: '' }); 
                cargarUsuarios(); 
            } else {
                setMensaje({ tipo: 'danger', texto: 'Error al crear el usuario' });
            }
        } catch (error) {
            console.error("Error guardando usuario:", error);
        }
    };

    // 5. Función para DELETE (Eliminar usuario)
    const eliminarUsuario = async (id) => {
        if (!window.confirm('¿Estás seguro de que deseas eliminar este usuario?')) return;

        try {
            const response = await fetch(`http://localhost:8080/api/usuarios/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                setMensaje({ tipo: 'warning', texto: 'Usuario eliminado.' });
                cargarUsuarios(); // Recarga la tabla
            }
        } catch (error) {
            console.error("Error eliminando usuario:", error);
        }
    };

    // Manejador del buscador
    const handleBuscar = (e) => {
        e.preventDefault();
        cargarUsuarios(busqueda);
    };

    return (
        <div style={{ display: 'flex', minHeight: '100vh' }}>
            {/* Aquí iría tu componente Sidebar. Por ahora lo simulamos */}
            <div className="sidebar" style={{ width: '250px', backgroundColor: '#f8f9fa', borderRight: '1px solid #dee2e6' }}>
                <div className="p-3">Menú Lateral (Mock)</div>
            </div>

            <div className="main-content" style={{ flexGrow: 1, padding: '2rem' }}>
                <h3>Módulo Usuarios</h3>
                <hr />

                {/* Mensajes de Alerta */}
                {mensaje && (
                    <div className={`alert alert-${mensaje.tipo}`}>
                        {mensaje.texto}
                    </div>
                )}

                {/* Formulario de búsqueda */}
                <form onSubmit={handleBuscar} className="mb-3">
                    <div className="row g-2 align-items-center">
                        <div className="col-md-6">
                            <div className="input-group">
                                <span className="input-group-text"><i className="fa-solid fa-search"></i></span>
                                <input 
                                    type="text" 
                                    className="form-control" 
                                    placeholder="Buscar por nombre o correo" 
                                    value={busqueda}
                                    onChange={(e) => setBusqueda(e.target.value)}
                                />
                            </div>
                        </div>
                        <div className="col-md-6 text-end">
                            <button type="submit" className="btn btn-info me-2"><i className="fa-solid fa-search"></i> Buscar</button>
                            <button type="button" className="btn btn-warning" onClick={() => { setBusqueda(''); cargarUsuarios(''); }}>
                                <i className="fa-solid fa-list"></i> Reset
                            </button>
                        </div>
                    </div>
                </form>

                {/* Formulario para agregar usuario */}
                <form onSubmit={guardarUsuario} className="mb-4">
                    <div className="row g-3">
                        <div className="col-md-4">
                            <label className="form-label">Nombre</label>
                            <input 
                                type="text" 
                                className="form-control" 
                                required 
                                value={nuevoUsuario.nombre}
                                onChange={(e) => setNuevoUsuario({...nuevoUsuario, nombre: e.target.value})}
                            />
                        </div>
                        <div className="col-md-4">
                            <label className="form-label">Correo</label>
                            <input 
                                type="email" 
                                className="form-control" 
                                required 
                                value={nuevoUsuario.correo}
                                onChange={(e) => setNuevoUsuario({...nuevoUsuario, correo: e.target.value})}
                            />
                        </div>
                        <div className="col-md-4">
                            <label className="form-label">Contraseña</label>
                            <input 
                                type="password" 
                                className="form-control" 
                                required 
                                value={nuevoUsuario.contrasena}
                                onChange={(e) => setNuevoUsuario({...nuevoUsuario, contrasena: e.target.value})}
                            />
                        </div>
                    </div>
                    <div className="text-end mt-3">
                        <button type="submit" className="btn btn-primary"><i className="fa-solid fa-plus"></i> Guardar</button>
                    </div>
                </form>

                {/* Tabla de usuarios */}
                {usuarios.length > 0 ? (
                    <table className="table table-striped table-hover table-bordered">
                        <thead className="table-primary">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Correo</th>
                                <th>Contraseña</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {usuarios.map((usuario) => (
                                <tr key={usuario.idUsuario}>
                                    <td>{usuario.idUsuario}</td>
                                    <td>{usuario.nombre}</td>
                                    <td>{usuario.correo}</td>
                                    <td>{/* Ocultamos la contraseña o mostramos asteriscos por seguridad */} ****</td>
                                    <td>
                                        <button className="btn btn-success btn-sm me-2">
                                            <i className="fa-solid fa-pen-to-square"></i> Editar
                                        </button>
                                        <button 
                                            onClick={() => eliminarUsuario(usuario.idUsuario)} 
                                            className="btn btn-danger btn-sm"
                                        >
                                            <i className="fa-solid fa-trash"></i> Eliminar
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <div className="alert alert-info text-center mt-3">
                        <i className="fa-solid fa-info-circle"></i>
                        {busqueda ? ` No se encontraron usuarios con el dato "${busqueda}"` : ' No hay usuarios registrados.'}
                    </div>
                )}
            </div>
        </div>
    );
    }