package com.rhlinkcon.payload.simuladorAposentadoria;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Data {

	private Date inicio;

	private Date fim;

	private int ano;

	private int mes;

	private int dia;

	@NotNull
	@NotBlank
	private TipoDataEnum tipo;

	private TempoTotal tempoTotal;

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public TipoDataEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoDataEnum tipo) {
		this.tipo = tipo;
	}

	public TempoTotal getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(TempoTotal tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

}
