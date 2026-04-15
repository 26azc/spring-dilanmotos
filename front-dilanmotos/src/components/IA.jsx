import React, { useState } from 'react';

const AsistenteMotos = () => {
  const [pregunta, setPregunta] = useState('');
  const [respuesta, setRespuesta] = useState('');
  const [cargando, setCargando] = useState(false); // Nuevo estado para la carga

  const consultarIA = async () => {
    if (!pregunta.trim()) return; // Evita enviar consultas vacías
    
    setCargando(true); // Activamos el mensaje de "Pensando..."
    setRespuesta('');  // Limpiamos la respuesta anterior

    try {
      const res = await fetch('http://localhost:8080/api/ia/consultar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ pregunta: pregunta, modelo: "CFMoto 250NK" })
      });

      // TRUCO: Leemos la respuesta como texto crudo primero. 
      // Así no importa si Spring Boot manda JSON o Texto, React no se rompe.
      const textoCrudo = await res.text();
      
      try {
        // Intentamos ver si es un JSON válido
        const data = JSON.parse(textoCrudo);
        setRespuesta(data.recomendacion || textoCrudo);
      } catch (e) {
        // Si no era JSON (era texto con asteriscos de Gemini), lo mostramos tal cual
        setRespuesta(textoCrudo);
      }

    } catch (error) {
      setRespuesta("Error: No se pudo conectar con el servidor de Dilan Motos.");
    } finally {
      setCargando(false); // Apagamos el estado de carga
    }
  };

  return (
    <div className="p-4 border rounded shadow-lg max-w-2xl mx-auto mt-10">
      <h3 className="text-xl font-bold mb-4">Chanda AI</h3>
      
      <div className="flex gap-2 mb-4">
        <input 
          type="text"
          className="border p-2 flex-grow rounded"
          value={pregunta} 
          onChange={(e) => setPregunta(e.target.value)}
          placeholder="Ej: ¿Qué llantas le sirven a mi moto?"
          disabled={cargando}
        />
        <button 
          className="bg-blue-600 text-white px-4 py-2 rounded disabled:bg-gray-400"
          onClick={consultarIA} 
          disabled={cargando}
        >
          {cargando ? 'Pensando...' : 'Consultar'}
        </button>
      </div>
      
      {/* Caja de respuesta */}
      <div className="mt-4 p-3 bg-gray-50 border rounded min-h-[100px]">
        {cargando ? (
          <p className="text-gray-500 italic">⏳ El experto está analizando el manual de taller...</p>
        ) : (
          <p className="whitespace-pre-wrap">{respuesta}</p> 
        )}
      </div>
    </div>
  );
};

export default AsistenteMotos;