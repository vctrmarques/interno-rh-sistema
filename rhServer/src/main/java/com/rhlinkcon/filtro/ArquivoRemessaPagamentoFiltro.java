package com.rhlinkcon.filtro;

import java.util.List;

public class ArquivoRemessaPagamentoFiltro {

	private Long tipoProcessamento;
	
	private List<String> situacoes;

	public Long getTipoProcessamento() {
		return tipoProcessamento;
	}

	public void setTipoProcessamento(Long tipoProcessamento) {
		this.tipoProcessamento = tipoProcessamento;
	}

	public List<String> getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(List<String> situacoes) {
		this.situacoes = situacoes;
	}
	
	
}
