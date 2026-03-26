package com.prova.airbus.service;

import com.prova.airbus.model.Tratta;

import java.util.List;

public interface TrattaService {

    List<Tratta> listAllElements();

    Tratta caricaSingoloElemento(Long id);

    Tratta caricaSingoloElementoconAirbus(Long id);

    Tratta aggiorna(Tratta filmInstance);

    Tratta inserisciNuovo(Tratta filmInstance);

    void rimuovi(Long idToRemove);

    List<Tratta> findByExample(Tratta example);

    void concludiTratteAttive();
}
