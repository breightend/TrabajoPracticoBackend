package com.example.demo.repositories.interfaces;

import com.example.demo.domain.model.Vehiculos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculos, Long> {
}
