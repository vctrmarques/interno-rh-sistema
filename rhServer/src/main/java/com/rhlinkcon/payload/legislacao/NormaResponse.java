package com.rhlinkcon.payload.legislacao;

import com.rhlinkcon.model.legislacao.Norma;
import com.rhlinkcon.payload.DadoBasicoDto;

public class NormaResponse extends DadoBasicoDto {

	private Integer codigo;

	public NormaResponse(Norma morma) {
		super(morma);
		this.codigo = morma.getCodigo();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

}
