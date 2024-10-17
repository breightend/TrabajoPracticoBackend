package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Marcas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Atributos
    private Integer id;
    private String nombre;



    public Marcas(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Marcas() {
        super();
    }
}
