package com.rhlinkcon.model;

public enum ArrecadacaoAliquotaEncargoEnum {
	PATRONAL("Patronal", "a"),
	JUROS_PATRONAL("Juros patronal", "b"), 
	//IPCA_PATRONAL("IPCA patronal", "c"), - removido por solicitacao do cliente
	PATRONAL_13("Patronal 13º", "d"),
	JUROS_PATRONAL_13("Juros patronal 13º", "e"),
	//IPCA_PATRONAL_13("IPCA patronal 13º", "f"),  - removido por solicitacao do cliente
	SERVIDOR("Servidor", "g"),
	JUROS_SERVIDOR("Juros servidor", "h"),
	//IPCA_SERVIDOR("IPCA servidor", "i"),  - removido por solicitacao do cliente
	SERVIDOR_13("Servidor 13º", "j"),
	JUROS_SERVIDOR_13("Juros servidor 13º", "k");
	//IPCA_SERVIDOR_13("IPCA servidor 13º", "l");  - removido por solicitacao do cliente

	private String label;
	private String other;

	private ArrecadacaoAliquotaEncargoEnum(String label, String other) {
		this.label = label;
		this.other = other;
	}

	public String getLabel() {
		return label;
	}
	
	public String getOther() {
		return other;
	}
	
	public static ArrecadacaoAliquotaEncargoEnum getEnumByString(String str) {
		for (ArrecadacaoAliquotaEncargoEnum e : ArrecadacaoAliquotaEncargoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
