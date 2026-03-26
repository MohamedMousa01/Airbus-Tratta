package com.prova.airbus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.prova.airbus.model.Stato;
import com.prova.airbus.model.Tratta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrattaDTO {

    private Long id;

    @NotBlank(message = "{codice.notblank}")
    private String codice;

    @NotBlank(message = "{descrizione.notblank}")
    private String descrizione;

    @NotNull(message = "La data è obbligatoria")
    private LocalDate data;

    @NotNull(message = "L'ora di decollo è obbligatoria")
    private LocalTime oraDecollo;

    @NotNull(message = "L'ora di atterraggio è obbligatoria")
    private LocalTime oraAtterraggio;

    private Stato stato;

    @NotNull(message = "L'airbus è obbligatorio")
    @JsonIgnoreProperties(value = { "tratte" })
    private AirbusDTO airbusDTO;

    public TrattaDTO(){}

    public TrattaDTO(String codice, String descrizione, LocalDate data, LocalTime oraDecollo, LocalTime oraAtterraggio, Stato stato, AirbusDTO airbusDTO) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.data = data;
        this.oraDecollo = oraDecollo;
        this.oraAtterraggio = oraAtterraggio;
        this.stato = stato;
        this.airbusDTO = airbusDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOraDecollo() {
        return oraDecollo;
    }

    public void setOraDecollo(LocalTime oraDecollo) {
        this.oraDecollo = oraDecollo;
    }

    public LocalTime getOraAtterraggio() {
        return oraAtterraggio;
    }

    public void setOraAtterraggio(LocalTime oraAtterraggio) {
        this.oraAtterraggio = oraAtterraggio;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public AirbusDTO getAirbus() {
        return airbusDTO;
    }

    public void setAirbus(AirbusDTO airbusDTO) {
        this.airbusDTO = airbusDTO;
    }

    public static Tratta buildTrattaModelFromDTO(TrattaDTO trattaDTO) {
        Tratta result = new Tratta();
        result.setId(trattaDTO.getId());
        result.setCodice(trattaDTO.getCodice());
        result.setDescrizione(trattaDTO.getDescrizione());
        result.setData(trattaDTO.getData());
        result.setOraDecollo(trattaDTO.getOraDecollo());
        result.setOraAtterraggio(trattaDTO.getOraAtterraggio());
        result.setStato(trattaDTO.getStato());

        if (trattaDTO.getAirbus() != null) {
            result.setAirbus(AirbusDTO.buildAirbusModelFromDTO(trattaDTO.getAirbus()));
        }

        return result;
    }


    public static TrattaDTO buildTrattaDTOFromModel(Tratta trattaModel, boolean includeAirbus) {
        TrattaDTO result = new TrattaDTO();
        result.setId(trattaModel.getId());
        result.setCodice(trattaModel.getCodice());
        result.setDescrizione(trattaModel.getDescrizione());
        result.setData(trattaModel.getData());
        result.setOraDecollo(trattaModel.getOraDecollo());
        result.setOraAtterraggio(trattaModel.getOraAtterraggio());
        result.setStato(trattaModel.getStato());

        if (includeAirbus && trattaModel.getAirbus() != null) {
            result.setAirbus(AirbusDTO.buildAirbusDTOFromModel(trattaModel.getAirbus(), false));
        }

        return result;
    }


    public static List<TrattaDTO> createTrattaDTOListFromModelList(List<Tratta> modelList, boolean includeAirbus) {
        return modelList.stream().map(trattaEntity -> {
            return TrattaDTO.buildTrattaDTOFromModel(trattaEntity, includeAirbus);
        }).collect(Collectors.toList());
    }

}
