package com.rhlinkcon.payload.certidaoExSegurado;

import java.time.Instant;

import com.rhlinkcon.model.PeriodoCertidaoExSegurado;
import com.rhlinkcon.util.Projecao;

public class PeriodoCertidaoExSeguradoResponse {

	private Long id;

	private CertidaoExSeguradoResponse certidaoExSegurado;

	private Instant periodoInicio;

	private Instant periodoFinal;

	private String aproveitamento;
	
	public PeriodoCertidaoExSeguradoResponse(PeriodoCertidaoExSegurado obj) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExSegurado(), Projecao.BASICA));
		setPeriodoInicio(obj.getPeriodoInicio());
		setPeriodoFinal(obj.getPeriodoFinal());
		setAproveitamento(obj.getAproveitamento());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoExSeguradoResponse getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSeguradoResponse certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
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
