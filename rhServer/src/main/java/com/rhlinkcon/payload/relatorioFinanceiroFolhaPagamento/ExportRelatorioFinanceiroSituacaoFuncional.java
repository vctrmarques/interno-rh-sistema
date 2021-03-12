package com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.rhlinkcon.model.StatusRelatorioFinanceiroEnum;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportRelatorioFinanceiroSituacaoFuncional {
	
	private String cnpj;
	
	private String filial;
	
	private String situacaoFuncional;
	
	private StatusRelatorioFinanceiroEnum status;
	
	private FolhaPagamentoResponse folhaPagamento;
	
	private Date dataAtual = new Date();
	
	private HashMap<String, Double> listProventos;
	
	private HashMap<String, Double> listDescontos;
	
	private Double totalProventos;
	
	private Double totalLiquido;
	
	private Double totalDescontos;
	
	private Integer totalFuncionarios;
	
	private List<ExportRelatorioFinanceiroFuncionarioPensionista> listFuncionarioPensionistas;
	
	public ExportRelatorioFinanceiroSituacaoFuncional() {
		this.setTotalProventos(0.0);
		this.setTotalLiquido(0.0);
		this.setTotalDescontos(0.0);
		this.setTotalFuncionarios(0);
		this.setListProventos(new HashMap<String, Double>());
		this.setListDescontos(new HashMap<String, Double>());
		this.setListFuncionarioPensionistas(new ArrayList<ExportRelatorioFinanceiroFuncionarioPensionista>());
	}

}
