package com.rhlinkcon.repository.avaliacaoDesempenho;

import com.rhlinkcon.model.AvaliacaoDesempenho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoDesempenhoRepository extends JpaRepository<AvaliacaoDesempenho, Long> {

	Boolean existsByNome(String nome);

	Boolean existsByNomeAndIdNot(String nome, Long id);

	Boolean existsByCodAvaliacao(String codAvaliacao);

	Boolean existsByCodAvaliacaoAndIdNot(String codAvaliacao, Long id);

	Page<AvaliacaoDesempenho> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
}