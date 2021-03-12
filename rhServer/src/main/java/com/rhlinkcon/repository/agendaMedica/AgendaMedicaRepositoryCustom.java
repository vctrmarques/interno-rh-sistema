package com.rhlinkcon.repository.agendaMedica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.AgendaMedicaFiltro;
import com.rhlinkcon.model.AgendaMedica;

public interface AgendaMedicaRepositoryCustom {

	@Autowired
	Page<AgendaMedica> filtro(AgendaMedicaFiltro filtro, Pageable pageable);

	List<AgendaMedica> filtro(AgendaMedicaFiltro filtro);
}
