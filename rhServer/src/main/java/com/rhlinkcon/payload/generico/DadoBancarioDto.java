package com.rhlinkcon.payload.generico;

import com.rhlinkcon.model.DadoBancario;
import com.rhlinkcon.payload.DadoBasicoDto;

public class DadoBancarioDto {

	private String tipoConta;

	private DadoBasicoDto banco;

	private DadoBasicoDto agencia;

	private String conta;

	private String digitoConta;

	public DadoBancarioDto() {

	}

	public DadoBancarioDto(DadoBancario dadoBancario) {
		this.tipoConta = dadoBancario.getTipoConta().getLabel();
		this.banco = new DadoBasicoDto(dadoBancario.getBanco());
		this.agencia = new DadoBasicoDto(dadoBancario.getAgencia());
		this.conta = dadoBancario.getConta();
		this.digitoConta = dadoBancario.getDigitoConta();
	}

//	public DadoBancarioDto(ResponsavelLegal responsavelLegal) {
//		this.tipoConta = responsavelLegal.getTipoConta().getLabel();
//		this.banco = new DadoBasicoDto(responsavelLegal.getBanco());
//		this.agencia = new DadoBasicoDto(responsavelLegal.getAgenciaBancaria());
//		this.conta = responsavelLegal.getContaCorrente().toString();
//		this.digitoConta = responsavelLegal.getDigitoConta();
//	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public DadoBasicoDto getBanco() {
		return banco;
	}

	public void setBanco(DadoBasicoDto banco) {
		this.banco = banco;
	}

	public DadoBasicoDto getAgencia() {
		return agencia;
	}

	public void setAgencia(DadoBasicoDto agencia) {
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
