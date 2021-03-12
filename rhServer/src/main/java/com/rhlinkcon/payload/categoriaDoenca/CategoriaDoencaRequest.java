package com.rhlinkcon.payload.categoriaDoenca;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoriaDoencaRequest {

	private Long id;

	@NotNull
	@NotBlank
	private String codigo;

	@NotBlank
	@NotNull
	private String descricao;

	private boolean ativo;

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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	

}
