import React, { useState, useEffect } from 'react';

const Productos = () => {
  const [productos, setProductos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [marcas, setMarcas] = useState([]);
  
  // Estado para el formulario de registro
  const [nuevoProducto, setNuevoProducto] = useState({
    nombre: '',
    descripcion: '',
    precio: '',
    id_categoria: '',
    id_marca: ''
  });

  const API_URL = "http://localhost:8080/api/productos";

  // Cargar datos al montar el componente
  useEffect(() => {
    fetchProductos();
    fetchCategorias();
    fetchMarcas();
  }, []);

  const fetchProductos = async () => {
    const res = await fetch(API_URL);
    setProductos(await res.json());
  };

  const fetchCategorias = async () => {
    const res = await fetch("http://localhost:8080/api/categorias");
    setCategorias(await res.json());
  };

  const fetchMarcas = async () => {
    const res = await fetch("http://localhost:8080/api/marcas");
    setMarcas(await res.json());
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nuevoProducto)
    });

    if (response.ok) {
      alert("Producto guardado correctamente");
      fetchProductos();
      setNuevoProducto({ nombre: '', descripcion: '', precio: '', id_categoria: '', id_marca: '' });
    }
  };

  const eliminarProducto = async (id) => {
    if (window.confirm("¿Deseas eliminar este producto?")) {
      await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
      fetchProductos();
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Gestión de Productos - Módulo Andrés</h1>

      {/* Formulario de Registro */}
      <div className="bg-gray-100 p-4 rounded mb-6">
        <h2 className="text-lg font-semibold mb-2">Agregar Nuevo Producto</h2>
        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
          <input 
            type="text" placeholder="Nombre" className="p-2 border" required
            value={nuevoProducto.nombre}
            onChange={(e) => setNuevoProducto({...nuevoProducto, nombre: e.target.value})}
          />
          <input 
            type="number" placeholder="Precio" className="p-2 border" required
            value={nuevoProducto.precio}
            onChange={(e) => setNuevoProducto({...nuevoProducto, precio: e.target.value})}
          />
          <textarea 
            placeholder="Descripción" className="p-2 border col-span-2"
            value={nuevoProducto.descripcion}
            onChange={(e) => setNuevoProducto({...nuevoProducto, descripcion: e.target.value})}
          />
          
          <select 
            className="p-2 border" required
            value={nuevoProducto.id_categoria}
            onChange={(e) => setNuevoProducto({...nuevoProducto, id_categoria: e.target.value})}
          >
            <option value="">Seleccionar Categoría</option>
            {categorias.map(cat => <option key={cat.id} value={cat.id}>{cat.nombre}</option>)}
          </select>

          <select 
            className="p-2 border" required
            value={nuevoProducto.id_marca}
            onChange={(e) => setNuevoProducto({...nuevoProducto, id_marca: e.target.value})}
          >
            <option value="">Seleccionar Marca</option>
            {marcas.map(mar => <option key={mar.id} value={mar.id}>{mar.nombre}</option>)}
          </select>

          <button type="submit" className="bg-blue-500 text-white p-2 rounded col-span-2">
            Guardar Producto
          </button>
        </form>
      </div>

      {/* Tabla de Productos */}
      <table className="w-full border-collapse border border-gray-300">
        <thead className="bg-gray-200">
          <tr>
            <th className="border p-2">Nombre</th>
            <th className="border p-2">Descripción</th>
            <th className="border p-2">Precio</th>
            <th className="border p-2">Marca</th>
            <th className="border p-2">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {productos.map(prod => (
            <tr key={prod.id} className="text-center">
              <td className="border p-2">{prod.nombre}</td>
              <td className="border p-2">{prod.descripcion}</td>
              <td className="border p-2">${prod.precio}</td>
              <td className="border p-2">{prod.marca?.nombre}</td>
              <td className="border p-2">
                <button 
                  onClick={() => eliminarProducto(prod.id)}
                  className="bg-red-500 text-white px-2 py-1 rounded"
                >
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Productos;