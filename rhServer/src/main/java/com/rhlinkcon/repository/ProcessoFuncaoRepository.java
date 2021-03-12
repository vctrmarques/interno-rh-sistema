package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ProcessoFuncao;

@Repository
public interface ProcessoFuncaoRepository extends JpaRepository<ProcessoFuncao, Long> {

	Page<ProcessoFuncao> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	List<ProcessoFuncao> findByDescricaoIgnoreCaseContaining(String descricao);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}
