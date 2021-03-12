package com.rhlinkcon.model;

public enum TipoPensaoEnum {

	ACAO_ALIMENTOS("Ação de Alimentos"), AUXILIO_RECLUSAO("Auxílio-reclusão");

	private String label;

	private TipoPensaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoPensaoEnum getEnumByString(String str) {
		for (TipoPensaoEnum e : TipoPensaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
