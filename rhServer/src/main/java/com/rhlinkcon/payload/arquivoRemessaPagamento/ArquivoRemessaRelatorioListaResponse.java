package com.rhlinkcon.payload.arquivoRemessaPagamento;

public class ArquivoRemessaRelatorioListaResponse {

	private String codigoFilial;
	
	private String matricula;
	
	private String nome;
	
	private String conta;
	
	private String agencia;
	
	private Double valorLiquido;
	
	private String codigoLotacao;

	public String getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(String codigoFilial) {
		this.codigoFilial = codigoFilial;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public String getCodigoLotacao() {
		return codigoLotacao;
	}

	public void setCodigoLotacao(String codigoLotacao) {
		this.codigoLotacao = codigoLotacao;
	}
	
	
}
