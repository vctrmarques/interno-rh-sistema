package com.rhlinkcon.model;

public enum DadoBancarioTipoContaEnum {

	CHEQUE_SALARIO("Cheque Salário"), CONTA_CORRENTE("Conta Corrente"), CONTA_POUPANCA("Conta Poupança"),
	CONTA_SALARIO("Conta Salário");

	private String label;

	private DadoBancarioTipoContaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DadoBancarioTipoContaEnum getEnumByString(String str) {
		for (DadoBancarioTipoContaEnum e : DadoBancarioTipoContaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}