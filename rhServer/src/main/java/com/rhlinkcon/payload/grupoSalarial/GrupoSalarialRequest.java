package com.rhlinkcon.payload.grupoSalarial;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.classeSalarial.ClasseSalarialRequest;

public class GrupoSalarialRequest {

	private Long id;

	@NotNull
	private String nome;
	
	@NotNull
	private List<ClasseSalarialRequest> listClassesSalariais;

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

	public List<ClasseSalarialRequest> getListClassesSalariais() {
		return listClassesSalariais;
	}

	public void setClassesSalariais(List<ClasseSalarialRequest> listClassesSalariais) {
		this.listClassesSalariais = listClassesSalariais;
	}

}
