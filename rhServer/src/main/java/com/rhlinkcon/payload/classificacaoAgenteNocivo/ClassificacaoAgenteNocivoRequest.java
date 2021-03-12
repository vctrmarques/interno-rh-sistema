package com.rhlinkcon.payload.classificacaoAgenteNocivo;

import javax.validation.constraints.NotNull;

public class ClassificacaoAgenteNocivoRequest {

	private Long id;
	
	@NotNull
	private String codigo;
	
	@NotNull
	private String descricao;
	
	private Integer tempoExposicao;

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

	public Integer getTempoExposicao() {
		return tempoExposicao;
	}

	public void setTempoExposicao(Integer tempoExposicao) {
		this.tempoExposicao = tempoExposicao;
	}

}