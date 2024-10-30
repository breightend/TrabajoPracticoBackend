package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Getter
    private Integer id;
    @Column(name = "PATENTE")
    @Getter
    private String patente;

    //Relaciones
    //Modelos
    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "ID_MODELO")
    private Modelos modelos;

    @Column(name = "ANIO")
    @Getter
    @Setter
    private Integer anio;

    public Vehiculos(Integer id, Modelos modelos, String patente) {
        this.id = id;
        this.modelos = modelos;
        this.patente = patente;
    }

    public Vehiculos() {
        super();
    }
}
