package com.example.demo.domain.services;

import com.example.demo.domain.model.Posiciones;
import com.example.demo.domain.model.Vehiculos;
import com.example.demo.repositories.interfaces.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehiculosService {

    private final VehiculoRepository vehiculoRepository;
    private final PosicionesService posicionesService;

    @Autowired
    public VehiculosService(VehiculoRepository vehiculoRepository, PosicionesService posicionesService) {
        this.vehiculoRepository = vehiculoRepository;
        this.posicionesService = posicionesService;
    }

    public Vehiculos findByID(Long Id){
        return this.vehiculoRepository.findById(Id).orElseThrow();
    }

    public List<Vehiculos> findAll() {
        return this.vehiculoRepository.findAll();
    }

    public Posiciones obtenerPosicionActual(Long vehiculoId){
        try{
            return posicionesService.getPosicionActualVehiculo(vehiculoId);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}
