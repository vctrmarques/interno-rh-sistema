package com.rhlinkcon.payload.pensionistaVerba;

public class PensionistaVerbasRequest {

	private Long id;
	
	private Long pensionistaId;
	
	private Long verbaId;
	
	private Double valor;
	
	private String tipo;
	
	private String recorrencia;
	
	private Integer parcelas;

	private Integer parcelasPagas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPensionistaId() {
		return pensionistaId;
	}

	public void setPensionistaId(Long pensionistaId) {
		this.pensionistaId = pensionistaId;
	}

	public Long getVerbaId() {
		return verbaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
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
