package com.rhlinkcon.payload.areaFormacao;

import javax.validation.constraints.NotNull;

public class AreaFormacaoRequest {

	private Long id;
	
	@NotNull
	private String areaFormacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaFormacao() {
		return areaFormacao;
	}

	public void setAreaFormacao(String areaFormacao) {
		this.areaFormacao = areaFormacao;
	}

}