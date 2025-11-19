package com.grupouno.spring.dilanmotos.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "mecanico")
public class Mecanico {

    @Id    
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int idMecanico;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria")
    @Column(name = "especialidad")
    private String especialidad;

    @NotBlank(message = "El telefono es obligatoria")
    @Column(name = "telefono")
    private String telefono;

    public Mecanico() {}

    public int getIdMecanico() {return idMecanico;}
    public void setIdMecanico(int idMecanico) {this.idMecanico = idMecanico;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEspecialidad() {return especialidad;}
    public void setEspecialidad(String especialidad) {this.especialidad = especialidad;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

}
