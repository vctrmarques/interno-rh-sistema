package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.FrequenciaCertidaoExServidorDetalhamento;
import com.rhlinkcon.repository.FrequenciaCertidaoExServidorDetalhamentoRepository;

@Service
public class FrequenciaCertidaoExServidorDetalhamentoService {

	@Autowired
	private FrequenciaCertidaoExServidorDetalhamentoRepository repository;

	public void create(FrequenciaCertidaoExServidorDetalhamento detalhamento) {
		repository.save(detalhamento);
	}

	public void delete(Long id) {
		FrequenciaCertidaoExServidorDetalhamento detalhamento = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("FrequenciaCertidaoExServidorDetalhamento", "id", id));
		repository.delete(detalhamento);
	}
}
