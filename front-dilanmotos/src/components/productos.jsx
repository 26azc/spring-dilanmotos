import { useEffect, useState } from "react";

export default function Productos() {
    const [productos, setProductos] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [marcas, setMarcas] = useState([]);
    
    const [nuevo, setNuevo] = useState({ 
        nombre: '', 
        descripcion: '', 
        precio: '', 
        id_categoria: '', 
        id_marca: '' 
    });
    
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const API_URL = 'http://localhost:8080/api/productos';

    const cargarDatos = async () => {
        try {
            const [resProd, resCat, resMar] = await Promise.all([
                fetch(API_URL),
                fetch('http://localhost:8080/api/categorias'),
                fetch('http://localhost:8080/api/marcas')
            ]);
            
            setProductos(await resProd.json());
            setCategorias(await resCat.json());
            setMarcas(await resMar.json());
        } catch (e) { 
            console.error("Error cargando datos:", e); 
        }
    };

    useEffect(() => { cargarDatos(); }, []);

    const guardar = async (e) => {
        e.preventDefault();
        const url = editMode ? `${API_URL}/${selectedId}` : API_URL;
        const method = editMode ? 'PUT' : 'POST';

        try {
            const res = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevo)
            });
            
            if (res.ok) {
                setNuevo({ nombre: '', descripcion: '', precio: '', id_categoria: '', id_marca: '' });
                setEditMode(false);
                setSelectedId(null);
                cargarDatos();
                alert(editMode ? "Producto actualizado" : "Producto registrado");
            }
        } catch (error) { 
            alert("Error de conexión"); 
        }
    };

    const iniciarEdicion = (p) => {
        window.scrollTo(0, 0);
        setEditMode(true);
        setSelectedId(p.id);
        setNuevo({ 
            nombre: p.nombre, 
            descripcion: p.descripcion, 
            precio: p.precio, 
            id_categoria: p.id_categoria, 
            id_marca: p.id_marca 
        });
    };

    const eliminar = async (id) => {
        if (!id || !window.confirm("¿Eliminar producto?")) return;
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        cargarDatos();
    };

    return (
        <div className="main-content-inner">
            {/* PANEL DE REGISTRO */}
            <div className="card-panel">
                <h3 className="text-primary">{editMode ? 'Editar Producto' : 'Gestión de Productos'}</h3>
                <hr />
                <form onSubmit={guardar}>
                    <input className="input-bs" placeholder="Nombre del producto" value={nuevo.nombre} onChange={e => setNuevo({...nuevo, nombre: e.target.value})} required />
                    
                    <input className="input-bs" type="number" placeholder="Precio" value={nuevo.precio} onChange={e => setNuevo({...nuevo, precio: e.target.value})} required />
                    
                    <textarea className="input-bs" placeholder="Descripción" value={nuevo.descripcion} onChange={e => setNuevo({...nuevo, descripcion: e.target.value})} rows="2" />

                    <select className="input-bs" value={nuevo.id_categoria} onChange={e => setNuevo({...nuevo, id_categoria: e.target.value})} required>
                        <option value="">Seleccionar Categoría</option>
                        {categorias?.map(c => <option key={c.id} value={c.id}>{c.nombre}</option>)}
                    </select>

                    <select className="input-bs" value={nuevo.id_marca} onChange={e => setNuevo({...nuevo, id_marca: e.target.value})} required>
                        <option value="">Seleccionar Marca</option>
                        {marcas?.map(m => <option key={m.id} value={m.id}>{m.nombre}</option>)}
                    </select>

                    <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100`}>
                        {editMode ? 'Guardar Cambios' : 'Registrar Producto'}
                    </button>
                    
                    {editMode && (
                        <button type="button" className="btn-bs btn-danger w-100 mt-2" onClick={() => {setEditMode(false); setNuevo({nombre:'', descripcion:'', precio:'', id_categoria:'', id_marca:''})}}>
                            Cancelar
                        </button>
                    )}
                </form>
            </div>

            {/* TABLA DE RESULTADOS  */}
            <div className="card-panel">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>ID</div>
                        <div>Producto</div>
                        <div>Precio</div>
                        <div>Marca</div>
                        <div className="text-center">Acciones</div>
                    </div>
                    {productos.map(p => (
                        <div className="custom-table-row" key={p.id}>
                            <div>#{p.id}</div>
                            <div>{p.nombre}</div>
                            <div className="text-success fw-bold">${p.precio}</div>
                            <div>{p.marca?.nombre || 'N/A'}</div>
                            <div style={{display: 'flex', justifyContent: 'center', gap: '8px'}}>
                                <button className="btn-bs btn-success btn-sm" onClick={() => iniciarEdicion(p)}>
                                    <i className="fa-solid fa-pen"></i>
                                </button>
                                <button className="btn-bs btn-danger btn-sm" onClick={() => eliminar(p.id)}>
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