package com.rhlinkcon.model;

public enum TipoAdicionalTempoServicoEnum {

	ANUENIO("Anuênio"), TRIENIO("Triênio"), QUINQUENIO("Quinquênio");

	private String label;

	private TipoAdicionalTempoServicoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoAdicionalTempoServicoEnum getEnumByString(String str) {
		for (TipoAdicionalTempoServicoEnum e : TipoAdicionalTempoServicoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
