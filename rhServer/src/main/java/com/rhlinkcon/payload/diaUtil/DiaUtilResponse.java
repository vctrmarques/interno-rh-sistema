package com.rhlinkcon.payload.diaUtil;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.DiaUtil;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiaUtilResponse extends DadoCadastralResponse {

	private String ano;
	
	private HashMap<String, DtoDiaUtil> dadosDosMeses;

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public HashMap<String, DtoDiaUtil> getDadosDosMeses() {
		return dadosDosMeses;
	}
	

	public void setDadosDosMeses(HashMap<String, DtoDiaUtil> dadosDosMeses) {
		this.dadosDosMeses = dadosDosMeses;
	}

	public DiaUtilResponse() {
	}

}
