package com.rhlinkcon.payload.motivoAfastamento;

import javax.validation.constraints.NotNull;

public class MotivoAfastamentoRequest {

	private Long id;

	@NotNull
	private String codigo;

	@NotNull
	private String descricao;
	
	private boolean disponivelParaPericia; 

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

	public boolean isDisponivelParaPericia() {
		return disponivelParaPericia;
	}

	public void setDisponivelParaPericia(boolean disponivelParaPericia) {
		this.disponivelParaPericia = disponivelParaPericia;
	}
	
}