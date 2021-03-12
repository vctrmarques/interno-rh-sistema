package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.NaturezaFuncao;

@Repository
public interface NaturezaFuncaoRepository extends JpaRepository<NaturezaFuncao, Long> {

	public Boolean existsByDescricao(String descricao);

	public Page<NaturezaFuncao> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	public List<NaturezaFuncao> findByDescricaoIgnoreCaseContaining(String descricao);

	public Boolean existsByDescricaoAndIdNot(String descricao, Long id);
}