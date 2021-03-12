package com.rhlinkcon.payload.frequencia;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class FrequenciaRequest {
	private Long id;

	@NotNull
	private Long funcionarioId;

	@NotNull
	private Date entradaUm;

	@NotNull
	private Date saidaUm;

	@NotNull
	private Date entradaDois;

	@NotNull
	private Date saidaDois;

	private Long faltaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Date getEntradaUm() {
		return entradaUm;
	}

	public void setEntradaUm(Date entradaUm) {
		this.entradaUm = entradaUm;
	}

	public Date getSaidaUm() {
		return saidaUm;
	}

	public void setSaidaUm(Date saidaUm) {
		this.saidaUm = saidaUm;
	}

	public Date getEntradaDois() {
		return entradaDois;
	}

	public void setEntradaDois(Date entradaDois) {
		this.entradaDois = entradaDois;
	}

	public Date getSaidaDois() {
		return saidaDois;
	}

	public void setSaidaDois(Date saidaDois) {
		this.saidaDois = saidaDois;
	}

	public Long getFaltaId() {
		return faltaId;
	}

	public void setFaltaId(Long faltaId) {
		this.faltaId = faltaId;
	}

	

}
