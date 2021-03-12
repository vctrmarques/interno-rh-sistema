package com.rhlinkcon.repository.periciaMedica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.model.PacientePericiaMedica;

@Repository
public interface PericiaMedicaRepository extends JpaRepository<ConsultaPericiaMedica, Long> {
	
	@Query("SELECT cpm FROM ConsultaPericiaMedica cpm"
			+ " WHERE cpm.pacientePericiaMedica.id = :id "
			+ " AND cpm.compareceu = false "
			+ " AND DATENAME(YEAR, CURRENT_TIMESTAMP) >= DATENAME(YEAR, cpm.agendaMedicaData.data)")
	List<ConsultaPericiaMedica> getConsultaPericiaMedicaByPacientePericiaMedicaId(@Param("id") Long id);
	
}
