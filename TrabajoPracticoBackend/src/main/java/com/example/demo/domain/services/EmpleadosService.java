package com.example.demo.domain.services;

import com.example.demo.domain.model.Empleados;
import com.example.demo.domain.model.Pruebas;
import com.example.demo.repositories.interfaces.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadosService {

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadosService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }


    public List<Empleados> getEmpleados(){
        return empleadoRepository.findAll();
    }


    public void addNewEmpleado(Empleados empleado) {
        empleadoRepository.save(empleado);
    }

    public Empleados getEmpleadoById(long id) {
        return empleadoRepository.findById(id).orElseThrow();
    }
}
