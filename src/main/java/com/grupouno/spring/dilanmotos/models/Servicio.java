package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotNull(message = "El ID del mecánico es obligatorio")
    @Column(name = "id_mecanico")
    private Integer idMecanico;

    @NotNull(message = "El ID del tipo de servicio es obligatorio")
    @Column(name = "id_tipo_servicio")
    private Integer idTipoServicio;

    @NotNull(message = "La fecha del servicio es obligatoria")
    @Column(name = "fecha_servicio")
    private LocalDate fechaServicio;

    @NotBlank(message = "El estado del servicio es obligatorio")
    @Column(name = "estado_servicio")
    private String estadoServicio;

    @NotBlank(message = "El comentario es obligatorio")
    @Column(name = "comentario")
    private String comentario;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    @Column(name = "puntuacion")
    private Integer puntuacion;

    @NotNull(message = "Debe indicar si es visible en el historial")
    @Column(name = "visible_en_historial")
    private Boolean visibleEnHistorial;

    // Getters y setters

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
    }

    public Integer getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public LocalDate getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(LocalDate fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(String estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Boolean getVisibleEnHistorial() {
        return visibleEnHistorial;
    }

    public void setVisibleEnHistorial(Boolean visibleEnHistorial) {
        this.visibleEnHistorial = visibleEnHistorial;
    }
}
