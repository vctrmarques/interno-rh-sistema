package com.rhlinkcon.repository.contracheque;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.ContrachequeFiltro;
import com.rhlinkcon.model.Contracheque;

public interface ContrachequeRepositoryQuery {
	List<Contracheque> filtro(ContrachequeFiltro filtro);

	@Autowired
	Page<Contracheque> filtro(ContrachequeFiltro filtro, Pageable pageable);

}