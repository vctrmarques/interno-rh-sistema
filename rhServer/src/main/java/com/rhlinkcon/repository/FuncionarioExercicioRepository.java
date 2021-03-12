package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FuncionarioExercicio;

@Repository
public interface FuncionarioExercicioRepository extends JpaRepository<FuncionarioExercicio, Long> {
	
	List<FuncionarioExercicio> findByFuncionarioId(Long funcionarioId);

}
