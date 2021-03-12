package com.rhlinkcon.payload.recisaoContrato;

import java.time.Instant;

import javax.validation.constraints.NotNull;

public class RecisaoContratoRequest {
	private Long id;
	
	@NotNull
	private Long funcionarioId;
	
	@NotNull
	private Boolean feriasVencidas;
	
	@NotNull
	private Boolean avisoPrevio;
	
	private Instant dataAviso;
	
	private Integer diasAviso;
	
	@NotNull
	private Long motivoId;
	
	@NotNull
	private Instant dataDesligamento;
	
	private Instant dataHomologacao;
	
	private Instant dataPagamento;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFeriasVencidas() {
		return feriasVencidas;
	}

	public void setFeriasVencidas(Boolean feriasVencidas) {
		this.feriasVencidas = feriasVencidas;
	}

	public Boolean getAvisoPrevio() {
		return avisoPrevio;
	}

	public void setAvisoPrevio(Boolean avisoPrevio) {
		this.avisoPrevio = avisoPrevio;
	}

	public Instant getDataAviso() {
		return dataAviso;
	}

	public void setDataAviso(Instant dataAviso) {
		this.dataAviso = dataAviso;
	}

	public Integer getDiasAviso() {
		return diasAviso;
	}

	public void setDiasAviso(Integer diasAviso) {
		this.diasAviso = diasAviso;
	}

	public Instant getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Instant dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public Instant getDataHomologacao() {
		return dataHomologacao;
	}

	public void setDataHomologacao(Instant dataHomologacao) {
		this.dataHomologacao = dataHomologacao;
	}

	public Instant getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Instant dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Long getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(Long motivoId) {
		this.motivoId = motivoId;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
