package com.rhlinkcon.repository.arquivoRemessaPagamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.ArquivoRemessaPagamentoFiltro;
import com.rhlinkcon.model.ArquivoRemessaPagamento;

public interface ArquivoRemessaPagamentoRepositoryCustom {

	Page<ArquivoRemessaPagamento> filtro(ArquivoRemessaPagamentoFiltro arquivoRemessaFiltro, Pageable pageable);
	
}
