package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "historial")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorial;

    @Column(name = "id_usuario")
    private String idUsuario;

    @Column(name = "id_servicio")
    private Integer idServicio;

    @NotBlank(message = "Ingrese la accion realizada")
    @Column(name = "accion")
    private String accion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @NotBlank(message = "Ingrese el detalle de la accion")
    @Column(name = "detalle")
    private String detalle;

    // Getters y setters
    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
