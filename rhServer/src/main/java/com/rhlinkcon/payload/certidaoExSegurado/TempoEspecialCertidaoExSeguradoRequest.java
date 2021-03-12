package com.rhlinkcon.payload.certidaoExSegurado;

import java.time.Instant;

import javax.validation.constraints.NotNull;

public class TempoEspecialCertidaoExSeguradoRequest {
	private Long id;

	private Long certidaoExSeguradoId;

	private Instant periodoInicial;

	private Instant periodoFinal;

	private Integer tempo;

	private String tipoTempo;

	private String grauDeficiencia;

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

	public Instant getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Instant periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public String getTipoTempo() {
		return tipoTempo;
	}

	public void setTipoTempo(String tipoTempo) {
		this.tipoTempo = tipoTempo;
	}

	public String getGrauDeficiencia() {
		return grauDeficiencia;
	}

	public void setGrauDeficiencia(String grauDeficiencia) {
		this.grauDeficiencia = grauDeficiencia;
	}

}
