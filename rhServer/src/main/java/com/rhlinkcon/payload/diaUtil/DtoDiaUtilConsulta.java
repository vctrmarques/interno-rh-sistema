package com.rhlinkcon.payload.diaUtil;

public class DtoDiaUtilConsulta {

	/**
	 * 
	 */
	private long totalDiaDaSemana;
	private String ano;
	private String mes;
	private String diaDaSemana;

	public long getTotalDiaDaSemana() {
		return totalDiaDaSemana;
	}

	public void setTotalDiaDaSemana(long totalDiaDaSemana) {
		this.totalDiaDaSemana = totalDiaDaSemana;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getDiaDaSemana() {
		return diaDaSemana;
	}

	public void setDiaDaSemana(String diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public DtoDiaUtilConsulta(long totalDiaDaSemana, String ano, String mes, String diaDaSemana) {
		this.totalDiaDaSemana = totalDiaDaSemana;
		this.ano = ano;
		this.mes = mes;
		this.diaDaSemana = diaDaSemana;
	}
	
}
