import React, {useState, useEffect} from 'react'

const TipoServicio = () => {

const [tipoServicios, setTipoServicios] = useState([]); // lista de servicios
const [busqueda, setBusqueda] = useState(""); // barra de busqueda 
const [nuevoTipo, setNuevoTipo] = useState({nombre: "", descripcion: ""});//formulario

//URL BASE DEL RESTCONTROLLER

const API_URL = "http://localhost:8080/api/tipo-servicio";

//Cargamos los datos al iniciar 
useEffect (()=> {
    cargarServicios();
},[]);


  //Nos comunicamos con nuestro backend
const cargarServicios = async () =>{

    try{
        const url = busqueda ? `${API_URL}?search=${busqueda}` : API_URL;
        const response = await fetch(url);
        const data = await response.json();
        setTipoServicios (data);
    }catch (error){
        console.log("Error al conectar con la API:", error)
    }
};
//Hacemos el POST = Guardar  @PostMapping del restcontroller

const handleGuardar = async (e) =>{
    e.preventDefault();

try{
const response  = await  fetch (API_URL,{
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(nuevoTipo) // convertimos el objeto JSON

});
if(response.ok){
    alert("Servicio guardado con exito!!")
    setNuevoTipo({nombre:"", descripcion: ""});// limpiamos el formulario
    cargarServicios(); // Recargamos la lista 
    }
}catch(error){
    console.log("Error al guardar:", error);
    }
};

//DELETE - ELIMINAR  (@DeleteMapping("/{id}"))
const handleEliminar = async(id) => {
if(window.confirm("Estas seguro de eliminar este servicio?")){

        try{
            const response = await fetch (`${API_URL}/${id}`,{
                method: 'DELETE'           
            });
            if (response.status === 204){
                setTipoServicios(tipoServicios.filter(ts => ts.id !== id));
                alert("Registro eliminado con exito!");
            }else{
                alert("No se pudo eliminar el registro seleccionado");
            }
        }catch(error){
            console.log("Error al eliminar",error);
            }

        }
    };

return(

    <div className = "container mt-4">
    <h2 className = "text-primary">Gestion de Tipos de Servicio</h2>

    {/*Buscador */}
    <div className = "mb-3 d-flex shadow-sm p-3 bg-light rounded">
        <input
        type ="text"
        className = "form-control me-2"
        placeholder = "Filtrar por nombre..."
        value = {busqueda}
        onChange = {(e) => setBusqueda(e.target.value)}
        
        />
        <button className = "btn btn-primary" onClick={cargarServicios}>Buscar</button>
    </div>

    {/*FORMULARIO*/}
    <form onSubmit={handleGuardar} className = "card card-body mb-5 shadow-sm">
        <h4 className = "card-title mb-3">Agregar Nuevo Tipo de Servicio</h4>
        <div className = "row">
            <div className="col">
                <input
                    type="text"
                    className="form-control mb-2" 
                    placeholder="Nombre del servicio"
                    value={nuevoTipo.nombre}
                    onChange={(e) => setNuevoTipo({...nuevoTipo, nombre: e.target.value})}
                    required
                />
            </div>
            <div className="col-auto">
                <button type="submit" className="btn btn-success px-4">Guardar</button>
            </div>
        </div>
    </form>

            {/* TABLA */}
            <div className="table-responsive shadow-sm rounded">
                <table className="table table-hover align-middle">
                    <thead className="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th className="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tipoServicios.map((ts) => (
                            <tr key={ts.id}>
                                <td>{ts.id}</td>
                                <td>{ts.nombre}</td>
                                <td className="text-center">
                                    <button className="btn btn-outline-warning btn-sm me-2">Editar</button>
                                    <button 
                                        className="btn btn-outline-danger btn-sm"
                                        onClick={() => handleEliminar(ts.id)}
                                    >
                                        Eliminar
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default TipoServicio;