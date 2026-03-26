package com.prova.airbus.repository.airbus;

import com.prova.airbus.model.Airbus;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostumAirbusRepositoryImpl implements CostumAirbusRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Airbus> findByExample(Airbus example) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<String> whereClauses = new ArrayList<String>();

        // Alias 'a' per Airbus
        StringBuilder queryBuilder = new StringBuilder("select a from Airbus a where 1=1 ");

        if (StringUtils.isNotEmpty(example.getCodice())) {
            whereClauses.add(" a.codice like :codice ");
            parameterMap.put("codice", "%" + example.getCodice() + "%");
        }

        if (StringUtils.isNotEmpty(example.getDescrizione())) {
            whereClauses.add(" a.descrizione like :descrizione ");
            parameterMap.put("descrizione", "%" + example.getDescrizione() + "%");
        }

        if (example.getDataInizioServizio() != null) {
            // Cerchiamo tutti gli Airbus entrati in servizio da una certa data in poi
            whereClauses.add(" a.dataInizioServizio >= :dataInizioServizio ");
            parameterMap.put("dataInizioServizio", example.getDataInizioServizio());
        }

        if (example.getNumeroPasseggeri() > 0) {
            // Cerchiamo Airbus che portano ALMENO quel numero di passeggeri
            whereClauses.add(" a.numeroPasseggeri >= :numeroPasseggeri ");
            parameterMap.put("numeroPasseggeri", example.getNumeroPasseggeri());
        }

        queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
        queryBuilder.append(org.apache.commons.lang3.StringUtils.join(whereClauses, " and "));
        TypedQuery<Airbus> typedQuery = entityManager.createQuery(queryBuilder.toString(), Airbus.class);

        for (String key : parameterMap.keySet()) {
            typedQuery.setParameter(key, parameterMap.get(key));
        }

        return typedQuery.getResultList();
    }
}
