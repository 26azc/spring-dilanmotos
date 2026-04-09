package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "caracteristicas")
public class Caracteristicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristica")
    private int idCaracteristica;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_moto", nullable = false)
    @JsonIgnoreProperties("listaCaracteristicas") 
    private Moto moto;

    public Caracteristicas() {}

    // Getters y Setters
    public int getIdCaracteristica() { return idCaracteristica; }
    public void setIdCaracteristica(int idCaracteristica) { this.idCaracteristica = idCaracteristica; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }
}