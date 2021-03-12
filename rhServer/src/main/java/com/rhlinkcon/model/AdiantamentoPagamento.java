package com.rhlinkcon.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.adiantamentoPagamento.AdiantamentoPagamentoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@SuppressWarnings("serial")
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Adiantamento de Pagamento")
@Table(name = "adiantamento_pagamento")
public class AdiantamentoPagamento extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "empresa_filial_id")
	private EmpresaFilial empresaFilial;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "lotacao_id")
	private Lotacao lotacao;

	@Column(name = "tipo_adiantamento")
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoPagamentoEnum tipoAdiantamento;

	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private RecebimentoEnum recebimento;

	@Column(name = "percentual_adiantamento")
	@NotNull
	private Double percentualAdiantamento;

	@Column(name = "valor_adiantamento")
	@NotNull
	private BigDecimal valorAdiantamento;

	@NotNull
	@Column(name = "data_inicio")
	private Instant dataInicio;

	@Column(name = "data_fim")
	private Instant dataFim;

	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusAdiantamentoEnum status;

	@Column
	@NotNull
	private String competencia;

	public AdiantamentoPagamento() {
	}

	public AdiantamentoPagamento(AdiantamentoPagamentoRequest adiantamentoPagamentoRequest) {
		setTipoAdiantamento(TipoPagamentoEnum.getEnumByString(adiantamentoPagamentoRequest.getTipoAdiantamento()));
		setRecebimento(RecebimentoEnum.getEnumByString(adiantamentoPagamentoRequest.getRecebimento()));
		setPercentualAdiantamento(adiantamentoPagamentoRequest.getPercentualAdiantamento());
		setValorAdiantamento(adiantamentoPagamentoRequest.getValorAdiantamento());
		setDataInicio(adiantamentoPagamentoRequest.getDataInicio());
		setDataFim(adiantamentoPagamentoRequest.getDataFim());
		setCompetencia(adiantamentoPagamentoRequest.getCompetencia());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public EmpresaFilial getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilial empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public TipoPagamentoEnum getTipoAdiantamento() {
		return tipoAdiantamento;
	}

	public void setTipoAdiantamento(TipoPagamentoEnum tipoAdiantamento) {
		this.tipoAdiantamento = tipoAdiantamento;
	}

	public RecebimentoEnum getRecebimento() {
		return recebimento;
	}

	public void setRecebimento(RecebimentoEnum recebimento) {
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

	public StatusAdiantamentoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusAdiantamentoEnum status) {
		this.status = status;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
