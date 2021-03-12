package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.CertidaoCompensacaoPeriodo;
import com.rhlinkcon.payload.certidaoCompensacaoPeriodo.CertidaoCompensacaoPeriodoRequest;
import com.rhlinkcon.repository.CertidaoCompensacaoPeriodoRepository;

@Service
public class CertidaoCompensacaoPeriodoService {

	@Autowired
	private CertidaoCompensacaoPeriodoRepository repository;

	@Transactional
	public void create(CertidaoCompensacaoPeriodoRequest request) {
		CertidaoCompensacaoPeriodo da = new CertidaoCompensacaoPeriodo(request);
		repository.save(da);
	}

	@Transactional
	public void create(CertidaoCompensacaoPeriodo certidaoCompensacaoPeriodo) {
		repository.save(certidaoCompensacaoPeriodo);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
