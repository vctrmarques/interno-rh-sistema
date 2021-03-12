package com.rhlinkcon.repository.legislacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.LegislacaoFiltroRequest;
import com.rhlinkcon.model.legislacao.Legislacao;

public interface LegislacaoRepositoryCustom {
	Page<Legislacao> filtro(LegislacaoFiltroRequest legislacaoFiltroRequest, Pageable pageable);

}
