package com.grupouno.spring.dilanmotos.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCotizacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario; // Cambiado a la entidad Usuarios

    @Column(name = "producto") // Cambiado de @JoinColumn a @Column
    @NotBlank(message = "El producto es obligatorio")
    private String producto;

    @Column(name = "cantidad") // Cambiado de @JoinColumn a @Column
    @NotNull(message = "La cantidad es obligatoria")
    private int cantidad;

    @Column(name = "precio_unitario") // Cambiado de @JoinColumn a @Column
    @NotNull(message = "El precioUnitario es obligatorio")
    private Double precioUnitario;

    @Column(name = "fecha") // Cambiado de @JoinColumn a @Column
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Column(name = "producto_agregado") // Cambiado de @JoinColumn a @Column
    private boolean productoAgregado;

    public Cotizacion() {}

    public int getIdCotizacion() {return idCotizacion;}
    public void setIdCotizacion(int idCotizacion) {this.idCotizacion = idCotizacion;}

    // Cambiados los getters y setters para Usuarios
    public Usuarios getUsuarios() {return usuario;}
    public void setUsuarios(Usuarios usuario) {this.usuario = usuario;}

    // Método conveniente para obtener el ID del usuario
    public Integer getIdUsuarios() {
        return this.usuario != null ? this.usuario.getIdUsuario() : null;
    }

    public String getProducto() {return producto;}
    public void setProducto(String producto) {this.producto = producto;}

    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public Double getPrecioUnitario() {return precioUnitario;}
    public void setPrecioUnitario(Double precioUnitario) {this.precioUnitario = precioUnitario;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public boolean isProductoAgregado() {return productoAgregado;}
    public void setProductoAgregado(boolean productoAgregado) {this.productoAgregado = productoAgregado;}
}