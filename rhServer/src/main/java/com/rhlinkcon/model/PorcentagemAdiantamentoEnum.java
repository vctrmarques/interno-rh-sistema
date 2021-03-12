package com.rhlinkcon.model;

public enum PorcentagemAdiantamentoEnum {

	PARCIAL("Parcial"), TOTAL("Total");

	private String label;

	private PorcentagemAdiantamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static PorcentagemAdiantamentoEnum getEnumByString(String str) {
		for (PorcentagemAdiantamentoEnum e : PorcentagemAdiantamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
