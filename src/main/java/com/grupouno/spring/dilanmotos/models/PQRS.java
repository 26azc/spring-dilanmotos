package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entidad que representa un registro de PQRS (Peticiones, Quejas, Reclamos y Sugerencias).
 * 
 * <p>Se mapea a la tabla <b>pqrs</b> en la base de datos mediante JPA/Hibernate.
 * Cada registro contiene información sobre el usuario que lo envía, el tipo de solicitud,
 * su descripción, las respuestas del administrador y la calificación del servicio.</p>
 * 
 * <p>Campos principales:</p>
 * <ul>
 *   <li><b>idPqrs</b>: Identificador único del PQRS (PK).</li>
 *   <li><b>idUsuario</b>: Identificador del usuario que envía la solicitud.</li>
 *   <li><b>tipo</b>: Tipo de solicitud (Petición, Queja, Reclamo, Sugerencia).</li>
 *   <li><b>asunto</b>: Asunto principal del PQRS.</li>
 *   <li><b>descripcion</b>: Detalle de la solicitud.</li>
 *   <li><b>fecha</b>: Fecha en que se envió la solicitud.</li>
 *   <li><b>respuesta_admin</b>: Respuesta proporcionada por el administrador.</li>
 *   <li><b>fecha_respuesta</b>: Fecha en que se respondió la solicitud.</li>
 *   <li><b>calificacion_servicio</b>: Calificación otorgada por el usuario al servicio recibido.</li>
 *   <li><b>comentario_usuario</b>: Comentario adicional del usuario.</li>
 *   <li><b>comentario_servicio</b>: Comentario del administrador sobre el servicio.</li>
 *   <li><b>estado</b>: Estado actual del PQRS (pendiente, atendido, cerrado, etc.).</li>
 * </ul>
 * 
 * @author Neyder Estiben Manrique Alvarez
 * @version 1.0
 */
@Entity
@Table(name = "pqrs")
public class PQRS {

    /** Identificador único del PQRS. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pqrs")
    private Integer idPqrs;

    /** Identificador del usuario que envía la solicitud. */
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /** Tipo de solicitud (Petición, Queja, Reclamo, Sugerencia). Obligatorio. */
    @NotBlank
    @Column(name = "tipo")
    private String tipo;

    /** Asunto principal del PQRS. Obligatorio. */
    @NotBlank
    @Column(name = "asunto")
    private String asunto;

    /** Descripción detallada de la solicitud. Obligatoria. */
    @NotBlank
    @Column(name = "descripcion")
    private String descripcion;

    /** Fecha en que se envió la solicitud. */
    @Column(name = "fecha_envio")
    private LocalDateTime fecha;

    /** Respuesta proporcionada por el administrador. */
    @Column(name = "respuesta_admin")
    private String respuesta_admin;

    /** Fecha en que se respondió la solicitud. */
    @Column(name = "fecha_respuesta")
    private LocalDateTime fecha_respuesta;

    /** Calificación otorgada por el usuario al servicio recibido. */
    @Column(name = "calificacion_servicio")
    private String calificacion_servicio;

    /** Comentario adicional del usuario. Obligatorio. */
    @NotBlank
    @Column(name = "comentario_usuario")
    private String comentario_usuario;

    /** Comentario del administrador sobre el servicio. */
    @Column(name = "comentario_servicio")
    private String comentario_servicio;

    /** Estado actual del PQRS (pendiente, atendido, cerrado, etc.). */
    @Column(name = "estado")
    private String estado;

    /** Constructor vacío requerido por JPA. */
    public PQRS() {}

    // ------------------- Getters y Setters -------------------

    public Integer getIdPqrs() { return idPqrs; }
    public void setIdPqrs(Integer idPqrs) { this.idPqrs = idPqrs; }

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

    public String getCalificacion_servicio() { return calificacion_servicio; }
    public void setCalificacion_servicio(String calificacion_servicio) { this.calificacion_servicio = calificacion_servicio; }

    public String getComentario_usuario() { return comentario_usuario; }
    public void setComentario_usuario(String comentario_usuario) { this.comentario_usuario = comentario_usuario; }

    public String getComentario_servicio() { return comentario_servicio; }
    public void setComentario_servicio(String comentario_servicio) { this.comentario_servicio = comentario_servicio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
