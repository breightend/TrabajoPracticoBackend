package com.example.demo.controlers;

import com.example.demo.domain.model.Notificacion;
import com.example.demo.domain.model.Vehiculos;
import com.example.demo.domain.services.NotificacionesService;
import com.example.demo.domain.services.VehiculosService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/notificacion")
public class NotificacionController {
    private final NotificacionesService notificacionesService;
    private final VehiculosService vehiculosService;
    @Autowired
    public NotificacionController(NotificacionesService notificacionesService, VehiculosService vehiculosService){
        this.notificacionesService = notificacionesService;
        this.vehiculosService = vehiculosService;
    }

    @GetMapping
    public List<Notificacion> obtenerNotificacion() { return notificacionesService.getNotificaciones(); }

    @PostMapping(path = "/{id}?precio")
    public void enviarNotificacion(@RequestParam Long id_vehiculo){
        Vehiculos vehiculo = vehiculosService.findByID(id_vehiculo);
        Notificacion notificacion = notificacionesService.generarNotificacion(vehiculo);
        notificacionesService.notificar(notificacion);
    }



}
