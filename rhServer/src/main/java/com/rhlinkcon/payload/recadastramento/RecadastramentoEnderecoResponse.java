package com.rhlinkcon.payload.recadastramento;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.rhlinkcon.model.RecadastramentoEndereco;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.telefone.TelefoneReponse;

public class RecadastramentoEnderecoResponse {

	private Long id;
	
	private MunicipioResponse municipio;
	
	private String cep;
	
	private String bairro;
	
	private String logradouro;
	
	private String complemento;
	
	private String numero;
	
	private String email;
	
	private String observacao;
	
	private List<TelefoneReponse> telefones;
	
	public RecadastramentoEnderecoResponse(RecadastramentoEndereco obj) {
		
		setId(obj.getId());
		
		if(Objects.nonNull(obj.getMunicipio()))
			setMunicipio(new MunicipioResponse(obj.getMunicipio()));
		
		setCep(obj.getCep());
		setBairro(obj.getBairro());
		setLogradouro(obj.getLogradouro());
		setComplemento(obj.getComplemento());
		setNumero(obj.getNumero());
		setEmail(obj.getEmail());
		setObservacao(obj.getObservacao());
		
		if (Objects.nonNull(obj.getTelefones()))
			setTelefones(obj.getTelefones().stream().map(tel -> new TelefoneReponse(tel)).collect(Collectors.toList()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TelefoneReponse> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneReponse> telefones) {
		this.telefones = telefones;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
