/**
 * 
 */
package com.rhlinkcon.model;

/**
 * @author railson
 *
 */
public enum CrmCreaEnum {
	CRM("CRM"), CREA("CREA");

	private String tipo;

	private CrmCreaEnum(String tipo) {
		this.tipo = tipo;
	}

	public String getLabel() {
		return tipo;
	}

	public static CrmCreaEnum getEnumByString(String tipo) {
		for (CrmCreaEnum e : CrmCreaEnum.values()) {
			if (tipo.equals(e.getLabel()))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}
}
