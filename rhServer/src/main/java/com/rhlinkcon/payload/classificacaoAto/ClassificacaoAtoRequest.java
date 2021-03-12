package com.rhlinkcon.payload.classificacaoAto;

import javax.validation.constraints.NotNull;
public class ClassificacaoAtoRequest {

	private Long id;

	@NotNull
	private String descricao;

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

}