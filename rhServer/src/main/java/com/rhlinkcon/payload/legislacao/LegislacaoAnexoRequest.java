package com.rhlinkcon.payload.legislacao;

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.anexo.AnexoRequest;

public class LegislacaoAnexoRequest {

	private Long id;

	private DadoBasicoDto textoDocumento;

	private AnexoRequest anexo;

	public LegislacaoAnexoRequest() {

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

	public AnexoRequest getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexoRequest anexo) {
		this.anexo = anexo;
	}

}
