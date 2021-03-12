package com.rhlinkcon.payload.adiantamentoPagamento;

import java.time.Instant;
import java.util.List;

import com.rhlinkcon.model.AdiantamentoPagamento;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

public class AdiantamentoPagamentoResponse {
	private Long id;
	private Long funcionarioId;
	private FuncionarioResponse funcionarioResponse;
	private List<Long> funcionariosIds;
	private Long empresaFilialId;
	private Long lotacaoId;
	private String tipoAdiantamento;
	private String recebimento;
	private Double percentualAdiantamento;
	private Double valorAdiantamento;
	private Instant dataInicio;
	private Instant dataFim;
	private String status;
	private String competencia;
	
	public AdiantamentoPagamentoResponse(AdiantamentoPagamento adiantamentoPagamento) {
		setId(adiantamentoPagamento.getId());
		setFuncionarioId(adiantamentoPagamento.getFuncionario().getId());
		setEmpresaFilialId(adiantamentoPagamento.getEmpresaFilial().getId());
		setLotacaoId(adiantamentoPagamento.getLotacao().getId());
		setTipoAdiantamento(adiantamentoPagamento.getTipoAdiantamento().getLabel());
		setRecebimento(adiantamentoPagamento.getRecebimento().getLabel());
		setPercentualAdiantamento(adiantamentoPagamento.getPercentualAdiantamento());
		setValorAdiantamento(adiantamentoPagamento.getValorAdiantamento().doubleValue());
		setDataInicio(adiantamentoPagamento.getDataInicio());
		setDataFim(adiantamentoPagamento.getDataFim());
		setStatus(adiantamentoPagamento.getStatus().getLabel());
		setCompetencia(adiantamentoPagamento.getCompetencia());
	}
	
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
	
	public FuncionarioResponse getFuncionarioResponse() {
		return funcionarioResponse;
	}

	public void setFuncionarioResponse(FuncionarioResponse funcionarioResponse) {
		this.funcionarioResponse = funcionarioResponse;
	}

	public List<Long> getFuncionariosIds() {
		return funcionariosIds;
	}

	public void setFuncionariosIds(List<Long> funcionariosIds) {
		this.funcionariosIds = funcionariosIds;
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
	
	public Double getValorAdiantamento() {
		return valorAdiantamento;
	}
	
	public void setValorAdiantamento(Double valorAdiantamento) {
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
}
