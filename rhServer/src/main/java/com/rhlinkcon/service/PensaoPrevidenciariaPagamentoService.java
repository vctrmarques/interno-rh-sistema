package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.PensaoPrevidenciariaPagamento;
import com.rhlinkcon.payload.pensionista.PensaoPrevidenciariaPagamentoRequest;
import com.rhlinkcon.repository.PensaoPrevidenciariaPagamentoRepository;

@Service
public class PensaoPrevidenciariaPagamentoService {

	@Autowired
	private PensaoPrevidenciariaPagamentoRepository repository;
	
	@Transactional
	public void create(PensaoPrevidenciariaPagamentoRequest request) {
		PensaoPrevidenciariaPagamento ppp = new PensaoPrevidenciariaPagamento(request);
		repository.save(ppp);
	}

}
