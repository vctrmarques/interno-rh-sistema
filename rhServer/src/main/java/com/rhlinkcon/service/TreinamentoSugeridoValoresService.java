package com.rhlinkcon.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.TreinamentoSugeridoValores;
import com.rhlinkcon.payload.treinamentoSugeridoValores.TreinamentoSugeridoValoresRequest;
import com.rhlinkcon.repository.TreinamentoSugeridoValoresRepository;

@Service
public class TreinamentoSugeridoValoresService {
	
	@Autowired
	private TreinamentoSugeridoValoresRepository valoresRepository;

	@Transactional
	public void create(@Valid TreinamentoSugeridoValoresRequest request) {
		TreinamentoSugeridoValores tsv = new TreinamentoSugeridoValores(request);
		valoresRepository.save(tsv);
	}

}
