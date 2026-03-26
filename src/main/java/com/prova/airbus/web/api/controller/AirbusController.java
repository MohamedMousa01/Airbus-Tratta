package com.prova.airbus.web.api.controller;

import com.prova.airbus.dto.AirbusDTO;
import com.prova.airbus.service.AirbusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

    @Autowired
    AirbusService airbusService;

    @GetMapping
    public List<AirbusDTO> getAll(){
        return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllElements(true), true );
    }

}
