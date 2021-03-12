package com.rhlinkcon.model;

public enum TempoContribuicaoEnum {

	QUINZE_ANOS("15 Anos"), VINTE_ANOS("20 Anos"), VINTE_E_CINCO_ANOS("25 Anos");

	private String label;

	private TempoContribuicaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TempoContribuicaoEnum getEnumByString(String str) {
		for (TempoContribuicaoEnum e : TempoContribuicaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
