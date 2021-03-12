package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.CertidaoCompensacaoHistorico;
import com.rhlinkcon.repository.CertidaoCompensacaoHistoricoRepository;

@Service
public class CertidaoCompensacaoHistoricoService {
	
	@Autowired
	private CertidaoCompensacaoHistoricoRepository repository;

	@Transactional
	public void create(CertidaoCompensacaoHistorico obj) {
		repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
