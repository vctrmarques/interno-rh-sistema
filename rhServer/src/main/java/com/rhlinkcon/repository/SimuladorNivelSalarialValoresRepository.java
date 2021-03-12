package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.SimuladorNivelSalarial;
import com.rhlinkcon.model.SimuladorNivelSalarialValores;

@Repository
public interface SimuladorNivelSalarialValoresRepository extends JpaRepository<SimuladorNivelSalarialValores, Long> {

	

	Long countBySimulador(SimuladorNivelSalarial simulador);
	
	List<SimuladorNivelSalarialValores> findBySimulador(SimuladorNivelSalarial simulador); 
}