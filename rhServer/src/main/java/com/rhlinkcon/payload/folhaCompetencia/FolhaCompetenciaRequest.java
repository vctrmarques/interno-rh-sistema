package com.rhlinkcon.payload.folhaCompetencia;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class FolhaCompetenciaRequest {

	private Long id;

	@NotNull
	private Integer mesCompetencia;

	@NotNull
	private Date inicioCompetencia;

	private Integer anoCompetencia;

	private Date fimCompetencia;

	private boolean checarRecad;

	private String checarRecadAmpLegal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMesCompetencia() {
		return mesCompetencia;
	}

	public void setMesCompetencia(Integer mesCompetencia) {
		this.mesCompetencia = mesCompetencia;
	}

	public Date getInicioCompetencia() {
		return inicioCompetencia;
	}

	public void setInicioCompetencia(Date inicioCompetencia) {
		this.inicioCompetencia = inicioCompetencia;
	}

	public Integer getAnoCompetencia() {
		return anoCompetencia;
	}

	public void setAnoCompetencia(Integer anoCompetencia) {
		this.anoCompetencia = anoCompetencia;
	}

	public Date getFimCompetencia() {
		return fimCompetencia;
	}

	public void setFimCompetencia(Date fimCompetencia) {
		this.fimCompetencia = fimCompetencia;
	}

	public boolean isChecarRecad() {
		return checarRecad;
	}

	public void setChecarRecad(boolean checarRecad) {
		this.checarRecad = checarRecad;
	}

	public String getChecarRecadAmpLegal() {
		return checarRecadAmpLegal;
	}

	public void setChecarRecadAmpLegal(String checarRecadAmpLegal) {
		this.checarRecadAmpLegal = checarRecadAmpLegal;
	}
}
