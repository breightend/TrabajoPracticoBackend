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

    public Posiciones(String fecha_hora, Integer id, Vehiculos id_vehiculo, Integer latitud, Integer longitud) {
        this.fecha_hora = fecha_hora;
        this.id = id;
        this.id_vehiculo = id_vehiculo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Posiciones() {
        super();
    }
}
