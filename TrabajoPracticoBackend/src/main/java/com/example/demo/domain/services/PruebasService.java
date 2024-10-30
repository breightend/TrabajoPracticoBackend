package com.example.demo.domain.services;

import com.example.demo.domain.model.Pruebas;
import com.example.demo.repositories.interfaces.PruebasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PruebasService {

    private final PruebasRepository pruebasRepository;

    @Autowired
    public PruebasService(PruebasRepository pruebasRepository) {
        this.pruebasRepository = pruebasRepository;
    }

    public List<Pruebas> getPruebas() {
        return pruebasRepository.findAll();
    }

    public void createPrueba(Pruebas prueba){
        pruebasRepository.save(prueba);

    }

    public List<Pruebas> getPruebasOnCourse(){
        return pruebasRepository.getPruebasOnCourse();
    }

    public Pruebas getPruebaById(Long id){
        return pruebasRepository.findById(id).orElseThrow();
    }

    public Pruebas finalizarPrueba(Long id, Optional<String> comentario)throws InvalidObjectException, NoSuchElementException {
        try{
        Pruebas prueba = this.getPruebaById(id);
        LocalDateTime now = LocalDateTime.now();

        if(prueba.getFecha_hora_fin() != null){
            throw new InvalidObjectException("Esta prueba ya se encuentra finalizada");
        }

        prueba.setFecha_hora_fin(now);
        if(comentario.isPresent()){
            prueba.setComentarios(comentario.get());
        }else{
            prueba.setComentarios("Sin comentarios extras");
        }

        pruebasRepository.save(prueba);
        return prueba;

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No se encontro la prueba");
        }
    }

    public Boolean encontrarPruebaEnCursoConVehiculo(Long idVehiculo){
        List<Pruebas> pruebas = this.getPruebasOnCourse();
        Iterator<Pruebas> iterator = pruebas.iterator();
        while(iterator.hasNext()){
            Pruebas prueba = iterator.next();
            if(prueba.getId_vehiculo().getId().equals(idVehiculo)){
                return true;
            }
        }
        return false;
    }
    public Boolean encontrarPruebaEnCursoConEmpleado(Long idEmpleado){
        List<Pruebas> pruebas = this.getPruebasOnCourse();
        Iterator<Pruebas> iterator = pruebas.iterator();
        while(iterator.hasNext()){
            Pruebas prueba = iterator.next();
            if(prueba.getEmpleado().getLegajo().equals(idEmpleado)){
                return true;
            }
        }
        return false;
    }
    public Boolean encontrarPruebaEnCursoConInteresado(Long idInteresado){
        List<Pruebas> pruebas = this.getPruebasOnCourse();
        Iterator<Pruebas> iterator = pruebas.iterator();
        while(iterator.hasNext()){
            Pruebas prueba = iterator.next();
            if(prueba.getInteresados().getId().equals(idInteresado)){
                return true;
            }
        }
        return false;
    }
}
