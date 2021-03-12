package com.rhlinkcon.payload.importadorLancamentoManual;

public class PlanilhaDados {

	private Long verbaId;

	private String verbaCodigo;

	private Long funcionarioId;

	private Double valor;

	public PlanilhaDados() {

	}

	public Long getVerbaId() {
		return verbaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getVerbaCodigo() {
		return verbaCodigo;
	}

	public void setVerbaCodigo(String verbaCodigo) {
		this.verbaCodigo = verbaCodigo;
	}

}
