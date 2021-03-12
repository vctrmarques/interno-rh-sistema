package com.rhlinkcon.payload.atividade;

import java.time.Instant;

import com.rhlinkcon.model.Atividade;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class AtividadeResponse extends DadoCadastralResponse {
	
	private Long id;
	
	private String codigo;
	
	private String descricao;

	private String observacao;

	public AtividadeResponse(Atividade atividade) {
		this.setId(atividade.getId());
		this.setCodigo(atividade.getCodigo());
		this.setDescricao(atividade.getDescricao());
		this.setObservacao(atividade.getObservacao());
	}
	
	public AtividadeResponse(Atividade atividade, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		this.setId(atividade.getId());
		this.setCodigo(atividade.getCodigo());
		this.setDescricao(atividade.getDescricao());
		this.setObservacao(atividade.getObservacao());
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
