import { useEffect, useState } from "react";

export default function Productos() {
    const [productos, setProductos] = useState([]);
    const [marcas, setMarcas] = useState([]);
    const [categorias, setCategorias] = useState([]);
    
    const [nuevo, setNuevo] = useState({ 
        nombre: '', descripcion: '', precio: '', idMarca: '', idCategoria: '' 
    });

    const cargarDatos = async () => {
        try {
            // Usamos try/catch individual para que un 404 no rompa todo
            const fetchJson = (url) => fetch(url).then(r => r.ok ? r.json() : []);
            
            const dataP = await fetchJson('http://localhost:8080/api/productos');
            const dataM = await fetchJson('http://localhost:8080/api/marcas');
            const dataC = await fetchJson('http://localhost:8080/api/categorias');

            setProductos(dataP);
            setMarcas(dataM);
            setCategorias(dataC);
        } catch (e) { console.error("Error cargando productos:", e); }
    };

    useEffect(() => { cargarDatos(); }, []);

    const guardar = async (e) => {
        e.preventDefault();
        const payload = {
            nombre: nuevo.nombre,
            descripcion: nuevo.descripcion,
            precio: parseFloat(nuevo.precio),
            marca: { idMarca: parseInt(nuevo.idMarca) },
            categoria: { idCategoria: parseInt(nuevo.idCategoria) }
        };

        const res = await fetch('http://localhost:8080/api/productos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            setNuevo({ nombre: '', descripcion: '', precio: '', idMarca: '', idCategoria: '' });
            cargarDatos();
            alert("Producto guardado");
        }
    };

    return (
        <div className="main-content-inner">
            <div className="card-panel">
                <h3 className="text-primary">📦 Gestión de Productos</h3>
                <form onSubmit={guardar}>
                    <input className="input-bs" placeholder="Nombre Producto" value={nuevo.nombre} onChange={e => setNuevo({...nuevo, nombre: e.target.value})} required />
                    <textarea className="input-bs" placeholder="Descripción" value={nuevo.descripcion} onChange={e => setNuevo({...nuevo, descripcion: e.target.value})} required />
                    <input className="input-bs" type="number" placeholder="Precio" value={nuevo.precio} onChange={e => setNuevo({...nuevo, precio: e.target.value})} required />
                    
                    <div className="row">
                        <div className="col-md-6">
                            <select className="input-bs" value={nuevo.idMarca} onChange={e => setNuevo({...nuevo, idMarca: e.target.value})} required>
                                <option value="">Marca...</option>
                                {marcas.map(m => <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>)}
                            </select>
                        </div>
                        <div className="col-md-6">
                            <select className="input-bs" value={nuevo.idCategoria} onChange={e => setNuevo({...nuevo, idCategoria: e.target.value})} required>
                                <option value="">Categoría...</option>
                                {categorias.map(c => <option key={c.idCategoria} value={c.idCategoria}>{c.nombre}</option>)}
                            </select>
                        </div>
                    </div>
                    <button type="submit" className="btn-bs btn-primary w-100 mt-2">Registrar Producto</button>
                </form>
            </div>

            <div className="card-panel mt-4">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>Producto</div><div>Marca</div><div>Precio</div><div className="text-center">Acciones</div>
                    </div>
                    {productos.map(p => (
                        <div className="custom-table-row" key={p.idProducto}>
                            <div className="fw-bold">{p.nombre}</div>
                            <div>{p.marca?.nombre || 'S/M'}</div>
                            <div className="text-success fw-bold">${p.precio}</div>
                            <div className="text-center">
                                <button className="btn-bs btn-danger btn-sm" onClick={() => {/* eliminar */}}>
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