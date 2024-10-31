package com.example.demo.controlers;

import com.example.demo.domain.model.Vehiculos;
import com.example.demo.domain.services.VehiculosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/vehiculos")
public class VehiculoController {

    private final VehiculosService vehiculosService;

    @Autowired
    public VehiculoController(VehiculosService vehiculosService) {
        this.vehiculosService = vehiculosService;
    }

    @GetMapping
    public Vehiculos findbyId(@RequestParam Long id) {
        return ResponseEntity.ok(vehiculosService.findByID(id)).getBody();

    }

}
