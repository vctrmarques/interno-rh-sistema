package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.SimuladorNivelSalarial;
import com.rhlinkcon.model.SimuladorNivelSalarialAcordo;

@Repository
public interface SimuladorNivelSalarialAcordoRepository extends JpaRepository<SimuladorNivelSalarialAcordo, Long> {

	
	SimuladorNivelSalarialAcordo findBySimulador(SimuladorNivelSalarial simulador);

}