package com.rhlinkcon.model;

public enum StatusFolhaEnum {

	BLOQUEADO("Bloqueado"), DESBLOQUEADO("Desbloqueado");

	private String label;

	private StatusFolhaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static StatusFolhaEnum getEnumByString(String str) {
		for (StatusFolhaEnum e : StatusFolhaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
