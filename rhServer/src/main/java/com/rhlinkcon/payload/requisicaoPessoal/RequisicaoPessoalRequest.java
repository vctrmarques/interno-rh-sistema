package com.rhlinkcon.payload.requisicaoPessoal;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.RequisicaoPessoalCandidato;
import com.rhlinkcon.payload.requisicaoPessoalFuncao.RequisicaoPessoalFuncaoRequest;

public class RequisicaoPessoalRequest {

	private Long id;

	@NotNull
	private Long solicitanteId;

	@NotNull
	private String situacao;

	@NotNull
	private String justificativa;

	@NotNull
	private Instant dataEntrada;

	@NotNull
	private Instant dataLimite;

	@NotNull
	private String motivoSolicitacao;

	private Long funcionarioSubstituidoId;

	@NotNull
	private Instant dataPrevistaAdimissao;

	private List<RequisicaoPessoalFuncaoRequest> funcoes;

	private List<RequisicaoPessoalCandidato> candidatos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSolicitanteId() {
		return solicitanteId;
	}

	public void setSolicitanteId(Long solicitanteId) {
		this.solicitanteId = solicitanteId;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Instant getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Instant dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Instant getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Instant dataLimite) {
		this.dataLimite = dataLimite;
	}

	public String getMotivoSolicitacao() {
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(String motivoSolicitacao) {
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public Long getFuncionarioSubstituidoId() {
		return funcionarioSubstituidoId;
	}

	public void setFuncionarioSubstituidoId(Long funcionarioSubstituidoId) {
		this.funcionarioSubstituidoId = funcionarioSubstituidoId;
	}

	public Instant getDataPrevistaAdimissao() {
		return dataPrevistaAdimissao;
	}

	public void setDataPrevistaAdimissao(Instant dataPrevistaAdimissao) {
		this.dataPrevistaAdimissao = dataPrevistaAdimissao;
	}

	public List<RequisicaoPessoalFuncaoRequest> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<RequisicaoPessoalFuncaoRequest> funcoes) {
		this.funcoes = funcoes;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public List<RequisicaoPessoalCandidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<RequisicaoPessoalCandidato> candidatos) {
		this.candidatos = candidatos;
	}

}
