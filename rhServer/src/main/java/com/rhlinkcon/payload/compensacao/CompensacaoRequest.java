package com.rhlinkcon.payload.compensacao;

import java.time.Instant;

import javax.validation.constraints.NotNull;

public class CompensacaoRequest {

	private Long id;

	private Long tomadorServicoId;
	
	@NotNull
	private Instant competencia;
	
	@NotNull
	private Double valorCompensacao;
	
	private Instant dataCompensacaoInicial;
	
	private Instant dataCompensacaoFinal;
	
	private Integer campoSeisGpsAnterior;
	
	private Integer campoNoveGpsAnterior;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTomadorServicoId() {
		return tomadorServicoId;
	}

	public void setTomadorServicoId(Long tomadorServicoId) {
		this.tomadorServicoId = tomadorServicoId;
	}

	public Instant getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Instant competencia) {
		this.competencia = competencia;
	}

	public Double getValorCompensacao() {
		return valorCompensacao;
	}

	public void setValorCompensacao(Double valorCompensacao) {
		this.valorCompensacao = valorCompensacao;
	}

	public Instant getDataCompensacaoInicial() {
		return dataCompensacaoInicial;
	}

	public void setDataCompensacaoInicial(Instant dataCompensacaoInicial) {
		this.dataCompensacaoInicial = dataCompensacaoInicial;
	}

	public Instant getDataCompensacaoFinal() {
		return dataCompensacaoFinal;
	}

	public void setDataCompensacaoFinal(Instant dataCompensacaoFinal) {
		this.dataCompensacaoFinal = dataCompensacaoFinal;
	}

	public Integer getCampoSeisGpsAnterior() {
		return campoSeisGpsAnterior;
	}

	public void setCampoSeisGpsAnterior(Integer campoSeisGpsAnterior) {
		this.campoSeisGpsAnterior = campoSeisGpsAnterior;
	}

	public Integer getCampoNoveGpsAnterior() {
		return campoNoveGpsAnterior;
	}

	public void setCampoNoveGpsAnterior(Integer campoNoveGpsAnterior) {
		this.campoNoveGpsAnterior = campoNoveGpsAnterior;
	}
	
}