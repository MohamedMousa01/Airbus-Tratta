package com.prova.airbus.service;

import com.prova.airbus.model.Airbus;
import com.prova.airbus.model.Stato;
import com.prova.airbus.model.Tratta;
import com.prova.airbus.repository.tratta.TrattaRepository;
import com.prova.airbus.web.api.exception.TrattaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TrattaServiceImpl implements TrattaService{

    @Autowired
    TrattaRepository repository;

    public List<Tratta> listAllElements() {
        return (List<Tratta>) repository.findAll();
    }

    public Tratta caricaSingoloElemento(Long id){ return repository.findById(id).orElse(null);}

    public Tratta caricaSingoloElementoconAirbus(Long id){ return repository.findByIdEager(id);}

    @Transactional
    public Tratta aggiorna(Tratta trattaInstance) {
        return repository.save(trattaInstance);
    }

    @Transactional
    public Tratta inserisciNuovo(Tratta trattaInstance) { return repository.save(trattaInstance);
    }

    @Transactional
    public void rimuovi(Long idToRemove) {
        repository.findById(idToRemove)
                .orElseThrow(() -> new TrattaNotFoundException("Regista not found con id: " + idToRemove));
        repository.deleteById(idToRemove);
    }

    @Transactional
    public List<Tratta> findByExample(Tratta example) {

        return repository.findByExample(example);
    }

    @Transactional
    public void concludiTratteAttive(){
        List<Tratta> tratteAttive = repository.findActive();
        LocalDateTime adesso = LocalDateTime.now();

        for (Tratta trattaItem : tratteAttive) {
            LocalDateTime momentoAtterraggio = LocalDateTime.of(trattaItem.getData(), trattaItem.getOraAtterraggio());

            if (momentoAtterraggio.isBefore(adesso)) {
                trattaItem.setStato(Stato.CONCLUSA);
            }
        }
    }



}
