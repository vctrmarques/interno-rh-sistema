package com.rhlinkcon.payload;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DadoCadastralResponse extends DadoBasicoDto {

	private Instant criadoEm;

	private Instant alteradoEm;

	private String criadoPor;

	private String alteradoPor;

	public Instant getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Instant criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public String getAlteradoPor() {
		return alteradoPor;
	}

	public void setAlteradoPor(String alteradoPor) {
		this.alteradoPor = alteradoPor;
	}

	public Instant getAlteradoEm() {
		return alteradoEm;
	}

	public void setAlteradoEm(Instant alteradoEm) {
		this.alteradoEm = alteradoEm;
	}

}
