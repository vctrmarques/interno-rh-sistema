package com.rhlinkcon.payload.legislacao;

import com.rhlinkcon.model.legislacao.DetalhamentoNorma;
import com.rhlinkcon.payload.DadoBasicoDto;

public class DetalhamentoNormaResponse extends DadoBasicoDto {

	private Integer codigo;

	public DetalhamentoNormaResponse(DetalhamentoNorma detalhamentoNorma) {
		super(detalhamentoNorma);
		this.codigo = detalhamentoNorma.getCodigo();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

}
