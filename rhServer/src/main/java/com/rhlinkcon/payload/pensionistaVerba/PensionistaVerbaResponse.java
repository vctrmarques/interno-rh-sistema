package com.rhlinkcon.payload.pensionistaVerba;

import java.util.Objects;

import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class PensionistaVerbaResponse {

	private Long id;

	private VerbaResponse verba;

	private Double valor;

	private String tipo;

	private String recorrencia;

	private Integer parcelas;

	private Integer parcelasPagas;

	public PensionistaVerbaResponse(PensionistaVerba pensionistaVerba) {
		this.parcelas = pensionistaVerba.getParcelas();
		this.parcelasPagas = pensionistaVerba.getParcelasPagas();
		this.id = pensionistaVerba.getId();
		this.verba = new VerbaResponse(pensionistaVerba.getVerba());
		this.valor = pensionistaVerba.getValor();
		if (Objects.nonNull(pensionistaVerba.getTipoValor()))
			this.tipo = pensionistaVerba.getTipoValor().getLabel();
		if (Objects.nonNull(pensionistaVerba.getRecorrencia()))
			this.recorrencia = pensionistaVerba.getRecorrencia().getLabel();

	}

	public PensionistaVerbaResponse() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VerbaResponse getVerba() {
		return verba;
	}

	public void setVerba(VerbaResponse verba) {
		this.verba = verba;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(String recorrencia) {
		this.recorrencia = recorrencia;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public Integer getParcelasPagas() {
		return parcelasPagas;
	}

	public void setParcelasPagas(Integer parcelasPagas) {
		this.parcelasPagas = parcelasPagas;
	}

}
