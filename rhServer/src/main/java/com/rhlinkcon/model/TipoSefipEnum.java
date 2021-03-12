package com.rhlinkcon.model;

public enum TipoSefipEnum {

	CATEGORIA("Categoria"), OCORRENCIA("Ocorrência");

	private String label;

	private TipoSefipEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoSefipEnum getEnumByString(String str) {
		for (TipoSefipEnum e : TipoSefipEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
