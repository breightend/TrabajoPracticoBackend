package com.TrabajoPractico.Reportes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Pruebas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Atributos
    @Column(name = "ID")
    private long id;
    @Column(name = "FECHA_HORA_INICIO")
    private LocalDateTime fecha_hora_inicio;            //no tendria que ser String??
    @Column(name = "FECHA_HORA_FIN")
    private LocalDateTime fecha_hora_fin;
    @Column(name = "COMENTARIOS")
    private String comentarios;

    @Column(name = "INCIDENTES")
    private boolean incidentes;


    //Relaciones
    //Interesados
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "interesados")
    @OneToOne
    @JoinColumn(referencedColumnName = "ID", name = "ID_INTERESADO")
    private Interesados interesados;
    //Empleados
    @OneToOne
    @JoinColumn(referencedColumnName = "LEGAJO", name = "ID_EMPLEADO")
    private Empleados empleado;
    //Prueba
    @OneToOne
    @JoinColumn(referencedColumnName = "ID", name = "ID_VEHICULO")
    private Vehiculos id_vehiculo;

    public Pruebas(String comentarios, Empleados empleados, LocalDateTime fecha_hora_inicio, LocalDateTime fehca_hora_fin, Long id, Vehiculos id_vehiculo, Interesados interesados) {
        this.comentarios = comentarios;
        this.empleado = empleados;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fehca_hora_fin;
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
