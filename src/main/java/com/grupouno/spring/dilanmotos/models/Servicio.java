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
    @JoinColumn(name = "id_moto") // 🏍️ RELACIÓN CON MOTO AGREGADA
    private Moto moto;

    @ManyToOne
    @JoinColumn(name = "id_mecanico")
    private Mecanico mecanico;

    @ManyToOne
    @JoinColumn(name = "id_tipo_servicio") 
    private TipoServicio tipoServicio;

    @NotNull
    @Column(name = "fecha_servicio")
    private LocalDate fechaServicio;

    @NotBlank
    @Column(name = "estado_servicio")
    private String estadoServicio;

    @NotBlank
    @Column(name = "comentario") // Comentario del cliente
    private String comentario;

    @Column(name = "observaciones_mecanico") // 🛠️ NUEVO: Comentario del mecánico
    private String observacionesMecanico;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "puntuacion")
    private Integer puntuacion;

    @NotNull
    @Column(name = "visible_en_historial")
    private Boolean visibleEnHistorial;

    // --- AGREGAR GETTERS Y SETTERS PARA LOS NUEVOS CAMPOS ---
    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }

    public String getObservacionesMecanico() { return observacionesMecanico; }
    public void setObservacionesMecanico(String observacionesMecanico) { this.observacionesMecanico = observacionesMecanico; }
    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }

    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }

    public Mecanico getMecanico() { return mecanico; }
    public void setMecanico(Mecanico mecanico) { this.mecanico = mecanico; }

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }

    public LocalDate getFechaServicio() { return fechaServicio; }
    public void setFechaServicio(LocalDate fechaServicio) { this.fechaServicio = fechaServicio; }

    public String getEstadoServicio() { return estadoServicio; }
    public void setEstadoServicio(String estadoServicio) { this.estadoServicio = estadoServicio; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Integer getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }

    public Boolean getVisibleEnHistorial() { return visibleEnHistorial; }
    public void setVisibleEnHistorial(Boolean visibleEnHistorial) { this.visibleEnHistorial = visibleEnHistorial; }
}