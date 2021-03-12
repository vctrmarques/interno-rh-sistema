package com.rhlinkcon.repository.arrecadacaoIndice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.ArrecadacaoIndiceFiltro;
import com.rhlinkcon.model.ArrecadacaoIndice;

public interface ArrecadacaoIndiceCustom {

	Page<ArrecadacaoIndice> filtro(ArrecadacaoIndiceFiltro filtro, Pageable pageable);
	
	List<ArrecadacaoIndice> filtro(ArrecadacaoIndiceFiltro filtro);
}
