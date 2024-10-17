package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Modelos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;
    //Relaciones
    @ManyToOne(cascade = CascadeType.ALL)
    private Marcas id_marca;


    public Modelos(String descripcion, Integer id, Marcas id_marca) {
        this.descripcion = descripcion;
        this.id = id;
        this.id_marca = id_marca;
    }

    public Modelos() {

    }
}
