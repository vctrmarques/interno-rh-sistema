package com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao;

import java.time.LocalDate;

import com.rhlinkcon.model.DeclaracaoAposentadoriaAverbacao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.declaracaoAposentadoria.DeclaracaoAposentadoriaResponse;
import com.rhlinkcon.util.Projecao;

public class DeclaracaoAposentadoriaAverbacaoResponse extends DadoCadastralResponse {

	private Long id;
	
	private DeclaracaoAposentadoriaResponse declaracaoAposentadoria;
	
	private String empregador;
	
	private LocalDate periodoInicio;
	
	private LocalDate periodoFim;
	
	private String fonteInf;
	
	private String observacao;
	
	private Boolean averbado;
	
	public DeclaracaoAposentadoriaAverbacaoResponse(DeclaracaoAposentadoriaAverbacao obj) {
		this.id = obj.getId();
		this.declaracaoAposentadoria = new DeclaracaoAposentadoriaResponse(obj.getDeclaracaoAposentadoria(), Projecao.BASICA);
		this.empregador = obj.getEmpregador();
		this.periodoInicio = obj.getPeriodoInicio();
		this.periodoFim = obj.getPeriodoFim();
		this.fonteInf = obj.getFonteInf();
		this.observacao = obj.getObservacao();
		this.averbado = obj.getAverbado();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeclaracaoAposentadoriaResponse getDeclaracaoAposentadoria() {
		return declaracaoAposentadoria;
	}

	public void setDeclaracaoAposentadoria(DeclaracaoAposentadoriaResponse declaracaoAposentadoria) {
		this.declaracaoAposentadoria = declaracaoAposentadoria;
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
