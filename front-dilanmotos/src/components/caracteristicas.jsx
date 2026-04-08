import { useEffect, useState } from "react";

export default function Caracteristicas() {
    const [caracteristicas, setCaracteristicas] = useState([]);
    const [motos, setMotos] = useState([]); // Para el selector de motos
    
    const [nuevo, setNuevo] = useState({ 
        nombre: '', 
        valor: '', 
        id_moto: '' 
    });
    
    const [editMode, setEditMode] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    const API_URL = 'http://localhost:8080/api/caracteristicas';

    const cargarDatos = async () => {
        try {
            const [resCar, resMoto] = await Promise.all([
                fetch(API_URL),
                fetch('http://localhost:8080/api/motos')
            ]);
            
            setCaracteristicas(await resCar.json());
            setMotos(await resMoto.json());
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
                setNuevo({ nombre: '', valor: '', id_moto: '' });
                setEditMode(false);
                setSelectedId(null);
                cargarDatos();
                alert(editMode ? "Característica actualizada" : "Característica registrada");
            }
        } catch (error) { 
            alert("Error de conexión"); 
        }
    };

    const iniciarEdicion = (c) => {
        window.scrollTo(0, 0);
        setEditMode(true);
        setSelectedId(c.id);
        setNuevo({ 
            nombre: c.nombre, 
            valor: c.valor, 
            id_moto: c.id_moto 
        });
    };

    const eliminar = async (id) => {
        if (!id || !window.confirm("¿Eliminar característica?")) return;
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        cargarDatos();
    };

    return (
        <div className="main-content-inner">
            {/* PANEL DE REGISTRO */}
            <div className="card-panel">
                <h3 className="text-primary">{editMode ? 'Editar Característica' : 'Gestión de Características'}</h3>
                <hr />
                <form onSubmit={guardar}>
                    <input 
                        className="input-bs" 
                        placeholder="Nombre (Ej: Cilindraje, Color)" 
                        value={nuevo.nombre} 
                        onChange={e => setNuevo({...nuevo, nombre: e.target.value})} 
                        required 
                    />
                    
                    <input 
                        className="input-bs" 
                        placeholder="Valor (Ej: 250cc, Negro Mate)" 
                        value={nuevo.valor} 
                        onChange={e => setNuevo({...nuevo, valor: e.target.value})} 
                        required 
                    />

                    <select 
                        className="input-bs" 
                        value={nuevo.id_moto} 
                        onChange={e => setNuevo({...nuevo, id_moto: e.target.value})} 
                        required
                    >
                        <option value="">Asignar a Moto</option>
                        {motos?.map(m => (
                            <option key={m.id} value={m.id}>{m.nombre} - {m.modelo}</option>
                        ))}
                    </select>

                    <button type="submit" className={`btn-bs ${editMode ? 'btn-success' : 'btn-primary'} w-100`}>
                        {editMode ? 'Guardar Cambios' : 'Registrar Característica'}
                    </button>
                    
                    {editMode && (
                        <button 
                            type="button" 
                            className="btn-bs btn-danger w-100 mt-2" 
                            onClick={() => {setEditMode(false); setNuevo({nombre:'', valor:'', id_moto:''})}}
                        >
                            Cancelar
                        </button>
                    )}
                </form>
            </div>

            {/* TABLA DE RESULTADOS */}
            <div className="card-panel">
                <div className="custom-table-container">
                    <div className="custom-table-header">
                        <div>ID</div>
                        <div>Característica</div>
                        <div>Valor</div>
                        <div>Moto</div>
                        <div className="text-center">Acciones</div>
                    </div>
                    {caracteristicas.map(c => (
                        <div className="custom-table-row" key={c.id}>
                            <div>#{c.id}</div>
                            <div className="fw-bold">{c.nombre}</div>
                            <div>{c.valor}</div>
                            <div className="text-muted">{c.moto?.nombre || 'N/A'}</div>
                            <div style={{display: 'flex', justifyContent: 'center', gap: '8px'}}>
                                <button className="btn-bs btn-success btn-sm" onClick={() => iniciarEdicion(c)}>
                                    <i className="fa-solid fa-pen"></i>
                                </button>
                                <button className="btn-bs btn-danger btn-sm" onClick={() => eliminar(c.id)}>
                                    <i className="fa-solid fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    ))}
                    {caracteristicas.length === 0 && (
                        <div className="p-4 text-center text-muted">No hay características registradas.</div>
                    )}
                </div>
            </div>
        </div>
    );
}