package com.rhlinkcon.payload.sindicato;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Sindicato;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SindicatoResponse extends DadoCadastralResponse {

	private Long id;
	
	private String descricao;
	
	private Integer mesBase;
	
	private Integer pisoSalarial;
	
	private String cnpj;
	
	private String codigoEntidade;
	
	private Integer ddd;
	
	private String telefone;
	
	private String patronal;
	
	private String endereco;
	
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	private String municipio;
	
	private UnidadeFederativaResponse unidadeFederativa;
	
	private String cep;

	public SindicatoResponse(Sindicato sindicato) {
		
		this.setId(sindicato.getId());
		this.setDescricao(sindicato.getDescricao());
		this.setMesBase(sindicato.getMesBase());
		this.setPisoSalarial(sindicato.getPisoSalarial());
		this.setCnpj(sindicato.getCnpj());
		this.setCodigoEntidade(sindicato.getCodigoEntidade());
		this.setDdd(sindicato.getDdd());
		this.setTelefone(sindicato.getTelefone());
		this.setPatronal(sindicato.getPatronal().getLabel());
		this.setEndereco(sindicato.getEndereco());
		this.setNumero(sindicato.getNumero());
		this.setComplemento(sindicato.getComplemento());
		this.setBairro(sindicato.getBairro());
		this.setMunicipio(sindicato.getMunicipio());
		
		if (sindicato.getUnidadeFederativa() != null)
			this.setUnidadeFederativa(new UnidadeFederativaResponse(sindicato.getUnidadeFederativa()));
		
		
		this.setCep(sindicato.getCep());
		
	}

	public SindicatoResponse(Sindicato sindicato, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		
		this.setId(sindicato.getId());
		this.setDescricao(sindicato.getDescricao());
		this.setMesBase(sindicato.getMesBase());
		this.setPisoSalarial(sindicato.getPisoSalarial());
		this.setCnpj(sindicato.getCnpj());
		this.setCodigoEntidade(sindicato.getCodigoEntidade());
		this.setDdd(sindicato.getDdd());
		this.setTelefone(sindicato.getTelefone());
		this.setPatronal(sindicato.getPatronal().getLabel());
		this.setEndereco(sindicato.getEndereco());
		this.setNumero(sindicato.getNumero());
		this.setComplemento(sindicato.getComplemento());
		this.setBairro(sindicato.getBairro());
		this.setMunicipio(sindicato.getMunicipio());
		
		if (sindicato.getUnidadeFederativa() != null)
			this.setUnidadeFederativa(new UnidadeFederativaResponse(sindicato.getUnidadeFederativa()));
		
		
		this.setCep(sindicato.getCep());

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getMesBase() {
		return mesBase;
	}
	
	public void setMesBase(Integer mesBase) {
		this.mesBase = mesBase;
	}
	
	public Integer getPisoSalarial() {
		return pisoSalarial;
	}
	
	public void setPisoSalarial(Integer pisoSalarial) {
		this.pisoSalarial = pisoSalarial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCodigoEntidade() {
		return codigoEntidade;
	}

	public void setCodigoEntidade(String codigoEntidade) {
		this.codigoEntidade = codigoEntidade;
	}
	
	public Integer getDdd() {
		return ddd;
	}
	
	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getPatronal() {
		return patronal;
	}

	public void setPatronal(String patronal) {
		this.patronal = patronal;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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
	
	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public UnidadeFederativaResponse getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(UnidadeFederativaResponse unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
