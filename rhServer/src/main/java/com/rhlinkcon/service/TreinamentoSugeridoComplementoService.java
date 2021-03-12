package com.rhlinkcon.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.TreinamentoSugeridoComplemento;
import com.rhlinkcon.payload.treinamentoSugeridoComplemento.TreinamentoSugeridoComplementoRequest;
import com.rhlinkcon.repository.TreinamentoSugeridoComplementoRepository;

@Service
public class TreinamentoSugeridoComplementoService {
	
	@Autowired
	private TreinamentoSugeridoComplementoRepository complementoRepository;

	@Transactional
	public void create(@Valid TreinamentoSugeridoComplementoRequest request) {
		TreinamentoSugeridoComplemento tsc = new TreinamentoSugeridoComplemento(request);
		complementoRepository.save(tsc);
	}

}
