package com.rhlinkcon.payload.evento;

import javax.validation.constraints.NotNull;

public class EventoRequest {

	private Long id;

	@NotNull
	private String descricao;

	@NotNull
	private String situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}