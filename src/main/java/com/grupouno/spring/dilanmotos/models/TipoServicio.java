package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "tiposervicio")
public class TipoServicio {

    @Id    
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_tipo_servicio")
    private int idTipoServicio;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Column(name = "descripcion")
    private String descripcion;


    public TipoServicio() {}


    public int getIdTipoServicio() {return idTipoServicio;}
    public void setIdTipoServicio(int idTipoServicio) {this.idTipoServicio = idTipoServicio;}


    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}


    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

}

