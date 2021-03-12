package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.UnidadeFederativa;

@Repository
public interface UnidadeFederativaRepository extends JpaRepository<UnidadeFederativa, Long> {

	Page<UnidadeFederativa> findBySiglaIgnoreCaseContaining(String sigla, Pageable pageable);

	UnidadeFederativa findBySiglaIgnoreCaseContaining(String sigla);

	Boolean existsBySigla(String sigla);

	Boolean existsBySiglaAndIdNot(String sigla, Long id);

}
