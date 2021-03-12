package com.rhlinkcon.payload.treinamentoSugeridoComplemento;

public class TreinamentoSugeridoComplementoRequest {
	
	private Long id;

	private Long treinamentoSugeridoId;

	private String local;

	private String endereco;
	
	private String complemento;
	
	private String bairro;
	
	private Long municipioId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTreinamentoSugeridoId() {
		return treinamentoSugeridoId;
	}

	public void setTreinamentoSugeridoId(Long treinamentoSugeridoId) {
		this.treinamentoSugeridoId = treinamentoSugeridoId;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}
}
