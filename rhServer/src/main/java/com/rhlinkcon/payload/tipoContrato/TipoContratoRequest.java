package com.rhlinkcon.payload.tipoContrato;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TipoContratoRequest {
	
	private Long id;

	@NotNull
	@NotBlank
	private String nome;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
