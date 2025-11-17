package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Caracteristicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_caracteristica;

    private Integer id_moto;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

 
    public Integer getId_caracteristica() {
        return id_caracteristica;
    }

    public void setId_caracteristica(Integer id_caracteristica) {
        this.id_caracteristica = id_caracteristica;
    }

    public Integer getId_moto() {
        return id_moto;
    }

    public void setId_moto(Integer id_moto) {
        this.id_moto = id_moto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}