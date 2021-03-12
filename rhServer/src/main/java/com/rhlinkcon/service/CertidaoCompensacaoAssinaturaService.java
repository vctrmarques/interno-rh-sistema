package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.CertidaoCompensacaoAssinatura;
import com.rhlinkcon.payload.certidaoCompensacaoAssinatura.CertidaoCompensacaoAssinaturaRequest;
import com.rhlinkcon.repository.CertidaoCompensacaoAssinaturaRepository;

@Service
public class CertidaoCompensacaoAssinaturaService {

	@Autowired
	private CertidaoCompensacaoAssinaturaRepository repository;

	public void create(CertidaoCompensacaoAssinaturaRequest assinaturaRequest) {
		CertidaoCompensacaoAssinatura da = new CertidaoCompensacaoAssinatura(assinaturaRequest);
		repository.save(da);
	}

	public void create(CertidaoCompensacaoAssinatura certidaoCompensacaoAssinatura) {
		repository.save(certidaoCompensacaoAssinatura);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
