package com.TrabajoPractico.Gateway.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder, @Value("${ruta-servicio-CarRental}") String uriVehiculos, @Value("${ruta-reportes-CarRental}") String uriReportes) {
        return builder.routes()
                .route(p -> p.path("/interesados/**").uri(uriVehiculos))
                .route(p -> p.path("/empleados/**").uri(uriVehiculos))
                .route(p -> p.path("/notificacion/**").uri(uriVehiculos))
                .route(p -> p.path("/pruebas/**").uri(uriVehiculos))
                .route(p -> p.path("/vehiculos/**").uri(uriVehiculos))
                .route(p -> p.path("/reportes/**").uri(uriReportes))
                .build();
    }


}



