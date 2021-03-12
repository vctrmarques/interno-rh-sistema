package com.rhlinkcon.payload.naturezaJuridica;

import javax.validation.constraints.NotNull;

public class NaturezaJuridicaRequest {

	private Long id;

	@NotNull
	private String codigo;

	@NotNull
	private String nome;

	@NotNull
	private String grupamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGrupamento() {
		return grupamento;
	}

	public void setGrupamento(String grupamento) {
		this.grupamento = grupamento;
	}

}