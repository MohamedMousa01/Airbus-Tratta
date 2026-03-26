package com.prova.airbus.service;

import com.prova.airbus.model.Airbus;
import com.prova.airbus.repository.airbus.AirbusRepository;
import com.prova.airbus.web.api.exception.AirbusNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AirbusServiceImpl implements AirbusService{

    @Autowired
    AirbusRepository repository;

    public List<Airbus> listAllElements(boolean eager){
        if(eager)
            return repository.findAllAirbusEager();
        return repository.findAll();
    }

    public Airbus caricaSingoloElemento(Long id){
        return repository.findById(id).orElse(null);
    }

    public Airbus caricaSingoloElementoEager(Long id){
        return repository.findSingleAirbusEager(id);
    }

    @Transactional
    public Airbus aggiorna(Airbus airbusInstance) {
        return repository.save(airbusInstance);
    }

    @Transactional
    public Airbus inserisciNuovo(Airbus airbusInstance) {
        return repository.save(airbusInstance);
    }

    @Transactional
    public void rimuovi(Long idToRemove) {
        repository.findById(idToRemove)
                .orElseThrow(() -> new AirbusNotFoundException("Airbus not found con id: " + idToRemove));
        repository.deleteById(idToRemove);
    }

    public List<Airbus> findByExample(Airbus example) {

        return repository.findByExample(example);
    }
}
