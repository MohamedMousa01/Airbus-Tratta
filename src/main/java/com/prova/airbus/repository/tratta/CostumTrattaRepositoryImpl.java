package com.prova.airbus.repository.tratta;

import com.prova.airbus.model.Airbus;
import com.prova.airbus.model.Tratta;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostumTrattaRepositoryImpl implements CostumTrattaRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tratta> findByExample(Tratta example) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<String> whereClauses = new ArrayList<String>();

        // Alias 'a' per Airbus
        StringBuilder queryBuilder = new StringBuilder("select t from Tratta t left join t.airbus a where 1=1 ");

        if (StringUtils.isNotEmpty(example.getCodice())) {
            whereClauses.add(" t.codice like :codice ");
            parameterMap.put("codice", "%" + example.getCodice() + "%");
        }

        if (StringUtils.isNotEmpty(example.getDescrizione())) {
            whereClauses.add(" t.descrizione like :descrizione ");
            parameterMap.put("descrizione", "%" + example.getDescrizione() + "%");
        }

        if (example.getData() != null) {
            // Cerchiamo tutti gli Airbus entrati in servizio da una certa data in poi
            whereClauses.add(" t.data >= :data ");
            parameterMap.put("data", example.getData());
        }

        if (example.getOraAtterraggio() != null) {
            // Cerchiamo Airbus che portano ALMENO quel numero di passeggeri
            whereClauses.add(" t.oraAtterraggio >= :oraAtterraggio ");
            parameterMap.put("oraAtterraggio", example.getOraAtterraggio());
        }

        if (example.getOraDecollo() != null) {
            // Cerchiamo Airbus che portano ALMENO quel numero di passeggeri
            whereClauses.add(" t.oraDecollo >= :oraDecollo ");
            parameterMap.put("oraDecollo", example.getOraDecollo());
        }

        if (example.getStato() != null) {
            // Cerchiamo Airbus che portano ALMENO quel numero di passeggeri
            whereClauses.add(" t.stato >= :stato ");
            parameterMap.put("stato", example.getOraDecollo());
        }

        if (example.getAirbus() != null && example.getAirbus().getId() != null) {
            whereClauses.add(" a.id = :idAirbus ");
            parameterMap.put("idAirbus", example.getAirbus().getId());
        }



        queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
        queryBuilder.append(org.apache.commons.lang3.StringUtils.join(whereClauses, " and "));
        TypedQuery<Tratta> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tratta.class);

        for (String key : parameterMap.keySet()) {
            typedQuery.setParameter(key, parameterMap.get(key));
        }

        return typedQuery.getResultList();
    }
}
