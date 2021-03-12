package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Verba;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long> {

	Page<Funcao> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	Boolean existsByNome(String nome);

	Boolean existsByNomeAndIdNot(String nome, Long id);

	Boolean existsByVerbasContaining(Verba verba);
	
	List<Funcao> findByNomeIgnoreCaseContaining(String search);
}
