package com.rhlinkcon.payload.categoriaDoenca;


import com.rhlinkcon.model.CategoriaDoenca;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CategoriaDoencaResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	private boolean ativo;

	public CategoriaDoencaResponse() {
	}

	public CategoriaDoencaResponse(CategoriaDoenca categoriaDoenca) {
		setId(categoriaDoenca.getId());
		setDescricao(categoriaDoenca.getDescricao());
		setCodigo(categoriaDoenca.getCodigo());
		setCriadoEm(categoriaDoenca.getCreatedAt());
		setAlteradoEm(categoriaDoenca.getUpdatedAt());
		setAtivo(categoriaDoenca.isAtivo());
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
