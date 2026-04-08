import React, { useState, useEffect } from 'react';

const Caracteristicas = () => {
  const [caracteristicas, setCaracteristicas] = useState([]);
  const [motos, setMotos] = useState([]);
  const [busqueda, setBusqueda] = useState('');
  
  // Estado inicial ajustado a los nombres de tu tabla
  const [formData, setFormData] = useState({
    id_caracteristica: null,
    id_moto: '',
    descripcion: ''
  });

  const API_URL = "http://localhost:8080/api/caracteristicas";

  useEffect(() => {
    fetchDatos();
    fetchMotos();
  }, []);

  const fetchDatos = async (query = '') => {
    const url = query ? `${API_URL}?search=${query}` : API_URL;
    const res = await fetch(url);
    const data = await res.json();
    setCaracteristicas(data);
  };

  const fetchMotos = async () => {
    const res = await fetch("http://localhost:8080/api/motos");
    const data = await res.json();
    setMotos(data);
  };

  const handleGuardar = async (e) => {
    e.preventDefault();
    
    // Si hay id_caracteristica es una actualización, si no, es creación
    const url = formData.id_caracteristica ? `${API_URL}/actualizar` : API_URL;
    const metodo = formData.id_caracteristica ? 'PUT' : 'POST';

    const response = await fetch(url, {
      method: metodo,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData)
    });

    if (response.ok) {
      alert("Operación exitosa");
      setFormData({ id_caracteristica: null, id_moto: '', descripcion: '' });
      fetchDatos();
    }
  };

  const prepararEdicion = (item) => {
    setFormData({
      id_caracteristica: item.id_caracteristica,
      id_moto: item.id_moto,
      descripcion: item.descripcion
    });
  };

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h2>Gestión de Características - Módulo Andrés</h2>

      {/* Formulario */}
      <div style={{ background: '#f9f9f9', padding: '20px', borderRadius: '10px', marginBottom: '20px' }}>
        <h3>{formData.id_caracteristica ? 'Editar' : 'Agregar'} Registro</h3>
        <form onSubmit={handleGuardar}>
          <div style={{ marginBottom: '10px' }}>
            <label>Moto:</label><br/>
            <select 
              required
              value={formData.id_moto}
              onChange={(e) => setFormData({...formData, id_moto: e.target.value})}
              style={{ width: '100%', padding: '8px' }}
            >
              <option value="">-- Seleccione una Moto --</option>
              {motos.map(m => (
                <option key={m.id_moto} value={m.id_moto}>
                  ID: {m.id_moto} - {m.modelo}
                </option>
              ))}
            </select>
          </div>

          <div style={{ marginBottom: '10px' }}>
            <label>Descripción:</label><br/>
            <textarea 
              required
              value={formData.descripcion}
              onChange={(e) => setFormData({...formData, descripcion: e.target.value})}
              style={{ width: '100%', padding: '8px' }}
            />
          </div>

          <button type="submit" style={{ background: '#28a745', color: 'white', padding: '10px 20px', border: 'none', borderRadius: '5px' }}>
            {formData.id_caracteristica ? 'Actualizar' : 'Guardar'}
          </button>
          
          {formData.id_caracteristica && (
            <button 
              type="button" 
              onClick={() => setFormData({ id_caracteristica: null, id_moto: '', descripcion: '' })}
              style={{ marginLeft: '10px' }}
            >
              Cancelar
            </button>
          )}
        </form>
      </div>

      {/* Tabla de Resultados */}
      <table border="1" style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ background: '#333', color: 'white' }}>
            <th>ID Característica</th>
            <th>ID Moto</th>
            <th>Descripción</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {caracteristicas.map((item) => (
            <tr key={item.id_caracteristica} style={{ textAlign: 'center' }}>
              <td>{item.id_caracteristica}</td>
              <td>{item.id_moto}</td>
              <td>{item.descripcion}</td>
              <td>
                <button onClick={() => prepararEdicion(item)}>Editar</button>
                <button onClick={() => handleEliminar(item.id_caracteristica)} style={{ color: 'red', marginLeft: '5px' }}>Eliminar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Caracteristicas;