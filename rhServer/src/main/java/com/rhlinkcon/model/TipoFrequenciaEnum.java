package com.rhlinkcon.model;

public enum TipoFrequenciaEnum {

	CARTAO_PONTO("Cartão de Ponto"), FOLHA_PONTO("Folha de Ponto"), PONTO_ELETRONICO("Ponto Eletrônico");

	private String label;

	private TipoFrequenciaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoFrequenciaEnum getEnumByString(String str) {
		for (TipoFrequenciaEnum e : TipoFrequenciaEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
