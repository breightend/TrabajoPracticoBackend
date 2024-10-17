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

}
