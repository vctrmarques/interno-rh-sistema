package com.rhlinkcon.filtro;

public class LegislacaoFiltroRequest {

	private long enteFederadoId;
	private long normaId;
	private long detalhamentoNormaId;
	private Integer numero;
	private Integer ano;

	public LegislacaoFiltroRequest() {

	}

	public long getEnteFederadoId() {
		return enteFederadoId;
	}

	public void setEnteFederadoId(long enteFederadoId) {
		this.enteFederadoId = enteFederadoId;
	}

	public long getNormaId() {
		return normaId;
	}

	public void setNormaId(long normaId) {
		this.normaId = normaId;
	}

	public long getDetalhamentoNormaId() {
		return detalhamentoNormaId;
	}

	public void setDetalhamentoNormaId(long detalhamentoNormaId) {
		this.detalhamentoNormaId = detalhamentoNormaId;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

}
