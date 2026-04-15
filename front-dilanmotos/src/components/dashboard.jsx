import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Dashboard.css'; // Mueve aquí el contenido de estilo_dashboard.css

const Dashboard = () => {
    const navigate = useNavigate();
    const [showDropdown, setShowDropdown] = useState(false);
    const [nombreUsuario, setNombreUsuario] = useState("Usuario");

    useEffect(() => {
        // Recuperamos el nombre guardado en el login
        const nombre = localStorage.getItem('nombreUsuario');
        if (nombre) setNombreUsuario(nombre);
    }, []);

    const handleLogout = () => {
        localStorage.clear(); // Limpiamos idUsuario, isAuthenticated, etc.
        navigate('/login');
    };

    return (
        <div className="dashboard-container">
            {/* Header */}
            <header className="header">
                <img src="/assets/LogoDylanMotosI.png" alt="Dilan Motos" className="logo" />
                
                <div className="search-bar">
                    <input type="text" placeholder="Buscar repuestos..." />
                </div>

                <div className="icons">
                    <nav className="main-nav">
                        <span className="money-icon">
                            <Link to="/cotizacion">
                                <img src="/assets/simboloPesos.png" alt="Pesos" />
                            </Link>
                        </span>
                        
                        <div className="user-menu" style={{ position: 'relative' }}>
                            <span className="user-icon" onClick={() => setShowDropdown(!showDropdown)}>
                                <img src="/assets/iconoPerfil.png" alt="Perfil" />
                                <span className="ms-2 text-white small">{nombreUsuario}</span>
                            </span>

                            {showDropdown && (
                                <ul className="dropdown-menu-custom">
                                    <li><Link to="/cuenta">Mi cuenta</Link></li>
                                    <li><Link to="/pqrs">Mis PQRS</Link></li>
                                    <li><hr className="dropdown-divider" /></li>
                                    <li>
                                        <button onClick={handleLogout} className="dropdown-logout-btn">
                                            Cerrar Sesión
                                        </button>
                                    </li>
                                </ul>
                            )}
                        </div>
                    </nav>
                </div>
            </header>

            {/* Main Content */}
            <main className="main-content">
                <nav className="nav-central">
                    <Link to="/recomendacion" className="btn-category-main">Recomendaciones para ti</Link>
                </nav>

                <div className="row text-center mt-4">
                    {/* Tarjeta 1: Kits */}
                    <div className="col-md-4 mb-4">
                        <div className="category-card">
                            <img src="/assets/KitDeArrastre.png" alt="Kit de arrastre" className="img-fluid mb-3" />
                            <Link to="/catalogo/kits" className="btn-category">Kits de arrastre</Link>
                        </div>
                    </div>

                    {/* Tarjeta 2: Llantas */}
                    <div className="col-md-4 mb-4">
                        <div className="category-card">
                            <img src="/assets/Llanta.png" alt="Llanta" className="img-fluid mb-3" />
                            <Link to="/catalogo/llantas" className="btn-category">Llantas</Link>
                        </div>
                    </div>

                    {/* Tarjeta 3: Aceites */}
                    <div className="col-md-4 mb-4">
                        <div className="category-card">
                            <img src="/assets/AceiteMotul.png" alt="Aceite" className="img-fluid mb-3" />
                            <Link to="/catalogo/aceites" className="btn-category">Aceites</Link>
                        </div>
                    </div>
                </div>
            </main>

            {/* Footer */}
            <footer className="dashboard-footer">
                <Link to="/comunicacion-tec" className="btn-comunicar">
                    Comunícate con un técnico
                </Link>
            </footer>
        </div>
    );
};

export default Dashboard;