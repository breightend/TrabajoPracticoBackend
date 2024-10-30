package com.example.demo.domain.services;

import com.example.demo.domain.model.Pruebas;
import com.example.demo.repositories.interfaces.PruebasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.List;

@Service
public class PruebasService {

    private final PruebasRepository pruebasRepository;

    @Autowired
    public PruebasService(PruebasRepository pruebasRepository) {
        this.pruebasRepository = pruebasRepository;
    }

    public List<Pruebas> getPruebas() {
        return pruebasRepository.findAll();
    }

    public void createPrueba(Pruebas prueba){
        pruebasRepository.save(prueba);

    }
}
