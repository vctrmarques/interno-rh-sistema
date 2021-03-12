package com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao;

import java.time.LocalDate;

public class DeclaracaoAposentadoriaAverbacaoRequest {

	private Long id;
	
	private Long declaracaoAposentadoriaId;
	
	private String empregador;
	
	private LocalDate periodoInicio;
	
	private LocalDate periodoFim;
	
	private String fonteInf;
	
	private String observacao;
	
	private Boolean averbado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeclaracaoAposentadoriaId() {
		return declaracaoAposentadoriaId;
	}

	public void setDeclaracaoAposentadoriaId(Long declaracaoAposentadoriaId) {
		this.declaracaoAposentadoriaId = declaracaoAposentadoriaId;
	}

	public String getEmpregador() {
		return empregador;
	}

	public void setEmpregador(String empregador) {
		this.empregador = empregador;
	}

	public LocalDate getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(LocalDate periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public LocalDate getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(LocalDate periodoFim) {
		this.periodoFim = periodoFim;
	}

	public String getFonteInf() {
		return fonteInf;
	}

	public void setFonteInf(String fonteInf) {
		this.fonteInf = fonteInf;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getAverbado() {
		return averbado;
	}

	public void setAverbado(Boolean averbado) {
		this.averbado = averbado;
	}
	
}
