package com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportRelatorioFinanceiroSomatorioGeral {

	private HashMap<String, Double> listProventos;

	private HashMap<String, Double> listDescontos;

	private Double totalProventos;

	private Double totalLiquido;

	private Double totalDescontos;

	private Integer totalFuncionarios;

	public ExportRelatorioFinanceiroSomatorioGeral() {
		this.setTotalProventos(0.0);
		this.setTotalLiquido(0.0);
		this.setTotalDescontos(0.0);
		this.setTotalFuncionarios(0);
		this.setListProventos(new HashMap<String, Double>());
		this.setListDescontos(new HashMap<String, Double>());
	}

}
