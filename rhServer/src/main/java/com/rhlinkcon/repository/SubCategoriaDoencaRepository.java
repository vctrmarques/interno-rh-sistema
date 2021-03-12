package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.SubCategoriaDoenca;

@Repository
public interface SubCategoriaDoencaRepository extends JpaRepository<SubCategoriaDoenca, Long> {

	List<SubCategoriaDoenca> findByCodigoOrDescricaoAllIgnoreCaseContaining(String codigo, String descricao);

	Page<SubCategoriaDoenca> findByDescricaoIgnoreCaseContaining(String nome, Pageable pageable);

	Boolean existsByCodigo(String codigo);

	Boolean existsByDescricao(String descricao);

	Boolean existsByCodigoAndIdNot(String codigo, Long id);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
}
