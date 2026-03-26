package com.prova.airbus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prova.airbus.model.Airbus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirbusDTO {

    private Long id;

    @NotBlank(message = "{codice.notblank}")
    private String codice;

    @NotBlank(message = "{descrizione.notblank}")
    private String descrizione;

    // Per LocalDate e int si usa @NotNull, non @NotBlank
    @NotNull(message = "{dataInizioServizio.notnull}")
    private LocalDate dataInizioServizio;

    @Min(value = 1, message = "{numeroPasseggeri.min}")
    private int numeroPasseggeri;

    // Usiamo il DTO e non l'Entity!
    private List<TrattaDTO> tratte = new ArrayList<>();

    // Questo ci serve per la traccia dell'esercizio
    private Boolean conSovrapposizioni;

    public AirbusDTO() {}

    // Getter e Setter allineati (tutti al plurale 'tratte')
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public LocalDate getDataInizioServizio() { return dataInizioServizio; }
    public void setDataInizioServizio(LocalDate dataInizioServizio) { this.dataInizioServizio = dataInizioServizio; }

    public int getNumeroPasseggeri() { return numeroPasseggeri; }
    public void setNumeroPasseggeri(int numeroPasseggeri) { this.numeroPasseggeri = numeroPasseggeri; }

    public List<TrattaDTO> getTratte() { return tratte; }
    public void setTratte(List<TrattaDTO> tratte) { this.tratte = tratte; }

    public Boolean getConSovrapposizioni() { return conSovrapposizioni; }
    public void setConSovrapposizioni(Boolean conSovrapposizioni) { this.conSovrapposizioni = conSovrapposizioni; }



    // --- Metodi di Conversione ---
    public Airbus buildAirbusModel() {
        return new Airbus(this.id, this.codice, this.descrizione, this.dataInizioServizio,this.numeroPasseggeri);
    }
    public static Airbus buildAirbusModelFromDTO(AirbusDTO airbusDTO) {
        Airbus result = new Airbus();
        result.setId(airbusDTO.getId());
        result.setCodice(airbusDTO.getCodice());
        result.setDescrizione(airbusDTO.getDescrizione());
        result.setDataInizioServizio(airbusDTO.getDataInizioServizio());
        result.setNumeroPasseggeri(airbusDTO.getNumeroPasseggeri());
        return result;
    }

    public static AirbusDTO buildAirbusDTOFromModel(Airbus airbusModel, boolean includeTratte) {
        AirbusDTO result = new AirbusDTO();
        result.setId(airbusModel.getId());
        result.setCodice(airbusModel.getCodice());
        result.setDescrizione(airbusModel.getDescrizione());
        result.setDataInizioServizio(airbusModel.getDataInizioServizio());
        result.setNumeroPasseggeri(airbusModel.getNumeroPasseggeri());

        if (includeTratte && airbusModel.getTratte() != null && !airbusModel.getTratte().isEmpty()) {
            result.setTratte(TrattaDTO.createTrattaDTOListFromModelList(
                    new ArrayList<>(airbusModel.getTratte()), false));
        }
        return result;
    }

    public static List<AirbusDTO> createAirbusDTOListFromModelList(List<Airbus> modelList, boolean includeTratte) {
        return modelList.stream()
                .map(airbusEntity -> buildAirbusDTOFromModel(airbusEntity, includeTratte))
                .collect(Collectors.toList());
    }


}