package com.TrabajoPractico.Reportes.model;

import lombok.Data;

@Data
public class ZonasRestringidas {
    private Coordenadas noroeste;
    private Coordenadas sureste;

    public ZonasRestringidas(Coordenadas noroeste, Coordenadas sureste) {
        this.noroeste = noroeste;
        this.sureste = sureste;
    }
}
