package com.rhlinkcon.payload.adiantamentoPagamento;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;

public class AdiantamentoPagamentoRequest {
	private Long id;
	
	private Long funcionarioId;
	
	@NotNull
	private Long empresaFilialId;
	
	@NotNull
	private Long lotacaoId;
	
	private List<Long> funcionariosIds;
	
	@NotNull
	private String tipoAdiantamento;
	
	@NotNull
	private String recebimento;
	
	@NotNull
	private Double percentualAdiantamento;
	
	@NotNull
	private BigDecimal valorAdiantamento;
	
	@NotNull
	private Instant dataInicio;
	
	private Instant dataFim;
	
	private String status;
	
	private String competencia;

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

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public String getTipoAdiantamento() {
		return tipoAdiantamento;
	}

	public void setTipoAdiantamento(String tipoAdiantamento) {
		this.tipoAdiantamento = tipoAdiantamento;
	}

	public String getRecebimento() {
		return recebimento;
	}

	public void setRecebimento(String recebimento) {
		this.recebimento = recebimento;
	}

	public Double getPercentualAdiantamento() {
		return percentualAdiantamento;
	}

	public void setPercentualAdiantamento(Double percentualAdiantamento) {
		this.percentualAdiantamento = percentualAdiantamento;
	}

	public BigDecimal getValorAdiantamento() {
		return valorAdiantamento;
	}

	public void setValorAdiantamento(BigDecimal valorAdiantamento) {
		this.valorAdiantamento = valorAdiantamento;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataFim() {
		return dataFim;
	}

	public void setDataFim(Instant dataFim) {
		this.dataFim = dataFim;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public List<Long> getFuncionariosIds() {
		return funcionariosIds;
	}

	public void setFuncionariosIds(List<Long> funcionariosIds) {
		this.funcionariosIds = funcionariosIds;
	}

	
}
