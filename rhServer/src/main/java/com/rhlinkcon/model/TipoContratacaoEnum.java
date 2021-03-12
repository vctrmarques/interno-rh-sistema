package com.rhlinkcon.model;

public enum TipoContratacaoEnum {
	ESTAGIRARIO("Estagiário"), FUNCIONARIO("Funcionário"), TEMPORARIO("Temporário"), TERCEIRIZADO("Terceirizado");

	private String label;

	private TipoContratacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoContratacaoEnum getEnumByString(String str) {
		for (TipoContratacaoEnum e : TipoContratacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
