package com.rhlinkcon.payload.certidaoExSegurado;

import java.time.Instant;
import java.util.Objects;

import com.rhlinkcon.model.TempoEspecialCertidaoExSegurado;
import com.rhlinkcon.util.Projecao;

public class TempoEspecialCertidaoExSeguradoResponse {

	private Long id;

	private CertidaoExSeguradoResponse certidaoExSegurado;

	private Instant periodoInicial;

	private Instant periodoFinal;

	private Integer tempo;

	private String tipoTempo;

	private String grauDeficiencia;
	
	public TempoEspecialCertidaoExSeguradoResponse(TempoEspecialCertidaoExSegurado obj) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExSegurado(), Projecao.BASICA));
		setPeriodoInicial(obj.getPeriodoInicial());
		setPeriodoFinal(obj.getPeriodoFinal());
		setTempo(obj.getTempo());
		setTipoTempo(obj.getTipoTempo().getLabel());
		setGrauDeficiencia(Objects.isNull(obj.getGrauDeficiencia())?"":obj.getGrauDeficiencia().getLabel());
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
