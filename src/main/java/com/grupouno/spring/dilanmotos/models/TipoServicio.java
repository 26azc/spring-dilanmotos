package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tiposervicio") // nombre exacto de la tabla
public class TipoServicio {

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_servicio", nullable = false, updatable = false) // snake_case
    private int idTipoServicio;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Column(name = "descripcion")
    private String descripcion;

    public TipoServicio() {}

    // Getters y Setters
    public int getIdTipoServicio() { return idTipoServicio; }
    public void setIdTipoServicio(int idTipoServicio) { this.idTipoServicio = idTipoServicio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}