package com.rhlinkcon.model;

public enum MotivoPensionistaEnum {

	NASCIMENTO("Nascimento"), ADOCAO("Adoção"), FILHO_POSTUMO("Filho Póstumo"), TUTELA("Tutela"),
	INVALIDEZ("Invalidez"), CASAMENTO("Casamento"), UNICAO_ESTAVEL("União Estável"),
	DEPENDENCIA_ECONOMICA("Dependência Econômica"), OUTROS("Outros");

	private String label;

	private MotivoPensionistaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static MotivoPensionistaEnum getEnumByString(String str) {
		for (MotivoPensionistaEnum e : MotivoPensionistaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
