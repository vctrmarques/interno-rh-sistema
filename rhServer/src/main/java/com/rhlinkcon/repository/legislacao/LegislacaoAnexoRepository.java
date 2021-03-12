package com.rhlinkcon.repository.legislacao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.Legislacao;
import com.rhlinkcon.model.legislacao.LegislacaoAnexo;

@Repository
public interface LegislacaoAnexoRepository extends JpaRepository<LegislacaoAnexo, Long> {

	Optional<List<LegislacaoAnexo>> findByLegislacao(Legislacao legislacao);

}