import React, { useState, useEffect } from 'react';

const Referencia = () => {
    const [marcas, setMarcas] = useState([]);
    const [todasLasReferencias, setTodasLasReferencias] = useState([]);
    const [filtroMarca, setFiltroMarca] = useState(''); // 👈 Estado para el select de filtro
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);
    const [formData, setFormData] = useState({
        nombre: '', 
        cilindraje: '', 
        idMarca: ''
    });

    const cargarTodo = async () => {
        try {
            const [resMarcas, resRefs] = await Promise.all([
                fetch("http://localhost:8080/api/marcas"),
                fetch("http://localhost:8080/api/referencias")
            ]);
            setMarcas(await resMarcas.json());
            setTodasLasReferencias(await resRefs.json());
        } catch (error) {
            console.error("Error cargando datos:", error);
        }
    };

    useEffect(() => { cargarTodo(); }, []);

    // 🎯 Lógica del Filtro por Select
    const referenciasFiltradas = filtroMarca === '' 
        ? todasLasReferencias 
        : todasLasReferencias.filter(ref => ref.marca?.idMarca === parseInt(filtroMarca));

    const prepararEdicion = (ref) => {
        setEditMode(true);
        setSelectedId(ref.idReferencia);
        setFormData({
            nombre: ref.nombre,
            cilindraje: ref.cilindraje,
            idMarca: ref.marca?.idMarca || ''
        });
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    const handleSubmit = async (e) => {
    e.preventDefault();


        const url = editMode 
        ? `http://localhost:8080/api/referencias/${selectedId}` 
        : "http://localhost:8080/api/referencias";
        
        const payload = {
            nombre: formData.nombre,
            cilindraje: parseFloat(formData.cilindraje),
            marca: { idMarca: parseInt(formData.idMarca) }
        };

        const eliminarReferencia = async (id) => {
        if(!id) return; 

        if(window.confirm("¿Seguro que quieres borrar este modelo?")) {
        const res = await fetch(`http://localhost:8080/api/referencias/${id}`, {
            method: 'DELETE'
        });

        if (res.ok) {
            alert("Eliminado");
            cargarTodo(); 
        } else {
            alert("Error al eliminar: " + res.status);
        }
    }
};

        const res = await fetch(url, {
            method: editMode ? "PUT" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert(editMode ? "✅ Modelo actualizado" : "✅ Modelo agregado al catálogo");
            setFormData({ nombre: '', cilindraje: '', idMarca: '' });
            setEditMode(false);
            cargarTodo();
        }
    };

    return (
        <div className="main-content-inner">
            {/* FORMULARIO */}
            <div className="card-panel">
                <h3 className="text-primary mb-4">
                    {editMode ? '✏️ Editar Modelo' : '⚙️ Nueva Referencia'}
                </h3>
                <form onSubmit={handleSubmit} className="row">
                    <div className="col-md-4 mb-3">
                        <label className="form-label">Marca</label>
                        <select className="input-bs" value={formData.idMarca} onChange={e => setFormData({...formData, idMarca: e.target.value})} required>
                            <option value="">Seleccione Marca</option>
                            {marcas.map(m => <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>)}
                        </select>
                    </div>
                    <div className="col-md-4 mb-3">
                        <label className="form-label">Nombre del Modelo</label>
                        <input className="input-bs" type="text" value={formData.nombre} onChange={e => setFormData({...formData, nombre: e.target.value})} required />
                    </div>
                    <div className="col-md-4 mb-3">
                        <label className="form-label">Cilindraje</label>
                        <input className="input-bs" type="number" value={formData.cilindraje} onChange={e => setFormData({...formData, cilindraje: e.target.value})} required />
                    </div>
                    <div className="col-12 mt-2 d-flex gap-2">
                        <button className={`btn-bs w-100 ${editMode ? 'btn-warning' : 'btn-primary'}`} type="submit">
                            {editMode ? 'Actualizar Cambios' : 'Guardar en Catálogo'}
                        </button>
                        {editMode && (
                            <button className="btn-bs btn-secondary" onClick={() => {
                                setEditMode(false); 
                                setFormData({nombre:'', cilindraje:'', idMarca:''})
                            }}>Cancelar</button>
                        )}
                    </div>
                </form>
            </div>

            {/* TABLA CON FILTRO SELECT */}
            <div className="card-panel mt-4">
                <div className="row align-items-center mb-3">
                    <div className="col-md-6">
                        <h4 className="text-muted m-0">📚 Modelos Registrados</h4>
                    </div>
                    <div className="col-md-6 d-flex align-items-center gap-2">
                        <span className="text-nowrap fw-bold">Filtrar por:</span>
                        {/* 🔎 SELECT DE FILTRO */}
                        <select 
                            className="input-bs" 
                            value={filtroMarca} 
                            onChange={(e) => setFiltroMarca(e.target.value)}
                        >
                            <option value="">-- Ver todas las marcas --</option>
                            {marcas.map(m => <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>)}
                        </select>
                    </div>
                </div>

                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Marca</div><div>Modelo</div><div>Cilindraje</div><div className="text-center">Acciones</div>
                    </div>
                    {referenciasFiltradas.length > 0 ? (
                        referenciasFiltradas.map(ref => (
                            <div className="custom-table-row" key={ref.idReferencia}>
                                <div className="fw-bold">{ref.marca?.nombre}</div>
                                <div>{ref.nombre}</div>
                                <div>{ref.cilindraje} cc</div>
                                <div className="text-center d-flex justify-content-center gap-2">
                                    <button className="btn-bs btn-warning btn-sm" onClick={() => prepararEdicion(ref)}>
                                        <i className="fa-solid fa-pen"></i>
                                    </button>
                                    <button className="btn-bs btn-danger btn-sm" onClick={async () => {
                                        if(window.confirm("¿Eliminar del catálogo?")) {
                                            await fetch(`http://localhost:8080/api/referencias/${ref.idReferencia}`, {method:'DELETE'});
                                            cargarTodo();
                                        }
                                    }}>
                                        <i className="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="p-4 text-center text-muted">No hay modelos para esta marca.</div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Referencia;