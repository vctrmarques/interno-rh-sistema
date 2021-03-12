package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ResponsavelLegal;

@Repository
public interface ResponsavelLegalRepository extends JpaRepository<ResponsavelLegal, Long> {

	Page<ResponsavelLegal> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
	
	Boolean existsByNome(String nome);
	
	Boolean existsByNomeAndIdNot(String nome, Long id);

	List<ResponsavelLegal> findByNomeIgnoreCaseContaining(String search);
	
}
