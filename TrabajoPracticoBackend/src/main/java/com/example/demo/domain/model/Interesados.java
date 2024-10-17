package com.example.demo.domain.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Interesados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tipo_documento;
    private String nombre;
    private String apellido;
    private Integer restringido;
    private Integer nro_licencia;
    private String fecha_vencimiento;
    @OneToMany(mappedBy = "interesados")
    private Set<Pruebas> pruebas;
}
