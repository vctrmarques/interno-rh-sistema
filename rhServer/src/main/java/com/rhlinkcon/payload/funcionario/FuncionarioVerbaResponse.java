package com.rhlinkcon.payload.funcionario;

import java.util.Objects;

import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class FuncionarioVerbaResponse {

	private Long id;

	private VerbaResponse verba;

	private Double valor;

	private String tipo;

	private String recorrencia;

	private Integer parcelas;

	private Integer parcelasPagas;

	public FuncionarioVerbaResponse(FuncionarioVerba funcionarioVerba) {
		this.parcelas = funcionarioVerba.getParcelas();
		this.parcelasPagas = funcionarioVerba.getParcelasPagas();
		this.id = funcionarioVerba.getId();
		this.verba = new VerbaResponse(funcionarioVerba.getVerba());
		this.valor = funcionarioVerba.getValor();
		if (Objects.nonNull(funcionarioVerba.getTipoValor()))
			this.tipo = funcionarioVerba.getTipoValor().getLabel();
		if (Objects.nonNull(funcionarioVerba.getRecorrencia()))
			this.recorrencia = funcionarioVerba.getRecorrencia().getLabel();

	}

	public FuncionarioVerbaResponse() {
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
