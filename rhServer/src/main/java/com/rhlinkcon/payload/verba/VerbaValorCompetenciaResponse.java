package com.rhlinkcon.payload.verba;

public class VerbaValorCompetenciaResponse {
	
	private String mesAno;
	
	private Double valor;
	
	public VerbaValorCompetenciaResponse(String mesAno, Double valor) {
		this.setMesAno(mesAno);
		this.setValor(valor);
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
