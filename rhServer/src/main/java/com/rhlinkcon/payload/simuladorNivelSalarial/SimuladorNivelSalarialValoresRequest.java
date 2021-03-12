package com.rhlinkcon.payload.simuladorNivelSalarial;

import javax.validation.constraints.NotNull;

public class SimuladorNivelSalarialValoresRequest {

	private Long id;
	
	@NotNull
	private Double valorAjustado;
	
	private Double valorRetroativo;
	
	private Long nivelSalarialId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorAjustado() {
		return valorAjustado;
	}

	public void setValorAjustado(Double valorAjustado) {
		this.valorAjustado = valorAjustado;
	}

	public Double getValorRetroativo() {
		return valorRetroativo;
	}

	public void setValorRetroativo(Double valorRetroativo) {
		this.valorRetroativo = valorRetroativo;
	}

	public Long getNivelSalarialId() {
		return nivelSalarialId;
	}

	public void setNivelSalarialId(Long nivelSalarialId) {
		this.nivelSalarialId = nivelSalarialId;
	}
	
	
}
