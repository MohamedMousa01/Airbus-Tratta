package com.prova.airbus.repository.airbus;

import com.prova.airbus.model.Airbus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AirbusRepository extends CrudRepository<Airbus, Long>,
        JpaRepository<Airbus,Long>, PagingAndSortingRepository<Airbus, Long>, JpaSpecificationExecutor<Airbus>, CostumAirbusRepository{

    @Query("select a from Airbus a left join fetch a.tratte")
    List<Airbus> findAllAirbusEager();

    @Query("from Airbus a left join fetch a.tratte where a.id = ?1")
    Airbus findSingleAirbusEager(Long id);

}
