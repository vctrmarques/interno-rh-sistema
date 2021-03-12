package com.rhlinkcon.payload.certidaoCompensacaoPeriodo;

import java.time.LocalDate;

public class CertidaoCompensacaoPeriodoRequest {
	
	private Long id;
	
	private Long certidaoCompensacaoId;
	
	private LocalDate periodoInicio;
	
	private LocalDate periodoFim;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCertidaoCompensacaoId() {
		return certidaoCompensacaoId;
	}

	public void setCertidaoCompensacaoId(Long certidaoCompensacaoId) {
		this.certidaoCompensacaoId = certidaoCompensacaoId;
	}

	public LocalDate getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(LocalDate periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public LocalDate getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(LocalDate periodoFim) {
		this.periodoFim = periodoFim;
	}
	
}
