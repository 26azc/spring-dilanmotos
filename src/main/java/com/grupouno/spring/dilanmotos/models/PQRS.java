package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "PQRS")
public class PQRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPqrs;

    @NotBlank(message = "Ingrese una pqrs valida")
    @Column(name = "tipo")
    private String tipo;

    @NotBlank(message = "Ingrese el asunto de la pqrs")
    @Column(name = "asunto")
    private String asunto;

    @NotBlank(message = "Ingrese la descripcion de la pqrs")
    @Column(name = "descripcion")
    private String descripcion; 

    @NotBlank(message = "Ingrese la fecha del PQRS")
    @Column(name = "fecha")    
    private Double fecha;

    @NotBlank(message = "Ingrese la respuesta del administrador")
    @Column(name = "respuesta_admin")
    private String respuesta_admin;
    
    @NotBlank(message = "Ingrese la fecha de respuesta del adnministrador")
    @Column(name = "fecha_respuesta")
    private double fecha_respuesta;

    @NotBlank(message = "Ingrese el estado del PQRS")
    @Column(name = "estado")
    private String estado;

    @NotBlank(message = "Ingrese la calificacion del servicio")
    @Column(name = "calificacion_servicio")
    private String calificacion_servicio;

    @NotBlank(message = "Ingrese el comentario del servicio")
    @Column(name = "comentario_servicio")    
    private String comentario_servicio;

    public PQRS() {}

    public int getIdPqrs() { return idPqrs; }
    public void setIdPqrs(int idPqrs) { this.idPqrs = idPqrs; }

    public String getComentario_servicio() { return comentario_servicio; }
    public void setComentario_servicio(String comentario_servicio) { this.comentario_servicio = comentario_servicio; }

    public String getCalificacion_servicio() { return calificacion_servicio; }
    public void setCalificacion_servicio(String calificacion_servicio) { this.calificacion_servicio = calificacion_servicio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getFecha_respuesta() { return fecha_respuesta; }
    public void setFecha_respuesta(double fecha_respuesta) { this.fecha_respuesta = fecha_respuesta; }

    public String getRespuesta_admin() { return respuesta_admin; }
    public void setRespuesta_admin(String respuesta_admin) { this.respuesta_admin = respuesta_admin; }  

    public Double getFecha() { return fecha; }
    public void setFecha(Double fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }  

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getTipo() { return tipo; }    public void setTipo(String tipo) { this.tipo = tipo; }


}