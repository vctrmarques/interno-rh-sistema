package com.rhlinkcon.repository.agencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.AgenciaFiltro;
import com.rhlinkcon.model.Agencia;

public interface AgenciaRepositoryCustom {
	Page<Agencia> filtro(AgenciaFiltro agenciaFiltro, Pageable pageable);
}
