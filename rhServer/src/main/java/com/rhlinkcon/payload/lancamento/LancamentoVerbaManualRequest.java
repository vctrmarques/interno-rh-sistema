package com.rhlinkcon.payload.lancamento;

import javax.validation.constraints.NotNull;

public class LancamentoVerbaManualRequest {

	@NotNull
	private Long verbaId;

	@NotNull
	private Double valor;

	public LancamentoVerbaManualRequest() {
	}

	public Long getVerbaId() {
		return verbaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
