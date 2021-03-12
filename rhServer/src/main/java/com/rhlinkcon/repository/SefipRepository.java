package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Sefip;
import com.rhlinkcon.model.TipoSefipEnum;

@Repository
public interface SefipRepository extends JpaRepository<Sefip, Long> {

	Page<Sefip> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	List<Sefip> findByDescricaoIgnoreCaseContainingAndTipo(String descricao, TipoSefipEnum tipo);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}
