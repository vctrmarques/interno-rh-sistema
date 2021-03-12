package com.rhlinkcon.model;

public enum TipoContaBancariaEnum {

	CHEQUE_SALARIO("Cheque Salário"), CONTA_CORRENTE("Conta Corrente"), CONTA_POUPANCA("Conta Poupança"),
	CONTA_SALARIO("Conta Salário");

	private String label;

	private TipoContaBancariaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoContaBancariaEnum getEnumByString(String str) {
		for (TipoContaBancariaEnum e : TipoContaBancariaEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
