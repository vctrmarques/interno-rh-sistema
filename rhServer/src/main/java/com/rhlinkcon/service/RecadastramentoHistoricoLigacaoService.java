package com.rhlinkcon.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.RecadastramentoHistoricoLigacao;
import com.rhlinkcon.payload.recadastramento.RecadastramentoHistoricoLigacaoRequest;
import com.rhlinkcon.repository.recadastramento.RecadastramentoHistoricoLigacaoRepository;

@Service
public class RecadastramentoHistoricoLigacaoService {

	@Autowired
	private RecadastramentoHistoricoLigacaoRepository repository;
	
	public void create(@Valid RecadastramentoHistoricoLigacaoRequest request) {
		RecadastramentoHistoricoLigacao obj = new RecadastramentoHistoricoLigacao(request);
		repository.save(obj);
		
	}

	public void delete(Long id) {
		RecadastramentoHistoricoLigacao obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("RecadastramentoHistoricoLigacao", "id", id));
		repository.delete(obj);
		
	}

}
