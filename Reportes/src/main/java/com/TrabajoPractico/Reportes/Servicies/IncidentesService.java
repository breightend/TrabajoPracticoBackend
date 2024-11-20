package com.TrabajoPractico.Reportes.Servicies;

import com.TrabajoPractico.Reportes.Repositories.EmpleadosRepository;
import com.TrabajoPractico.Reportes.Repositories.PruebasRepositories;
import com.TrabajoPractico.Reportes.model.Empleados;
import com.TrabajoPractico.Reportes.model.Pruebas;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IncidentesService {

    private final PruebasRepositories pruebasRepositories;
    private final EmpleadosRepository empleadosRepository;

    public IncidentesService(PruebasRepositories pruebasRepositories, EmpleadosRepository empleadosRepository) {
        this.pruebasRepositories = pruebasRepositories;
        this.empleadosRepository = empleadosRepository;
    }

    public List<Pruebas> getPruebasConInicidentes(){
        return this.pruebasRepositories.getPruebasConIncidentes();
    }

    public Empleados findEmpleadoById(long id) throws NoSuchElementException {
        return empleadosRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
