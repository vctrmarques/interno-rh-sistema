package com.rhlinkcon.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RelacaoRemuneracaoCertidaoExSegurado;
@Repository
public interface RelacaoRemuneracaoCertidaoExSeguradoRepository extends JpaRepository<RelacaoRemuneracaoCertidaoExSegurado,Long> {

	void deleteByCertidaoExSeguradoId(Long id);
} 