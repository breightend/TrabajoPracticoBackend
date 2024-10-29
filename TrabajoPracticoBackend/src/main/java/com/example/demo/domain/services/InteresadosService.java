package com.example.demo.domain.services;

import com.example.demo.domain.model.Interesados;
import com.example.demo.repositories.interfaces.InteresadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.List;

@Service
public class InteresadosService {

    private final InteresadosRepository interesadosRepository;

    @Autowired
    public InteresadosService(InteresadosRepository interesadosRepository) {
        this.interesadosRepository = interesadosRepository;
    }

    public List<Interesados> getAllInteresados(){
        return interesadosRepository.findAll();
    }

    public void saveInteresado(Interesados interesado) throws InvalidObjectException {
        if(interesado.getRestringido()){
            interesadosRepository.save(interesado);
        } else{
            throw new InvalidObjectException("Usuario restringido para las pruebas");
        }
    }

    public void deleteInteresadoById(long id){
        Interesados interesado = interesadosRepository.findById(id).orElseThrow();
        this.interesadosRepository.delete(interesado);
    }
}
