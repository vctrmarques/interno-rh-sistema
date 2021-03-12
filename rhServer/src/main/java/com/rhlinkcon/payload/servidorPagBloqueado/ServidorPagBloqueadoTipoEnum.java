package com.rhlinkcon.payload.servidorPagBloqueado;

public enum ServidorPagBloqueadoTipoEnum {
	SITUACAO_FUNCIONAL("Situação Funcional"), RECADASTRAMENTO_PENDENTE("Recadastramento Pendente"),
	SITUACAO_FUNCIONAL_DATA_INICIO("Início da Situação Funcional"),
	SITUACAO_FUNCIONAL_DATA_FIM("Fim da Situação Funcional"), PENSIONISTA_INATIVO("Pensionista Inativo"),
	DATA_PRIMEIRO_PAGAMENTO_PENS("Data inicial de pagamento"), DATA_FINAL_PAGAMENTO_PENS("Data final de pagamento");

	private String label;

	private ServidorPagBloqueadoTipoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ServidorPagBloqueadoTipoEnum getEnumByString(String str) {
		for (ServidorPagBloqueadoTipoEnum e : ServidorPagBloqueadoTipoEnum.values()) {
			if (str.equals(e.label))
				return e;
		}
		return null;
	}

}
