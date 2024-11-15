package com.example.demo.domain.services;

import com.example.demo.domain.model.Coordenadas;
import com.example.demo.domain.model.ZonasRestringidas;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccesoAPI {
    private String url = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public Coordenadas getCoordenadaAgencia(){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // Procesar la respuesta JSON con Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            double longitud = Double.parseDouble(jsonNode.get("coordenadasAgencia").get("lon").toString());
            double latitud = Double.parseDouble(jsonNode.get("coordenadasAgencia").get("lat").toString());
            return new Coordenadas(latitud, longitud);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Coordenadas getRadioAdmitido() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // Procesar la respuesta JSON con Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            Integer radioAdmitidoKm = Integer.parseInt(jsonNode.get("radioAdmitidoKm").toString());
            double latitudAgencia = Double.parseDouble(jsonNode.get("coordenadasAgencia").get("lat").toString());
            return calcularCoordenadas(radioAdmitidoKm, latitudAgencia);
        } catch (JsonMappingException ex) {
            throw new RuntimeException(ex);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<ZonasRestringidas> getZonasRestringidas() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // Procesar la respuesta JSON con Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            List<ZonasRestringidas> zonasRestringidas = new ArrayList<>();
            JsonNode coordenadas = jsonNode.get("zonasRestringidas");
            coordenadas.forEach(zona -> {
                double latitudNoroeste = Double.parseDouble(zona.get("noroeste").get("lat").toString());
                double longitudNoroeste = Double.parseDouble(zona.get("noroeste").get("lat").toString());

                double latitudSureste = Double.parseDouble(zona.get("sureste").get("lat").toString());
                double longitudSureste = Double.parseDouble(zona.get("sureste").get("lat").toString());

                Coordenadas coordenadasNoroeste = new Coordenadas(latitudNoroeste, longitudNoroeste);
                Coordenadas coordenadasSureste = new Coordenadas(latitudSureste, longitudSureste);

                zonasRestringidas.add(new ZonasRestringidas(coordenadasNoroeste, coordenadasSureste));
            });

            return zonasRestringidas;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //Coordenadas màximas de movimiento que el vehiculo tiene permitido.
    public Coordenadas calcularCoordenadas(Integer radioAdmitidoKm, double latitud) {
        double latitudGrado = radioAdmitidoKm / 111.0;
        double longitudGrado = radioAdmitidoKm / 111.0 * Math.cos(Math.toRadians(latitud));
        return new Coordenadas(latitudGrado, longitudGrado);
    }
}
