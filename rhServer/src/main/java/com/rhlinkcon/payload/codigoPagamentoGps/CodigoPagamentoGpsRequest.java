package com.rhlinkcon.payload.codigoPagamentoGps;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

public class CodigoPagamentoGpsRequest {

	private Long id;
	
	@NotNull
	private String codigo;
	
	@NotNull
	@NotBlank
	private String descricao;

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
	
}