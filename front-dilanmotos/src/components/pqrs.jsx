import React, { useState, useEffect } from 'react';

const PqrsManager = () => {
    const [listaPqrs, setListaPqrs] = useState([]);
    const [busqueda, setBusqueda] = useState('');
    const [formData, setFormData] = useState({ tipo: 'PETICION', asunto: '', descripcion: '' });
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    useEffect(() => {
        cargarPqrs();
    }, []);

    const cargarPqrs = async (termino = '') => {
        const url = termino ? `http://localhost:8080/api/pqrs?search=${termino}` : 'http://localhost:8080/api/pqrs';
        const res = await fetch(url);
        const data = await res.json();
        setListaPqrs(data);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const url = editMode ? `http://localhost:8080/api/pqrs/${selectedId}` : 'http://localhost:8080/api/pqrs';
        const method = editMode ? 'PUT' : 'POST';

        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });

        if (res.ok) {
            setFormData({ tipo: 'PETICION', asunto: '', descripcion: '' });
            setEditMode(false);
            cargarPqrs();
        }
    };

    const handleEliminar = async (id) => {
        if (window.confirm("¿Eliminar este registro?")) {
            await fetch(`http://localhost:8080/api/pqrs/${id}`, { method: 'DELETE' });
            cargarPqrs();
        }
    };

    const iniciarEdicion = (item) => {
        setEditMode(true);
        setSelectedId(item.idPqrs); // Verifica que el nombre del ID coincida con tu entidad
        setFormData({ tipo: item.tipo, asunto: item.asunto, descripcion: item.descripcion });
    };

    return (
        <div className="container mt-4">
            <h3 className="mb-4"><i className="fa-solid fa-comments"></i> Gestión de PQRS</h3>
            
            {/* Formulario */}
            <div className="card mb-4 shadow-sm">
                <div className="card-body">
                    <form onSubmit={handleSubmit} className="row g-3">
                        <div className="col-md-3">
                            <label className="form-label">Tipo</label>
                            <select className="form-select" value={formData.tipo} 
                                onChange={(e) => setFormData({...formData, tipo: e.target.value})}>
                                <option value="PETICION">Petición</option>
                                <option value="QUEJA">Queja</option>
                                <option value="RECLAMO">Reclamo</option>
                                <option value="SUGERENCIA">Sugerencia</option>
                            </select>
                        </div>
                        <div className="col-md-9">
                            <label className="form-label">Asunto</label>
                            <input type="text" className="form-control" value={formData.asunto} required
                                onChange={(e) => setFormData({...formData, asunto: e.target.value})} />
                        </div>
                        <div className="col-12">
                            <label className="form-label">Descripción</label>
                            <textarea className="form-control" rows="2" value={formData.descripcion} required
                                onChange={(e) => setFormData({...formData, descripcion: e.target.value})}></textarea>
                        </div>
                        <div className="col-12 text-end">
                            {editMode && <button type="button" className="btn btn-secondary me-2" onClick={() => setEditMode(false)}>Cancelar</button>}
                            <button type="submit" className={`btn ${editMode ? 'btn-warning' : 'btn-primary'}`}>
                                {editMode ? 'Actualizar PQRS' : 'Enviar PQRS'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            {/* Buscador */}
            <div className="input-group mb-3">
                <input type="text" className="form-control" placeholder="Buscar por tipo o asunto..." 
                    value={busqueda} onChange={(e) => setBusqueda(e.target.value)} />
                <button className="btn btn-outline-secondary" onClick={() => cargarPqrs(busqueda)}>Buscar</button>
            </div>

            {/* Tabla */}
            <table className="table table-hover align-middle">
                <thead className="table-light">
                    <tr>
                        <th>Fecha</th>
                        <th>Tipo</th>
                        <th>Asunto</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {listaPqrs.map((item) => (
                        <tr key={item.idPqrs}>
                            <td>{new Date(item.fecha).toLocaleDateString()}</td>
                            <td><span className="badge bg-info text-dark">{item.tipo}</span></td>
                            <td>{item.asunto}</td>
                            <td>
                                <span className={`badge ${item.estado === 'PENDIENTE' ? 'bg-warning' : 'bg-success'}`}>
                                    {item.estado}
                                </span>
                            </td>
                            <td>
                                <button className="btn btn-sm btn-outline-success me-2" onClick={() => iniciarEdicion(item)}>
                                    <i className="fa-solid fa-edit"></i>
                                </button>
                                <button className="btn btn-sm btn-outline-danger" onClick={() => handleEliminar(item.idPqrs)}>
                                    <i className="fa-solid fa-trash"></i>
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default PqrsManager;