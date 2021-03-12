package com.rhlinkcon.repository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.SimuladorNivelSalarial;
import com.rhlinkcon.model.SituacaoSimuladorNivelSalarialEnum;;

@Repository
public interface SimuladorNivelSalarialRepository extends JpaRepository<SimuladorNivelSalarial, Long> {

	Page<SimuladorNivelSalarial> findByDescricaoIgnoreCaseContaining(String nome, Pageable pageable);

	@Query(value = "SELECT ns.id FROM  simulador_nivel_salarial_valores snsv, simulador_nivel_salarial sns, nivel_salarial ns "
			+ "WHERE snsv.simulador_id = sns.id " + "AND snsv.nivel_salarial_id = ns.id "
			+ "AND sns.id != :simuladorNivelSalarialId " + "AND MONTH(sns.data_competencia) = :mes "
			+ "AND YEAR(sns.data_competencia) = :ano", nativeQuery = true)
	List<BigInteger> findSimuladorCompetencia(@Param("simuladorNivelSalarialId") Long simuladorNivelSalarialId,
			@Param("mes") String mes, @Param("ano") String ano);

	List<SimuladorNivelSalarial> findByDataCompetenciaAfterAndDataCompetenciaBeforeAndProgramarAjusteAndSituacao(
			Instant data1, Instant data2, boolean programar, SituacaoSimuladorNivelSalarialEnum situacao);

	List<SimuladorNivelSalarial> findByDataCompetenciaBeforeAndSituacao(Instant data1,
			SituacaoSimuladorNivelSalarialEnum situacao);

	List<SimuladorNivelSalarial> findByProgramarAjusteAndSituacao(boolean programar,
			SituacaoSimuladorNivelSalarialEnum situacao);
}