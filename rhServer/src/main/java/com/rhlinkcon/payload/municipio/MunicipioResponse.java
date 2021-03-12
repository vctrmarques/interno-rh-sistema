package com.rhlinkcon.payload.municipio;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MunicipioResponse extends DadoCadastralResponse {

	private Long id;

	private String regiaoFiscal;

	private String descricao;

	private UnidadeFederativaResponse uf;

	private String cep;

	private String naturalidade;

	private Long codigoIbge;

	public MunicipioResponse(Municipio municipio) {
		setMunicipio(municipio);
	}

	public MunicipioResponse(Municipio municipio, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setMunicipio(municipio);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setMunicipio(Municipio municipio) {
		this.setId(municipio.getId());
		this.setRegiaoFiscal(municipio.getRegiaoFiscal());
		this.setDescricao(municipio.getDescricao());
		this.setUf(new UnidadeFederativaResponse(municipio.getUf()));
		this.setCep(municipio.getCep());
		this.setNaturalidade(municipio.getNaturalidade());
		this.setCodigoIbge(municipio.getCodigoIbge());
	}

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

	public UnidadeFederativaResponse getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativaResponse uf) {
		this.uf = uf;
	}

	public Long getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(Long codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

}
