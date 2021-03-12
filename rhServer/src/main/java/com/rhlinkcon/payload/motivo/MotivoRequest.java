package com.rhlinkcon.payload.motivo;

import javax.validation.constraints.NotNull;

public class MotivoRequest {

	private Long id;

	@NotNull
	private String descricao;

	private String tipo;

	private String evento;
	
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}
	
}