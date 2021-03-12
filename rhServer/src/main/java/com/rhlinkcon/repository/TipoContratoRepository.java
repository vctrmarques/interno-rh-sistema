package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TipoContrato;

@Repository
public interface TipoContratoRepository extends JpaRepository<TipoContrato, Long> {
	
	Page<TipoContrato> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
	
	List<TipoContrato> findByNomeIgnoreCaseContaining(String nome);
	
	Boolean existsByNome(String nome);

	Boolean existsByNomeAndIdNot(String nome, Long id);

}
