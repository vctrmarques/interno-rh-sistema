package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.LicencaPremio;

@Repository
public interface LicencaPremioRepository extends JpaRepository<LicencaPremio, Long> {
	
	List<LicencaPremio> findByFuncionarioExercicioId(Long funcionarioExercicioId);
	
	void deleteFindByFuncionarioExercicioId(Long funcionarioId);

}
