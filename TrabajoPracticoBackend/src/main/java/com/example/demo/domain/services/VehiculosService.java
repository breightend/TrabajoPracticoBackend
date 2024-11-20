package com.example.demo.domain.services;

import com.example.demo.domain.model.*;
import com.example.demo.repositories.interfaces.VehiculoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InvalidObjectException;
import java.util.*;

@Service
public class VehiculosService {

    private final VehiculoRepository vehiculoRepository;
    private final PosicionesService posicionesService;
    private final RestTemplate restTemplate;
    private final PruebasService pruebasService;
    private final NotificacionesService notificacionesService;
    private final EmpleadosService empleadosService;
    private final InteresadosService interesadosService;
    private final AccesoAPI accesoAPI;


    @Autowired
    public VehiculosService(InteresadosService interesadosService, EmpleadosService empleadosService, NotificacionesService notificacionesService, VehiculoRepository vehiculoRepository, PosicionesService posicionesService, RestTemplate restTemplate, PruebasService pruebasService, AccesoAPI accesoAPI) {
        this.vehiculoRepository = vehiculoRepository;
        this.posicionesService = posicionesService;
        this.restTemplate = restTemplate;
        this.pruebasService = pruebasService;
        this.notificacionesService = notificacionesService;
        this.empleadosService = empleadosService;
        this.interesadosService = interesadosService;
        this.accesoAPI = accesoAPI;
    }

    public Vehiculos findByID(Long Id) {
        return this.vehiculoRepository.findById(Id).orElseThrow();
    }

    public List<Vehiculos> findAll() {
        return this.vehiculoRepository.findAll();
    }

    public Posiciones obtenerPosicionActual(Long vehiculoId) {
        try {
            Coordenadas max = accesoAPI.getRadioAdmitido();
            Pruebas pruebaEnCurso = pruebasService.recibirPruebasEnCursoConVehiculo(vehiculoId);
            if (posicionesService.getPosicionActualVehiculo(vehiculoId).dentroDelLimite(max.getLatitud(), max.getLongitud())) {
                return posicionesService.getPosicionActualVehiculo(vehiculoId);
            } else {
                long telefonoEmpleado = pruebaEnCurso.getEmpleado().getTelefono_contacto();
                String patenteVehiculo = this.findByID(vehiculoId).getPatente();
                notificacionesService.generarNotificacionACelularAdvertencia(telefonoEmpleado, patenteVehiculo);
                pruebaEnCurso.getInteresados().setRestringido(true);
                pruebaEnCurso.setIncidentes(true);
                interesadosService.saveInteresado(pruebaEnCurso.getInteresados());
                return posicionesService.getPosicionActualVehiculo(vehiculoId);
            }
        } catch (NoSuchElementException e) {
            return posicionesService.getPosicionActualVehiculo(vehiculoId);
        } catch (InvalidObjectException e) {
            throw new RuntimeException(e);
        }
    }


}