package com.rhlinkcon.model;

import java.util.Objects;

public enum TipoAnaliseEnum {

	APOSENTADORIA_POR_INVALIDEZ("Aposentadoria por Invalidez"),
	REVISAO_DE_APOSENTADORIA("Revisão de Aposentadoria");

	private String label;

	private TipoAnaliseEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoAnaliseEnum getEnumByString(String str) {
		for (TipoAnaliseEnum e : TipoAnaliseEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
	
	public static String getEnumByLabel(String str) {
		for (TipoAnaliseEnum e : TipoAnaliseEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.name()))
				return e.getLabel();
		}
		return null;
	}
}
