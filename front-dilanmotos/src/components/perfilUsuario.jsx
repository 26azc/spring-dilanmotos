import React from 'react';
import '/Global.css'

const CuentaUsuario = ({ usuarios}) => {
//verficamos de seguridad por si los datos aun no han cargado

if(!usuarios)
    return <p> Cargando los datos del usuario... </p>


return (

    <div className = "container py-4">
        <h1 className = "mb-4">Mi cuenta</h1>

        {/*DATOS PERSONALES */}
        <h2>Tus datos personales registrados.</h2>
        <div className ="profile-section d-flex align-items-center mb-4">

        <img
        src="/assets/iconoPerfil.png"
        alt ="icono perfil"
        className = "me-3 rounded-circle"
        width = "100"
        />

    <div className = "personal-info">
    {/* Usames las llaves para insersar varibles directamente*/}
    <p><strong>Nombre:</strong><span>{usuarios.nombre}</span></p>
    <p><strong>Correo:</strong><span>{usuarios.correo}</span></p>
    
         </div>
        </div>

    {/*Datos de la moto registrada*/}
    <h2>Tu moto registrada. </h2>
{usuarios.motos.map((moto, index) => (
<div key = {index} className = "moto-card mb-3">
<p><strong>Marca:</strong> <span>{moto.marca.nombre}</span></p>
<p><strong>Modelo:</strong><span>{moto.modelo}</span></p>
<p><strong>Cilindraje:</strong><span>{moto.cilindraje}</span> </p>
<p><strong>Tipo de reparacion:</strong><span>{moto.tipoReparacion}</span></p>
</div>


))}
</div>
);
};
export default CuentaUsuario;