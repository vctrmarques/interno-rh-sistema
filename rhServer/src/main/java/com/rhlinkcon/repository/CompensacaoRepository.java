package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Compensacao;
import com.rhlinkcon.model.TomadorServico;

@Repository
public interface CompensacaoRepository extends JpaRepository<Compensacao, Long> {

	Boolean existsByTomadorServico(TomadorServico tomadorServico);

	// Boolean existsByCompetenciaAndIdNot(Instant competencia, Long id);

	Compensacao findByTomadorServico(TomadorServico tomadorServico);

}
