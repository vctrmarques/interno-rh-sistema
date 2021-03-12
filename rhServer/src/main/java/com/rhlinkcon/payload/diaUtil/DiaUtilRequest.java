package com.rhlinkcon.payload.diaUtil;


import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class DiaUtilRequest {

	private String ano;
	
	private String mes;
	
	private List<LocalDate> arrayListDiasUteis;

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

	public List<LocalDate> getArrayListDiasUteis() {
		return arrayListDiasUteis;
	}

	public void setArrayListDiasUteis(List<LocalDate> arrayListDiasUteis) {
		this.arrayListDiasUteis = arrayListDiasUteis;
	}
	
		

}
