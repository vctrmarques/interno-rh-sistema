package com.rhlinkcon.payload.generico;

import java.util.Objects;

import javax.validation.constraints.Size;

import com.rhlinkcon.model.Endereco;
import com.rhlinkcon.payload.DadoBasicoDto;

public class EnderecoDto {

	private DadoBasicoDto uf;

	private DadoBasicoDto municipio;

	@Size(min = 8, max = 8)
	private String cep;

	@Size(max = 255)
	private String logradouro;

	@Size(max = 255)
	private String complemento;

	@Size(max = 255)
	private String numero;

	@Size(max = 255)
	private String bairro;

	public EnderecoDto() {

	}

	public EnderecoDto(Endereco endereco) {
		if (Objects.nonNull(endereco.getUf()))
			this.uf = new DadoBasicoDto(endereco.getUf());
		if (Objects.nonNull(endereco.getMunicipio()))
			this.municipio = new DadoBasicoDto(endereco.getMunicipio());
		this.cep = endereco.getCep();
		this.logradouro = endereco.getLogradouro();
		this.complemento = endereco.getComplemento();
		this.numero = endereco.getNumero();
		this.bairro = endereco.getBairro();
	}

	public DadoBasicoDto getUf() {
		return uf;
	}

	public void setUf(DadoBasicoDto uf) {
		this.uf = uf;
	}

	public DadoBasicoDto getMunicipio() {
		return municipio;
	}

	public void setMunicipio(DadoBasicoDto municipio) {
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
