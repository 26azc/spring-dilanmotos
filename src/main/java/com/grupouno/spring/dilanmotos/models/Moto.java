package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMoto;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;

    @NotBlank(message = "La marca es obligatoria")
    @Column(name = "marca")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Column(name = "modelo")
    private String modelo;

    @NotNull(message = "El cilindraje es obligatorio")
    @Column(name = "cilindraje")
    private Double cilindraje;

    @NotBlank(message = "El tipo de reparación es obligatorio")
    @Column(name = "tipoReparacion")
    private String tipoReparacion;

    public Moto() {}

    // Getters y Setters
    public int getIdMoto() { return idMoto; }
    public void setIdMoto(int idMoto) { this.idMoto = idMoto; }

    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Double getCilindraje() { return cilindraje; }
    public void setCilindraje(Double cilindraje) { this.cilindraje = cilindraje; }

    public String getTipoReparacion() { return tipoReparacion; }
    public void setTipoReparacion(String tipoReparacion) { this.tipoReparacion = tipoReparacion; }
}