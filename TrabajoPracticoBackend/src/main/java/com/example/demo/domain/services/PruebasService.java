package com.example.demo.domain.services;

import com.example.demo.domain.model.Coordenadas;
import com.example.demo.domain.model.Posiciones;
import com.example.demo.domain.model.Pruebas;
import com.example.demo.domain.model.Vehiculos;
import com.example.demo.repositories.interfaces.PruebasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PruebasService {

    private final PruebasRepository pruebasRepository;
    private final PosicionesService posicionesService;
    private final AccesoAPI accesoAPI;
    private final NotificacionesService notificacionesService;

    @Autowired
    public PruebasService(PruebasRepository pruebasRepository, PosicionesService posicionesService, AccesoAPI accesoAPI, NotificacionesService notificacionesService) {
        this.pruebasRepository = pruebasRepository;
        this.posicionesService = posicionesService;
        this.accesoAPI = accesoAPI;
        this.notificacionesService = notificacionesService;
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

        //Actualizar posicion del auto
        Coordenadas concesionaria = accesoAPI.getCoordenadaAgencia();
        Vehiculos vehiculo = prueba.getId_vehiculo();
        posicionesService.savePosiciones(new Posiciones(now.toString(), vehiculo, concesionaria.getLatitud(), concesionaria.getLongitud()));

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

    public List<Pruebas> encontrarPruebasConVehiculo(Long idVehiculo) throws NoSuchElementException {
        List<Pruebas> pruebas = this.getPruebas();
        List<Pruebas> pruebasToReturn = new ArrayList<>();
        Iterator<Pruebas> iterator = pruebas.iterator();
        while(iterator.hasNext()){
            Pruebas prueba = iterator.next();
            if(prueba.getId_vehiculo().getId().equals(idVehiculo)){
                pruebasToReturn.add(prueba);
            }
        }
        if (pruebasToReturn.isEmpty()){
            throw new NoSuchElementException("No se encontraron pruebas con ese vehiculo");
        }else{
            return pruebasToReturn;
        }
    }

    public Pruebas recibirPruebasEnCursoConVehiculo(Long idVehiculo) throws NoSuchElementException {
        List<Pruebas> pruebas = this.getPruebasOnCourse();
        Iterator<Pruebas> iterator = pruebas.iterator();
        while(iterator.hasNext()){
            Pruebas prueba = iterator.next();
            if(prueba.getId_vehiculo().getId().equals(idVehiculo)){
                return prueba;
            }
        }
        throw new NoSuchElementException("No se encontraron pruebas con vehiculo");
    }

    public Posiciones AvanzarVehiculoEnPrueba(Long idVehiculo, Double avanceEnLatitud, Double avanceEnLongitud) throws NoSuchElementException {
        try{
            Pruebas prueba = this.recibirPruebasEnCursoConVehiculo(idVehiculo);
            LocalDateTime now = LocalDateTime.now();
            Coordenadas agencia = accesoAPI.getCoordenadaAgencia();
            Double latitudAgencia = agencia.getLatitud();
            Double longitudAgencia = agencia.getLongitud();
//            Double latitudAvanzada

            Posiciones posicion = new Posiciones(now.toString(), prueba.getId_vehiculo(), latitudAgencia + avanceEnLatitud, longitudAgencia + avanceEnLongitud);

            //verificaion si esta en zona prohibida y mandamos notificacion
            if(posicionesService.verificarPosicion(posicion)){
                long telefono = prueba.getEmpleado().getTelefono_contacto();
                notificacionesService.generarNotificacionACelularAdvertencia(telefono, prueba.getId_vehiculo().getPatente());

            };

            return posicion;
        } catch (NoSuchElementException e) {
            throw new RuntimeException("No se encontraron pruebas con vehiculo");
        }

    }
}
