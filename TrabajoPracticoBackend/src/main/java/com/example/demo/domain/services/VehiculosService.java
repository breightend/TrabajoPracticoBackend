package com.example.demo.domain.services;

import com.example.demo.domain.model.Vehiculos;
import com.example.demo.repositories.interfaces.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class VehiculosService {

    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculosService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public Vehiculos findByID(Long Id){
        return this.vehiculoRepository.findById(Id).orElseThrow();
    }
}
