import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, Link, useLocation } from 'react-router-dom';

// Importa tus componentes
import Login from './components/login';
import Register from './components/register'; 
import Usuarios from './components/usuarios';
import PqrsManager from './components/pqrs';

// Estilos
import './global.css';

const PrivateRoute = ({ children }) => {
    const auth = localStorage.getItem('isAuthenticated');
    return auth === 'true' ? children : <Navigate to="/login" />;
};

const Sidebar = () => {
    const location = useLocation();
    const handleLogout = () => {
        localStorage.removeItem('isAuthenticated');
        window.location.href = '/login';
    };

    return (
        <aside className="sidebar">
            <div className="sidebar-header">Dilan Motos</div>
            <nav className="sidebar-nav">
                <Link to="/usuarios" className={`nav-link ${location.pathname === '/usuarios' ? 'active' : ''}`}>
                    <i className="fa-solid fa-users me-2"></i> Usuarios
                </Link>
                <Link to="/pqrs" className={`nav-link ${location.pathname === '/pqrs' ? 'active' : ''}`}>
                    <i className="fa-solid fa-comments me-2"></i> PQRS
                </Link>
            </nav>
            <div className="sidebar-footer">
                <button onClick={handleLogout} className="btn-bs btn-danger w-100">
                    <i className="fa-solid fa-right-from-bracket me-2"></i> Cerrar Sesión
                </button>
            </div>
        </aside>
    );
};

function App() {
    return (
        <Router>
            <Routes>
                {/* 1. Redirección inicial: Si entran a "/" los manda a "/login" */}
                <Route path="/" element={<Navigate to="/login" />} />

                {/* 2. Rutas Públicas */}
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />

                {/* 3. Rutas Privadas Protegidas */}
                <Route path="/*" element={
                    <PrivateRoute>
                        <div className="app-container">
                            <Sidebar />
                            <main className="main-content">
                                <Routes>
                                    <Route path="/usuarios" element={<Usuarios />} />
                                    <Route path="/pqrs" element={<PqrsManager />} />
                                    {/* Si alguien pone una ruta mal dentro del panel, vuelve a usuarios */}
                                    <Route path="*" element={<Navigate to="/usuarios" />} />
                                </Routes>
                            </main>
                        </div>
                    </PrivateRoute>
                } />
            </Routes>
        </Router>
    );
}

export default App;