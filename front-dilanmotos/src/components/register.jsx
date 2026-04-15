import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../auth.css';

const Register = () => {
    const navigate = useNavigate();
    const [marcas, setMarcas] = useState([]);
    const [modelosFiltrados, setModelosFiltrados] = useState([]);
    const [formData, setFormData] = useState({
        nombre: '',
        correo: '',
        contrasena: '',
        idMarca: '',
        modelo: ''
    });

    useEffect(() => {
        fetch("http://localhost:8080/api/marcas").then(res => res.json()).then(setMarcas);
    }, []);

    const handleMarcaChange = (e) => {
        const id = e.target.value;
        setFormData({...formData, idMarca: id, modelo: ''});
        // Cargar modelos de esa marca para el segundo select
        fetch(`http://localhost:8080/api/motos/marca/${id}`)
            .then(res => res.json())
            .then(data => setModelosFiltrados([...new Set(data.map(m => m.modelo))]));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const res = await fetch("http://localhost:8080/api/usuarios/registrar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        });

        if (res.ok) {
            alert("¡Cuenta y moto registradas!");
            navigate("/login");
        }
    };

    return (
        <div className="auth-body">
            <div className="auth-card">
                <h2>Únete a Dilan Motos</h2>
                <form onSubmit={handleSubmit}>
                    <input className="auth-input" type="text" placeholder="Nombre" onChange={e => setFormData({...formData, nombre: e.target.value})} required />
                    <input className="auth-input" type="email" placeholder="Correo" onChange={e => setFormData({...formData, correo: e.target.value})} required />
                    <input className="auth-input" type="password" placeholder="Contraseña" onChange={e => setFormData({...formData, contrasena: e.target.value})} required />
                    
                    <label>Tu Marca</label>
                    <select className="auth-input" onChange={handleMarcaChange} required>
                        <option value="">Selecciona marca</option>
                        {marcas.map(m => <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>)}
                    </select>

                    <label>Tu Modelo</label>
                    <select className="auth-input" onChange={e => setFormData({...formData, modelo: e.target.value})} disabled={!formData.idMarca} required>
                        <option value="">Selecciona modelo</option>
                        {modelosFiltrados.map(mod => <option key={mod} value={mod}>{mod}</option>)}
                    </select>

                    <button type="submit" className="auth-btn-primary">Crear Cuenta</button>
                </form>
            </div>
        </div>
    );
};

export default Register;