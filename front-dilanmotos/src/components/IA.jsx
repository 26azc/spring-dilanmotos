import React, { useState, useRef, useEffect } from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
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
                const data = await res.json();
                
                if (data && data.modelo) {
                    setModeloSeleccionado(data.modelo.toUpperCase());
                } else {
                    setModeloSeleccionado("SIN MOTO ASIGNADA");
                }
            } catch (error) { 
                setModeloSeleccionado("ERROR DE CONEXIÓN"); 
            }
        };
        cargarMoto();
    }, [idLogueado]);

    const consultarIA = async (e) => {
        e.preventDefault();
        if (!pregunta.trim() || cargando) return;

        const nuevoMensaje = { rol: 'usuario', texto: pregunta };
        setMensajes([...mensajes, nuevoMensaje]);
        setPregunta('');
        setCargando(true);

        try {
            const res = await fetch('http://localhost:8080/api/ia/consultar', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 
                    motor: modeloSeleccionado,
                    falla: pregunta,
                    historial: mensajes 
                })
            });
            const data = await res.json();
            setMensajes(prev => [...prev, { rol: 'ia', texto: data.recomendacion }]);
        } catch (error) {
            setMensajes(prev => [...prev, { rol: 'ia', texto: "🚨 Error en el motor del chat." }]);
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
                    <p>Memoria de taller 100%</p>
                </div>
            </div>
            <div className="vehicle-selector">
                <span>🏍️ Moto detectada:</span>
                <strong style={{marginLeft: '10px', color: '#e74c3c'}}>{modeloSeleccionado}</strong>
            </div>
            <div className="chat-messages">
                {mensajes.map((msg, i) => (
                    <div key={i} className={`message-row ${msg.rol}`}>
                        <div className={`bubble ${msg.rol}`}>
                            <ReactMarkdown remarkPlugins={[remarkGfm]}>{msg.texto}</ReactMarkdown>
                        </div>
                    </div>
                ))}
                {cargando && <div className="bubble ia">Analizando...</div>}
                <div ref={mensajesFinRef} />
            </div>
            <form className="chat-input-area" onSubmit={consultarIA}>
                <input type="text" value={pregunta} onChange={e => setPregunta(e.target.value)} placeholder="¿Qué falla tiene la máquina?" />
                <button type="submit">Enviar</button>
            </form>
        </div>
    );
};

export default AsistenteMotos;