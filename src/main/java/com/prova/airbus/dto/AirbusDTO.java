package com.prova.airbus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirbusDTO {

    private Long id;

    @NotBlank(message = "{codice.notblank}")
    private String codice;

    @NotBlank(message = "{descrizione.notblank}")
    private String descrizione;

    @NotBlank(message = "{data.notblank}")
    private String data;

}
