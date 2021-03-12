package com.rhlinkcon.payload.subCategoriaDoenca;

import com.rhlinkcon.model.SubCategoriaDoenca;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class SubCategoriaDoencaResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	public SubCategoriaDoencaResponse() {
	}

	public SubCategoriaDoencaResponse(SubCategoriaDoenca subCategoriaDoenca) {
		setId(subCategoriaDoenca.getId());
		setDescricao(subCategoriaDoenca.getDescricao());
		setCodigo(subCategoriaDoenca.getCodigo());
		setCriadoEm(subCategoriaDoenca.getCreatedAt());
		setAlteradoEm(subCategoriaDoenca.getUpdatedAt());
	}

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
