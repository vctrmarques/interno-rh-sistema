package com.rhlinkcon.payload.funcao;

import java.time.Instant;

import com.rhlinkcon.model.FuncaoHistoricoLei;

public class FuncaoHistoricoLeiResponse {

	private Long id;

	private Integer numeroLei;

	private Instant dataLei;

	private String motivoLei;

	public FuncaoHistoricoLeiResponse(FuncaoHistoricoLei funcaoHistoricoLei) {
		this.id = funcaoHistoricoLei.getId();
		this.numeroLei = funcaoHistoricoLei.getNumeroLei();
		this.dataLei = funcaoHistoricoLei.getDataLei();
		this.motivoLei = funcaoHistoricoLei.getMotivoLei().getLabel();
	}

	public FuncaoHistoricoLeiResponse() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroLei() {
		return numeroLei;
	}

	public void setNumeroLei(Integer numeroLei) {
		this.numeroLei = numeroLei;
	}

	public Instant getDataLei() {
		return dataLei;
	}

	public void setDataLei(Instant dataLei) {
		this.dataLei = dataLei;
	}

	public String getMotivoLei() {
		return motivoLei;
	}

	public void setMotivoLei(String motivoLei) {
		this.motivoLei = motivoLei;
	}

}
