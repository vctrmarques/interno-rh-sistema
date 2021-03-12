package com.rhlinkcon.payload.classificacaoInternacionalDoenca;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClassificacaoInternacionalDoencaRequest {

	private Long id;

	@NotBlank
	@NotNull
	private String codigo;

	@NotBlank
	@NotNull
	private String descricao;

	private Long categoriaDoencaId;

	private Long subCategoriaDoencaId;

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

	public Long getCategoriaDoencaId() {
		return categoriaDoencaId;
	}

	public void setCategoriaDoencaId(Long categoriaDoencaId) {
		this.categoriaDoencaId = categoriaDoencaId;
	}

	public Long getSubCategoriaDoencaId() {
		return subCategoriaDoencaId;
	}

	public void setSubCategoriaDoencaId(Long subCategoriaDoencaId) {
		this.subCategoriaDoencaId = subCategoriaDoencaId;
	}

}
