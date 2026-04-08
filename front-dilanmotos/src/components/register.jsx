import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "../auth.css";

const Register = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nombre: '', correo: '', contrasena: '',
        marca: '', modelo: '', cilindraje: '', tipoReparacion: ''
    });

    const handleRegister = (e) => {
        e.preventDefault();
        console.log("Datos registrados:", formData);
        alert("Usuario registrado con éxito");
        navigate('/login');
    };

    return (
        <div className="auth-body">
            <div className="auth-card">
                <h2>Registro de Usuario</h2>
                <form onSubmit={handleRegister}>
                    <div className="form-group">
                        <label>Nombre</label>
                        <input className="auth-input" type="text" required onChange={(e) => setFormData({...formData, nombre: e.target.value})} />
                    </div>
                    <div className="form-group">
                        <label>Correo</label>
                        <input className="auth-input" type="email" required onChange={(e) => setFormData({...formData, correo: e.target.value})} />
                    </div>
                    <div className="form-group">
                        <label>Contraseña</label>
                        <input className="auth-input" type="password" required onChange={(e) => setFormData({...formData, contrasena: e.target.value})} />
                    </div>

                    <h4>Datos de tu moto</h4>
                    <div className="form-group">
                        <label>Marca</label>
                        <select className="auth-input" onChange={(e) => setFormData({...formData, marca: e.target.value})}>
                            <option value="">Seleccione una marca</option>
                            <option value="Yamaha">Yamaha</option>
                            <option value="Suzuki">Suzuki</option>
                            <option value="Honda">Honda</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Modelo</label>
                        <input className="auth-input" type="text" placeholder="Ej: 2024" onChange={(e) => setFormData({...formData, modelo: e.target.value})} />
                    </div>
                    <div className="form-group">
                        <label>Cilindraje</label>
                        <input className="auth-input" type="text" placeholder="Ej: 150cc" onChange={(e) => setFormData({...formData, cilindraje: e.target.value})} />
                    </div>
                    <div className="form-group">
                        <label>Tipo de reparación</label>
                        <select className="auth-input" onChange={(e) => setFormData({...formData, tipoReparacion: e.target.value})}>
                            <option value="">Seleccione un servicio</option>
                            <option value="Mantenimiento General">Mantenimiento General</option>
                            <option value="Cambio de Aceite">Cambio de Aceite</option>
                            <option value="Frenos">Frenos</option>
                        </select>
                    </div>

                    <button type="submit" className="auth-btn-primary">Registrarse</button>
                    <button type="button" className="auth-btn-cancel" onClick={() => navigate('/login')}>Cancelar</button>
                </form>
            </div>
        </div>
    );
};

export default Register;