package com.example.demo.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Empleados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEGAJO")
    private Integer legajo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "TELEFONO_CONTACTO")
    private Long telefono_contacto;

    public Long getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(Long telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getLegajo() {
        return legajo;
    }

    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    public Empleados(String apellido, Integer legajo, String nombre, List<Pruebas> pruebas, Long telefono_contacto) {
        this.apellido = apellido;
        this.legajo = legajo;
        this.nombre = nombre;
        this.telefono_contacto = telefono_contacto;
    }

    @Override
    public String toString() {
        return "Empleados{" +
                "legajo=" + legajo +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono_contacto=" + telefono_contacto +
                '}';
    }

    public Empleados() {
        super();
    }
}
