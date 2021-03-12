package com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioFinanceiroFolhaPagamentoRequest {
	
	private Long id;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private List<Long> filiais;
	
	@NotNull
	private Long competencia;
	
	@NotNull
	private List<Long> situacoesFuncionais;
	
	@NotNull
	private List<Long> lotacoes;
	
	@NotNull
	private String status;

}
