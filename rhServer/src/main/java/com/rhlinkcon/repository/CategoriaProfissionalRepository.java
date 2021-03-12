package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CategoriaProfissional;
import com.rhlinkcon.model.Verba;

@Repository
public interface CategoriaProfissionalRepository extends JpaRepository<CategoriaProfissional, Long> {

	Page<CategoriaProfissional> findByDescricaoIgnoreCaseContaining(String nome, Pageable pageable);

	List<CategoriaProfissional> findByDescricaoIgnoreCaseContaining(String nome);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
	
	Boolean existsByVerbasContaining(Verba verba);

}
