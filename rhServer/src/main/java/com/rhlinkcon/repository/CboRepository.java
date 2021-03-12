package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Cbo;

@Repository
public interface CboRepository extends JpaRepository<Cbo, Long> {

	Page<Cbo> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	List<Cbo> findByNomeIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String nome, String codigo);

	List<Cbo> findByNomeIgnoreCaseContaining(String nome);

	Boolean existsByNome(String nome);

	Boolean existsByCodigo(String nome);

	Boolean existsByNomeAndIdNot(String nome, Long id);

}
