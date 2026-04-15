import React, { useState, useEffect } from 'react';
import '../auth.css';

const Register = () => {
    const [marcas, setMarcas] = useState([]);
    const [modelos, setModelos] = useState([]);
    const [formData, setFormData] = useState({
        nombre: '',
        correo: '',
        contrasena: '',
        idMarca: '',
        modelo: '',
        cilindraje: ''
    });

    // Cargar marcas al inicio
    useEffect(() => {
        fetch("http://localhost:8080/api/marcas")
            .then(res => res.json())
            .then(data => setMarcas(data))
            .catch(err => console.error("Error al traer marcas:", err));
    }, []);

    // Cargar modelos cuando cambie la marca
    useEffect(() => {
        if (formData.idMarca) {
            fetch(`http://localhost:8080/api/motos/marca/${formData.idMarca}`)
                .then(res => {
                    if (!res.ok) throw new Error("Error 404");
                    return res.json();
                })
                .then(data => {
                    if (Array.isArray(data)) {
                        const listaUnica = [...new Set(data.map(m => m.modelo))];
                        setModelos(listaUnica);
                    }
                })
                .catch(err => {
                    console.log("No hay modelos para esta marca aún");
                    setModelos([]);
                });
        }
    }, [formData.idMarca]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value,
            ...(name === 'idMarca' && { modelo: '' }) // Resetear modelo si cambia marca
        }));
    };

    return (
        <div className="auth-body">
            <div className="auth-card">
                <h2>Registro de Usuario</h2>
                <form>
                    <div className="form-group">
                        <label>Nombre Completo</label>
                        <input className="auth-input" type="text" name="nombre" onChange={handleChange} />
                    </div>
                    <div className="form-group">
                        <label>Correo Electrónico</label>
                        <input className="auth-input" type="email" name="correo" onChange={handleChange} />
                    </div>
                    
                    <hr style={{margin: '20px 0', opacity: 0.2}} />
                    <p style={{fontWeight: 'bold'}}>Datos de la Máquina</p>

                    <div className="form-group">
                        <label>Marca</label>
                        <select className="auth-input" name="idMarca" value={formData.idMarca} onChange={handleChange}>
                            <option value="">Seleccione una marca</option>
                            {marcas.map(m => (
                                <option key={m.idMarca} value={m.idMarca}>{m.nombre}</option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label>Modelo</label>
                        <select className="auth-input" name="modelo" value={formData.modelo} onChange={handleChange} disabled={!formData.idMarca}>
                            <option value="">Seleccione un modelo</option>
                            {modelos.map(mod => (
                                <option key={mod} value={mod}>{mod}</option>
                            ))}
                        </select>
                    </div>

                    <button type="submit" className="auth-btn-primary">Registrarme</button>
                </form>
            </div>
        </div>
    );
};

export default Register;