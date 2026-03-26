package com.prova.airbus.repository.tratta;


import com.prova.airbus.model.Airbus;
import com.prova.airbus.model.Tratta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrattaRepository extends CrudRepository<Tratta, Long>,
        JpaRepository<Tratta,Long>, PagingAndSortingRepository<Tratta, Long>, JpaSpecificationExecutor<Tratta>, CostumTrattaRepository {

    @Query("from Tratta t left join fetch t.airbus where t.id=?1")
    Tratta findByIdEager(Long idAirbus);
}
