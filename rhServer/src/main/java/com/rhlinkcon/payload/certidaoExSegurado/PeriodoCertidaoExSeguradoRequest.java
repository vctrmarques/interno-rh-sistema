package com.rhlinkcon.payload.certidaoExSegurado;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PeriodoCertidaoExSeguradoRequest {
	private Long id;

	private Long certidaoExSeguradoId;

	@NotNull
	private Instant periodoInicio;

	@NotNull
	private Instant periodoFinal;

	@NotNull
	@NotBlank
	private String aproveitamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCertidaoExSeguradoId() {
		return certidaoExSeguradoId;
	}

	public void setCertidaoExSeguradoId(Long certidaoExSeguradoId) {
		this.certidaoExSeguradoId = certidaoExSeguradoId;
	}

	public Instant getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(Instant periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public String getAproveitamento() {
		return aproveitamento;
	}

	public void setAproveitamento(String aproveitamento) {
		this.aproveitamento = aproveitamento;
	}

}
