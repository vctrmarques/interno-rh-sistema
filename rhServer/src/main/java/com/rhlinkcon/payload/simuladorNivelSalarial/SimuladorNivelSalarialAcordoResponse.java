package com.rhlinkcon.payload.simuladorNivelSalarial;

import java.time.Instant;

import com.rhlinkcon.model.SimuladorNivelSalarialAcordo;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class SimuladorNivelSalarialAcordoResponse  extends DadoCadastralResponse{

	private Long id;
	
	private String tipo;

	private Instant dataAcordo;
	
	private Instant dataInicial;
	
	private Instant dataFinal;
	
	private String descricao;

	public SimuladorNivelSalarialAcordoResponse(SimuladorNivelSalarialAcordo acordo) {
		this.setId(acordo.getId());
		this.setTipo(acordo.getTipo().getLabel());
		this.setDataAcordo(acordo.getDataAcordo());
		this.setDataInicial(acordo.getDataIncial());
		this.setDataFinal(acordo.getDataFinal());
		this.setDescricao(acordo.getDescricao());
	}
	
	public SimuladorNivelSalarialAcordoResponse() {
		
	}
	
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