package com.rhlinkcon.payload.simuladorNivelSalarial;

import java.time.Instant;

import javax.validation.constraints.NotNull;

public class SimuladorNivelSalarialAcordoRequest {

	private Long id;
	
	@NotNull
	private String tipo;

	private Instant dataAcordo;
	
	@NotNull
	private Instant dataInicial;
	
	@NotNull
	private Instant dataFinal;
	
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Instant getDataAcordo() {
		return dataAcordo;
	}

	public void setDataAcordo(Instant dataAcordo) {
		this.dataAcordo = dataAcordo;
	}


	public Instant getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Instant dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Instant getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Instant dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
