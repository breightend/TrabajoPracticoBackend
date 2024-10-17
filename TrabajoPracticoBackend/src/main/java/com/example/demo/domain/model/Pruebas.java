package com.example.demo.domain.model;

import jakarta.persistence.*;

@Entity
public class Pruebas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Atributos
    private Integer id;
    private String fecha_hora_inicio;
    private String fehca_hora_fin;
    private String comentarios;

    //Relaciones
    //Interesados
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interesados")
    private Interesados interesados;
    //Empleados
    @OneToOne
    private Empleados empleados;
    //Prueba
    @OneToOne
    private Vehiculos id_vehiculo;
}
