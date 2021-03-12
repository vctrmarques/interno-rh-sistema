package com.rhlinkcon.payload.unidadeFederativa;

import javax.validation.constraints.NotNull;

public class UnidadeFederativaRequest {

	private Long id;

	@NotNull
	private String sigla;
	
	@NotNull
	private String estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}