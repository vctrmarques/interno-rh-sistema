package com.rhlinkcon.model;

public enum TipoLotacaoEnum {

	SINTETICO("Sintético"), ANALITITO("Analítico");

	private String label;

	private TipoLotacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoLotacaoEnum getEnumByString(String str) {
		for (TipoLotacaoEnum e : TipoLotacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}

}
