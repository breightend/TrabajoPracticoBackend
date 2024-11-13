package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "Notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "Texto")
    private String texto;
    @Setter
    @Column(name = "Telefono")
    private long telefono;

    public Notificacion(String texto, long telefono){
        this.texto = texto;
        this.telefono = telefono;
    }


    public Notificacion() {

    }
}
