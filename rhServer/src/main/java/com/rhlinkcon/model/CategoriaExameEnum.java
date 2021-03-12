package com.rhlinkcon.model;

public enum CategoriaExameEnum {

	ADMISSAO("Admissão"), AFASTAMENTO("Afastamento"), COMPLEMENTAR("Complementar"), DEMISSAO("Demissão"), INSS("INSS"),
	PERIODICO("Periódico"), PROMOCAO("Promoção"), RETORNO("Retorno");

	private String label;

	private CategoriaExameEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static CategoriaExameEnum getEnumByString(String str) {
		for (CategoriaExameEnum e : CategoriaExameEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
