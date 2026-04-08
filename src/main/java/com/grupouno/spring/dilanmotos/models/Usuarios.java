package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import com.fasterxml.jackson.annotation.JsonIgnore; // 👈 Importante
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo inválido")
    @Column(unique = true)
    private String correo;

    private String contrasena;

    private String rol = "USER";
    private boolean habilitado;

    // 👈 Cortamos la recursión aquí para que al listar usuarios no intente traer sus motos
    @JsonIgnore 
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moto> motos = new ArrayList<>();

    public Usuarios() {}

    // Getters y Setters...
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public boolean isHabilitado() { return habilitado; }
    public void setHabilitado(boolean habilitado) { this.habilitado = habilitado; }
    public List<Moto> getMotos() { return motos; }
    public void setMotos(List<Moto> motos) { this.motos = motos; }
}