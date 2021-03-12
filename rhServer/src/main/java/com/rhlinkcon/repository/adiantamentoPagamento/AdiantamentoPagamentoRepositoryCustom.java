package com.rhlinkcon.repository.adiantamentoPagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.AdiantamentoPagamentoFiltro;
import com.rhlinkcon.model.AdiantamentoPagamento;

public interface AdiantamentoPagamentoRepositoryCustom {
	@Autowired
	Page<AdiantamentoPagamento> filtro(AdiantamentoPagamentoFiltro adiantamentoPagamentoFiltro, Pageable pageable);
}
