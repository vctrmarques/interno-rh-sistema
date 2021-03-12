package com.rhlinkcon.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.rhlinkcon.payload.generico.EnderecoDto;

@Embeddable
public class Endereco {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id")
	private UnidadeFederativa uf;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;

	@Size(min = 8, max = 8)
	@Column(name = "cep")
	private String cep;

	@Size(max = 255)
	@Column(name = "logradouro")
	private String logradouro;

	@Size(max = 255)
	@Column(name = "complemento")
	private String complemento;

	@Size(max = 255)
	@Column(name = "numero")
	private String numero;

	@Size(max = 255)
	@Column(name = "bairro")
	private String bairro;

	public Endereco() {
	}

	public Endereco(EnderecoDto request) {
		if (Objects.nonNull(request.getUf()) && !request.getUf().getId().equals(0L))
			this.uf = new UnidadeFederativa(request.getUf().getId());
		if (Objects.nonNull(request.getMunicipio()) && !request.getMunicipio().getId().equals(0L))
			this.municipio = new Municipio(request.getMunicipio().getId());
		this.cep = request.getCep();
		this.logradouro = request.getLogradouro();
		this.complemento = request.getComplemento();
		this.numero = request.getNumero();
		this.bairro = request.getBairro();
	}

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

}
