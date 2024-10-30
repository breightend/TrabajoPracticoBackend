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

import java.io.InvalidObjectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @GetMapping(path = "{id}")
    public ResponseEntity<Pruebas> getPruebasById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(pruebasService.getPruebaById(id));
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> postPruebasService(@RequestParam Long idInteresado, Long LegajoEmpleado,
                                                     Long idVehiculo) {
        try{
            Vehiculos vehiculo = vehiculosService.findByID(idVehiculo);
            Interesados interesado = interesadosService.getInteresadoById(idInteresado);
            Empleados empleado = empleadosService.getEmpleadoById(LegajoEmpleado);

            if(pruebasService.encontrarPruebaEnCursoConEmpleado(empleado.getLegajo()) ||
            pruebasService.encontrarPruebaEnCursoConInteresado(interesado.getId()) ||
            pruebasService.encontrarPruebaEnCursoConVehiculo(vehiculo.getId())){
                return ResponseEntity.status(403).body("Vehiculo, empleado o interesado se encuentra en una prueba existente");
            }

            if(interesado.licenciaVencida()){
                return ResponseEntity.status(403).body("Interesado con licencia vencida");
            }

            if(interesado.getRestringido()){
                return ResponseEntity.status(403).body("Interesado Restringido para realizar pruebas automovilisticas");
            }

            LocalDateTime now = LocalDateTime.now();

            Pruebas nuevaPrueba = new Pruebas(empleado, vehiculo, interesado, now);

            this.pruebasService.createPrueba(nuevaPrueba);

            return ResponseEntity.status(203).body("Objeto creado por exito");

        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Vehiculo, interesado o empleado no encontrados, revise su input");
        }
    }

    @GetMapping(path = "/enCurso")
    public ResponseEntity<Object> getPruebasEnCurso() {
        try{
            return ResponseEntity.ok().body(pruebasService.getPruebasOnCourse());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(403).body("No hay pruebas en curso actualmente");
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Object> finalizarPruebas(@PathVariable Long id, @RequestParam Optional<String> comentario) {
        try{
            System.out.println();
            return ResponseEntity.ok(pruebasService.finalizarPrueba(id, comentario));
        }catch (InvalidObjectException | NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
