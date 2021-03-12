package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TipoAposentadoria;

@Repository
public interface TipoAposentadoriaRepository extends JpaRepository<TipoAposentadoria, Long> {
	Page<TipoAposentadoria> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);
	Boolean existsByDescricao(String descricao);
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
}
