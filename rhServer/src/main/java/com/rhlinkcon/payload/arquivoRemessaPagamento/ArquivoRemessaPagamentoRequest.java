package com.rhlinkcon.payload.arquivoRemessaPagamento;

import java.time.LocalDate;

public class ArquivoRemessaPagamentoRequest {

	private Long id;
	
	private Long folhaPagamentoId;
	
	private LocalDate dataPagamento;
	
	private String situacao;
	
	private String motivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFolhaPagamentoId() {
		return folhaPagamentoId;
	}

	public void setFolhaPagamentoId(Long folhaPagamentoId) {
		this.folhaPagamentoId = folhaPagamentoId;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
