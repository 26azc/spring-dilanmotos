package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "caracteristicas")
public class Caracteristicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCaracteristica;

    // Relación con Moto
    @ManyToOne
    @JoinColumn(name = "id_moto") // columna en la tabla Caracteristicas
    private Moto moto;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(name = "descripcion")
    private String descripcion;

    // Getters y setters
    public Integer getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(Integer idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
