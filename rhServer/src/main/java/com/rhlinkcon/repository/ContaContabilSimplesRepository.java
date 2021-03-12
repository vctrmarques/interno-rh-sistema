package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ContaContabilSimples;

@Repository
public interface ContaContabilSimplesRepository extends JpaRepository<ContaContabilSimples, Long> {

	Page<ContaContabilSimples> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);
	
	Boolean existsByCodigo(String codigo);
	
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}
