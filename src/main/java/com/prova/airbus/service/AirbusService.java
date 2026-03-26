package com.prova.airbus.service;

import com.prova.airbus.model.Airbus;

import java.util.List;

public interface AirbusService {

    List<Airbus> listAllElements(boolean eager);

    Airbus caricaSingoloElemento(Long id);

    Airbus caricaSingoloElementoEager(Long id);

    Airbus aggiorna(Airbus filmInstance);

    Airbus inserisciNuovo(Airbus filmInstance);

    void rimuovi(Long idToRemove);

    List<Airbus> findByExample(Airbus example);

    public List<Airbus> listaAirbusConSovrapposizioni();

}
