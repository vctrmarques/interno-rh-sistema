package com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioFinanceiroFolhaPagamentoHistoricoRequest {

	private Long id;
	
	@NotNull
	private Long relatorioFinanceiroFolhaPagamento;
	
	@NotNull
	private String status;
}
