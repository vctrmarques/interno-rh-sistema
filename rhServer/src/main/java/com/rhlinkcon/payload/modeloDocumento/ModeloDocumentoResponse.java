package com.rhlinkcon.payload.modeloDocumento;

import com.rhlinkcon.model.ModeloDocumento;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ModeloDocumentoResponse extends  DadoCadastralResponse{
	
	private Long id;

	private String descricao;
	
	private String conteudo;
	
	public ModeloDocumentoResponse() {
		
	}
	
	public ModeloDocumentoResponse(ModeloDocumento modeloDocumento) {
		setId(modeloDocumento.getId());
		setDescricao(modeloDocumento.getDescricao());
		setConteudo(modeloDocumento.getConteudo());
		setCriadoEm(modeloDocumento.getCreatedAt());
		setAlteradoEm(modeloDocumento.getUpdatedAt());
		
	}

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
