package com.rhlinkcon.payload.SolAdiantamento;

import javax.validation.constraints.NotNull;

public class SolAdiantamentoRequest {

	private Long id;
	
	@NotNull
	private Long solicitante;
	
	@NotNull
	private String porcentagemAdiantamento;
	
	@NotNull
	private String mesAdiantamento;
	
	@NotNull
	private String situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Long solicitante) {
		this.solicitante = solicitante;
	}

	public String getPorcentagemAdiantamento() {
		return porcentagemAdiantamento;
	}

	public void setPorcentagemAdiantamento(String porcentagemAdiantamento) {
		this.porcentagemAdiantamento = porcentagemAdiantamento;
	}

	public String getMesAdiantamento() {
		return mesAdiantamento;
	}

	public void setMesAdiantamento(String mesAdiantamento) {
		this.mesAdiantamento = mesAdiantamento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}	
}
