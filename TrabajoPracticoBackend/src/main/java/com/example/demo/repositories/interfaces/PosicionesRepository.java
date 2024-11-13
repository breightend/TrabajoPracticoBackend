package com.example.demo.repositories.interfaces;

import com.example.demo.domain.model.Posiciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.NoSuchElementException;

public interface PosicionesRepository extends JpaRepository<Posiciones, Integer> {
    @Query("SELECT POS FROM Posiciones POS WHERE CAST(POS.id_vehiculo AS string) like %:idVehiculo%")
    List<Posiciones> findById_vehiculo(@Param("idVehiculo")String idVehiculo) throws NoSuchElementException;
}
