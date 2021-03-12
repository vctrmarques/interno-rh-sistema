package com.rhlinkcon.model;

public enum ModalidadeAposentadoriaEnum {
	GERAL("Geral"), ESPECIFICA("Específica");

	private String modalidadeAposentadoriaEnum;

	private ModalidadeAposentadoriaEnum(String modalidadeAposentadoriaEnum) {
		this.modalidadeAposentadoriaEnum = modalidadeAposentadoriaEnum;
	}

	public String getLabel() {
		return modalidadeAposentadoriaEnum;
	}

	public static ModalidadeAposentadoriaEnum getEnumByString(String str) {
		for (ModalidadeAposentadoriaEnum e : ModalidadeAposentadoriaEnum.values()) {
			if (str.equals(e.modalidadeAposentadoriaEnum))
				return e;
		}
		return null;
	}
}
