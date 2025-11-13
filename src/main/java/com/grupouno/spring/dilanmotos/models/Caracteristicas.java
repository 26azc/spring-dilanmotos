package com.grupouno.spring.dilanmotos.models;

public class Caracteristicas {

    private int idCaracteristica;
    private int idMoto;
    private String descripcion;


public Caracteristicas(int idCaracteristica, int idMoto, String descripcion){

    this.idCaracteristica = idCaracteristica;
    this.idMoto = idMoto;
    this.descripcion = descripcion;
}

    public int getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(int idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public int getIdMoto() {
        return idMoto;
    }

    public void setIdMoto(int idMoto) {
        this.idMoto = idMoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}