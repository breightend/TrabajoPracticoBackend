package com.example.demo.controlers;

import com.example.demo.domain.model.Interesados;
import com.example.demo.domain.services.InteresadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void guardarInteresado(@RequestBody Interesados interesado){
        interesadosService.saveInteresado(interesado);
    }
}
