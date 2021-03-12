package com.rhlinkcon.payload.naturezaFuncao;

import java.util.List;

import javax.validation.constraints.NotNull;

public class NaturezaFuncaoRequest {

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
