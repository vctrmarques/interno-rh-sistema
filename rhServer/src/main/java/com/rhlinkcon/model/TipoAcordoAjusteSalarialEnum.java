package com.rhlinkcon.model;

public enum TipoAcordoAjusteSalarialEnum {

	ACORDO_COLETIVO("Acordo coletivo de trabalho"), LEGISLACAO("Legislação"),
	VERBAS_DESLIGAMENTO("Verbas devidas após desligamento"), OUTRAS_VERBAS("Outras verbas devidas");

	private String label;

	private TipoAcordoAjusteSalarialEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoAcordoAjusteSalarialEnum getEnumByString(String str) {
		for (TipoAcordoAjusteSalarialEnum e : TipoAcordoAjusteSalarialEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
