package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.DeclaracaoAposentadoriaAverbacao;
import com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao.DeclaracaoAposentadoriaAverbacaoRequest;
import com.rhlinkcon.repository.DeclaracaoAposentadoriaAverbacaoRepository;

@Service
public class DeclaracaoAposentadoriaAverbacaoService {

	@Autowired
	private DeclaracaoAposentadoriaAverbacaoRepository repository;

	@Transactional
	public void create(DeclaracaoAposentadoriaAverbacaoRequest averbacaoRequest) {
		DeclaracaoAposentadoriaAverbacao da = new DeclaracaoAposentadoriaAverbacao(averbacaoRequest);
		repository.save(da);
	}

	public void create(DeclaracaoAposentadoriaAverbacao declaracaoAposentadoriaAverbacao) {
		repository.save(declaracaoAposentadoriaAverbacao);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
