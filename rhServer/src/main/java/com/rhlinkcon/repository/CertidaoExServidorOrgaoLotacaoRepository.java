package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CertidaoExServidorOrgaoLotacao;

@Repository
public interface CertidaoExServidorOrgaoLotacaoRepository extends JpaRepository<CertidaoExServidorOrgaoLotacao, Long>{

	void deleteByCertidaoExServidorId(Long id);

}
