import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../auth.css'; // 👈 Esta ruta debe ser idéntica a la del Registro

const Login = () => {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({ correo: '', contrasena: '' });

    const handleLogin = (e) => {
        e.preventDefault();
        
        // Simulación de validación
        if (credentials.correo && credentials.contrasena) {
            localStorage.setItem('isAuthenticated', 'true');
            navigate('/usuarios'); // Te manda al dashboard
        } else {
            alert("Por favor, llena todos los campos");
        }
    };

    return (
        <div className="auth-body">
            <div className="auth-card">
                <h2>Dilan Motos</h2>
                <p style={{ textAlign: 'center', color: '#666', marginBottom: '20px' }}>
                    Inicia sesión para gestionar el sistema
                </p>
                
                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>Correo Electrónico</label>
                        <input 
                            className="auth-input" 
                            type="email" 
                            placeholder="ejemplo@correo.com"
                            required 
                            onChange={(e) => setCredentials({...credentials, correo: e.target.value})} 
                        />
                    </div>

                    <div className="form-group">
                        <label>Contraseña</label>
                        <input 
                            className="auth-input" 
                            type="password" 
                            placeholder="********"
                            required 
                            onChange={(e) => setCredentials({...credentials, contrasena: e.target.value})} 
                        />
                    </div>

                    <button type="submit" className="auth-btn-primary">
                        Entrar al Sistema
                    </button>
                    
                    <button 
                        type="button" 
                        className="auth-btn-cancel" 
                        onClick={() => navigate('/register')}
                    >
                        ¿No tienes cuenta? Regístrate
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;