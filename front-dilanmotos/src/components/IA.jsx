import React, { useState, useRef, useEffect } from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import './AsistenteMotos.css';

const AsistenteMotos = () => {
    const [pregunta, setPregunta] = useState('');
    const [cargando, setCargando] = useState(false);
    const [modeloSeleccionado, setModeloSeleccionado] = useState('Buscando máquina...');
    const [mensajes, setMensajes] = useState([
        { rol: 'ia', texto: '¡Habla pues **parcero**! Bienvenido a **Dilan Motos**. ¿Qué máquina vamos a revisar hoy?' }
    ]);

    const idLogueado = localStorage.getItem("idUsuario");
    const mensajesFinRef = useRef(null);

    // 🏍️ Efecto para cargar la moto al iniciar el componente
    useEffect(() => {
        const cargarMoto = async () => {
            if (!idLogueado) {
                setModeloSeleccionado("USUARIO NO LOGUEADO");
                return;
            }
            try {
                const res = await fetch(`http://localhost:8080/api/motos/usuario/${idLogueado}`);
                if (res.ok) {
                    const data = await res.json();
                    if (data && data.modelo) {
                        setModeloSeleccionado(data.modelo.toUpperCase());
                    } else {
                        setModeloSeleccionado("SIN MOTO REGISTRADA");
                    }
                } else {
                    setModeloSeleccionado("SIN MOTO REGISTRADA");
                }
            } catch (error) {
                console.error("Error al cargar la moto:", error);
                setModeloSeleccionado("ERROR DE CONEXIÓN");
            }
        };
        cargarMoto();
    }, [idLogueado]);

    // Auto-scroll al final de los mensajes
    useEffect(() => {
        mensajesFinRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [mensajes, cargando]);

    const consultarIA = async (e) => {
        e.preventDefault();
        if (!pregunta.trim() || cargando) return;

        const nuevoMensaje = { rol: 'usuario', texto: pregunta };
        const historial = [...mensajes, nuevoMensaje];
        
        setMensajes(historial);
        setPregunta('');
        setCargando(true);

        try {
            const res = await fetch('http://localhost:8080/api/ia/consultar', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 
                    motor: modeloSeleccionado,
                    falla: pregunta,
                    historial: historial 
                })
            });

            if (res.ok) {
                const data = await res.json();
                setMensajes(prev => [...prev, { rol: 'ia', texto: data.recomendacion }]);
            } else {
                throw new Error("Falla en el servicio de IA");
            }
        } catch (error) {
            setMensajes(prev => [...prev, { rol: 'ia', texto: "🚨 **Error**, parcero. El motor de la IA se apagó." }]);
        } finally {
            setCargando(false);
        }
    };

    return (
        <div className="chat-container">
            <div className="chat-header">
                <div className="avatar">DM</div>
                <div className="header-info">
                    <h2>Mecánico Virtual</h2>
                    <p><span className="status-dot"></span> Online - Dilan Motos</p>
                </div>
            </div>

            <div className="vehicle-selector">
                <span>🏍️ Moto detectada:</span>
                <strong style={{marginLeft: '10px', color: '#e74c3c'}}>{modeloSeleccionado}</strong>
            </div>

            <div className="chat-messages">
                {mensajes.map((msg, index) => (
                    <div key={index} className={`message-row ${msg.rol === 'usuario' ? 'user' : 'ia'}`}>
                        <div className={`bubble ${msg.rol === 'usuario' ? 'user' : 'ia'}`}>
                            <ReactMarkdown remarkPlugins={[remarkGfm]}>
                                {msg.texto}
                            </ReactMarkdown>
                        </div>
                    </div>
                ))}
                {cargando && (
                    <div className="message-row ia">
                        <div className="bubble ia pulse">Analizando los fierros...</div>
                    </div>
                )}
                <div ref={mensajesFinRef} />
            </div>

            <div className="chat-input-area">
                <form onSubmit={consultarIA}>
                    <input 
                        type="text" 
                        value={pregunta} 
                        onChange={e => setPregunta(e.target.value)} 
                        placeholder="Ej: ¿Por qué mi moto pierde fuerza?" 
                    />
                    <button type="submit" disabled={cargando}>Enviar</button>
                </form>
            </div>
        </div>
    );
};

export default AsistenteMotos;