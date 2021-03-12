package com.rhlinkcon.payload.simuladorAposentadoria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Deducao extends Data {

	@NotNull
	@NotBlank
	private String contribuicao;

	public String getContribuicao() {
		return contribuicao;
	}

	public void setContribuicao(String contribuicao) {
		this.contribuicao = contribuicao;
	}

}
