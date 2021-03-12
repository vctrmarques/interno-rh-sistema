package com.rhlinkcon.repository.declaracaoExServidor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.DeclaracaoExServidorFiltro;
import com.rhlinkcon.model.DeclaracaoExServidor;

public interface DeclaracaoExServidorRepositoryCustom {

	Page<DeclaracaoExServidor> filtro(DeclaracaoExServidorFiltro declaracaoExServidorFiltro, Pageable pageable);
	
}
