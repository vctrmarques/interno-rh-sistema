package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rhlinkcon.model.Aliquota;
import com.rhlinkcon.model.FaixaEnum;

public interface AliquotaRepository extends JpaRepository<Aliquota, Long> {

	List<Aliquota> findAllByAnoAndFaixa(Integer ano, FaixaEnum faixa);

	@Query("select distinct(a.ano) from Aliquota a where a.faixa = :faixa")
	List<Long> findDistinctAnoByFaixa(@Param("faixa") FaixaEnum faixa);
}
