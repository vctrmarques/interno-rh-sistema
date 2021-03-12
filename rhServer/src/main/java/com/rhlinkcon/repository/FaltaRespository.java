package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Falta;

@Repository
public interface FaltaRespository extends JpaRepository<Falta, Long> {
	Falta findFaltaByAnexoId(Long id);
}
