package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "moto")
public class Moto {
 @Id
    private int idMoto;
    private int idUsuario;
    private int idMarca;
    private String modelo;
    private double cilindraje;

    public Moto() {}

    public int getIdMoto() { return idMoto; }
    public void setIdMoto(int idMoto) { this.idMoto = idMoto; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public double getCilindraje() { return cilindraje; }
    public void setCilindraje(double cilindraje) { this.cilindraje = cilindraje; }

}
