package com.rhlinkcon.payload.legislacao;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.DadoBasicoDto;

public class LegislacaoRequest {

	private Long id;

	@NotNull
	private DadoBasicoDto enteFederado;

	@NotNull
	private DadoBasicoDto norma;

	@NotNull
	private DadoBasicoDto detalhamentoNorma;

	@NotNull
	private Integer numeroNorma;

	@NotNull
	private Integer anoNorma;

	@NotNull
	@NotBlank
	private String ementaNorma;

	@NotNull
	private Instant inicioEfeitoFinanceiro;

	@NotNull
	private Instant publicacao;

	@NotNull
	private Instant inicioVigencia;

	private Instant fimVigencia;

	@NotNull
	private DadoBasicoDto assuntoNorma;

	@NotNull
	private DadoBasicoDto unidadeGestora;

	private DadoBasicoDto pessoalLegislacao;

	@NotNull
	@NotEmpty
	private List<LegislacaoAnexoRequest> legislacaoAnexoList;

	public LegislacaoRequest() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DadoBasicoDto getEnteFederado() {
		return enteFederado;
	}

	public void setEnteFederado(DadoBasicoDto enteFederado) {
		this.enteFederado = enteFederado;
	}

	public DadoBasicoDto getNorma() {
		return norma;
	}

	public void setNorma(DadoBasicoDto norma) {
		this.norma = norma;
	}

	public DadoBasicoDto getDetalhamentoNorma() {
		return detalhamentoNorma;
	}

	public void setDetalhamentoNorma(DadoBasicoDto detalhamentoNorma) {
		this.detalhamentoNorma = detalhamentoNorma;
	}

	public Integer getNumeroNorma() {
		return numeroNorma;
	}

	public void setNumeroNorma(Integer numeroNorma) {
		this.numeroNorma = numeroNorma;
	}

	public Integer getAnoNorma() {
		return anoNorma;
	}

	public void setAnoNorma(Integer anoNorma) {
		this.anoNorma = anoNorma;
	}

	public String getEmentaNorma() {
		return ementaNorma;
	}

	public void setEmentaNorma(String ementaNorma) {
		this.ementaNorma = ementaNorma;
	}

	public Instant getInicioEfeitoFinanceiro() {
		return inicioEfeitoFinanceiro;
	}

	public void setInicioEfeitoFinanceiro(Instant inicioEfeitoFinanceiro) {
		this.inicioEfeitoFinanceiro = inicioEfeitoFinanceiro;
	}

	public Instant getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Instant publicacao) {
		this.publicacao = publicacao;
	}

	public Instant getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Instant inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Instant getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(Instant fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public DadoBasicoDto getAssuntoNorma() {
		return assuntoNorma;
	}

	public void setAssuntoNorma(DadoBasicoDto assuntoNorma) {
		this.assuntoNorma = assuntoNorma;
	}

	public DadoBasicoDto getUnidadeGestora() {
		return unidadeGestora;
	}

	public void setUnidadeGestora(DadoBasicoDto unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}

	public DadoBasicoDto getPessoalLegislacao() {
		return pessoalLegislacao;
	}

	public void setPessoalLegislacao(DadoBasicoDto pessoalLegislacao) {
		this.pessoalLegislacao = pessoalLegislacao;
	}

	public List<LegislacaoAnexoRequest> getLegislacaoAnexoList() {
		return legislacaoAnexoList;
	}

	public void setLegislacaoAnexoList(List<LegislacaoAnexoRequest> legislacaoAnexoList) {
		this.legislacaoAnexoList = legislacaoAnexoList;
	}

}
