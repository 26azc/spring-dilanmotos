import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../auth.css';

const Register = () => {
    const navigate = useNavigate();
    const [marcas, setMarcas] = useState([]);
    const [referencias, setReferencias] = useState([]);
    const [formData, setFormData] = useState({
        nombre: '', correo: '', contrasena: '', idReferencia: ''
    });

    useEffect(() => {
        fetch("http://localhost:8080/api/marcas")
            .then(res => res.json())
            .then(setMarcas);
    }, []);

    const handleMarcaChange = (e) => {
        const idMarca = e.target.value;
        setReferencias([]);
        setFormData({ ...formData, idReferencia: '' });

        if (idMarca) {
            fetch(`http://localhost:8080/api/referencias?marcaId=${idMarca}`)
                .then(res => res.json())
                .then(data => {
                    console.log("Catálogo cargado:", data); // Para debug
                    setReferencias(data);
                    const validos = data.filter(ref => ref.nombre && ref.nombre !== '');
                    setReferencias(validos);

                });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const res = await fetch("http://localhost:8080/api/usuarios/registrar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        });

        if (res.ok) {
            alert("¡Bienvenido a Dilan Motos!");
            navigate("/login");
        } else {
            alert("Hubo un problema al registrarte.");
        }
    };

    return (
        <div className="auth-body">
            <div className="auth-card">
                <h2>Crea tu cuenta</h2>
                <form onSubmit={handleSubmit}>
                    <input className="auth-input" type="text" placeholder="Tu Nombre" onChange={e => setFormData({...formData, nombre: e.target.value})} required />
                    <input className="auth-input" type="email" placeholder="Correo" onChange={e => setFormData({...formData, correo: e.target.value})} required />
                    <input className="auth-input" type="password" placeholder="Contraseña" onChange={e => setFormData({...formData, contrasena: e.target.value})} required />

                    <label>Marca de tu moto</label>
                    <select className="auth-input" onChange={handleMarcaChange} required>
                        <option value="">-- Elige Marca --</option>
                        {marcas.map(m => <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>)}
                    </select>

                    <label>Modelo (de nuestro catálogo)</label>
                    <select 
                        className="auth-input" 
                        value={formData.idReferencia}
                        onChange={e => setFormData({...formData, idReferencia: e.target.value})}
                        disabled={referencias.length === 0}
                        required
                    >
                        <option value="">-- Elige el modelo --</option>
                        {referencias.map(ref => (
                            <option key={ref.idReferencia} value={ref.idReferencia}>
                                {ref.nombre} ({ref.cilindraje} cc)
                            </option>
                        ))}
                    </select>

                    <button type="submit" className="auth-btn-primary">Registrarme</button>
                </form>
            </div>
        </div>
    );
};

export default Register;