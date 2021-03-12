package com.rhlinkcon.repository.consultaPericiaMedica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.ConsultaPericiaMedicaFiltro;
import com.rhlinkcon.model.ConsultaPericiaMedica;

public interface ConsultaPericiaMedicaRepositoryCustom {

	@Autowired
	Page<ConsultaPericiaMedica> filtro(ConsultaPericiaMedicaFiltro filtro, Pageable pageable);

	List<ConsultaPericiaMedica> filtro(ConsultaPericiaMedicaFiltro filtro);
}
