package com.rhlinkcon.repository.dirf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.DirfFiltro;
import com.rhlinkcon.model.Dirf;

public interface DirfRepositoryCustom {

	Page<Dirf> filtro(DirfFiltro filtro, Pageable pageable);
	
}
