package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ModeloDocumento;

@Repository
public interface ModeloConteudoRepository extends JpaRepository<ModeloDocumento, Long>{

	Page<ModeloDocumento> findByDescricaoIgnoreCaseContaining(String nome, Pageable pageable);
	
	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
}
