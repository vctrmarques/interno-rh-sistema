package com.rhlinkcon.payload.esocial;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EsocialRequest {

	private Long id;

	@NotBlank
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
