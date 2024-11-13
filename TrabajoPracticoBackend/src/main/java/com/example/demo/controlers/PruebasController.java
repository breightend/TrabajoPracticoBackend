package com.example.demo.controlers;

import com.example.demo.domain.model.*;
import com.example.demo.domain.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.time.LocalDateTime;
import java.util.Iterator;
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
    private final PosicionesService posicionesService;

    @Autowired
    public PruebasController(PruebasService pruebasService, VehiculosService vehiculosService,
                             InteresadosService interesadosService, EmpleadosService empleadosService,
                             PosicionesService posicionesService) {
        this.pruebasService = pruebasService;
        this.vehiculosService = vehiculosService;
        this.interesadosService = interesadosService;
        this.empleadosService = empleadosService;
        this.posicionesService = posicionesService;

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
            return ResponseEntity.ok(pruebasService.finalizarPrueba(id, comentario));
        }catch (InvalidObjectException | NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/reportes/pruebasXvehiculos")
    public ResponseEntity<String> reporteDePruebasPorVehiculo(@RequestParam Long vehiculoId) throws IOException {
        List<Pruebas> pruebasConVehiculo = pruebasService.encontrarPruebasConVehiculo(vehiculoId);

        FileWriter fw = new FileWriter("src\\main\\resources\\ArchivoPruebasXVehiculos.txt");
        StringBuilder sb = new StringBuilder("Archivo de información de pruebas para el vehiculo" + vehiculosService.findByID(vehiculoId).getPatente()).append("\n");
        sb.append("---------------------------------------------------------------");
        fw.append(sb.toString());
        sb.delete(0, sb.length());

        Iterator<Pruebas> iterator = pruebasConVehiculo.iterator();
        while (iterator.hasNext()) {
            Pruebas prueba = iterator.next();
            sb.append("ID Prueba: ").append(prueba.getId()).append("\n");
            sb.append("Nombre de Empleado encargado de la prueba: ").append(prueba.getEmpleado().getNombre()).append("\n");
            sb.append("Nombre del interesado que solicitó la prueba: ").append(prueba.getInteresados().getNombre()).append("\n");
            sb.append("Fecha y hora de inicio de la prueba: ").append(prueba.getFecha_hora_inicio()).append("\n");
            sb.append("Fecha y hora de fin de la prueba: ");
            if (prueba.getFecha_hora_fin() != null) {
                sb.append(prueba.getFecha_hora_fin()).append("\n");
            }else{
                sb.append("La prueba se encuentra en curso").append("\n");
            }
            sb.append("Comentarios respecto a la prueba realizada: ").append(prueba.getComentarios()).append("\n");

            sb.append("------------------------------------------------------------------------------------------------------------------").append("\n");
            fw.append(sb.toString());
            sb.delete(0, sb.length());
        }

        fw.flush();

        return ResponseEntity.ok("El archivo fue creado correctamente en la carpeta del proyecto");
    }

    @PostMapping(path = "avanzarVehiculo")
    public ResponseEntity<String> avanzarVehiculo(@RequestParam Long vehiculoId, Double latitud, Double longitud){
        try{
            Posiciones posicion = pruebasService.AvanzarVehiculoEnPrueba(vehiculoId, latitud, longitud);
            posicionesService.savePosiciones(posicion);
            return ResponseEntity.ok(posicion.toString());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(403).body("Vehiculo sin pruebas en curso asignadas");
        }

    }
}
