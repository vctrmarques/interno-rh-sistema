package com.rhlinkcon.payload.exame;

import java.time.Instant;

import com.rhlinkcon.model.Exame;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ExameResponse extends DadoCadastralResponse {

	private Long id;
	
	private String codigo;

	private String descricao;

	private String categoria;

	public ExameResponse(Exame exame) {
		setExame(exame);
	}

	public ExameResponse(Exame exame, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setExame(exame);
		
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}
	
	public void setExame(Exame exame) {
		this.setId(exame.getId());
		this.setCodigo(exame.getCodigo());
		this.setDescricao(exame.getDescricao());

		if (!exame.getCategoria().getLabel().isEmpty())
			this.setCategoria(exame.getCategoria().getLabel());
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
