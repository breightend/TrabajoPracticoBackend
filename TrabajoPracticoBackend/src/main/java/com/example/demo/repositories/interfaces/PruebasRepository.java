package com.example.demo.repositories.interfaces;

import com.example.demo.domain.model.Pruebas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.NoSuchElementException;

public interface PruebasRepository extends JpaRepository<Pruebas, Long> {
    @Query(value = "SELECT P FROM Pruebas P WHERE P.fecha_hora_fin IS NULL")
    List<Pruebas> getPruebasOnCourse() throws NoSuchElementException;
}
