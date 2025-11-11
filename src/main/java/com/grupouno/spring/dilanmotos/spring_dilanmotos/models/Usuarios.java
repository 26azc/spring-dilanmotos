package com.grupouno.spring.dilanmotos.spring_dilanmotos.models;

public class Usuarios {
     private String nombre, correo,contrasena;
     private int id_usuario;

    public Usuarios(String nombre, String correo, String contrasena, int id_usuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.id_usuario = id_usuario;
    }

     public String getNombre() {
         return nombre;
     }
     public void setNombre(String nombre) {
         this.nombre = nombre;
     }
     public String getCorreo() {
         return correo;
     }
     public void setCorreo(String correo) {
         this.correo = correo;
     }
     public String getContrasena() {
         return contrasena;
     }
     public void setContrasena(String contrasena) {
         this.contrasena = contrasena;
     }
     public int getId_usuario() {
         return id_usuario;
     }
     public void setId_usuario(int id_usuario) {
         this.id_usuario = id_usuario;
     }

     
}
