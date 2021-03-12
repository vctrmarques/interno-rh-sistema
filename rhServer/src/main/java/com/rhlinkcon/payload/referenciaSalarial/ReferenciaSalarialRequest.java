package com.rhlinkcon.payload.referenciaSalarial;

import javax.validation.constraints.NotNull;

public class ReferenciaSalarialRequest {

	private Long id;

	@NotNull
	private String codigo;

	@NotNull
	private String descricao;

	@NotNull
	private Double valor;

	private boolean nivelReferencia;
	
	private String mesAnoCompetencia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public boolean isNivelReferencia() {
		return nivelReferencia;
	}

	public void setNivelReferencia(boolean nivelReferencia) {
		this.nivelReferencia = nivelReferencia;
	}

	public String getMesAnoCompetencia() {
		return mesAnoCompetencia;
	}

	public void setMesAnoCompetencia(String mesAnoCompetencia) {
		this.mesAnoCompetencia = mesAnoCompetencia;
	}

}
