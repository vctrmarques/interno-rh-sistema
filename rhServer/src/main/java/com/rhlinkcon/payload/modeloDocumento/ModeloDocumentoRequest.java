package com.rhlinkcon.payload.modeloDocumento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



public class ModeloDocumentoRequest {

	private Long id;

	@NotBlank
	@NotNull
	private String descricao;
	
	@NotBlank
	@NotNull
	private String conteudo;

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

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
}
