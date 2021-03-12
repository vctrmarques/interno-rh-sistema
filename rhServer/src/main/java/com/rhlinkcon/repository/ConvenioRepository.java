package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Convenio;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

	List<Convenio> findByIdOrDescricaoAllIgnoreCaseContaining(Integer codigo, String descricao);

	Page<Convenio> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}
