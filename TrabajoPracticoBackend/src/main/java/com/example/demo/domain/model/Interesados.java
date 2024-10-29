package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Interesados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    @Getter
    @Column(name = "TIPO_DOCUMENTO")
    private String tipo_documento;
    @Setter
    @Getter
    @Column(name = "DOCUMENTO")
    private String documento;
    @Setter
    @Getter
    @Column(name = "NOMBRE")
    private String nombre;
    @Setter
    @Getter
    @Column(name = "APELLIDO")

    private String apellido;
    @Setter
    @Getter
    @Column(name = "RESTRINGIDO")

    private Boolean restringido;
    @Setter
    @Getter
    @Column(name = "NRO_LICENCIA")

    private Integer nro_licencia;
    @Setter
    @Getter
    @Column(name = "FECHA_VENCIMIENTO_LICENCIA")

    private String fecha_vencimiento_licencia;
//    @OneToMany(mappedBy = "interesados")
//    private Set<Pruebas> pruebas;

    public Interesados(String apellido, String fecha_vencimiento, Integer id, String nombre, Integer nro_licencia, Boolean   restringido, String tipo_documento) {
        this.apellido = apellido;
        this.fecha_vencimiento_licencia = fecha_vencimiento;
        this.id = id;
        this.nombre = nombre;
        this.nro_licencia = nro_licencia;
//        this.pruebas = pruebas;
        this.restringido = restringido;
        this.tipo_documento = tipo_documento;
    }

    public Interesados() {
        super();
    }

    public boolean licenciaVencida(){
        LocalDate fechaLocal = LocalDate.parse(fecha_vencimiento_licencia);
        return fechaLocal.isAfter(LocalDate.now());

    }
}
