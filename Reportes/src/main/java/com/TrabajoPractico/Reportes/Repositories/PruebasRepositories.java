package com.TrabajoPractico.Reportes.Repositories;

import com.TrabajoPractico.Reportes.model.Pruebas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.NoSuchElementException;

public interface PruebasRepositories extends JpaRepository<Pruebas, Long> {
    @Query(value = "SELECT * FROM Pruebas P WHERE P.incidentes == true", nativeQuery = true)
    List<Pruebas> getPruebasConIncidentes();

    @Query(value = "SELECT * FROM Pruebas P WHERE P.INCIDENTES == true AND P.ID_EMPLEADO == :legajoEmpleado", nativeQuery = true)
    List<Pruebas> getPruebasConIncidentesConEmpleado(@Param("legajoEmpleado") long legajoEmpleado) throws NoSuchElementException;
}
