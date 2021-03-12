package com.rhlinkcon.model;

public enum RequisicaoPessoalAnaliseEnum {

	APROVADO("Aprovado"), REPROVADO("Reprovado"), PENDENTE("Pendente");

	private String nome;

	private RequisicaoPessoalAnaliseEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public static RequisicaoPessoalAnaliseEnum getEnumByString(String str) {
		for (RequisicaoPessoalAnaliseEnum e : RequisicaoPessoalAnaliseEnum.values()) {
			if (str.equalsIgnoreCase(e.getNome())) {
				return e;
			}
		}
		return null;
	}
}
