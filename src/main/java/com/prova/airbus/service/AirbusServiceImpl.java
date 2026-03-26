package com.prova.airbus.service;

import com.prova.airbus.model.Airbus;
import com.prova.airbus.model.Tratta;
import com.prova.airbus.repository.airbus.AirbusRepository;
import com.prova.airbus.web.api.exception.AirbusNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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


    @Override
    @Transactional(readOnly = true)
    public List<Airbus> listaAirbusConSovrapposizioni() {
        // 1. Carichiamo tutti gli Airbus (assicurati di usare una JOIN FETCH sulle tratte nel repository)
        List<Airbus> tuttiGliAirbus = repository.findAllAirbusEager();

        for (Airbus airbus : tuttiGliAirbus) {
            boolean sovrapposizioneTrovata = false;

            // Convertiamo il Set in Lista per comodità di confronto
            List<Tratta> listaTratte = new ArrayList<>(airbus.getTratte());

            for (int i = 0; i < listaTratte.size(); i++) {
                for (int j = i + 1; j < listaTratte.size(); j++) {
                    Tratta t1 = listaTratte.get(i);
                    Tratta t2 = listaTratte.get(j);

                    // Controllo: Stessa data E orari sovrapposti
                    if (t1.getData().equals(t2.getData()) && isOverlapping(t1, t2)) {
                        sovrapposizioneTrovata = true;
                        break;
                    }
                }
                if (sovrapposizioneTrovata) break;
            }

            // Settiamo il campo transient
            airbus.setConSovrapposizioni(sovrapposizioneTrovata);
        }
        return tuttiGliAirbus;
    }

    private boolean isOverlapping(Tratta t1, Tratta t2) {
        // Logica: (InizioA < FineB) AND (InizioB < FineA)
        return t1.getOraDecollo().isBefore(t2.getOraAtterraggio())
                && t2.getOraDecollo().isBefore(t1.getOraAtterraggio());
    }

}
