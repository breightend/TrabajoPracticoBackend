package com.example.demo.controlers;

import com.example.demo.domain.model.Interesados;
import com.example.demo.domain.services.InteresadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.util.List;

@RestController
@RequestMapping(path = "/interesados")
public class InteresadosController {

    private final InteresadosService interesadosService;

    @Autowired
    public InteresadosController(InteresadosService interesadosService) {
        this.interesadosService = interesadosService;
    }

    @GetMapping
    public List<Interesados> obtenerInteresados(){
        return interesadosService.getAllInteresados();
    }

    @PostMapping
    public ResponseEntity<Object> guardarInteresado(@RequestBody Interesados interesado){
        try{
            interesadosService.saveInteresado(interesado);
            return ResponseEntity.ok("El interesado se guardo correctamente");
        }catch (InvalidObjectException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> eliminarInteresadoById(@RequestParam long id){
        try{
            interesadosService.deleteInteresadoById(id);
            return ResponseEntity.ok("El interesado se elimino correctamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
