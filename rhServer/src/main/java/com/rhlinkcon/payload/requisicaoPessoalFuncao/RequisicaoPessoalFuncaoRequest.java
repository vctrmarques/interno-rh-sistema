package com.rhlinkcon.payload.requisicaoPessoalFuncao;

import javax.validation.constraints.NotNull;

public class RequisicaoPessoalFuncaoRequest {
	
	private Long id;

	@NotNull
	private Long funcaoId;

	@NotNull
	private String tipoContratacao;

	@NotNull
	private Integer qtdVagas;

	@NotNull
	private Double custoPorVaga;

	@NotNull
	private Long turnoId;

	@NotNull
	private Long requisicaoPessoalId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncaoId() {
		return funcaoId;
	}

	public void setFuncaoId(Long funcaoId) {
		this.funcaoId = funcaoId;
	}

	public String getTipoContratacao() {
		return tipoContratacao;
	}

	public void setTipoContratacao(String tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}

	public Integer getQtdVagas() {
		return qtdVagas;
	}

	public void setQtdVagas(Integer qtdVagas) {
		this.qtdVagas = qtdVagas;
	}

	public Double getCustoPorVaga() {
		return custoPorVaga;
	}

	public void setCustoPorVaga(Double custoPorVaga) {
		this.custoPorVaga = custoPorVaga;
	}

	public Long getTurnoId() {
		return turnoId;
	}

	public void setTurnoId(Long turnoId) {
		this.turnoId = turnoId;
	}

	public Long getRequisicaoPessoalId() {
		return requisicaoPessoalId;
	}

	public void setRequisicaoPessoalId(Long requisicaoPessoalId) {
		this.requisicaoPessoalId = requisicaoPessoalId;
	}
	
	
	
}
