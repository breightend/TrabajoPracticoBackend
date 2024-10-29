package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Modelos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")

    private Integer id;
    @Column(name = "DESCRIPCION")

    private String descripcion;
    //Relaciones
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "ID")
    private Marcas marcas;


    public Modelos(String descripcion, Integer id, Marcas marcas) {
        this.descripcion = descripcion;
        this.id = id;
        this.marcas = marcas;
    }

    public Modelos() {

    }
}
