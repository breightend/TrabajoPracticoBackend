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

    public Pruebas(String comentarios, Empleados empleados, String fecha_hora_inicio, String fehca_hora_fin, Integer id, Vehiculos id_vehiculo, Interesados interesados) {
        this.comentarios = comentarios;
        this.empleados = empleados;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fehca_hora_fin = fehca_hora_fin;
        this.id = id;
        this.id_vehiculo = id_vehiculo;
        this.interesados = interesados;
    }

    public Pruebas() {
        super();
    }
}
