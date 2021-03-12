package com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento;

import java.util.ArrayList;

import com.rhlinkcon.model.EmpresaFilial;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioFolhaPagamentoFiltroDto {

	private String nomeFilial;
	private ArrayList<Long> filialList; 
	private ArrayList<String> filialString; 
	private ArrayList<String> situacaoFuncionalList; 
	private Integer ano;
	private Integer competencia;
	private Long tipoProcessamentoId;
	private String tipoProcessamento;

}
