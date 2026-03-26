package com.prova.airbus.repository.tratta;

import com.prova.airbus.model.Airbus;
import com.prova.airbus.model.Tratta;

import java.util.List;

public interface CostumTrattaRepository {

    List<Tratta> findByExample(Tratta example);
}
