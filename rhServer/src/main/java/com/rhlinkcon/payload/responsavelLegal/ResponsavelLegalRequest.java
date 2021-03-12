package com.rhlinkcon.payload.responsavelLegal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsavelLegalRequest {

	private Long id;
	
	@NotNull
	private String codigoResponsavel;
	
	@NotNull
	private String nome;
	
	@NotNull
	private Long bancoId;

	@NotNull
	private Integer agencia;
	
	private Long agenciaBancariaId;
	
	private Integer contaCorrente;
	
	private String digitoConta;
	
	@NotNull
	private String tipoResponsavel;
	
	@NotNull
	private String cpf;
	
	@NotNull
	private Integer identidade;
	
	@NotNull
	private String tipoConta;
	
	private String logradouro;
	
	private String numero;

	private String complemento;
	
	private String bairro;
	
	private Long municipioId;
	
	private String cep;
	
	private Long unidadeFederativaId;

	private String telefone;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigoResponsavel() {
		return codigoResponsavel;
	}
	
	public void setCodigoResponsavel(String codigoResponsavel) {
		this.codigoResponsavel = codigoResponsavel;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Long getBancoId() {
		return bancoId;
	}
	
	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public Integer getAgencia() {
		return agencia;
	}
	
	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}
	
	public Integer getContaCorrente() {
		return contaCorrente;
	}
	
	public void setContaCorrente(Integer contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public String getTipoResponsavel() {
		return tipoResponsavel;
	}
	
	public void setTipoResponsavel(String tipoResponsavel) {
		this.tipoResponsavel = tipoResponsavel;
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public Integer getIdentidade() {
		return identidade;
	}
	
	public void setIdentidade(Integer identidade) {
		this.identidade = identidade;
	}

	public String getTipoConta() {
		return tipoConta;
	}
	
	public void setTipoConta(String tipoConta){
		this.tipoConta = tipoConta;
	}

	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
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

	public Long getMunicipioId() {
		return municipioId;
	}
	
	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}

	public Long getUnidadeFederativaId() {
		return unidadeFederativaId;
	}
	
	public void setUnidadeFederativaId(Long unidadeFederativaId) {
		this.unidadeFederativaId = unidadeFederativaId;
	}

	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone (String telefone){
		this.telefone = telefone;
	}

	public Long getAgenciaBancariaId() {
		return agenciaBancariaId;
	}

	public void setAgenciaBancariaId(Long agenciaId) {
		this.agenciaBancariaId = agenciaId;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}
}