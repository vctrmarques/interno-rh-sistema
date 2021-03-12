package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.GrupoSalarial;

@Repository
public interface GrupoSalarialRepository extends JpaRepository<GrupoSalarial, Long> {
	
	public Boolean existsByNome(String nome);
	
	public Page<GrupoSalarial> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
	
	public Boolean existsByNomeAndIdNot(String nome, Long id);

}
