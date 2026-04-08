package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore; // 👈 Importante

@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private int idMoto;

    // 👈 Cortamos la recursión aquí también
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El cilindraje es obligatorio")
    private Double cilindraje;

    @NotBlank(message = "El tipo de reparación es obligatorio")
    private String tipoReparacion;

    public Moto() {}

    // Getters y Setters...
    public int getIdMoto() { return idMoto; }
    public void setIdMoto(int idMoto) { this.idMoto = idMoto; }
    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }
    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Double getCilindraje() { return cilindraje; }
    public void setCilindraje(Double cilindraje) { this.cilindraje = cilindraje; }
    public String getTipoReparacion() { return tipoReparacion; }
    public void setTipoReparacion(String tipoReparacion) { this.tipoReparacion = tipoReparacion; }
}