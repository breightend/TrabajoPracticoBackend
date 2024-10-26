package com.example.demo.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Para hacer que sea un enrutador
@RequestMapping("/pruebas")
public class TestControler {

    @GetMapping
    public String saludo(){
        return "hola";
    }
}
