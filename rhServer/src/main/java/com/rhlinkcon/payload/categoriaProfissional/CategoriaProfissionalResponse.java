package com.rhlinkcon.payload.categoriaProfissional;

import com.rhlinkcon.model.CategoriaProfissional;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CategoriaProfissionalResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	public CategoriaProfissionalResponse() {
	}

	public CategoriaProfissionalResponse(CategoriaProfissional categoriaProfissional) {
		setId(categoriaProfissional.getId());
		setDescricao(categoriaProfissional.getDescricao());
		setCodigo(categoriaProfissional.getCodigo());
		setCriadoEm(categoriaProfissional.getCreatedAt());
		setAlteradoEm(categoriaProfissional.getUpdatedAt());
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
