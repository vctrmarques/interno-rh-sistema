package com.rhlinkcon.util;

public enum Projecao {
	BASICA("BASICA"), 
	MEDIA("MEDIA"), 
	COMPLETA("COMPLETA"), 
	APOSENTADORIA("APOSENTADORIA"), 
	CERTIDAO_COMPENSACAO("CERTIDAO COMPENSACAO"), 
	CERTIDAO_EXSEGURADO("CERTIDAO EX-SEGURADO"),
	RELATORIO_EXSEGURADO("RELATÓRIO EX-SEGURADO"), 
	CARGO("CARGO");

	private String projecao;

	private Projecao(String projecao) {
		this.projecao = projecao;
	}

	public static Projecao getEnumByString(String str) {
		for (Projecao e : Projecao.values()) {
			if (str.equals(e.projecao))
				return e;
		}
		return null;
	}
}
