package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Requisito;

@Repository
public interface RequisitoRepository extends JpaRepository<Requisito, Long> {

	Page<Requisito> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String nome);

	Boolean existsByDescricaoAndIdNot(String nome, Long id);

}
