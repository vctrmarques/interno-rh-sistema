package com.rhlinkcon.payload.valeTransporte;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

public class ValeTransporteRequest {

	private Long id;
	
	@NotNull
	private String codigo;
	
	@NotNull
	private Double valor;

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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}