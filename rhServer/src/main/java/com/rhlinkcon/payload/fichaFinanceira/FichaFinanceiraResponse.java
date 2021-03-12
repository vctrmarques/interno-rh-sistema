package com.rhlinkcon.payload.fichaFinanceira;

import java.util.List;

import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.lancamento.LancamentoResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class FichaFinanceiraResponse {
	
	private FuncionarioResponse funcionario;
	
	private Double valorBruto;

	private Double valorLiquido;

	private Double valorDesconto;
	
	private List<FolhaCompetenciaResponse> listFolhaCompetenciaResponse;
	
	private List<LancamentoResponse> lancamentosVantagens;

	private List<LancamentoResponse> lancamentosDescontos;

	private List<LancamentoResponse> lancamentosOutros;
	
	private List<VerbaResponse> verbasProventos;
	
	private List<VerbaResponse> verbasDescontos;
	
	private List<VerbaResponse> verbasOutros;
	
	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public List<VerbaResponse> getVerbasProventos() {
		return verbasProventos;
	}

	public void setVerbasProventos(List<VerbaResponse> verbasProventos) {
		this.verbasProventos = verbasProventos;
	}

	public List<VerbaResponse> getVerbasDescontos() {
		return verbasDescontos;
	}

	public void setVerbasDescontos(List<VerbaResponse> verbasDescontos) {
		this.verbasDescontos = verbasDescontos;
	}

	public List<LancamentoResponse> getLancamentosVantagens() {
		return lancamentosVantagens;
	}

	public void setLancamentosVantagens(List<LancamentoResponse> lancamentosVantagens) {
		this.lancamentosVantagens = lancamentosVantagens;
	}

	public List<LancamentoResponse> getLancamentosDescontos() {
		return lancamentosDescontos;
	}

	public void setLancamentosDescontos(List<LancamentoResponse> lancamentosDescontos) {
		this.lancamentosDescontos = lancamentosDescontos;
	}

	public List<LancamentoResponse> getLancamentosOutros() {
		return lancamentosOutros;
	}

	public void setLancamentosOutros(List<LancamentoResponse> lancamentosOutros) {
		this.lancamentosOutros = lancamentosOutros;
	}

	public Double getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Double valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public List<VerbaResponse> getVerbasOutros() {
		return verbasOutros;
	}

	public void setVerbasOutros(List<VerbaResponse> verbasOutros) {
		this.verbasOutros = verbasOutros;
	}

	public List<FolhaCompetenciaResponse> getListFolhaCompetenciaResponse() {
		return listFolhaCompetenciaResponse;
	}

	public void setListFolhaCompetenciaResponse(List<FolhaCompetenciaResponse> listFolhaCompetenciaResponse) {
		this.listFolhaCompetenciaResponse = listFolhaCompetenciaResponse;
	}

}
