package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
public class Empleados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEGAJO")
    private Long legajo;
    @Column(name = "NOMBRE")
    @Setter
    private String nombre;
    @Setter
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "TELEFONO_CONTACTO")
    @Setter
    private Long telefono_contacto;


    public Empleados(String apellido, Long legajo, String nombre, List<Pruebas> pruebas, Long telefono_contacto) {
        this.apellido = apellido;
        this.legajo = legajo;
        this.nombre = nombre;
        this.telefono_contacto = telefono_contacto;
    }

    @Override
    public String toString() {
        return "Empleados{" +
                "legajo=" + legajo +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono_contacto=" + telefono_contacto +
                '}';
    }

    public Empleados() {
        super();
    }
}
