package com.rhlinkcon.repository.avaliacaoDesempenho;

import com.rhlinkcon.model.AvaliacaoDesempenho;
import com.rhlinkcon.model.AvaliacaoDesempenhoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoDesempenhoItemRepository extends JpaRepository<AvaliacaoDesempenhoItem, Long> {

	Boolean existsByNome(String nome);

	Boolean existsByNomeAndAvaliacaoDesempenhoNot(String nome, AvaliacaoDesempenho avaliacaoDesempenho);

	Boolean existsByNomeAndIdNot(String nome, Long id);

	Boolean existsByCodItemAvaliacao(String codItemAvaliacao);

	Boolean existsByCodItemAvaliacaoAndAvaliacaoDesempenhoNot(String codItemAvaliacao, AvaliacaoDesempenho avaliacaoDesempenho);

	Boolean existsByCodItemAvaliacaoAndIdNot(String codItemAvaliacao, Long id);

	List<AvaliacaoDesempenhoItem> findAllByAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho);
}