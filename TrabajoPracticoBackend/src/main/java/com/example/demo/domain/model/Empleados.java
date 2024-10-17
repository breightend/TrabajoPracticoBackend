package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Empleados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer legajo;
    private String nombre;
    private String apellido;
    private Integer telefono_contacto;
    @OneToOne
    private Pruebas pruebas;

    public Empleados(String apellido, Integer legajo, String nombre, Pruebas pruebas, Integer telefono_contacto) {
        this.apellido = apellido;
        this.legajo = legajo;
        this.nombre = nombre;
        this.pruebas = pruebas;
        this.telefono_contacto = telefono_contacto;
    }


    public Empleados() {
        super();
    }
}
