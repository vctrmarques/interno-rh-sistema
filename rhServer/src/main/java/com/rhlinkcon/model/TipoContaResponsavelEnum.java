package com.rhlinkcon.model;

public enum TipoContaResponsavelEnum {

	ANTIGA_CONTA_SALARIO("Antiga Conta Salário"), CHEQUE_SALARIO("Cheque Salário"), CONTA_CORRENTE("Conta Corrente"),
	CONTA_POUPANCA("Conta Poupança"), CONTA_SALARIO("Conta Salário"), EM_BRANCO("EM BRANCO");

	private String label;

	private TipoContaResponsavelEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoContaResponsavelEnum getEnumByString(String str) {
		for (TipoContaResponsavelEnum e : TipoContaResponsavelEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
