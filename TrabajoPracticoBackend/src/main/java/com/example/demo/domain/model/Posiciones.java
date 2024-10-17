package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Posiciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fecha_hora;
    private Integer latitud;
    private Integer longitud;

    @OneToOne
    private Vehiculos id_vehiculo;
}
