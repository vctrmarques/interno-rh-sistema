package com.rhlinkcon.repository.prontuarioPerciaMedica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ProntuarioPericiaMedica;

@Repository
public interface ProntuarioPericiaMedicaRepository extends  JpaRepository<ProntuarioPericiaMedica, Long> {

	@Query("SELECT ppm FROM ProntuarioPericiaMedica ppm"
			+ " WHERE ppm.pacientePericiaMedica.id = :id ")
	List<ProntuarioPericiaMedica> getConsultaProntuarioPericiaMedicaByPacientePericiaMedicaId(@Param("id") Long id);
	
	@Query("SELECT ppm FROM ProntuarioPericiaMedica ppm"
			+ " WHERE ppm.consultaPericiaMedica.id = :id ")
	ProntuarioPericiaMedica getConsultaProntuarioPericiaMedicaByConsultaPericiaMedicaId(@Param("id") Long id);
	
}
