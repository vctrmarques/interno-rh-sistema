package com.rhlinkcon.repository.consultaPericiaMedica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.model.PacientePericiaMedica;

@Repository
public interface ConsultaPericiaMedicaRepository extends JpaRepository<ConsultaPericiaMedica, Long>, ConsultaPericiaMedicaRepositoryCustom {

	boolean existsById(Long id);
	
	@Query("SELECT cpm FROM ConsultaPericiaMedica cpm "
			+ "WHERE cpm.pacientePericiaMedica.id = :id")
	ConsultaPericiaMedica getConsultaPericiaMedicaById(@Param("id") Long id);
}