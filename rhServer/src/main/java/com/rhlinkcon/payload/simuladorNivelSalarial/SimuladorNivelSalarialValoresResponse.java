package com.rhlinkcon.payload.simuladorNivelSalarial;

import com.rhlinkcon.model.SimuladorNivelSalarialValores;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class SimuladorNivelSalarialValoresResponse  extends DadoCadastralResponse{



	private Long id;
	
	private Double valorAjustado;
	
	private Double valorRetroativo;
	
	private Long nivelSalarialId;
	
	private String descricao;
	
	private Double valor;

	public SimuladorNivelSalarialValoresResponse() {
		
	}
	
	public SimuladorNivelSalarialValoresResponse(SimuladorNivelSalarialValores valores) {
		this.setValorAjustado(valores.getValorAjustado());
		this.setValorRetroativo(valores.getValorRetroativo());
		this.setNivelSalarialId(valores.getNivelSalarial().getId());
		this.setDescricao(valores.getNivelSalarial().getDescricao());
		this.setValor(valores.getNivelSalarial().getValor());
		this.setId(valores.getId());
	}

	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorRetroativo() {
		return valorRetroativo;
	}

	public void setValorRetroativo(Double valorRetroativo) {
		this.valorRetroativo = valorRetroativo;
	}

	public Double getValorAjustado() {
		return valorAjustado;
	}

	public void setValorAjustado(Double valorAjustado) {
		this.valorAjustado = valorAjustado;
	}

	public Long getNivelSalarialId() {
		return nivelSalarialId;
	}

	public void setNivelSalarialId(Long nivelSalarialId) {
		this.nivelSalarialId = nivelSalarialId;
	}

	
	
}
