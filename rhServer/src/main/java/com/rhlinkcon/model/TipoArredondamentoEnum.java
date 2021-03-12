package com.rhlinkcon.model;

public enum TipoArredondamentoEnum {

	NAO_ARREDONDA("Não Arredonda"), ARREDONDAMENTO_A_MAIOR("Arredondamento à Maior"),
	ARREDONDAMENTO_A_MENOR("Arredondamento à Menor"), ARREDONDAMENTO_AO_MEIO("Arredondamento ao Meio");

	private String label;

	private TipoArredondamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoArredondamentoEnum getEnumByString(String str) {
		for (TipoArredondamentoEnum e : TipoArredondamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
