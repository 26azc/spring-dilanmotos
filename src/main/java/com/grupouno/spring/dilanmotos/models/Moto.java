package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private int idMoto;

    private String modelo;
    private Double cilindraje;

    @Column(name = "tipo_reparacion")
    private String tipoReparacion;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    @JsonIgnoreProperties({"motos", "productos"})
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"motos", "contrasena", "habilitado", "rol"})
    private Usuarios usuario;

    // Relación con características (opcional)
    @OneToMany(mappedBy = "moto")
    @JsonIgnoreProperties("moto")
    private List<Caracteristicas> listaCaracteristicas;

    public Moto() {}

    // Getters y Setters
    public int getIdMoto() { return idMoto; }
    public void setIdMoto(int idMoto) { this.idMoto = idMoto; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Double getCilindraje() { return cilindraje; }
    public void setCilindraje(Double cilindraje) { this.cilindraje = cilindraje; }
    public String getTipoReparacion() { return tipoReparacion; }
    public void setTipoReparacion(String tipoReparacion) { this.tipoReparacion = tipoReparacion; }
    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }
    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }
}