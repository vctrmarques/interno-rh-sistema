package com.rhlinkcon.model;

public enum TipoAverbacaoEnum {

	TEMPO_SERVICO_PUBLICO("Tempo de Serviço Público"), TEMPO_SERVICO_PRIVADO("Tempo de Serviço Privado"),
	LICENCA_PREMIO_DOBRO("Licença - Prêmio em Dobro");

	private String label;

	private TipoAverbacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoAverbacaoEnum getEnumByString(String str) {
		for (TipoAverbacaoEnum e : TipoAverbacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
