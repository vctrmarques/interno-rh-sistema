package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.GrauAcademico;

@Repository
public interface GrauAcademicoRepository extends JpaRepository<GrauAcademico, Long> {

	Page<GrauAcademico> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
	
	List<GrauAcademico> findByNomeIgnoreCaseContaining(String nome);

	Boolean existsByNome(String nome);
	
	Boolean existsByNomeAndIdNot(String nome, Long id);

}