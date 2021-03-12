package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TipoAverbacao;

@Repository
public interface TipoAverbacaoRepository extends JpaRepository<TipoAverbacao, Long> {

	Page<TipoAverbacao> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);
	
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}
