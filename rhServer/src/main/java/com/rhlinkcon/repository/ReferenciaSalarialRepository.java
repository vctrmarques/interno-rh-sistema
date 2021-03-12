package com.rhlinkcon.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ReferenciaSalarial;

@Repository
public interface ReferenciaSalarialRepository extends JpaRepository<ReferenciaSalarial, Long> {

	Page<ReferenciaSalarial> findByDescricaoIgnoreCaseContaining(String nome, Pageable pageable);

	Boolean existsByCodigo(String codigo);

	Boolean existsByCodigoAndIdNot(String codigo, Long id);
	
	List<ReferenciaSalarial> findByIdNotIn(Collection<Long> listaNotIn);

}