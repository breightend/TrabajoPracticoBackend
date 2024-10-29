package com.example.demo.controlers;

import com.example.demo.domain.model.Pruebas;
import com.example.demo.domain.services.PruebasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/pruebas")
public class PruebasController {

    private final PruebasService pruebasService;

    @Autowired
    public PruebasController(PruebasService pruebasService) {
        this.pruebasService = pruebasService;
    }

    @PostMapping
    public ResponseEntity<Object> postPruebasService(@RequestBody Pruebas prueba) {
//        prueba.setFecha_hora_inicio(LocalDateTime.now());
        this.pruebasService.createPrueba(prueba);
        return ResponseEntity.status(403).build();
    }
}
