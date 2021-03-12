package com.rhlinkcon.payload.feriasProgramacao;

import java.util.List;

public class FeriasProgramacaoCancelarRequest {
	
	private List<Long> ids;
	
	private String motivo;

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	
	
	
}
