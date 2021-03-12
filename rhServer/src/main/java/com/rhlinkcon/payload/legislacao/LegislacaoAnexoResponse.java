package com.rhlinkcon.payload.legislacao;

import com.rhlinkcon.model.legislacao.LegislacaoAnexo;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.anexo.AnexoResponse;

public class LegislacaoAnexoResponse {

	private Long id;

	private DadoBasicoDto textoDocumento;

	private AnexoResponse anexo;

	public LegislacaoAnexoResponse(LegislacaoAnexo legislacaoAnexo) {
		this.id = legislacaoAnexo.getId();
		this.textoDocumento = new DadoBasicoDto(legislacaoAnexo.getTextoDocumento());
		this.anexo = new AnexoResponse(legislacaoAnexo.getAnexo());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DadoBasicoDto getTextoDocumento() {
		return textoDocumento;
	}

	public void setTextoDocumento(DadoBasicoDto textoDocumento) {
		this.textoDocumento = textoDocumento;
	}

	public AnexoResponse getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexoResponse anexo) {
		this.anexo = anexo;
	}

}
