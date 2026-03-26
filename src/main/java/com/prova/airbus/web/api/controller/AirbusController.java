package com.prova.airbus.web.api.controller;

import com.prova.airbus.dto.AirbusDTO;
import com.prova.airbus.model.Airbus;
import com.prova.airbus.service.AirbusService;
import com.prova.airbus.web.api.exception.AirbusNotFoundException;
import com.prova.airbus.web.api.exception.IdNotNullForInsertException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AirbusDTO> createNew(@Valid @RequestBody AirbusDTO airbusDTOInput){

        if(airbusDTOInput.getId() != null)
            throw new IdNotNullForInsertException("Non è permesso inserire un id nell'inserimento");

        Airbus airbusProva = AirbusDTO.buildAirbusModelFromDTO(airbusDTOInput);
        Airbus airbus = airbusService.inserisciNuovo(airbusProva);
        ResponseEntity.ok(HttpStatus.CREATED);

        AirbusDTO result =  AirbusDTO.buildAirbusDTOFromModel(airbus, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AirbusDTO> findById(  @PathVariable(value = "id", required = true) long id){

        Airbus airbus = airbusService.caricaSingoloElementoEager(id);
        if(airbus == null)
            throw new AirbusNotFoundException("Airbus non trovato con id " +id);

        AirbusDTO result = AirbusDTO.buildAirbusDTOFromModel(airbus, false);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirbusDTO> update(@Valid @RequestBody AirbusDTO airbusDTOInput, @PathVariable(value = "id", required = true) long id){

        Airbus airbus = airbusService.caricaSingoloElementoEager(id);
        if(airbus == null)
            throw new AirbusNotFoundException("Airbus non trovato con id " +id);
        airbusDTOInput.setId(airbus.getId());

        AirbusDTO result = AirbusDTO.buildAirbusDTOFromModel(airbusService.aggiorna(airbusDTOInput.buildAirbusModel()),false);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(required = true) Long id) {
        airbusService.rimuovi(id);
    }



    @PostMapping("/search")
    public ResponseEntity<List<AirbusDTO>> search(@RequestBody AirbusDTO exampleDTO) {

        Airbus exampleModel = AirbusDTO.buildAirbusModelFromDTO(exampleDTO);

        List<Airbus> risultatiModel = airbusService.findByExample(exampleModel);

        List<AirbusDTO> risultatiDTO = AirbusDTO.createAirbusDTOListFromModelList(risultatiModel, false);

        return ResponseEntity.ok(risultatiDTO);
    }


    @GetMapping("/operazione/listaAirbusConSovrapposizioni")
    public ResponseEntity<List<AirbusDTO>> listaAirbusConSovrapposizioni() {
        List<Airbus> airbusModels = airbusService.listaAirbusConSovrapposizioni();

        // Convertiamo in DTO
        // Se hai salvato l'info 'sovrapposizione' nel model (es. in un campo transient)
        // passalo al DTO qui.
        List<AirbusDTO> result = AirbusDTO.createAirbusDTOListFromModelList(airbusModels, false);

        return ResponseEntity.ok(result);
    }


}
