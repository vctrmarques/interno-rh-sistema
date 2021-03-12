package com.rhlinkcon.model;

public enum AvaliacaoDesempenhoItemFormaAvaliacaoEnum {
	SIM_NAO("Sim/Não"), CERTO_ERRADO("Certo/Errado"), BOM_REGULAR_RUIM("Bom/Regular/Ruim"),
	MUITO_BOM_BOM_REGULAR_RUIM_PESSIMO("Muito Bom/Bom/Regular/Ruim/Péssimo"),
	NUNCA_AS_VEZES_SEMPRE("Nunca/As Vezes/Sempre"), RESPOSTA_LIVRE("Resposta Livre");

	private String label;

	private AvaliacaoDesempenhoItemFormaAvaliacaoEnum(String label) {
		this.label = label;
	}

	public static AvaliacaoDesempenhoItemFormaAvaliacaoEnum getEnumByString(String str) {
		for (AvaliacaoDesempenhoItemFormaAvaliacaoEnum e : AvaliacaoDesempenhoItemFormaAvaliacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

	public String getLabel() {
		return label;
	}
}