package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.rhlinkcon.payload.generico.DadoBancarioDto;

@Embeddable
public class DadoBancario {

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta")
	private DadoBancarioTipoContaEnum tipoConta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banco_id")
	private Banco banco;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agencia_id")
	private Agencia agencia;

	@Column(name = "conta")
	@Size(max = 255)
	private String conta;

	@Column(name = "digito_conta")
	@Size(max = 255)
	private String digitoConta;

	public DadoBancario() {

	}

	public DadoBancario(DadoBancarioDto dadoBancarioDto) {
		this.tipoConta = DadoBancarioTipoContaEnum.getEnumByString(dadoBancarioDto.getTipoConta());
		this.banco = new Banco(dadoBancarioDto.getBanco().getId());
		this.agencia = new Agencia(dadoBancarioDto.getAgencia().getId());
		this.conta = dadoBancarioDto.getConta();
		this.digitoConta = dadoBancarioDto.getDigitoConta();
	}

	public DadoBancarioTipoContaEnum getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(DadoBancarioTipoContaEnum tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

}
