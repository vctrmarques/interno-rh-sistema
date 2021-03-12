package com.rhlinkcon.payload.historicoContabil;

import javax.validation.constraints.NotNull;

public class HistoricoContabilRequest {

	private Long id;

	@NotNull
	private String descricao;

	@NotNull
	private String codigo;

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}