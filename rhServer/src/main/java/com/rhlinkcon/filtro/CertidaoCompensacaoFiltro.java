package com.rhlinkcon.filtro;

import java.util.List;

public class CertidaoCompensacaoFiltro {

	private String descricao;

	private List<String> statusList;

	private List<String> fundoList;

	private List<String> classificacaoList;

	private Boolean aposentComAposentSemCompens;

	private String dataInicialStr;

	private String dataFinalStr;

	public CertidaoCompensacaoFiltro() {

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public List<String> getFundoList() {
		return fundoList;
	}

	public void setFundoList(List<String> fundoList) {
		this.fundoList = fundoList;
	}

	public List<String> getClassificacaoList() {
		return classificacaoList;
	}

	public void setClassificacaoList(List<String> classificacaoList) {
		this.classificacaoList = classificacaoList;
	}

	public Boolean getAposentComAposentSemCompens() {
		return aposentComAposentSemCompens;
	}

	public void setAposentComAposentSemCompens(Boolean aposentComAposentSemCompens) {
		this.aposentComAposentSemCompens = aposentComAposentSemCompens;
	}

	public String getDataInicialStr() {
		return dataInicialStr;
	}

	public void setDataInicialStr(String dataInicialStr) {
		this.dataInicialStr = dataInicialStr;
	}

	public String getDataFinalStr() {
		return dataFinalStr;
	}

	public void setDataFinalStr(String dataFinalStr) {
		this.dataFinalStr = dataFinalStr;
	}

}
