package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ClassificacaoInternacionalDoenca;

@Repository
public interface ClassificacaoInternacionalDoencaRepository
		extends JpaRepository<ClassificacaoInternacionalDoenca, Long> {

	Page<ClassificacaoInternacionalDoenca> findByDescricaoIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String nome,String codigo, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}