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
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehiculosService {

    private final VehiculoRepository vehiculoRepository;
    private final PosicionesService posicionesService;
    private final RestTemplate restTemplate;
    private final PruebasService pruebasService;
    private final NotificacionesService notificacionesService;
    private final EmpleadosService empleadosService;
    private final InteresadosService interesadosService;


    @Autowired
    public VehiculosService(InteresadosService interesadosService, EmpleadosService empleadosService, NotificacionesService notificacionesService,VehiculoRepository vehiculoRepository, PosicionesService posicionesService, RestTemplate restTemplate, PruebasService pruebasService) {
        this.vehiculoRepository = vehiculoRepository;
        this.posicionesService = posicionesService;
        this.restTemplate = restTemplate;
        this.pruebasService = pruebasService;
        this.notificacionesService = notificacionesService;
        this.empleadosService = empleadosService;
        this.interesadosService = interesadosService;
    }

    public Vehiculos findByID(Long Id){
        return this.vehiculoRepository.findById(Id).orElseThrow();
    }

    public List<Vehiculos> findAll() {
        return this.vehiculoRepository.findAll();
    }

    public Posiciones obtenerPosicionActual(Long vehiculoId){
        try{
            Coordenadas max = obtenerInfoApi("radioAdmitidoKm");
            Pruebas pruebaEnCurso = pruebasService.recibirPruebasEnCursoConVehiculo(vehiculoId);
            if (posicionesService.getPosicionActualVehiculo(vehiculoId).dentroDelLimite(max.getLatitud(), max.getLongitud())){
                return posicionesService.getPosicionActualVehiculo(vehiculoId);
            }else {
                long telefonoEmpleado = pruebaEnCurso.getEmpleado().getTelefono_contacto();
                notificacionesService.generarNotificacionACelular(telefonoEmpleado);
                pruebaEnCurso.getInteresados().setRestringido(true);
                interesadosService.saveInteresado(pruebaEnCurso.getInteresados());
                return posicionesService.getPosicionActualVehiculo(vehiculoId);
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (InvalidObjectException e) {
            throw new RuntimeException(e);
        }
    }

    public Coordenadas obtenerInfoApi(String infoRequerida)throws RuntimeException{
        String url = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

        try {
            if (infoRequerida == "coordenadasAgencia") {
                String response = restTemplate.getForObject(url, String.class);

                // Procesar la respuesta JSON con Jackson
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response);
                double longitud = Double.parseDouble( jsonNode.get("coordenadasAgencia").get("lon").toString());
                double latitud = Double.parseDouble( jsonNode.get("coordenadasAgencia").get("lat").toString());
                return new Coordenadas(latitud, longitud);

            }

            else if(infoRequerida == "radioAdmitidoKm"){
                String response = restTemplate.getForObject(url, String.class);

                // Procesar la respuesta JSON con Jackson
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response);
                Integer radioAdmitidoKm = Integer.parseInt( jsonNode.get("radioAdmitidoKm").toString());
                double latitudAgencia = Double.parseDouble(jsonNode.get("coordenadasAgencia").get("lat").toString());
                return calcularCoordenadas(radioAdmitidoKm, latitudAgencia);
            } else if (infoRequerida == "zonasRestringidas") {
                String response = restTemplate.getForObject(url, String.class);

                // Procesar la respuesta JSON con Jackson
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response);
                List<Coordenadas> coordenadasRestringidas = jsonNode.get("zonasRestringidas").forEach();
                double latitudAgencia = Double.parseDouble(jsonNode.get("coordenadasAgencia").get("lat").toString());
                return calcularCoordenadas(radioAdmitidoKm, latitudAgencia);
            }
            // Realizar la solicitud GET y obtener la respuesta JSON


        } catch (RuntimeException e) {
            throw new RuntimeException();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
//Coordenadas màximas de movimiento que el vehiculo tiene permitido.
    public Coordenadas calcularCoordenadas(Integer radioAdmitidoKm, double latitud) {
        double latitudGrado = radioAdmitidoKm/111.0;
        double longitudGrado = radioAdmitidoKm/111.0 * Math.cos(Math.toRadians(latitud));
        return new Coordenadas(latitudGrado, longitudGrado);

    }
}
