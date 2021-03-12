package com.rhlinkcon.payload.treinamentoSugeridoValores;

import com.rhlinkcon.model.TreinamentoSugeridoValores;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.treinamentoSugerido.TreinamentoSugeridoResponse;

public class TreinamentoSugeridoValoresResponse extends DadoCadastralResponse{
	
	private Long id;

	private TreinamentoSugeridoResponse treinamentoSugerido;
	
	private Double valorDocencia;
	
	private Double valorConducao;
	
	private Double valorDiarias;
	
	private Double valorMaterial;
	
	private Double valorHospedagem;
	
	private Double valorIndividual;
	
	public TreinamentoSugeridoValoresResponse(TreinamentoSugeridoValores treinamentoValores) {
		this.id = treinamentoValores.getId();
		this.treinamentoSugerido = new TreinamentoSugeridoResponse(treinamentoValores.getTreinamentoSugerido());
		this.valorDocencia = treinamentoValores.getValorDocencia();
		this.valorConducao = treinamentoValores.getValorConducao();
		this.valorDiarias = treinamentoValores.getValorDiarias();
		this.valorMaterial = treinamentoValores.getValorMaterial();
		this.valorHospedagem = treinamentoValores.getValorHospedagem();
		this.valorIndividual = treinamentoValores.getValorIndividual();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TreinamentoSugeridoResponse getTreinamentoSugerido() {
		return treinamentoSugerido;
	}

	public void setTreinamentoSugerido(TreinamentoSugeridoResponse treinamentoSugerido) {
		this.treinamentoSugerido = treinamentoSugerido;
	}

	public Double getValorDocencia() {
		return valorDocencia;
	}

	public void setValorDocencia(Double valorDocencia) {
		this.valorDocencia = valorDocencia;
	}

	public Double getValorConducao() {
		return valorConducao;
	}

	public void setValorConducao(Double valorConducao) {
		this.valorConducao = valorConducao;
	}

	public Double getValorDiarias() {
		return valorDiarias;
	}

	public void setValorDiarias(Double valorDiarias) {
		this.valorDiarias = valorDiarias;
	}

	public Double getValorMaterial() {
		return valorMaterial;
	}

	public void setValorMaterial(Double valorMaterial) {
		this.valorMaterial = valorMaterial;
	}

	public Double getValorHospedagem() {
		return valorHospedagem;
	}

	public void setValorHospedagem(Double valorHospedagem) {
		this.valorHospedagem = valorHospedagem;
	}

	public Double getValorIndividual() {
		return valorIndividual;
	}

	public void setValorIndividual(Double valorIndividual) {
		this.valorIndividual = valorIndividual;
	}
}
