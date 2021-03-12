package com.rhlinkcon.filtro;

public class DeclaracaoAposentadoriaFiltro {

	private String numero;

	private String ano;

	private String descricao;

	public DeclaracaoAposentadoriaFiltro(String numero, String ano, String descricao) {
		this.numero = numero;
		this.ano = ano;
		this.descricao = descricao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
