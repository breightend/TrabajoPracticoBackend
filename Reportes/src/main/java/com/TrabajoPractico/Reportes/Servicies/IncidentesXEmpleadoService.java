package com.TrabajoPractico.Reportes.Servicies;

import com.TrabajoPractico.Reportes.Repositories.PruebasRepositories;
import com.TrabajoPractico.Reportes.model.Pruebas;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IncidentesXEmpleadoService {

    private final PruebasRepositories pruebasRepositories;

    public IncidentesXEmpleadoService(PruebasRepositories pruebasRepositories) {
        this.pruebasRepositories = pruebasRepositories;
    }

    public List<Pruebas> getPruebasConIncidentesConEmpleado(long legajoEmpleado)throws NoSuchElementException {
        List<Pruebas> pruebasADevolver = pruebasRepositories.getPruebasConIncidentesConEmpleado(legajoEmpleado);
        if (pruebasADevolver.isEmpty()) {
            throw new NoSuchElementException("Este empleado no tiene pruebas con incidentes asociadas");
        }
        else {
            return pruebasADevolver;
        }
    }
}
