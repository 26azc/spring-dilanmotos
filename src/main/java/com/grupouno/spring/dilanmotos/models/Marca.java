package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Entidad que representa una marca de motos o productos.
 *
 * <p>Se mapea a la tabla <b>marca</b> en la base de datos.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Incluye el nombre de la marca.</li>
 *   <li>Relación uno a muchos con {@link Moto}.</li>
 *   <li>Relación uno a muchos con {@link Productos}.</li>
 * </ul>
 *
 * @author 26azc
 * @version 1.0
 */
@Entity
@Table(name = "marca")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private Integer idMarca;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moto> motos;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Productos> productos;

    // --- Getters y Setters ---
    public Integer getIdMarca() { return idMarca; }
    public void setIdMarca(Integer idMarca) { this.idMarca = idMarca; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Moto> getMotos() { return motos; }
    public void setMotos(List<Moto> motos) { this.motos = motos; }

    public List<Productos> getProductos() { return productos; }
    public void setProductos(List<Productos> productos) { this.productos = productos; }
}