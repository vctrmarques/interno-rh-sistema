package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CertidaoCompensacaoAssinatura;

@Repository
public interface CertidaoCompensacaoAssinaturaRepository extends JpaRepository<CertidaoCompensacaoAssinatura, Long>{

	
}
