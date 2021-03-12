package com.rhlinkcon.payload.importadorVerbasFuncionario;

import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.TipoValorEnum;

public class PlanilhaDadosImportVerbaFunc {

	private Long funcionarioId;

	private Long verbaId;

	private String verbaCodigo;

	private TipoValorEnum tipo;

	private RecorrenciaEnum recorrencia;

	private Integer parcela;

	private Double valor;

	public PlanilhaDadosImportVerbaFunc() {

	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getVerbaId() {
		return verbaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
	}

	public TipoValorEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoValorEnum tipo) {
		this.tipo = tipo;
	}

	public RecorrenciaEnum getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(RecorrenciaEnum recorrencia) {
		this.recorrencia = recorrencia;
	}

	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
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
