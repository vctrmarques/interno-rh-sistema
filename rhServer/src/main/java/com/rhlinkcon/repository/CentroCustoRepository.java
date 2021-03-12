package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CentroCusto;

@Repository
public interface CentroCustoRepository extends JpaRepository<CentroCusto, Long> {

	Page<CentroCusto> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
	
	Boolean existsByDescricao(String descricao);
	
	@Query("SELECT c.contaDebito FROM CentroCusto c WHERE c.id = :id")
	Integer getContaDebitoById(@Param("id") Long id);

	@Query("SELECT c.contaCredito FROM CentroCusto c WHERE c.id = :id")
	Integer getContaCreditoById(@Param("id") Long id);

}