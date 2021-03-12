package com.rhlinkcon.payload.batimentoFolhaPagamento;

import java.util.Objects;

import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.util.Utils;

public class BatimentoFolhaPagamentoResponse {

	private Long id;

	private String codigo;
	
	private String nome;
	
	private Long quantidadeFuncionarios;
	
	private Double vantagens;
	
	private Double descontos;
	
	private Double totalLiquidos;

	public BatimentoFolhaPagamentoResponse(EmpresaFilial empresa, ProjecaoSomatorioValores valores) {
		setId(empresa.getId());
		if(Objects.nonNull(empresa.getCodigoEmpregador())) {
			setCodigo(empresa.getCodigoEmpregador().toString());
		} else {
			setCodigo("S/N");
		}
		setNome(empresa.getNomeFilial());
		setQuantidadeFuncionarios(valores.getQuantidade());
		setVantagens(Utils.roundValue(valores.getVantagens()));
		setDescontos(Utils.roundValue(valores.getDescontos()));
		setTotalLiquidos(Utils.roundValue(valores.getLiquidos()));
	}

	public BatimentoFolhaPagamentoResponse(ProjecaoSomatorioValoresFiliais item, EmpresaFilialResponse empresa) {
		setId(empresa.getId());
		if(Objects.nonNull(empresa.getCodigoEmpregador())) {
			setCodigo(empresa.getCodigoEmpregador().toString());
		} else {
			setCodigo("S/N");
		}
		setNome(empresa.getNomeFilial());
		setQuantidadeFuncionarios(item.getQuantidade());
		setVantagens(Utils.roundValue(item.getVantagens()));
		setDescontos(Utils.roundValue(item.getDescontos()));
		setTotalLiquidos(Utils.roundValue(item.getLiquidos()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getQuantidadeFuncionarios() {
		return quantidadeFuncionarios;
	}

	public void setQuantidadeFuncionarios(Long quantidadeFuncionarios) {
		this.quantidadeFuncionarios = quantidadeFuncionarios;
	}

	public Double getVantagens() {
		return vantagens;
	}

	public void setVantagens(Double vantagens) {
		this.vantagens = vantagens;
	}

	public Double getDescontos() {
		return descontos;
	}

	public void setDescontos(Double descontos) {
		this.descontos = descontos;
	}

	public Double getTotalLiquidos() {
		return totalLiquidos;
	}

	public void setTotalLiquidos(Double totalLiquidos) {
		this.totalLiquidos = totalLiquidos;
	}
	
}
