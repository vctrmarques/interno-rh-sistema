package com.rhlinkcon.model;

public enum CategoriaCnhEnum {

	A("A"), B("B"), C("C"), D("D"), E("E"), AB("AB"), AC("AC"), AD("AD"), AE("AE");

	private String label;

	private CategoriaCnhEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static CategoriaCnhEnum getEnumByString(String str) {
		for (CategoriaCnhEnum e : CategoriaCnhEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
