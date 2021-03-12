package com.rhlinkcon.filtro;

public class PensionistaFiltro {

	private String searchFuncionario;

	private String searchPensionista;

	private Long filialId;

	private Long lotacaoId;

	private Boolean pensionistaVerbaAssociada;

	public Long getFilialId() {
		return filialId;
	}

	public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public String getSearchFuncionario() {
		return searchFuncionario;
	}

	public void setSearchFuncionario(String searchFuncionario) {
		this.searchFuncionario = searchFuncionario;
	}

	public String getSearchPensionista() {
		return searchPensionista;
	}

	public void setSearchPensionista(String searchPensionista) {
		this.searchPensionista = searchPensionista;
	}

	public Boolean getPensionistaVerbaAssociada() {
		return pensionistaVerbaAssociada;
	}

	public void setPensionistaVerbaAssociada(Boolean pensionistaVerbaAssociada) {
		this.pensionistaVerbaAssociada = pensionistaVerbaAssociada;
	}

}
