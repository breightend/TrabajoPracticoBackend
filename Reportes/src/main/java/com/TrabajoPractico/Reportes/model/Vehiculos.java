package com.TrabajoPractico.Reportes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PATENTE")
    private String patente;

    //Relaciones
    //Modelos
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "ID_MODELO")
    private Modelos modelos;

    @Column(name = "ANIO")
    @Setter
    private Integer anio;

    public Vehiculos(Long id, Modelos modelos, String patente) {
        this.id = id;
        this.modelos = modelos;
        this.patente = patente;
    }

    public Vehiculos() {
        super();
    }
}
