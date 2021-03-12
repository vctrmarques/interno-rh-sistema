package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CertidaoCompensacaoHistorico;

@Repository
public interface CertidaoCompensacaoHistoricoRepository extends JpaRepository<CertidaoCompensacaoHistorico, Long>{

}
