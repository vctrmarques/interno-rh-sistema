package com.rhlinkcon.repository.legislacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.Legislacao;

@Repository
public interface LegislacaoRepository extends JpaRepository<Legislacao, Long>, LegislacaoRepositoryCustom {

	Boolean existsByNumeroNormaAndEnteFederadoIdAndNormaId(Integer numeroNorma, Long enteFederadoId, Long normaId);

	Boolean existsByNumeroNormaAndEnteFederadoIdAndNormaIdAndIdNot(Integer numeroNorma, Long enteFederadoId,
			Long normaId, Long id);

	Boolean existsByPessoalLegislacao(Legislacao legislacao);
}
