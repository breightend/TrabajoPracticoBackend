package com.example.demo.domain.services;

import com.example.demo.domain.model.Notificacion;
import com.example.demo.domain.model.Vehiculos;
import com.example.demo.repositories.interfaces.NotificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Service
public class NotificacionesService {
    private final NotificacionesRepository notificacionesRepository;

    @Autowired
    public NotificacionesService(NotificacionesRepository notificacionesRepository) {
        this.notificacionesRepository = notificacionesRepository;
    }

    public void notificar(Notificacion notificacion) { notificacionesRepository.save(notificacion); }

    public List<Notificacion> getNotificaciones(){ return notificacionesRepository.findAll(); }

    public Notificacion getNotificacionById(long id) { return notificacionesRepository.findById(id).orElseThrow(); }

    public Notificacion generarNotificacion(Vehiculos vehiculo) {
        String texto = "¡Oferta especial en vehículos usados!\n " +
                "¡Hola! \n " +
                "Tenemos un vehiculo que puede interesarte \n" +
                "Marca: " + vehiculo.getModelos().getMarcas().getNombre() + "\n" +
                "Modelo: " + vehiculo.getModelos().getDescipcion() + "\n" +
                "¡Solo por esta semana ofrecemos financiamiento especial y descuento en la compra de este modelo! \n" +
                "Reserva tu prueba de manejo respondiendo a este mensaje, No dejes pasar esta oportunidad de llevarte tu próximo auto!";

        return new Notificacion(texto);
    }
}
