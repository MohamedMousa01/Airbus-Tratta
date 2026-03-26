package com.prova.airbus.web.api.controller;

import com.prova.airbus.dto.AirbusDTO;
import com.prova.airbus.dto.TrattaDTO;
import com.prova.airbus.model.Airbus;
import com.prova.airbus.model.Tratta;
import com.prova.airbus.service.TrattaService;
import com.prova.airbus.web.api.exception.IdNotNullForInsertException;
import com.prova.airbus.web.api.exception.TrattaNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tratta")
public class TrattaController {

    @Autowired
    TrattaService trattaService;

    @GetMapping
    public List<TrattaDTO> getAll(){
        return  TrattaDTO.createTrattaDTOListFromModelList(trattaService.listAllElements(), false);
    }

    @PostMapping
    public ResponseEntity<TrattaDTO> createNew(@Valid @RequestBody TrattaDTO trattaDTOInput){

        if(trattaDTOInput.getId() != null)
            throw new IdNotNullForInsertException("Non è permesso inserire un id nell'inserimento");

        Tratta trattaProva = TrattaDTO.buildTrattaModelFromDTO(trattaDTOInput);
        Tratta tratta = trattaService.inserisciNuovo(trattaProva);
        ResponseEntity.ok(HttpStatus.CREATED);

        TrattaDTO result = TrattaDTO.buildTrattaDTOFromModel(tratta, false);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TrattaDTO> findById(@PathVariable(value = "id", required = true) long id) {

        Tratta trattaModel = trattaService.caricaSingoloElementoconAirbus(id);

        if (trattaModel == null) {
            throw new TrattaNotFoundException("Tratta non trovata con id: " + id);
        }

        TrattaDTO result = TrattaDTO.buildTrattaDTOFromModel(trattaModel, true);

        return ResponseEntity.ok(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TrattaDTO> update(@Valid @RequestBody TrattaDTO trattaDTOInput, @PathVariable(value = "id", required = true) long id) {

        Tratta trattaEsistente = trattaService.caricaSingoloElemento(id);
        if (trattaEsistente == null) {
            throw new TrattaNotFoundException("Tratta non trovata con id: " + id);
        }

        trattaDTOInput.setId(id);

        Tratta trattaAggiornata = trattaService.aggiorna(trattaDTOInput.buildTrattaModel());
        TrattaDTO result = TrattaDTO.buildTrattaDTOFromModel(trattaAggiornata, true);

        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(required = true) Long id) {
        trattaService.rimuovi(id);
    }


    @PostMapping("/search")
    public ResponseEntity<List<TrattaDTO>> search(@RequestBody TrattaDTO exampleDTO) {

        Tratta exampleModel = exampleDTO.buildTrattaModel();

        List<Tratta> risultatiModel = trattaService.findByExample(exampleModel);

        List<TrattaDTO> risultatiDTO = TrattaDTO.createTrattaDTOListFromModelList(risultatiModel, true);

        return ResponseEntity.ok(risultatiDTO);
    }


    @GetMapping("/operazioni/concludiTratte")
    public void concludiTratte(){
        trattaService.concludiTratteAttive();

    }

}
