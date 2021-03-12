package com.rhlinkcon.payload.unidadeFederativa;

import java.time.Instant;

import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class UnidadeFederativaResponse extends DadoCadastralResponse {
	
	private Long id;

	private String sigla;
	
	private String estado;

	public UnidadeFederativaResponse(UnidadeFederativa unidadeFederativa) {
		setUnidadeFederativa(unidadeFederativa);
	}
	
	public UnidadeFederativaResponse(UnidadeFederativa unidadeFederativa, Instant criadoEm, String criadoPor, Instant alteradoEm, 
			String alteradoPor) {
		setUnidadeFederativa(unidadeFederativa);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
		this.setId(unidadeFederativa.getId());
		this.setSigla(unidadeFederativa.getSigla());
		this.setEstado(unidadeFederativa.getEstado());
		this.setLabel(unidadeFederativa.getLabel());
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
