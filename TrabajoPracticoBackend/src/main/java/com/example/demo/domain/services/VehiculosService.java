package com.example.demo.domain.services;

import com.example.demo.domain.model.Posiciones;
import com.example.demo.domain.model.Vehiculos;
import com.example.demo.repositories.interfaces.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehiculosService {

    private final VehiculoRepository vehiculoRepository;
    private final PosicionesService posicionesService;
    private final RestTemplate restTemplate;

    @Autowired
    public VehiculosService(VehiculoRepository vehiculoRepository, PosicionesService posicionesService, RestTemplate restTemplate) {
        this.vehiculoRepository = vehiculoRepository;
        this.posicionesService = posicionesService;
        this.restTemplate = restTemplate;
    }

    public Vehiculos findByID(Long Id){
        return this.vehiculoRepository.findById(Id).orElseThrow();
    }

    public List<Vehiculos> findAll() {
        return this.vehiculoRepository.findAll();
    }

    public Posiciones obtenerPosicionActual(Long vehiculoId){
        try{
            return posicionesService.getPosicionActualVehiculo(vehiculoId);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    public double[] obtenerInfoApi(){
        String url = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

        try {
            // Realizar la solicitud GET y obtener la respuesta JSON
            String response = restTemplate.getForObject(url, String.class);

            // Procesar la respuesta JSON con Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);

            // Acceso a los datos
            System.out.println("Coordenadas Agencia:");
            System.out.println("Latitud: " + jsonNode.get("coordenadasAgencia").get("lat"));
            System.out.println("Longitud: " + jsonNode.get("coordenadasAgencia").get("lon"));

            System.out.println("Zonas Restringidas:");
            for (JsonNode zona : jsonNode.get("zonasRestringidas")) {
                System.out.println("Noroeste: Lat " + zona.get("noroeste").get("lat") + ", Lon " + zona.get("noroeste").get("lon"));
                System.out.println("Sureste: Lat " + zona.get("sureste").get("lat") + ", Lon " + zona.get("sureste").get("lon"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
