package com.prova.airbus.web.api.controller;

import com.prova.airbus.dto.AirbusDTO;
import com.prova.airbus.dto.TrattaDTO;
import com.prova.airbus.model.Airbus;
import com.prova.airbus.model.Tratta;
import com.prova.airbus.service.TrattaService;
import com.prova.airbus.web.api.exception.IdNotNullForInsertException;
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



}
