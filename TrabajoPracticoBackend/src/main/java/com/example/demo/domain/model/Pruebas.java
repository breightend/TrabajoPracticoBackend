package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Pruebas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Atributos
    private Integer id;
    @Setter
    @Getter
    private String fecha_hora_inicio;
    @Setter
    @Getter
    private String fehca_hora_fin;
    @Setter
    @Getter
    private String comentarios;

    //Relaciones
    //Interesados
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interesados")
    @Setter
    @Getter
    private Interesados interesados;
    //Empleados
    @OneToOne
    @Setter
    @Getter
    private Empleados empleados;
    //Prueba
    @OneToOne
    @Setter
    @Getter
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
