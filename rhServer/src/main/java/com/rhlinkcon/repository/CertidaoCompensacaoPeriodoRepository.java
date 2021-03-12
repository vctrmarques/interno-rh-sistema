package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CertidaoCompensacaoPeriodo;

@Repository
public interface CertidaoCompensacaoPeriodoRepository extends JpaRepository<CertidaoCompensacaoPeriodo, Long>{

}
