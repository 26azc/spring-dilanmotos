package com.grupouno.spring.dilanmotos.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "PQRS")
public class PQRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pqrs") 
    private int idPqrs;
    
    // AGREGADO: Campo id_usuario, obligatorio para la BD
    @NotNull(message = "El id de usuario es obligatorio.")
    @Column(name = "id_usuario") 
    private Integer idUsuario; 

    @NotBlank(message = "Ingrese una pqrs valida")
    @Column(name = "tipo")
    private String tipo;

    @NotBlank(message = "Ingrese el asunto de la pqrs")
    @Column(name = "asunto")
    private String asunto;

    @NotBlank(message = "Ingrese la descripcion de la pqrs")
    @Column(name = "descripcion")
    private String descripcion; 

    @NotNull(message = "Ingrese la fecha del PQRS")
    @Column(name = "fecha_envio")    
    private LocalDateTime fecha;


    @NotBlank(message = "Ingrese la respuesta del administrador")
    @Column(name = "respuesta_admin")
    private String respuesta_admin;
    
    @Column(name = "fecha_respuesta", nullable = true) 
    private LocalDateTime fecha_respuesta;

    @NotBlank(message = "Ingrese el estado del PQRS")
    @Column(name = "estado")
    private String estado;

    @NotBlank(message = "Ingrese la calificacion del servicio")
    @Column(name = "calificacion_servicio")
    private String calificacion_servicio;
    
    @NotBlank(message = "Ingrese el comentario del usuario")
    @Column(name = "comentario_usuario") 
    private String comentario_usuario; 
    
    @NotBlank(message = "Ingrese el comentario del servicio")    
    @Column(name = "comentario_servicio")
    private String comentario_servicio;

    public PQRS() {}

    // --- GETTERS y SETTERS ---

    public int getIdPqrs() { return idPqrs; }
    public void setIdPqrs(int idPqrs) { this.idPqrs = idPqrs; }
    
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; } 

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getRespuesta_admin() { return respuesta_admin; }
    public void setRespuesta_admin(String respuesta_admin) { this.respuesta_admin = respuesta_admin; }  

    public LocalDateTime getFecha_respuesta() { return fecha_respuesta; }
    public void setFecha_respuesta(LocalDateTime fecha_respuesta) { this.fecha_respuesta = fecha_respuesta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCalificacion_servicio() { return calificacion_servicio; }
    public void setCalificacion_servicio(String calificacion_servicio) { this.calificacion_servicio = calificacion_servicio; }

    public String getComentario_usuario() { return comentario_usuario; }
    public void setComentario_usuario(String comentario_usuario) { this.comentario_usuario = comentario_usuario; }
    
    public String getComentario_servicio() { return comentario_servicio; }
    public void setComentario_servicio(String comentario_servicio) { this.comentario_servicio = comentario_servicio; }
}