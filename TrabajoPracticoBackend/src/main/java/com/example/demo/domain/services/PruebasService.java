package com.example.demo.domain.services;

import com.example.demo.domain.model.Pruebas;
import com.example.demo.repositories.interfaces.PruebasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;

@Service
public class PruebasService {

    private final PruebasRepository pruebasRepository;

    @Autowired
    public PruebasService(PruebasRepository pruebasRepository) {
        this.pruebasRepository = pruebasRepository;
    }

    public void createPrueba(Pruebas prueba){
            pruebasRepository.save(prueba);

    }
}
