package com.rhlinkcon.model;

public enum OperacaoContaBancariaEnum {
	
	ContaCorrentedePessoaFisica("001"),ContaSimplesdePessoaFisica("002"),
	ContaCorrentedePessoaJuridica("003"),EntidadesPublicas("006"),
	DepositosInstituicoesFinanceiras("007"),PoupançadePessoaFisica("013"),
	PoupançadePessoaJuridica("022"),ContaCaixaFacil("023"),
	PoupançadeCreditoImobiliario("028"),ContaInvestimentoPessoaFisica("032"),
	ContaInvestimentoPessoaJuridica("034"),ContaSalario("037"),
	DepositosLotericos("043"),PoupançaIntegrada("131");

	private String label;

	private OperacaoContaBancariaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static OperacaoContaBancariaEnum getEnumByString(String str) {
		for (OperacaoContaBancariaEnum e : OperacaoContaBancariaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
