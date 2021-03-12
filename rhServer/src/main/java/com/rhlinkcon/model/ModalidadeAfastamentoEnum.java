package com.rhlinkcon.model;

public enum ModalidadeAfastamentoEnum {

	RESERVA("Reserva"), EXONERACAO("Exoneração"), ADVERTENCIA("Advertência"), DEDUCAO("Dedução"),
	INFORMATIVO("Informativo"), COMESTABILIDADE("Com Estabilidade"), RESCISAO("Rescisão"),
	TRANSFERENCIA("Transferência"), APOSENTADORIA("Aposentadoria"), REFORMA("Reforma");

	private String label;

	private ModalidadeAfastamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ModalidadeAfastamentoEnum getEnumByString(String str) {
		for (ModalidadeAfastamentoEnum e : ModalidadeAfastamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
