package com.rhlinkcon.repository.agendaMedica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.AgendaMedicaData;

@Repository
public interface AgendaMedicaDataRepository extends JpaRepository<AgendaMedicaData, Long>, AgendaMedicaDataRepositoryCustom {

	
	List<AgendaMedicaData> findAllByAgendaMedicaId(Long id);
}
