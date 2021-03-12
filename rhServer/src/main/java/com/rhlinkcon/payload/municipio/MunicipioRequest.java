package com.rhlinkcon.payload.municipio;

import javax.validation.constraints.NotNull;

public class MunicipioRequest {

	private Long id;

	@NotNull
	private String regiaoFiscal;

	@NotNull
	private String descricao;

	@NotNull
	private Long ufId;

	@NotNull
	private String cep;

	@NotNull
	private String naturalidade;

	private Long codigoIbge;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegiaoFiscal() {
		return regiaoFiscal;
	}

	public void setRegiaoFiscal(String regiaoFiscal) {
		this.regiaoFiscal = regiaoFiscal;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getUfId() {
		return ufId;
	}

	public void setUfId(Long ufId) {
		this.ufId = ufId;
	}

	public Long getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(Long codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

}