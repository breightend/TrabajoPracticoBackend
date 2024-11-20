package com.TrabajoPractico.Reportes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Modelos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Getter
    private Long id;
    @Column(name = "DESCRIPCION")
    @Getter
    @Setter
    private String descripcion;
    //Relacione
    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "ID", name = "ID_MARCA")
    private Marcas marcas;


    public Modelos(String descripcion, Long id, Marcas marcas) {
        this.descripcion = descripcion;
        this.id = id;
        this.marcas = marcas;
    }

    public Modelos() {
        super();
    }
}
