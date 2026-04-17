package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "referencia_motos")
public class Referencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_referencia")
    private Integer idReferencia;

    @Column(nullable = false)
    private String nombre;
    private Double cilindraje;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    @JsonIgnoreProperties("referencias")
    private Marca marca;

    public Referencia() {
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(Double cilindraje) {
        this.cilindraje = cilindraje;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}