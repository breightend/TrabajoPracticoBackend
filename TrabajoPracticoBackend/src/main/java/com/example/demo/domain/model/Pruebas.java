package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Pruebas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Atributos
    @Column(name = "ID")
    private Integer id;
    @Setter
    @Getter
    @Column(name = "FECHA_HORA_INICIO")
    private LocalDateTime fecha_hora_inicio;
    @Setter
    @Getter
    @Column(name = "FECHA_HORA_FIN")
    private LocalDateTime fehca_hora_fin;
    @Setter
    @Getter
    @Column(name = "COMENTARIOS")
    private String comentarios;


    //Relaciones
    //Interesados
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "interesados")
    @OneToOne
    @Setter
    @Getter
    @JoinColumn(referencedColumnName = "ID")
    private Interesados interesados;
    //Empleados
    @OneToOne
    @Setter
    @Getter
    @JoinColumn(referencedColumnName = "LEGAJO")
    private Empleados empleado;
    //Prueba
    @OneToOne
    @Setter
    @Getter
    @JoinColumn(referencedColumnName = "ID")
    private Vehiculos id_vehiculo;

    public Pruebas(String comentarios, Empleados empleados, LocalDateTime fecha_hora_inicio, LocalDateTime fehca_hora_fin, Integer id, Vehiculos id_vehiculo, Interesados interesados) {
        this.comentarios = comentarios;
        this.empleado = empleados;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fehca_hora_fin = fehca_hora_fin;
        this.id = id;
        this.id_vehiculo = id_vehiculo;
        this.interesados = interesados;
    }

    public Pruebas(Empleados empleado, Vehiculos id_vehiculo, Interesados interesados, LocalDateTime fecha_hora_inicio) {
        this.empleado = empleado;
        this.id_vehiculo = id_vehiculo;
        this.interesados = interesados;
        this.fecha_hora_inicio = fecha_hora_inicio;
    }

    public Pruebas() {
        super();
    }
}
