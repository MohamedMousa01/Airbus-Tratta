package com.prova.airbus.web.api.controller;

import com.prova.airbus.dto.AirbusDTO;
import com.prova.airbus.model.Airbus;
import com.prova.airbus.service.AirbusService;
import com.prova.airbus.web.api.exception.IdNotNullForInsertException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

    @Autowired
    AirbusService airbusService;

    @GetMapping
    public List<AirbusDTO> getAll(){
        return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllElements(false), false );
    }

    @PostMapping
    public AirbusDTO createNew(@Valid @RequestBody AirbusDTO airbusDTOInput){

        if(airbusDTOInput.getId() != null)
            throw new IdNotNullForInsertException("Non è permesso inserire un id nell'inserimento");

        Airbus airbusProva = AirbusDTO.buildAirbusModelFromDTO(airbusDTOInput);
        Airbus airbus = airbusService.inserisciNuovo(airbusProva);
        return AirbusDTO.buildAirbusDTOFromModel(airbus, true);
    }



}
