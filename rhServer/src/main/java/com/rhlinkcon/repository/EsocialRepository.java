package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Esocial;

@Repository
public interface EsocialRepository extends JpaRepository<Esocial, Long> {

	Page<Esocial> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	List<Esocial> findByDescricaoIgnoreCaseContaining(String descricao);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
}
