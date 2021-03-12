package com.rhlinkcon.payload.classeSalarial;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialRequest;

public class ClasseSalarialRequest {

	private Long id;

	@NotNull
	private String nome;
	
	private GrupoSalarialRequest grupoSalarial;

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
	
	public GrupoSalarialRequest getGrupoSalarial() {
		return grupoSalarial;
	}

	public void setGrupoSalarial(GrupoSalarialRequest grupoSalarial) {
		this.grupoSalarial = grupoSalarial;
	}

}
