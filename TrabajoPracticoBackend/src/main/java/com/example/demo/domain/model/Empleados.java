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

}
