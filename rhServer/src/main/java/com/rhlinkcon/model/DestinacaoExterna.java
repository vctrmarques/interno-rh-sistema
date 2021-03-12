package com.rhlinkcon.model;

import java.util.Objects;

public enum DestinacaoExterna {

	CONSIGNACAO("Consignação"), PENSAO_ALIMENTICIA("Pensão Alimentícia"),
	SEM_DESTINACAO_EXTERNA("Sem Destinação Externa");

	private String label;

	private DestinacaoExterna(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DestinacaoExterna getEnumByString(String str) {
		for (DestinacaoExterna e : DestinacaoExterna.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}
}
