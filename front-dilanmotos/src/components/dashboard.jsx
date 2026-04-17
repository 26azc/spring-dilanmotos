import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
    const navigate = useNavigate();
    const [showDropdown, setShowDropdown] = useState(false);
    const [user, setUser] = useState({ nombre: "Socio", rol: "USER", id: null });

    useEffect(() => {
        const id = localStorage.getItem('idUsuario');
        const nombre = localStorage.getItem('nombreUsuario');
        const rol = localStorage.getItem('rolUsuario');
        
        setUser({ nombre: nombre || "Socio", rol: rol || "USER", id });
    }, []);

    const handleLogout = () => {
        localStorage.clear();
        navigate('/login');
    };

    return (
        <div className="dashboard-wrapper">
            {/* Cabecera */}
            <header className="dashboard-header">
                <div className="header-container">
                    <img 
                        src="/LogoDilanMotos.png" 
                        alt="Dilan Motos" 
                        className="main-logo" 
                        style={{cursor: 'pointer'}} 
                        onClick={() => navigate('/dashboard')} 
                    />
                    
                    <div className="header-nav">
                        <div className="user-trigger" onClick={() => setShowDropdown(!showDropdown)}>
                            <img src="/iconoPerfil.png" alt="Perfil" className="nav-icon avatar" />
                            <span>{user.nombre}</span>
                        </div>

                        {showDropdown && (
                            <ul className="dropdown-menu-custom shadow-lg">
                                <li><Link to="/perfil">Mi Cuenta</Link></li>
                                <li><Link to="/asistente">Asistente IA</Link></li>
                                
                                {user.rol === 'ADMIN' && (
                                    <>
                                        <li className="divider"></li>
                                        <li>
                                            <Link to="/usuarios" className="admin-link">
                                                Gestion de Sistema
                                            </Link>
                                        </li>
                                    </>
                                )}
                                
                                <li className="divider"></li>
                                <li>
                                    <button onClick={handleLogout} className="logout-btn-custom">
                                        Cerrar Sesion
                                    </button>
                                </li>
                            </ul>
                        )}
                    </div>
                </div>
            </header>

            {/* Contenido Principal */}
            <main className="dashboard-content">
                <div className="hero-section text-center">
                    <h1 style={{marginBottom: '20px', fontWeight: '800'}}>Mantenimiento Inteligente</h1>
                    
                    {/* Enlace a la nueva pagina de recomendaciones */}
                    <Link 
                        to="/recomendaciones" 
                        className="promo-banner"
                    >
                        Ver Recomendaciones de la IA
                    </Link>
                </div>

                {/* Seccion de Categorias de Productos */}
                <h2 style={{margin: '40px 0 20px 0', fontWeight: '700'}}>Nuestros Productos</h2>
                <div className="categories-grid">
                    {/* Kit de Arrastre */}
                    <div className="category-item">
                        <div className="category-img">
                            <img src="/KitDeArrastre.png" alt="Kits" />
                        </div>
                        <h3>Kits de Arrastre</h3>
                        <Link to="/catalogo/kits" className="category-btn">Ver Catalogo</Link>
                    </div>

                    {/* Llantas */}
                    <div className="category-item">
                        <div className="category-img">
                            <img src="/Llanta.png" alt="Llantas" />
                        </div>
                        <h3>Llantas</h3>
                        <Link to="/catalogo/llantas" className="category-btn">Ver Catalogo</Link>
                    </div>

                    {/* Aceites */}
                    <div className="category-item">
                        <div className="category-img">
                            <img src="/AceiteMotul.png" alt="Aceites" />
                        </div>
                        <h3>Aceites y Lubricantes</h3>
                        <Link to="/catalogo/aceites" className="category-btn">Ver Catalogo</Link>
                    </div>
                </div>
            </main>

            <footer className="dashboard-footer">
                <div className="btn-tech-support">
                    Soporte Tecnico: 300-XXX-XXXX
                </div>
            </footer>
        </div>
    );
};

export default Dashboard;