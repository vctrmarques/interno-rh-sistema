package com.rhlinkcon.payload.certidaoExSegurado;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class CertidaoExSeguradoRequest {
	
	private Long id;
	
	private Integer numeroCertidao;
	
	private Long numeroRetificacao;

	private Integer anoCertidao;

	private String statusSituacaoCertidao;
	
	private Long lotacaoId;
	
	private String fonteInformacao;
	
	private Instant dataExoneracao;
	
	private List<Long> anexosRequest;
	
	private Set<AssinaturaCertidaoExSeguradoRequest> assinaturas;
	
	//Aba Dados Funcionais
	
	private Instant periodoEfetivoContribuicaoFinal;
	
	@NotNull
	private Long funcionarioId;
	
	private Set<PeriodoCertidaoExSeguradoRequest> periodos;
	
	private LinkedHashSet<CertidaoExServidorCargoRequest> cargos;
	
	private LinkedHashSet<CertidaoExServidorOrgaoLotacaoRequest> orgaosLotacao;

	//Aba Detalhamento Frequência
	
	private Set<FrequenciaCertidaoExSeguradoRequest> frequencias;
	
	private Set<FrequenciaCertidaoExServidorDetalhamentoRequest> detalhamentosFrequencia;
	
	private Set<TempoEspecialCertidaoExSeguradoRequest> tempoEspecial;
	
	//Ana Relação Remuneração
	
	private Set<RelacaoRemuneracaoCertidaoExSeguradoRequest> relacaoRemuneracoes;
	
	private boolean isRetificacao;
	
	private boolean isRascunho;
	
	private Long certidaoExSeguradoId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Integer getNumeroCertidao() {
		return numeroCertidao;
	}

	public void setNumeroCertidao(Integer numeroCertidao) {
		this.numeroCertidao = numeroCertidao;
	}

	public Integer getAnoCertidao() {
		return anoCertidao;
	}

	public void setAnoCertidao(Integer anoCertidao) {
		this.anoCertidao = anoCertidao;
	}

	public String getStatusSituacaoCertidao() {
		return statusSituacaoCertidao;
	}

	public void setStatusSituacaoCertidao(String statusSituacaoCertidao) {
		this.statusSituacaoCertidao = statusSituacaoCertidao;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public String getFonteInformacao() {
		return fonteInformacao;
	}

	public void setFonteInformacao(String fonteInformacao) {
		this.fonteInformacao = fonteInformacao;
	}
	

	public Instant getPeriodoEfetivoContribuicaoFinal() {
		return periodoEfetivoContribuicaoFinal;
	}

	public void setPeriodoEfetivoContribuicaoFinal(Instant periodoEfetivoContribuicaoFinal) {
		this.periodoEfetivoContribuicaoFinal = periodoEfetivoContribuicaoFinal;
	}

	public List<Long> getAnexosRequest() {
		return anexosRequest;
	}

	public void setAnexosRequest(List<Long> anexosRequest) {
		this.anexosRequest = anexosRequest;
	}

	public Instant getDataExoneracao() {
		return dataExoneracao;
	}

	public void setDataExoneracao(Instant dataExoneracao) {
		this.dataExoneracao = dataExoneracao;
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public boolean isRetificacao() {
		return isRetificacao;
	}

	public void setRetificacao(boolean isRetificacao) {
		this.isRetificacao = isRetificacao;
	}

	public boolean isRascunho() {
		return isRascunho;
	}

	public void setRascunho(boolean isRascunho) {
		this.isRascunho = isRascunho;
	}

	public Set<FrequenciaCertidaoExSeguradoRequest> getFrequencias() {
		return frequencias;
	}

	public void setFrequencias(Set<FrequenciaCertidaoExSeguradoRequest> frequencias) {
		this.frequencias = frequencias;
	}

	public Set<AssinaturaCertidaoExSeguradoRequest> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(Set<AssinaturaCertidaoExSeguradoRequest> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public Set<PeriodoCertidaoExSeguradoRequest> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<PeriodoCertidaoExSeguradoRequest> periodos) {
		this.periodos = periodos;
	}


	public Set<FrequenciaCertidaoExServidorDetalhamentoRequest> getDetalhamentosFrequencia() {
		return detalhamentosFrequencia;
	}

	public void setDetalhamentosFrequencia(Set<FrequenciaCertidaoExServidorDetalhamentoRequest> detalhamentosFrequencia) {
		this.detalhamentosFrequencia = detalhamentosFrequencia;
	}

	public Set<TempoEspecialCertidaoExSeguradoRequest> getTempoEspecial() {
		return tempoEspecial;
	}

	public void setTempoEspecial(Set<TempoEspecialCertidaoExSeguradoRequest> tempoEspecial) {
		this.tempoEspecial = tempoEspecial;
	}

	public Set<RelacaoRemuneracaoCertidaoExSeguradoRequest> getRelacaoRemuneracoes() {
		return relacaoRemuneracoes;
	}

	public void setRelacaoRemuneracoes(Set<RelacaoRemuneracaoCertidaoExSeguradoRequest> relacaoRemuneracoes) {
		this.relacaoRemuneracoes = relacaoRemuneracoes;
	}

	public Long getCertidaoExSeguradoId() {
		return certidaoExSeguradoId;
	}

	public void setCertidaoExSeguradoId(Long certidaoExSeguradoId) {
		this.certidaoExSeguradoId = certidaoExSeguradoId;
	}

	public LinkedHashSet<CertidaoExServidorCargoRequest> getCargos() {
		return cargos;
	}

	public void setCargos(LinkedHashSet<CertidaoExServidorCargoRequest> cargos) {
		this.cargos = cargos;
	}

	public LinkedHashSet<CertidaoExServidorOrgaoLotacaoRequest> getOrgaosLotacao() {
		return orgaosLotacao;
	}

	public void setOrgaosLotacao(LinkedHashSet<CertidaoExServidorOrgaoLotacaoRequest> orgaosLotacao) {
		this.orgaosLotacao = orgaosLotacao;
	}

}
