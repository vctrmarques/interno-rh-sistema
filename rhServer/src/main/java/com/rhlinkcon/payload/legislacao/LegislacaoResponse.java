package com.rhlinkcon.payload.legislacao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.legislacao.Legislacao;
import com.rhlinkcon.model.legislacao.LegislacaoAnexo;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class LegislacaoResponse extends DadoCadastralResponse {

	private Long id;

	private DadoBasicoDto enteFederado;

	private NormaResponse norma;

	private DetalhamentoNormaResponse detalhamentoNorma;

	private Integer numeroNorma;

	private Integer anoNorma;

	private String ementaNorma;

	private Instant inicioEfeitoFinanceiro;

	private Instant publicacao;

	private Instant inicioVigencia;

	private Instant fimVigencia;

	private DadoBasicoDto assuntoNorma;

	private DadoBasicoDto unidadeGestora;

	private DadoBasicoDto pessoalLegislacao;

	private List<LegislacaoAnexoResponse> legislacaoAnexoList;

	public LegislacaoResponse() {

	}

	public LegislacaoResponse(Legislacao legislacao) {
		this.id = legislacao.getId();
		this.numeroNorma = legislacao.getNumeroNorma();
		this.anoNorma = legislacao.getAnoNorma();
		this.ementaNorma = legislacao.getEmentaNorma();
		this.inicioEfeitoFinanceiro = legislacao.getInicioEfeitoFinanceiro();
		this.publicacao = legislacao.getPublicacao();
		this.inicioVigencia = legislacao.getInicioVigencia();
		this.fimVigencia = legislacao.getFimVigencia();

		this.setAlteradoEm(legislacao.getUpdatedAt());
		this.setCriadoEm(legislacao.getCreatedAt());

		this.enteFederado = new DadoBasicoDto(legislacao.getEnteFederado());

		this.norma = new NormaResponse(legislacao.getNorma());

		this.detalhamentoNorma = new DetalhamentoNormaResponse(legislacao.getDetalhamentoNorma());

		this.assuntoNorma = new DadoBasicoDto(legislacao.getAssuntoNorma());

		this.unidadeGestora = new DadoBasicoDto(legislacao.getUnidadeGestora());

		if (Objects.nonNull(legislacao.getPessoalLegislacao()))
			this.pessoalLegislacao = new DadoBasicoDto(legislacao.getPessoalLegislacao());

		this.legislacaoAnexoList = new ArrayList<>();
		for (LegislacaoAnexo legislacaoAnexo : legislacao.getAnexos()) {
			legislacaoAnexoList.add(new LegislacaoAnexoResponse(legislacaoAnexo));
		}

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

	public List<LegislacaoAnexoResponse> getLegislacaoAnexoList() {
		return legislacaoAnexoList;
	}

	public void setLegislacaoAnexoList(List<LegislacaoAnexoResponse> legislacaoAnexoList) {
		this.legislacaoAnexoList = legislacaoAnexoList;
	}

	public NormaResponse getNorma() {
		return norma;
	}

	public void setNorma(NormaResponse norma) {
		this.norma = norma;
	}

	public DetalhamentoNormaResponse getDetalhamentoNorma() {
		return detalhamentoNorma;
	}

	public void setDetalhamentoNorma(DetalhamentoNormaResponse detalhamentoNorma) {
		this.detalhamentoNorma = detalhamentoNorma;
	}

}
