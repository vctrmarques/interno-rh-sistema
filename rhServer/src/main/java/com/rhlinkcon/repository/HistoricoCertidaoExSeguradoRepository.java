package com.rhlinkcon.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.HistoricoCertidaoExSegurado;
@Repository
public interface HistoricoCertidaoExSeguradoRepository extends JpaRepository<HistoricoCertidaoExSegurado,Long> {
	
	@Query("select max(numeroRetificacao) from HistoricoCertidaoExSegurado c where c.certidaoExSegurado.id = :id ")
	Integer maxNumeroRetificacaoByAnoAndNumero(@Param("id") Long certidaoId);

	@Query("SELECT h FROM HistoricoCertidaoExSegurado h WHERE h.certidaoExSegurado.id IN(SELECT c.id FROM CertidaoExSegurado c WHERE c.id = :id OR c.certidaoExSegurado.id = :id)")
	List<HistoricoCertidaoExSegurado> findHistoricoByCertidaoId(@Param("id") Long certidaoId);

	void deleteByCertidaoExSeguradoId(Long id);

} 