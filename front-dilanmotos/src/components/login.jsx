import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../auth.css';

const Login = () => {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({ correo: '', contrasena: '' });
    const [loading, setLoading] = useState(false);

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        
        try {
            // 💡 Paso 1: Hacemos la petición real al endpoint de login
            const response = await fetch('http://localhost:8080/api/usuarios/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(credentials)
            });

            if (response.ok) {
                const usuarioLogueado = await response.json();
                
                // 💡 Paso 2: Guardamos la información clave en el localStorage
                localStorage.setItem('isAuthenticated', 'true');
                localStorage.setItem('idUsuario', usuarioLogueado.idUsuario); // Guardamos su ID REAL
                localStorage.setItem('nombreUsuario', usuarioLogueado.nombre); // Opcional: para mostrar saludo
                
                alert(`¡Bienvenido, ${usuarioLogueado.nombre}!`);
                navigate('/usuarios'); 
            } else {
                alert("Correo o contraseña incorrectos");
            }
        } catch (error) {
            console.error("Error al conectar con el servidor:", error);
            alert("No se pudo conectar con el servidor. ¿Está prendido IntelliJ?");
        } finally {
            setLoading(false);
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
                            value={credentials.correo}
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
                            value={credentials.contrasena}
                            onChange={(e) => setCredentials({...credentials, contrasena: e.target.value})} 
                        />
                    </div>

                    <button type="submit" className="auth-btn-primary" disabled={loading}>
                        {loading ? 'Validando...' : 'Entrar al Sistema'}
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