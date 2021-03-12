package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.EspecialidadeMedica;

@Repository
public interface EspecialidadeMedicaRepository extends JpaRepository<EspecialidadeMedica, Long> {

	Page<EspecialidadeMedica> findByCodigoContaining(String codigo, Pageable pageable);
	
	Page<EspecialidadeMedica> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	List<EspecialidadeMedica> findByNomeIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String nome, String codigo);

	List<EspecialidadeMedica> findByNomeIgnoreCaseContaining(String nome);
	
	EspecialidadeMedica findByNome(String nome);

	Boolean existsByNome(String nome);

	Boolean existsByCodigo(String nome);

	Boolean existsByNomeAndIdNot(String nome, Long id);
}
