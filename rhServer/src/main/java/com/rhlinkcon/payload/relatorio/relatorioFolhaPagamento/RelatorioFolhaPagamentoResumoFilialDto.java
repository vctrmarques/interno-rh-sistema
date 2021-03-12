package com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioFolhaPagamentoResumoFilialDto {
	private String nomeFilial;
	
	private Long countFuncionarios;
	
	private Double liquidos;
	
	private Double bruto;
	
	public RelatorioFolhaPagamentoResumoFilialDto(String nomeFilial, Long countFuncionarios, Double liquidos, Double bruto) {
		this.nomeFilial = nomeFilial;
		this.countFuncionarios = countFuncionarios;
		this.liquidos = liquidos;
		this.bruto = bruto;
	}
}
