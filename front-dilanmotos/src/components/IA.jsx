import React, { useState, useRef, useEffect } from 'react';
import ReactMarkdown from 'react-markdown';
import './AsistenteMotos.css';

const AsistenteMotos = () => {
  const [pregunta, setPregunta] = useState('');
  const [cargando, setCargando] = useState(false);
  const [modeloSeleccionado, setModeloSeleccionado] = useState('CFMoto 250NK');
  const [mensajes, setMensajes] = useState([
    { rol: 'ia', texto: '¡Habla pues **parcero**! Bienvenido a **Dilan Motos**. ¿Qué máquinas vamos a revisar hoy?' }
  ]);

  const mensajesFinRef = useRef(null);

  useEffect(() => {
    mensajesFinRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [mensajes, cargando]);

  const consultarIA = async (e) => {
    e.preventDefault();
    if (!pregunta.trim()) return;

    // 1. Creamos el nuevo mensaje del usuario
    const nuevoMensaje = { rol: 'usuario', texto: pregunta };
    const historialActualizado = [...mensajes, nuevoMensaje];
    
    setMensajes(historialActualizado);
    setPregunta('');
    setCargando(true);

    // 2. Formateamos el historial para que Gemini lo entienda (user/model)
    const historialFormateado = historialActualizado.map(msg => ({
      role: msg.rol === 'usuario' ? 'user' : 'model',
      parts: [{ text: msg.texto }]
    }));

    try {
      const res = await fetch('http://localhost:8080/api/ia/consultar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ 
          historial: historialFormateado,
          modelo: modeloSeleccionado 
        })
      });

      const textoRespuesta = await res.text();
      setMensajes(prev => [...prev, { rol: 'ia', texto: textoRespuesta }]);
    } catch (error) {
      setMensajes(prev => [...prev, { rol: 'ia', texto: "🚨 Se cayó la conexión, parcero." }]);
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
          <p><span className="status-dot"></span> Memoria Activada - Dilan Motos</p>
        </div>
      </div>

      <div className="vehicle-selector">
        <span>🏍️ Moto actual:</span>
        <select value={modeloSeleccionado} onChange={(e) => setModeloSeleccionado(e.target.value)}>
          <option value="CFMoto 250NK">CFMoto 250NK</option>
          <option value="CFMoto 450NK">CFMoto 450NK</option>
          <option value="KTM Duke 200">KTM Duke 200</option>
          <option value="Yamaha R3">Yamaha R3</option>
        </select>
      </div>

      <div className="chat-messages">
        {mensajes.map((msg, index) => (
          <div key={index} className={`message-row ${msg.rol === 'usuario' ? 'user' : 'ia'}`}>
            <div className={`bubble ${msg.rol === 'usuario' ? 'user' : 'ia'}`}>
              <ReactMarkdown>{msg.texto}</ReactMarkdown>
            </div>
          </div>
        ))}
        {cargando && <div className="message-row ia"><div className="bubble ia">Recordando y analizando...</div></div>}
        <div ref={mensajesFinRef} />
      </div>

      <div className="chat-input-area">
        <form onSubmit={consultarIA}>
          <input 
            type="text" 
            value={pregunta} 
            onChange={(e) => setPregunta(e.target.value)} 
            placeholder="Dime los repuestos y luego pídeme la factura..." 
          />
          <button type="submit" className={pregunta.trim() ? 'active' : ''} disabled={cargando}>Enviar</button>
        </form>
      </div>
    </div>
  );
};

export default AsistenteMotos;