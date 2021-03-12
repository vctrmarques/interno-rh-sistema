package com.rhlinkcon.payload.grauAcademico;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

public class GrauAcademicoRequest {

	private Long id;
	
	@NotBlank
	@NotNull
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