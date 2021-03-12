package com.rhlinkcon.model;

public enum AbaAssinaturaCertidao {
	CERTIDAO("Certidão"), DETALHAMENTO("Detalhamento"), RELACAO("Relação");

	private String label;

	private AbaAssinaturaCertidao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static AbaAssinaturaCertidao getEnumByString(String str) {
		for (AbaAssinaturaCertidao e : AbaAssinaturaCertidao.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
