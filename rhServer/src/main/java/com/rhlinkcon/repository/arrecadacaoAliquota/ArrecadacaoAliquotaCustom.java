package com.rhlinkcon.repository.arrecadacaoAliquota;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.ArrecadacaoAliquotaFiltro;
import com.rhlinkcon.model.ArrecadacaoAliquotaPeriodo;

public interface ArrecadacaoAliquotaCustom {

	Page<ArrecadacaoAliquotaPeriodo> filtro(ArrecadacaoAliquotaFiltro filtro, Pageable pageable);
	
	List<ArrecadacaoAliquotaPeriodo> filtro(ArrecadacaoAliquotaFiltro filtro);
}
