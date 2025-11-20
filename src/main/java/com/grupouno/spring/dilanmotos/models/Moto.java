package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.JoinColumn; 

@Entity
@Table(name = "moto")
public class Moto {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int idMoto;
    
    @ManyToOne 
    @JoinColumn(name = "id_usuario") 
    private Usuarios usuario; 
    
    
    @ManyToOne
    @JoinColumn(name = "id_marca") 
    private Marca marca; 

    @NotBlank(message = "El modelo es obligatorio")
    @Column(name = "modelo")
    private String modelo;

    @NotNull(message = "El cilindraje es obligatorio")
    @Column(name = "cilindraje")
    private Double cilindraje;

    @NotNull(message = "El tipo de reparación es obligatorio")
    @Column(name = "tipoReparacion")
    private String tipoReparacion;

    public Moto() {}

    
    public int getIdMoto() { return idMoto; }
    public void setIdMoto(int idMoto) { this.idMoto = idMoto; }

    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }
    
    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public Double getCilindraje() { return cilindraje; }
    public void setCilindraje(Double cilindraje) { this.cilindraje = cilindraje; }

    public String getTipoReparacion() { return tipoReparacion; }
    public void setTipoReparacion(String tipoReparacion) { this.tipoReparacion = tipoReparacion; }

}