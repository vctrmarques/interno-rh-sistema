package com.rhlinkcon.payload.categoriaEconomica;

import java.time.Instant;

import com.rhlinkcon.model.CategoriaEconomica;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CategoriaEconomicaResponse extends DadoCadastralResponse {
	
	private Long id;
	
	private String codigo;
	
	private String descricao;

	private String observacao;

	public CategoriaEconomicaResponse(CategoriaEconomica categoria) {
		this.setId(categoria.getId());
		this.setCodigo(categoria.getCodigo());
		this.setDescricao(categoria.getDescricao());
	}
	
	public CategoriaEconomicaResponse(CategoriaEconomica categoria, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		this.setId(categoria.getId());
		this.setCodigo(categoria.getCodigo());
		this.setDescricao(categoria.getDescricao());
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
