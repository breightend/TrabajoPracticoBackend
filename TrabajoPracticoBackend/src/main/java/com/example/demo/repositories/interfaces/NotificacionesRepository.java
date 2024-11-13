package com.example.demo.repositories.interfaces;

import com.example.demo.domain.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificacion, Long> {
}
