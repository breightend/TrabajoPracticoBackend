package com.example.demo.controlers;

import com.example.demo.domain.model.Empleados;
import com.example.demo.domain.services.EmpleadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/empleados")
public class EmpleadosController {

    private final EmpleadosService empleadosService;

    @Autowired
    public EmpleadosController(EmpleadosService empleadosService) {
        this.empleadosService = empleadosService;
    }

    @GetMapping
    public List<Empleados> getEmpleados(){
        return empleadosService.getEmpleados();
    }

    @PostMapping
    public void postEmpleado(@RequestBody Empleados empleado){
        empleadosService.addNewEmpleado(empleado);
    }
}
