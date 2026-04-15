import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../auth.css';

const Login = () => {
    const navigate = useNavigate();
    const [credenciales, setCredenciales] = useState({
        correo: '',
        contrasena: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCredenciales(prev => ({ ...prev, [name]: value }));
    };

    const handleLogin = async (e) => {
        e.preventDefault(); 
        
        try {
            const response = await fetch("http://localhost:8080/api/usuarios/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(credenciales)
            });

            if (response.ok) {
                const usuario = await response.json();
                
                // 🔑 Llavés de acceso para PrivateRoute y el Asistente
                localStorage.setItem('isAuthenticated', 'true'); 
                localStorage.setItem("idUsuario", usuario.idUsuario);
                localStorage.setItem("nombreUsuario", usuario.nombre);
                
                // Redirección forzada para refrescar el estado de las rutas
                window.location.href = "/asistente"; 
            } else {
                alert("Correo o contraseña incorrectos, parcero.");
            }
        } catch (error) {
            alert("Error de conexión. ¿IntelliJ está corriendo?");
        }
    };

    return (
        <div className="auth-body">
            <div className="auth-card">
                <h2>Dilan Motos</h2>
                <p style={{ textAlign: 'center', color: '#666' }}>Inicia sesión para ir al taller</p>
                
                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>Correo Electrónico</label>
                        <input 
                            className="auth-input"
                            type="email" 
                            name="correo" 
                            value={credenciales.correo}
                            onChange={handleChange}
                            required 
                        />
                    </div>

                    <div className="form-group">
                        <label>Contraseña</label>
                        <input 
                            className="auth-input"
                            type="password" 
                            name="contrasena" 
                            value={credenciales.contrasena}
                            onChange={handleChange}
                            required 
                        />
                    </div>

                    <button type="submit" className="auth-btn-primary">
                        Entrar al Sistema
                    </button>
                    
                    <div style={{ marginTop: '20px', textAlign: 'center', fontSize: '0.85rem' }}>
                        ¿No tienes cuenta? 
                        <span onClick={() => navigate("/register")} style={{ color: '#3b46d8', cursor: 'pointer', fontWeight: 'bold' }}>
                            Regístrate aquí
                        </span>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;