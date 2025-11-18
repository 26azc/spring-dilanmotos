package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "productos") // Opcional - si la tabla en BD se llama diferente
public class Productos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    public int idProducto;
    
    @Column(name = "id_categoria")
    public int idCategoria;
    
    @Column(name = "id_marca")
    public int idMarca;
    
    @Column(name = "nombre")
    public String nombre;
    
    @Column(name = "descripcion")
    public String descripcion;
    
    @Column(name = "precio")
    public double precio;

    public Productos() {
    }

    public Productos(int idProducto, int idCategoria, int idMarca, String nombre, String descripcion, double precio) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}