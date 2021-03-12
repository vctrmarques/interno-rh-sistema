package com.rhlinkcon.repository.agendaMedica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.AgendaMedicaDataFiltro;
import com.rhlinkcon.model.AgendaMedicaData;

public interface AgendaMedicaDataRepositoryCustom {

	@Autowired
	Page<AgendaMedicaData> filtro(AgendaMedicaDataFiltro filtro, Pageable pageable);

	List<AgendaMedicaData> filtro(AgendaMedicaDataFiltro filtro);
}
