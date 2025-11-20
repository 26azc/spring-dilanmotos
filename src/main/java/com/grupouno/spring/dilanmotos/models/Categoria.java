package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategoria;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;

    public int getIdCategoria() {return idCategoria;}
    public void setIdCategoria(int idCategoria) {this.idCategoria = idCategoria;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
}