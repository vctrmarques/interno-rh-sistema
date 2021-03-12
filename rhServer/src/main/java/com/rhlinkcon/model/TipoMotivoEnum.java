package com.rhlinkcon.model;

public enum TipoMotivoEnum {

	EFETIVO("Efetivo"), COMISSAO("Comissão");

	private String label;

	private TipoMotivoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoMotivoEnum getEnumByString(String str) {
		for (TipoMotivoEnum e : TipoMotivoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
