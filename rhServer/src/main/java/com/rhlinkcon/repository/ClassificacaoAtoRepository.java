package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ClassificacaoAto;

@Repository
public interface ClassificacaoAtoRepository extends JpaRepository<ClassificacaoAto, Long> {

	Page<ClassificacaoAto> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);
	
	List<ClassificacaoAto> findByDescricaoIgnoreCaseContaining(String descricao);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}
