package com.rhlinkcon.payload.classificacaoInternacionalDoenca;

import com.rhlinkcon.model.ClassificacaoInternacionalDoenca;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.categoriaDoenca.CategoriaDoencaResponse;
import com.rhlinkcon.payload.subCategoriaDoenca.SubCategoriaDoencaResponse;

public class ClassificacaoInternacionalDoencaResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	private CategoriaDoencaResponse categoriaDoenca;

	private SubCategoriaDoencaResponse subCategoriaDoenca;

	public ClassificacaoInternacionalDoencaResponse() {
	}

	public ClassificacaoInternacionalDoencaResponse(ClassificacaoInternacionalDoenca classificacaoInternacionalDoenca) {
		setId(classificacaoInternacionalDoenca.getId());
		setDescricao(classificacaoInternacionalDoenca.getDescricao());
		setCodigo(classificacaoInternacionalDoenca.getCodigo());
		setCriadoEm(classificacaoInternacionalDoenca.getCreatedAt());
		setAlteradoEm(classificacaoInternacionalDoenca.getUpdatedAt());
		if (classificacaoInternacionalDoenca.getCategoriaDoenca() != null) {
			setCategoriaDoenca(new CategoriaDoencaResponse(classificacaoInternacionalDoenca.getCategoriaDoenca()));
		}
		if (classificacaoInternacionalDoenca.getSubCategoriaDoenca() != null) {
			setSubCategoriaDoenca(
					new SubCategoriaDoencaResponse(classificacaoInternacionalDoenca.getSubCategoriaDoenca()));
		}
	}

	public CategoriaDoencaResponse getCategoriaDoenca() {
		return categoriaDoenca;
	}

	public void setCategoriaDoenca(CategoriaDoencaResponse categoriaDoenca) {
		this.categoriaDoenca = categoriaDoenca;
	}

	public SubCategoriaDoencaResponse getSubCategoriaDoenca() {
		return subCategoriaDoenca;
	}

	public void setSubCategoriaDoenca(SubCategoriaDoencaResponse subCategoriaDoenca) {
		this.subCategoriaDoenca = subCategoriaDoenca;
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
