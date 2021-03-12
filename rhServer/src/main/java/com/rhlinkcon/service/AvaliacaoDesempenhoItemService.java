package com.rhlinkcon.service;

import com.rhlinkcon.model.AvaliacaoDesempenho;
import com.rhlinkcon.model.AvaliacaoDesempenhoItem;
import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoItemRequest;
import com.rhlinkcon.repository.avaliacaoDesempenho.AvaliacaoDesempenhoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoDesempenhoItemService {

	@Autowired
	private AvaliacaoDesempenhoItemRepository avaliacaoDesempenhoItemRepository;

	public Boolean existsByNome(String nome) {
		return avaliacaoDesempenhoItemRepository.existsByNome(nome);
	}

	public Boolean existsByNomeAndAvaliacaoDesempenhoNot(String nome, AvaliacaoDesempenho avaliacaoDesempenho) {
		return avaliacaoDesempenhoItemRepository.existsByNomeAndAvaliacaoDesempenhoNot(nome, avaliacaoDesempenho);
	}

	public Boolean existsByNomeAndIdNot(String nome, Long id) {
		return avaliacaoDesempenhoItemRepository.existsByNomeAndIdNot(nome, id);
	}

	public Boolean existsByCodItemAvaliacao(String codItemAvaliacao) {
		return avaliacaoDesempenhoItemRepository.existsByCodItemAvaliacao(codItemAvaliacao);
	}

	public Boolean existsByCodItemAvaliacaoAndAvaliacaoDesempenhoNot(String codItemAvaliacao, AvaliacaoDesempenho avaliacaoDesempenho) {
		return avaliacaoDesempenhoItemRepository.existsByCodItemAvaliacaoAndAvaliacaoDesempenhoNot(codItemAvaliacao, avaliacaoDesempenho);
	}

	public Boolean existsByCodAvaliacaoAndIdNot(String codItemAvaliacao, Long id) {
		return avaliacaoDesempenhoItemRepository.existsByCodItemAvaliacaoAndIdNot(codItemAvaliacao, id);
	}

	private AvaliacaoDesempenhoItem createEntity(AvaliacaoDesempenhoItemRequest itemRequest) {
		AvaliacaoDesempenhoItem item = new AvaliacaoDesempenhoItem();
		item.setAvaliacaoDesempenho(itemRequest.getAvaliacaoDesempenho());
		item.setCodItemAvaliacao(itemRequest.getCodItemAvaliacao());
		item.setNome(itemRequest.getNome());
		item.setSeqAvaliacao(itemRequest.getSeqAvaliacao());
		item.setDescricao(itemRequest.getDescricao());
		item.setTipoQuestao(itemRequest.getTipoQuestao());
		item.setFormaAvaliacao(itemRequest.getFormaAvaliacao());
		return item;
	}

	public void create(AvaliacaoDesempenhoItemRequest itemRequest) {
		avaliacaoDesempenhoItemRepository.save(createEntity(itemRequest));
	}

	public void update(AvaliacaoDesempenhoItemRequest itemRequest) {
		AvaliacaoDesempenhoItem item = createEntity(itemRequest);
		item.setId(itemRequest.getId());
		avaliacaoDesempenhoItemRepository.save(item);
	}

	public void delete(Long id) {
		avaliacaoDesempenhoItemRepository.deleteById(id);
	}
}