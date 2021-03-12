package com.rhlinkcon.payload.simuladorAposentadoria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OutroPeriodoPublico extends Data {

	@NotNull
	@NotBlank
	private String atividade;

	public OutroPeriodoPublico() {
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

}
