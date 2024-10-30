package com.example.demo.controlers;

import com.example.demo.domain.model.Empleados;
import com.example.demo.domain.model.Interesados;
import com.example.demo.domain.model.Pruebas;
import com.example.demo.domain.model.Vehiculos;
import com.example.demo.domain.services.EmpleadosService;
import com.example.demo.domain.services.InteresadosService;
import com.example.demo.domain.services.PruebasService;
import com.example.demo.domain.services.VehiculosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/pruebas")
public class PruebasController {

    private final PruebasService pruebasService;
    private final VehiculosService vehiculosService;
    private final InteresadosService interesadosService;
    private final EmpleadosService empleadosService;

    @Autowired
    public PruebasController(PruebasService pruebasService, VehiculosService vehiculosService,
                             InteresadosService interesadosService, EmpleadosService empleadosService) {
        this.pruebasService = pruebasService;
        this.vehiculosService = vehiculosService;
        this.interesadosService = interesadosService;
        this.empleadosService = empleadosService;

    }

    @GetMapping
    public ResponseEntity<List<Pruebas>> getAllPruebas() {
        return ResponseEntity.ok().body(pruebasService.getPruebas());
    }

    @PostMapping
    public ResponseEntity<Object> postPruebasService(@RequestParam Long idInteresado, Long LegajoEmpleado,
                                                     Long idVehiculo) {
        try{
            Vehiculos vehiculo = vehiculosService.findByID(idVehiculo);
            Interesados interesado = interesadosService.getInteresadoById(idInteresado);
            Empleados empleado = empleadosService.getEmpleadoById(LegajoEmpleado);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime aft = LocalDateTime.now().plusHours(1);

            Pruebas nuevaPrueba = new Pruebas(empleado, vehiculo, interesado, now, aft);

            this.pruebasService.createPrueba(nuevaPrueba);

        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Vehiculo, interesado o empleado no encontrados, revise su input");
        }
        return ResponseEntity.status(403).build();
    }
}
