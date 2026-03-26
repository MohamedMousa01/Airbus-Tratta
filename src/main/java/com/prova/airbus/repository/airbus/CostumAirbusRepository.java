package com.prova.airbus.repository.airbus;

import com.prova.airbus.model.Airbus;

import java.util.List;

public interface CostumAirbusRepository {

    List<Airbus> findByExample(Airbus example);
}
