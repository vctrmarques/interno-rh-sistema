package com.rhlinkcon.model;

public enum EstadoCivilEnum {

	SOLTEIRO("Solteiro", "1"), CASADO("Casado", "2"), VIUVO("Viúvo", "3"),
	SEPARADO_JUDICIALMENTE("Separado(a) Judicialmente", "4"), DIVORCIADO("Divorciado", "5"),
	UNIAO_ESTAVEL("União Estável", "6"), OUTROS("Outros", "9");

	private String label;

	// Código SIPREV
	private String other;

	private EstadoCivilEnum(String label, String other) {
		this.label = label;
		this.other = other;
	}

	public String getLabel() {
		return label;
	}

	public String getOther() {
		return other;
	}

	public static EstadoCivilEnum getEnumByString(String str) {
		for (EstadoCivilEnum e : EstadoCivilEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
