package com.TrabajoPractico.Reportes.Repositories;

import com.TrabajoPractico.Reportes.model.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadosRepository extends JpaRepository<Empleados,Long> {
}
