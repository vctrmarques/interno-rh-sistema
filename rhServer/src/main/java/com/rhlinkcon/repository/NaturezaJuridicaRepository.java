package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.NaturezaJuridica;

@Repository
public interface NaturezaJuridicaRepository extends JpaRepository<NaturezaJuridica, Long> {

	Page<NaturezaJuridica> findByNomeOrCodigo(String nome, String codigo, Pageable pageable);
	
	Page<NaturezaJuridica> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
	
	Page<NaturezaJuridica> findByCodigo(Integer codigo, Pageable pageable);

	Boolean existsByNome(String nome);
	
	Boolean existsByNomeAndIdNot(String nome, Long id);
	
	Boolean existsByCodigo (String codigo);
	
	Boolean existsByCodigoAndIdNot (String codigo, Long id);

}
