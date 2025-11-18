package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "moto")
public class Moto {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int idMoto;
    
    @Column(name = "id_usuario")
    private int idUsuario;
    
    @Column(name = "id_marca")
    private int idMarca;

    @NotBlank(message = "El modelo es obligatorio")
    @Column(name = "modelo")
    private String modelo;

    @NotNull(message = "El cilindraje es obligatorio")
    @Column(name = "cilindraje")
    private Double cilindraje;

    public Moto() {}

    public int getIdMoto() { return idMoto; }
    public void setIdMoto(int idMoto) { this.idMoto = idMoto; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public Double getCilindraje() { return cilindraje; }
    public void setCilindraje(Double cilindraje) { this.cilindraje = cilindraje; }

}
