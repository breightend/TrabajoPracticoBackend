package com.example.demo.domain.services;

import com.example.demo.domain.model.Posiciones;
import com.example.demo.domain.model.ZonasRestringidas;
import com.example.demo.repositories.interfaces.PosicionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class PosicionesService {

    private final PosicionesRepository posicionesRepository;
    private final AccesoAPI accesoAPI;

    @Autowired
    public PosicionesService(PosicionesRepository posicionesRepository, AccesoAPI accesoAPI) {
        this.posicionesRepository = posicionesRepository;
        this.accesoAPI = accesoAPI;
    }

    public Posiciones savePosiciones(Posiciones posiciones) {
        return posicionesRepository.save(posiciones);
    }

    public List<Posiciones> getPosicionesByVehiculoId(Long vehiculoId) {
        try{
            String idString = String.valueOf(vehiculoId);
            return posicionesRepository.findById_vehiculo(idString);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("No se encontró posiciones para este vehiculo");
        }
    }

    public Posiciones getPosicionActualVehiculo(Long vehiculoId) throws NoSuchElementException {
        try{
            List<Posiciones> posicionesVehiculo = this.getPosicionesByVehiculoId(vehiculoId);
            Iterator<Posiciones> iterator = posicionesVehiculo.iterator();
            LocalDateTime fecha = null;
            Posiciones posicionMasReciente = null;
            while(iterator.hasNext()){
                Posiciones posicion = iterator.next();
                if(fecha == null){
                    fecha = LocalDateTime.parse(posicion.getFecha_hora());
                    posicionMasReciente = posicion;
                }else{
                    if(LocalDateTime.parse(posicion.getFecha_hora()).isAfter(fecha)){
                        fecha = LocalDateTime.parse(posicion.getFecha_hora());
                        posicionMasReciente = posicion;
                    }
                }
            }
            return posicionMasReciente;

        } catch (NoSuchElementException e) {
            throw e;
        }

    }

    public boolean verificarPosicion(Posiciones posicion) {
        List<ZonasRestringidas> zonasRestringidas = accesoAPI.getZonasRestringidas();
        AtomicBoolean flagZona = new AtomicBoolean(false);
        zonasRestringidas.forEach(zona -> {
            if(posicion.getLatitud() > zona.getSureste().getLatitud() && posicion.getLatitud() < zona.getNoroeste().getLatitud()){
                if (posicion.getLongitud() > zona.getNoroeste().getLongitud() && posicion.getLongitud() < zona.getSureste().getLongitud()){
                    flagZona.set(true);
                    System.out.println("SIIIIIIII");
                }
            }
        });
        return flagZona.get();
    }
}
