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


}
