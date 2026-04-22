import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, Link, useLocation } from 'react-router-dom';

// ---  IMPORTACIÓN DE COMPONENTES ---
import Login from './components/login';
import Register from './components/register'; 
import Dashboard from './components/dashboard';
import PerfilUsuario from './components/perfilUsuario';
import AsistenteMotos from './components/IA';
import Recomendaciones from './components/RecomendacionesPanel';
import CrearPqrs from './components/CrearPqrs'; 
import ServicioAdmin from './components/Servicio';
import CatalogoKit from './components/catalogoKit';
import CatalogoAceites from './components/catalogoAceites';
import CatalogoLlantas from './components/catalogoLlantas';

// Componentes de Gestión (ADMIN)
import Usuarios from './components/usuarios';
import Referencia from './components/referencia';
import Motos from './components/moto';
import Productos from './components/productos';
import Caracteristicas from './components/caracteristicas';
import TipoServicio from './components/tipoServicio';
import PqrsManager from './components/pqrs';
import Historial from './components/Historial';
import Mecanico from './components/mecanico';

import './global.css';

// ---  PROTECCIÓN POR ROL ---

const PrivateRoute = ({ children, requireAdmin = false }) => {
    const auth = localStorage.getItem('isAuthenticated');
    const rol = localStorage.getItem('rolUsuario');

    if (auth !== 'true') return <Navigate to="/login" />;
    if (requireAdmin && rol !== 'ADMIN') return <Navigate to="/dashboard" />;

    return children;
};

// ---  LAYOUT ADMIN (Sidebar) ---
// Este se aplica solo a las rutas de gestión para no ensuciar la vista del socio
const AdminLayout = ({ children }) => {
    const location = useLocation();
    const handleLogout = () => { 
        localStorage.clear(); 
        window.location.href = '/login'; 
    };

    const activeClass = (path) => location.pathname === path ? 'active' : '';

    return (
        <div className="app-container">
            <aside className="sidebar">
                <div className="sidebar-header">🛠️ PANEL CONTROL</div>
                <nav className="sidebar-nav">
                    <Link to="/dashboard" className="nav-link">
                        <i className="fa-solid fa-house me-2"></i> Inicio Usuario
                    </Link>
                    <hr style={{opacity: 0.1, margin: '15px 0'}}/>
                    
                    <Link to="/usuarios" className={`nav-link ${activeClass('/usuarios')}`}>
                        <i className="fa-solid fa-users me-2"></i> Usuarios
                    </Link>
                    <Link to="/motos" className={`nav-link ${activeClass('/motos')}`}>
                        <i className="fa-solid fa-motorcycle me-2"></i> Motos
                    </Link>
                    <Link to="/referencias" className={`nav-link ${activeClass('/referencias')}`}>
                        <i className="fa-solid fa-tags me-2"></i> Referencias
                    </Link>
                    <Link to="/productos" className={`nav-link ${activeClass('/productos')}`}>
                        <i className="fa-solid fa-box me-2"></i> Productos
                    </Link>

                    <Link to="/servicios" className={`nav-link ${activeClass('/servicios')}`}>
                        <i className="fa-solid fa-wrench me-2"></i> Servicios
                    </Link>

                    <Link to="/caracteristicas" className={`nav-link ${activeClass('/caracteristicas')}`}> 
                        <i className="fa-solid fa-list me-2"></i> Características
                    </Link>
                    <Link to="/tipo-servicio" className={`nav-link ${activeClass('/tipo-servicio')}`}>
                        <i className="fa-solid fa-gear me-2"></i> Tipos de Servicio
                    </Link>
                    <Link to="/mecanico" className={`nav-link ${activeClass('/mecanico')}`}>
                        <i className="fa-solid fa-gear me-2"></i> Mecanicos
                    </Link>

                    <Link to="/pqrs" className={`nav-link ${activeClass('/pqrs')}`}>
                        <i className="fa-solid fa-comments me-2"></i> PQRS
                    </Link>
                </nav>
                <div className="sidebar-footer">
                    <button onClick={handleLogout} className="btn-bs btn-danger w-100">
                        <i className="fa-solid fa-power-off me-2"></i> Salir
                    </button>
                </div>
            </aside>
            <main className="main-content">
                {children}
            </main>
        </div>
    );
};

// ---  COMPONENTE PRINCIPAL ---
function App() {
    return (
        <Router>
            <Routes>
                {/* RUTAS PÚBLICAS */}
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/catalogoKit" element={<CatalogoKit />} />
                <Route path="/catalogoAceites" element={<CatalogoAceites />} />
                <Route path="/catalogoLlantas" element={<CatalogoLlantas />} />

                {/* RUTAS DEL SOCIO (Vistas limpias, sin sidebar) */}
                <Route path="/dashboard" element={
                    <PrivateRoute><Dashboard /></PrivateRoute>
                } />
                <Route path="/historial" element={<PrivateRoute><Historial /></PrivateRoute>} />
                <Route path="/nueva-pqrs" element={<PrivateRoute><CrearPqrs /></PrivateRoute>} />
                <Route path="/perfil" element={
                    <PrivateRoute><PerfilUsuario /></PrivateRoute>
                } />
                
                <Route path="/asistente" element={
                    <PrivateRoute><AsistenteMotos /></PrivateRoute>
                } />
                <Route path="/recomendaciones" element={
                    <PrivateRoute><Recomendaciones /></PrivateRoute>
                } />

                {/* RUTAS ADMINISTRATIVAS (Con Sidebar y bloqueo de rol) */}
                <Route path="/usuarios" element={
                    <PrivateRoute requireAdmin><AdminLayout><Usuarios /></AdminLayout></PrivateRoute>
                } />

                <Route path="/servicios" element={
                    <PrivateRoute requireAdmin><AdminLayout><ServicioAdmin /></AdminLayout></PrivateRoute>
                } />

                <Route path="/referencias" element={
                    <PrivateRoute requireAdmin><AdminLayout><Referencia /></AdminLayout></PrivateRoute>
                } />
                <Route path="/motos" element={
                    <PrivateRoute requireAdmin><AdminLayout><Motos /></AdminLayout></PrivateRoute>
                } />
                <Route path="/productos" element={
                    <PrivateRoute requireAdmin><AdminLayout><Productos /></AdminLayout></PrivateRoute>
                } />
                <Route path="/caracteristicas" element={
                    <PrivateRoute requireAdmin><AdminLayout><Caracteristicas /></AdminLayout></PrivateRoute>
                } />
                <Route path="/tipo-servicio" element={
                    <PrivateRoute requireAdmin><AdminLayout><TipoServicio /></AdminLayout></PrivateRoute>
                } />
                <Route path="/pqrs" element={
                    <PrivateRoute requireAdmin><AdminLayout><PqrsManager /></AdminLayout></PrivateRoute>
                } />
                <Route path="/mecanico" element={
                    <PrivateRoute requireAdmin><AdminLayout><Mecanico /></AdminLayout></PrivateRoute>
                } />

                {/* REDIRECCIÓN POR DEFECTO */}
                <Route path="*" element={<Navigate to="/dashboard" />} />
            </Routes>
        </Router>
    );
}

export default App;