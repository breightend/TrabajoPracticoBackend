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
    private String tipo_documento;
    @Setter
    @Getter
    private String documento;
    @Setter
    @Getter
    private String nombre;
    @Setter
    @Getter
    private String apellido;
    @Setter
    @Getter
    private Boolean restringido;
    @Setter
    @Getter
    private Integer nro_licencia;
    @Setter
    @Getter
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
