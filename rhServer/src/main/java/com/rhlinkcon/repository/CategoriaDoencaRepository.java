package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CategoriaDoenca;

@Repository
public interface CategoriaDoencaRepository extends JpaRepository<CategoriaDoenca, Long> {

	List<CategoriaDoenca> findByCodigoOrDescricaoAllIgnoreCaseContaining(String codigo, String descricao);

	Page<CategoriaDoenca> findByDescricaoIgnoreCaseContaining(String nome, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}