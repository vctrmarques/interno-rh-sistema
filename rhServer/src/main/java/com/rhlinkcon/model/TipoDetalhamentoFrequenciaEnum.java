package com.rhlinkcon.model;

public enum TipoDetalhamentoFrequenciaEnum {
	FALTA("Falta"), LICENCA_SEM_VENCIMENTO("Licença sem vencimento"), LICENCA_SEM_DEDUCAO("Licença sem dedução"),
	SUSPENSAO("Suspensão"), DISPONIBILIDADE("Disponibilidade"), OUTROS("Outros");

	private String label;

	private TipoDetalhamentoFrequenciaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoDetalhamentoFrequenciaEnum getEnumByString(String str) {
		for (TipoDetalhamentoFrequenciaEnum e : TipoDetalhamentoFrequenciaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
