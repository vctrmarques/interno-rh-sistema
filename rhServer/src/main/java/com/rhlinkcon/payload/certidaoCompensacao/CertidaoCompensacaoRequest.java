package com.rhlinkcon.payload.certidaoCompensacao;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.certidaoCompensacaoAssinatura.CertidaoCompensacaoAssinaturaRequest;
import com.rhlinkcon.payload.certidaoCompensacaoPeriodo.CertidaoCompensacaoPeriodoRequest;

public class CertidaoCompensacaoRequest {

	private Long id;

	private Long numero;

	private Integer ano;

	private Long funcionarioId;

	private String numeroCtps;

	private String serieCtps;

	@NotNull
	private List<String> classificacoes;

	private String statusAtual;

	private Long lotacaoId;

	private String processo;

	private List<Long> anexos;

	private Set<CertidaoCompensacaoPeriodoRequest> periodos;

	private Set<CertidaoCompensacaoAssinaturaRequest> assinaturas;

	private String observacaoParaHistorico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getStatusAtual() {
		return statusAtual;
	}

	public void setStatusAtual(String statusAtual) {
		this.statusAtual = statusAtual;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public List<Long> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Long> anexos) {
		this.anexos = anexos;
	}

	public Set<CertidaoCompensacaoPeriodoRequest> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<CertidaoCompensacaoPeriodoRequest> periodos) {
		this.periodos = periodos;
	}

	public Set<CertidaoCompensacaoAssinaturaRequest> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(Set<CertidaoCompensacaoAssinaturaRequest> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public String getObservacaoParaHistorico() {
		return observacaoParaHistorico;
	}

	public void setObservacaoParaHistorico(String observacaoParaHistorico) {
		this.observacaoParaHistorico = observacaoParaHistorico;
	}

	public List<String> getClassificacoes() {
		return classificacoes;
	}

	public void setClassificacoes(List<String> classificacoes) {
		this.classificacoes = classificacoes;
	}

	public String getNumeroCtps() {
		return numeroCtps;
	}

	public void setNumeroCtps(String numeroCtps) {
		this.numeroCtps = numeroCtps;
	}

	public String getSerieCtps() {
		return serieCtps;
	}

	public void setSerieCtps(String serieCtps) {
		this.serieCtps = serieCtps;
	}

}
