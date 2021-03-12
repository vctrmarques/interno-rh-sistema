package com.rhlinkcon.util.arquivoRemessa;

import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.ResponsavelLegal;
import com.rhlinkcon.model.TipoContaResponsavelEnum;

public class Titulo {

	private Agencia agencia;
	private String nome;
	private String contaCorrente;
	private String digitoConta;
	private Integer tipoConta;
	private Double valorLancamento;
	private String cpf;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cep;
	private Municipio municipio;
	
	public Titulo (Funcionario funcionario, Double valorLiquido) {
		setAgencia(funcionario.getAgenciaBancaria());
		setNome(funcionario.getNome());
		setContaCorrente(funcionario.getNumeroConta());
		setDigitoConta(funcionario.getDigitoConta());
		if(funcionario.getTipoConta().equals(TipoContaResponsavelEnum.CONTA_POUPANCA)){
			setTipoConta(2);
		} else {
			setTipoConta(1);
		}
		setValorLancamento(valorLiquido);
		setCpf(funcionario.getCpf());
		setLogradouro(funcionario.getLogradouro());
		setNumero(Integer.valueOf(funcionario.getNumero()));
		setComplemento(funcionario.getComplemento());
		setBairro(funcionario.getBairro());
		setCep(funcionario.getCep());
		setMunicipio(funcionario.getMunicipio());
	}
	
	public Titulo (Pensionista pensionista, Double valorLiquido) {
		setAgencia(pensionista.getPensaoPagamento().getAgencia());
		setNome(pensionista.getNome());
		setContaCorrente(pensionista.getPensaoPagamento().getNumeroConta().toString());
		setDigitoConta(pensionista.getPensaoPagamento().getDigito());
		if(pensionista.getPensaoPagamento().getTipoConta().equals(TipoContaResponsavelEnum.CONTA_POUPANCA)){
			setTipoConta(2);
		} else {
			setTipoConta(1);
		}
		setValorLancamento(valorLiquido);
		setCpf(pensionista.getCpf());
		setLogradouro(pensionista.getLogradouro());
		setNumero(Integer.valueOf(pensionista.getNumeroLogradouro()));
		setComplemento(pensionista.getComplementoLogradouro());
		setBairro(pensionista.getBairro());
		setCep(pensionista.getCep().toString());
		setMunicipio(pensionista.getMunicipio());
	}
	
	public Titulo (ResponsavelLegal responsavel, Double valorLiquido) {
		
	}
	
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContaCorrente() {
		return contaCorrente;
	}
	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	public String getDigitoConta() {
		return digitoConta;
	}
	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}
	public Integer getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(Integer tipoConta) {
		this.tipoConta = tipoConta;
	}
	public Double getValorLancamento() {
		return valorLancamento;
	}
	public void setValorLancamento(Double valorLancamento) {
		this.valorLancamento = valorLancamento;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
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
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Municipio getMunicipio() {
		return municipio;
	}
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
	
	
	
	
}