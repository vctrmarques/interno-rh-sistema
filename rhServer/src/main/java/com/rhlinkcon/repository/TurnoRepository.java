package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

	Page<Turno> findByCodigoIgnoreCaseContaining(String nome, Pageable pageable);

	Boolean existsByCodigo(String codigo);

	Boolean existsByCodigoAndIdNot(String codigo, Long id);

}