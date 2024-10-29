package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PATENTE")
    private String patente;

    //Relaciones
    //Modelos
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "ID")
    private Modelos modelos;

    @Column(name = "ANIO")
    private Integer anio;

    public Vehiculos(Integer id, Modelos modelos, String patente) {
        this.id = id;
        this.modelos = modelos;
        this.patente = patente;
    }

    public Vehiculos() {
        super();
    }
}
