package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad que representa una moto registrada por un usuario.
 *
 * <p>Se mapea a la tabla <b>moto</b> en la base de datos.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Incluye datos básicos: modelo, cilindraje, tipo de reparación.</li>
 *   <li>Relación muchos a uno con {@link Usuarios} (cada moto pertenece a un usuario).</li>
 *   <li>Relación muchos a uno con {@link Marca} (cada moto tiene una marca).</li>
 * </ul>
 *
 * @author Juan
 * @version 1.0
 */
@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private int idMoto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @NotNull(message = "El cilindraje es obligatorio")
    @Column(name = "cilindraje", nullable = false)
    private Double cilindraje;

    @NotBlank(message = "El tipo de reparación es obligatorio")
    @Column(name = "tipoReparacion", nullable = false)
    private String tipoReparacion;

    public Moto() {}

    // --- Getters y Setters ---
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