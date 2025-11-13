package com.grupouno.spring.dilanmotos.spring_dilanmotos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "nombre_usuario")
    private String nombre;

    @Column(name = "correo_usuario")
    private String correo;

    @Column(name = "contrasena_usuario")
    private String contrasena;

    public Usuarios() {}

    public Usuarios(String nombre, String correo, String contrasena, int idUsuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idUsuario = idUsuario;
    }

    // Getters y setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
