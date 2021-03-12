package com.rhlinkcon.model;

public enum DirfTipoDeclaracaoEnum {
	ORIGINAL("Original"), RETIFICADORA("Retificadora");

	private String label;

	private DirfTipoDeclaracaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DirfTipoDeclaracaoEnum getEnumByString(String str) {
		for (DirfTipoDeclaracaoEnum e : DirfTipoDeclaracaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
