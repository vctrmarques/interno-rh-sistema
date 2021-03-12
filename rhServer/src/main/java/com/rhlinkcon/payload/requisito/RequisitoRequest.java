package com.rhlinkcon.payload.requisito;

import javax.validation.constraints.NotNull;

public class RequisitoRequest {

	private Long id;

	@NotNull
	private String descricao;

	@NotNull
	private String dadoComparativo;

	@NotNull
	private String comparacao;

	private String valor;

	private String inicioIntervalo;

	private String fimIntervalo;

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDadoComparativo() {
		return dadoComparativo;
	}

	public String getComparacao() {
		return comparacao;
	}

	public String getValor() {
		return valor;
	}

	public String getInicioIntervalo() {
		return inicioIntervalo;
	}

	public String getFimIntervalo() {
		return fimIntervalo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDadoComparativo(String dadoComparativo) {
		this.dadoComparativo = dadoComparativo;
	}

	public void setComparacao(String comparacao) {
		this.comparacao = comparacao;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setInicioIntervalo(String inicioIntervalo) {
		this.inicioIntervalo = inicioIntervalo;
	}

	public void setFimIntervalo(String fimIntervalo) {
		this.fimIntervalo = fimIntervalo;
	}

}