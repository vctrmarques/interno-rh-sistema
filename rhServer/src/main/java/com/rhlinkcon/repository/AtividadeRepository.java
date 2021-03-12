package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

	Page<Atividade> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);
	
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

	Boolean existsByCodigo(String codigo);
	
	Boolean existsByCodigoAndIdNot(String codigo, Long id);
}
