package com.rhlinkcon.repository.pacientePericiaMedica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.PacientePericiaMedica;

@Repository
public interface PacientePericiaMedicaRepository extends JpaRepository<PacientePericiaMedica, Long>{

	Boolean existsByCpf(String cpf);
	
	PacientePericiaMedica getByCpf(String cpf);
	
	@Query("SELECT ppm FROM PacientePericiaMedica ppm "
			+ "WHERE ppm.funcionario.id = :id")
	PacientePericiaMedica getPacientePericiaMedicaByFuncionarioId(@Param("id") Long id);
	
	@Query("SELECT ppm FROM PacientePericiaMedica ppm")
	List<PacientePericiaMedica> findAllByUpdatedAt();
	
	
}
