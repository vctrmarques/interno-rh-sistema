package com.rhlinkcon.payload.codigoRecolhimento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

public class CodigoRecolhimentoRequest {

	private Long id;
	
	@NotNull
	private Integer codigo;
	
	@NotNull
	@NotBlank
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}