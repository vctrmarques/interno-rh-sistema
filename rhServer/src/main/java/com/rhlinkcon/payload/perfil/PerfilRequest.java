package com.rhlinkcon.payload.perfil;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PerfilRequest {

	private Long id;

	@NotBlank
	@NotNull
	@Size(max = 255)
	private String nome;

	@NotNull
	@NotEmpty
	private List<Long> papelIds = new ArrayList<Long>();

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

	public List<Long> getPapelIds() {
		return papelIds;
	}

	public void setPapelIds(List<Long> papelIds) {
		this.papelIds = papelIds;
	}

}
