package com.rhlinkcon.payload.areaFormacao;

import com.rhlinkcon.model.AreaFormacao;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class AreaFormacaoResponse extends DadoCadastralResponse {
	private Long id;

	private String areaFormacao;
	
	public AreaFormacaoResponse(AreaFormacao areaFormacao) {
		this.setId(areaFormacao.getId());
		this.setAreaFormacao(areaFormacao.getAreaFormacao());
	}

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
