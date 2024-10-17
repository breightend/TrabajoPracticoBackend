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

    public Interesados(String apellido, String fecha_vencimiento, Integer id, String nombre, Integer nro_licencia, Set<Pruebas> pruebas, Integer restringido, Integer tipo_documento) {
        this.apellido = apellido;
        this.fecha_vencimiento = fecha_vencimiento;
        this.id = id;
        this.nombre = nombre;
        this.nro_licencia = nro_licencia;
        this.pruebas = pruebas;
        this.restringido = restringido;
        this.tipo_documento = tipo_documento;
    }

    public Interesados() {
        super();
    }
}
