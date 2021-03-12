package com.rhlinkcon.payload.nacionalidade;

import javax.validation.constraints.NotNull;

public class NacionalidadeRequest {

	private Long id;

	@NotNull
	private String sigla;

	@NotNull
	private String nacionalidadeFeminina;

	@NotNull
	private String nacionalidadeMasculina;

	private Long codigoSiprev;

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

	public String getNacionalidadeFeminina() {
		return nacionalidadeFeminina;
	}

	public void setNacionalidadeFeminina(String nacionalidadeFeminina) {
		this.nacionalidadeFeminina = nacionalidadeFeminina;
	}

	public String getNacionalidadeMasculina() {
		return nacionalidadeMasculina;
	}

	public void setNacionalidadeMasculina(String nacionalidadeMasculina) {
		this.nacionalidadeMasculina = nacionalidadeMasculina;
	}

	public Long getCodigoSiprev() {
		return codigoSiprev;
	}

	public void setCodigoSiprev(Long codigoSiprev) {
		this.codigoSiprev = codigoSiprev;
	}

}