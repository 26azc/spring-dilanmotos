import React, { useState, useRef, useEffect } from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm'; // Instalar esto: npm install remark-gfm
import './AsistenteMotos.css';

const AsistenteMotos = () => {
    const [pregunta, setPregunta] = useState('');
    const [cargando, setCargando] = useState(false);
    const [modeloSeleccionado, setModeloSeleccionado] = useState('Buscando máquina...');
    const [mensajes, setMensajes] = useState([
        { rol: 'ia', texto: '¡Habla pues **parcero**! Bienvenido a **Dilan Motos**. ¿Qué máquinas vamos a revisar hoy?' }
    ]);

    const idLogueado = localStorage.getItem("idUsuario");
    const mensajesFinRef = useRef(null);

    useEffect(() => {
        const cargarMoto = async () => {
            if (!idLogueado) return;
            try {
                const res = await fetch(`http://localhost:8080/api/motos/usuario/${idLogueado}`);
                if (res.ok) {
                    const data = await res.json();
                    setModeloSeleccionado(data.modelo);
                }
            } catch (error) { setModeloSeleccionado("Moto genérica"); }
        };
        cargarMoto();
    }, [idLogueado]);

    useEffect(() => {
        mensajesFinRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [mensajes, cargando]);

    const consultarIA = async (e) => {
        e.preventDefault();
        if (!pregunta.trim() || cargando) return;

        const nuevoMensaje = { rol: 'usuario', texto: pregunta };
        const historialActualizado = [...mensajes, nuevoMensaje];
        
        setMensajes(historialActualizado);
        setPregunta('');
        setCargando(true);

        try {
            const res = await fetch('http://localhost:8080/api/ia/consultar', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 
                    motor: modeloSeleccionado,
                    falla: pregunta,
                    historial: historialActualizado 
                })
            });

            if (res.ok) {
                const data = await res.json();
                setMensajes(prev => [...prev, { rol: 'ia', texto: data.recomendacion }]);
            }
        } catch (error) {
            setMensajes(prev => [...prev, { rol: 'ia', texto: "🚨 **Error de conexión**, parcero. Revisa el motor (backend)." }]);
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
                    <p><span className="status-dot"></span> Memoria de taller 100%</p>
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
                            {/* remarkGfm sirve para que las tablas de Markdown funcionen */}
                            <ReactMarkdown remarkPlugins={[remarkGfm]}>
                                {msg.texto}
                            </ReactMarkdown>
                        </div>
                    </div>
                ))}
                {cargando && (
                    <div className="message-row ia">
                        <div className="bubble ia">Analizando los fierros...</div>
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
                        placeholder="¿Qué le suena a la máquina?" 
                    />
                    <button type="submit" disabled={cargando}>Enviar</button>
                </form>
            </div>
        </div>
    );
};

export default AsistenteMotos;