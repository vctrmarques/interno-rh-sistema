package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.DeclaracaoAposentadoriaAssinatura;
import com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura.DeclaracaoAposentadoriaAssinaturaRequest;
import com.rhlinkcon.repository.DeclaracaoAposentadoriaAssinaturaRepository;

@Service
public class DeclaracaoAposentadoriaAssinaturaService {

	@Autowired
	private DeclaracaoAposentadoriaAssinaturaRepository repository;

	public void create(DeclaracaoAposentadoriaAssinaturaRequest assinaturaRequest) {
		DeclaracaoAposentadoriaAssinatura da = new DeclaracaoAposentadoriaAssinatura(assinaturaRequest);
		repository.save(da);
	}

	public void create(DeclaracaoAposentadoriaAssinatura declaracaoAposentadoriaAssinatura) {
		repository.save(declaracaoAposentadoriaAssinatura);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
