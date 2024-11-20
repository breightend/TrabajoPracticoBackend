package com.TrabajoPractico.Reportes.Servicies;

import com.TrabajoPractico.Reportes.Repositories.PruebasRepositories;
import com.TrabajoPractico.Reportes.Repositories.VehiculoRepository;
import com.TrabajoPractico.Reportes.model.Pruebas;
import com.TrabajoPractico.Reportes.model.Vehiculos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PruebasXVehiculosSerivce {

    private final PruebasRepositories pruebasRepositories;
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public PruebasXVehiculosSerivce(PruebasRepositories pruebasRepositories, VehiculoRepository vehiculoRepository) {
        this.pruebasRepositories = pruebasRepositories;
        this.vehiculoRepository = vehiculoRepository;

    }

    public List<Pruebas> encontrarPruebasConVehiculo(Long idVehiculo) throws NoSuchElementException {
        List<Pruebas> pruebas = this.pruebasRepositories.findAll();
        List<Pruebas> pruebasToReturn = new ArrayList<>();
        Iterator<Pruebas> iterator = pruebas.iterator();
        while(iterator.hasNext()){
            Pruebas prueba = iterator.next();
            if(prueba.getId_vehiculo().getId().equals(idVehiculo)){
                pruebasToReturn.add(prueba);
            }
        }
        if (pruebasToReturn.isEmpty()){
            throw new NoSuchElementException("No se encontraron pruebas con ese vehiculo");
        }else{
            return pruebasToReturn;
        }
    }

    public Vehiculos findVehiculoById(Long idVehiculo) throws NoSuchElementException {
        return this.vehiculoRepository.findById(idVehiculo).get();
    }
}
