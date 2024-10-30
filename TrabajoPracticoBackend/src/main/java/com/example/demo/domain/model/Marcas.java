package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Marcas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Atributos
    @Column(name = "ID")
    @Getter
    private Integer id;

    @Getter
    @Setter
    @Column(name = "NOMBRE")
    private String nombre;



    public Marcas(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Marcas() {
        super();
    }
}
