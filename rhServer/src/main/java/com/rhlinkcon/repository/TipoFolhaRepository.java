package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TipoFolha;
import com.rhlinkcon.model.Verba;

@Repository
public interface TipoFolhaRepository extends JpaRepository<TipoFolha, Long> {
	
	Page<TipoFolha> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
	
	Boolean existsByVerbasContaining(Verba verba);

}
