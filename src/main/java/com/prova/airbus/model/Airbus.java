package com.prova.airbus.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "airbus")
public class Airbus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "dataInizioServizio")
    private LocalDate dataInizioServizio;

    @Column(name = "numeropasseggeri")
    private int numeroPasseggeri;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airbus")
    private Set<Tratta> tratte = new HashSet<>();

    public Airbus(){}

    public Airbus(String codice, String descrizione, LocalDate dataInizioServizio, int numeroPasseggeri, Set<Tratta> tratte) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.dataInizioServizio = dataInizioServizio;
        this.numeroPasseggeri = numeroPasseggeri;
        this.tratte = tratte;
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

    public LocalDate getDataInizioServizio() {
        return dataInizioServizio;
    }

    public void setDataInizioServizio(LocalDate dataInizioServizio) {
        this.dataInizioServizio = dataInizioServizio;
    }

    public int getNumeroPasseggeri() {
        return numeroPasseggeri;
    }

    public void setNumeroPasseggeri(int numeroPasseggeri) {
        this.numeroPasseggeri = numeroPasseggeri;
    }

    public Set<Tratta> getTratte() {
        return tratte;
    }

    public void setTratte(Set<Tratta> tratte) {
        this.tratte = tratte;
    }
}
