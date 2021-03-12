package com.rhlinkcon.model;

public enum TipoIncidenciaPrincipalPensaoEnum {

	SALARIO_LIQUIDO("Salário Líquido"), SALARIO_MINIMO("Salário Mínimo"), SALARIO_BRUTO("Salário Bruto");

	private String label;

	private TipoIncidenciaPrincipalPensaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoIncidenciaPrincipalPensaoEnum getEnumByString(String str) {
		for (TipoIncidenciaPrincipalPensaoEnum e : TipoIncidenciaPrincipalPensaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}