package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String patente;

    //Relaciones
    //Modelos
    @ManyToOne(cascade = CascadeType.ALL)
    private Modelos id_modelo;

    public Vehiculos(Integer id, Modelos id_modelo, String patente) {
        this.id = id;
        this.id_modelo = id_modelo;
        this.patente = patente;
    }

    public Vehiculos() {
        super();
    }
}
