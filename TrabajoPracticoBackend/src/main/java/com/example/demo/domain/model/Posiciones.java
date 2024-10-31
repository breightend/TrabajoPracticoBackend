package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Posiciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FECHA_HORA")
    private String fecha_hora;
    @Column(name = "LATITUD")
    private Double latitud;

    @Override
    public String toString() {
        return "Posiciones{" +
                "id=" + id +
                ", fecha_hora='" + fecha_hora + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", id_vehiculo=" + id_vehiculo +
                '}';
    }

    @Column(name = "LONGITUD")
    private Double longitud;

    @OneToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculos id_vehiculo;

    public Posiciones(String fecha_hora, Vehiculos id_vehiculo, Double latitud, Double longitud) {
        this.fecha_hora = fecha_hora;
        this.id_vehiculo = id_vehiculo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Posiciones() {
        super();
    }
}
