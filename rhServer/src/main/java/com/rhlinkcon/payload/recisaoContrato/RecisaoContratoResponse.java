package com.rhlinkcon.payload.recisaoContrato;

import java.time.Instant;

import com.rhlinkcon.model.RecisaoContrato;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

public class RecisaoContratoResponse {
	private Long id;
	private FuncionarioResponse funcionarioResponse;
	private Long funcionarioId;
	private Boolean feriasVencidas;
	private Boolean avisoPrevio;
	private Instant dataAviso;
	private Integer diasAviso;
	private Long motivoId;
	private String motivoNome;
	private String codMotivo;
	private Instant dataDesligamento;
	private Instant dataHomologacao;
	private Instant dataPagamento;
	private String status;
	
	public RecisaoContratoResponse(RecisaoContrato recisaoContrato) {
		setId(recisaoContrato.getId());
		setFeriasVencidas(recisaoContrato.getFeriasVencidas());
		setAvisoPrevio(recisaoContrato.getAvisoPrevio());
		setDataAviso(recisaoContrato.getDataAviso());
		setDiasAviso(recisaoContrato.getDiasAviso());
		setMotivoId(recisaoContrato.getMotivo().getId());
		setDataDesligamento(recisaoContrato.getDataDesligamento());
		setDataHomologacao(recisaoContrato.getDataHomologacao());
		setDataPagamento(recisaoContrato.getDataPagamento());
		setStatus(recisaoContrato.getStatus().getLabel());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionarioResponse() {
		return funcionarioResponse;
	}

	public void setFuncionarioResponse(FuncionarioResponse funcionarioResponse) {
		this.funcionarioResponse = funcionarioResponse;
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

	public Long getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(Long motivoId) {
		this.motivoId = motivoId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getMotivoNome() {
		return motivoNome;
	}

	public void setMotivoNome(String motivoNome) {
		this.motivoNome = motivoNome;
	}

	public String getCodMotivo() {
		return codMotivo;
	}

	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}
	
}
