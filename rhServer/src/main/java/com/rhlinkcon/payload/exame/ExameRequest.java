package com.rhlinkcon.payload.exame;

import javax.validation.constraints.NotNull;

public class ExameRequest {

	private Long id;

	@NotNull
	private String descricao;
	
	@NotNull
	private String codigo;

	@NotNull
	private String categoria;

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

	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}