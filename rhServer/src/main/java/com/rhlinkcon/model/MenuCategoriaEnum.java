package com.rhlinkcon.model;

public enum MenuCategoriaEnum {
	GESTAO("Gestão"), MODULO_RH("Módulo RH"), ARRECADACAO("Arrecadação"), FOLHA_PAGAMENTO("Folha de Pgt"), MODULO_AVALIACAO("Módulo Avaliação"),
	RECRUTAMENTO_SELECAO("Recrutamento e Seleção"), CONSULTAS_GERENCIAIS("Consultas Gerenciais"),
	RELATORIO("Relatório"), MODULO_PREVIDENCIARIO("Módulo Previdenciário"), AUDITORIA("Auditoria"), JUNTA_MEDICA("Junta Médica");

	private String categoria;

	private MenuCategoriaEnum(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public static MenuCategoriaEnum getEnumByString(String str) {
		for (MenuCategoriaEnum e : MenuCategoriaEnum.values()) {
			if (str.equals(e.categoria))
				return e;
		}
		return null;
	}
}
