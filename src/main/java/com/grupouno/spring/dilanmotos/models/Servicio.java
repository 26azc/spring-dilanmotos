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

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_mecanico")
    private Mecanico mecanico;

    @NotNull
    @Column(name = "id_tipo_servicio")
    private Integer idTipoServicio;

    @NotNull
    @Column(name = "fecha_servicio")
    private LocalDate fechaServicio;

    @NotBlank
    @Column(name = "estado_servicio")
    private String estadoServicio;

    @NotBlank
    @Column(name = "comentario")
    private String comentario;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "puntuacion")
    private Integer puntuacion;

    @NotNull
    @Column(name = "visible_en_historial")
    private Boolean visibleEnHistorial;

    // Getters y setters

    public Integer getIdServicio() {
    return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
    this.idServicio = idServicio;
    }   

    public Usuarios getUsuario() {
    return usuario;
    }

    public void setUsuario(Usuarios usuario) {
    this.usuario = usuario;
    }

    public Mecanico getMecanico() {
    return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
    this.mecanico = mecanico;
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
