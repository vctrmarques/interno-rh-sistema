package com.rhlinkcon.payload.nacionalidade;

import java.time.Instant;

import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class NacionalidadeResponse extends DadoCadastralResponse {

	private Long id;

	private String sigla;

	private String nacionalidadeFeminina;

	private String nacionalidadeMasculina;
	
	private Long codigoSiprev;

	public NacionalidadeResponse(Nacionalidade nacionalidade) {
		setNacionalidade(nacionalidade);
	}

	public NacionalidadeResponse(Nacionalidade nacionalidade, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setNacionalidade(nacionalidade);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setNacionalidade(Nacionalidade nacionalidade) {
		this.setId(nacionalidade.getId());
		this.setSigla(nacionalidade.getSigla());
		this.setNacionalidadeFeminina(nacionalidade.getNacionalidadeFeminina());
		this.setNacionalidadeMasculina(nacionalidade.getNacionalidadeMasculina());
		this.setCodigoSiprev(nacionalidade.getCodigoSiprev());
	}

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
