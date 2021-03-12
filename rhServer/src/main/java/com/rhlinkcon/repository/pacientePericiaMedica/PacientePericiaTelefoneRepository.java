package com.rhlinkcon.repository.pacientePericiaMedica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.PacientePericiaTelefone;

@Repository
public interface PacientePericiaTelefoneRepository extends JpaRepository<PacientePericiaTelefone, Long>{

	boolean existsByPacientePericiaMedicaId(Long id);
	
	@Query("SELECT ppm FROM PacientePericiaTelefone ppm "
			+ "WHERE ppm.pacientePericiaMedica.id = :id")
	List<PacientePericiaTelefone> getPacientePericiaTelefoneByPacienteId(@Param("id") Long id);
	
}
