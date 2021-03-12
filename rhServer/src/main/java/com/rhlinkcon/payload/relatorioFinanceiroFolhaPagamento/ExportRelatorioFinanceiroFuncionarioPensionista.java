package com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento;

import java.util.HashMap;

import com.rhlinkcon.payload.pensionista.PensionistaResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportRelatorioFinanceiroFuncionarioPensionista {

	public String matricula;

	public String nome;

	public String lotacao;

	public String cargo;

	public String funcao;

	public String situacaoFuncional;

	public String dadoBancario;

	public PensionistaResponse pensionista;

	private HashMap<String, Double> listProventos;

	private HashMap<String, Double> listDescontos;

	private Double totalProventos;

	private Double totalDescontos;

	private Double totalLiquido;

	public ExportRelatorioFinanceiroFuncionarioPensionista() {
		this.setTotalProventos(0.0);
		this.setTotalLiquido(0.0);
		this.setTotalDescontos(0.0);
		this.setListProventos(new HashMap<String, Double>());
		this.setListDescontos(new HashMap<String, Double>());
	}

}
