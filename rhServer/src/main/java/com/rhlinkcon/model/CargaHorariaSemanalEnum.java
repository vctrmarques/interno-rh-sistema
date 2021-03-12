package com.rhlinkcon.model;

public enum CargaHorariaSemanalEnum {
	QUARENTAEQUATRO("44 Horas Semanais"), QUARENTA("40 Horas Semanais");

	private String modalidadeAposentadoriaEnum;

	private CargaHorariaSemanalEnum(String modalidadeAposentadoriaEnum) {
		this.modalidadeAposentadoriaEnum = modalidadeAposentadoriaEnum;
	}

	public String getLabel() {
		return modalidadeAposentadoriaEnum;
	}

	public static CargaHorariaSemanalEnum getEnumByString(String str) {
		for (CargaHorariaSemanalEnum e : CargaHorariaSemanalEnum.values()) {
			if (str.equals(e.modalidadeAposentadoriaEnum))
				return e;
		}
		return null;
	}
}
