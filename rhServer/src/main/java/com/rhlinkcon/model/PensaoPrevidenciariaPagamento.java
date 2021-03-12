package com.rhlinkcon.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

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
import com.rhlinkcon.payload.pensionista.PensaoPrevidenciariaPagamentoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Pensão Previdenciária Pagamento")
@Table(name = "pensao_previdenciaria_pagamento")
public class PensaoPrevidenciariaPagamento extends UserDateAudit {

	private static final long serialVersionUID = -915928047709645929L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "data_primeiro_pagamento")
	private LocalDate dataPrimeiroPagamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_rateio")
	private TipoRateioEnum tipoRateio;

	@Column(name = "valor_rateio")
	private Double valorRateio;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pensao")
	private TipoPensaoEnum tipoPensao;

	@NotNull
	@Column(name = "com_paridade")
	private Boolean comParidade;

	@NotNull
	@Column(name = "numero_processo_pensao")
	private String numeroProcessoPensao;

	@Column(name = "data_fim_beneficio")
	private LocalDate dataFimBeneficio;

	@NotNull
	@Column(name = "data_limite_retroativo")
	private LocalDate dataLimiteRetroativo;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_cota")
	private TipoCotaEnum tipoCota;

	@Column(name = "numero_reserva")
	private Integer numeroReserva;

	@JoinColumn(name = "agencia_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Agencia agencia;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta")
	private DadoBancarioTipoContaEnum tipoConta;

	@NotNull
	@Column(name = "numero_conta")
	private String numeroConta;

//	@Enumerated(EnumType.STRING)
//	@Column(name = "condicao_isencao")
//	private CondicaoIsencaoEnum condicaoIsencao;
//
//	@Column(name = "data_inicio_isencao")
//	private LocalDate dataInicioIsencao;
//
//	@Column(name = "data_fim_isencao")
//	private LocalDate dataFimIsencao;

	@Column(name = "dt_ini_isencao_ir")
	private Instant dataInicialIsencaoIr;

	@Column(name = "dt_fim_isencao_ir")
	private Instant dataFinalIsencaoIr;

	@Column(name = "dt_ini_isencao_previdencia")
	private Instant dataInicialIsencaoPrevidencia;

	@Column(name = "dt_fim_isencao_previdencia")
	private Instant dataFinalIsencaoPrevidencia;

	@Column(name = "digito")
	private String digito;

	@Column(name = "operacao")
	private String operacao;

	public PensaoPrevidenciariaPagamento() {
	}

	public PensaoPrevidenciariaPagamento(PensaoPrevidenciariaPagamentoRequest request) {

		this.id = request.getId();
		this.dataPrimeiroPagamento = request.getDataPrimeiroPagamento();

		if (Objects.nonNull(request.getTipoRateio()))
			this.tipoRateio = TipoRateioEnum.getEnumByString(request.getTipoRateio());

		this.valorRateio = request.getValorRateio();
		this.tipoPensao = TipoPensaoEnum.getEnumByString(request.getTipoPensao());
		this.comParidade = request.getComParidade();
		this.numeroProcessoPensao = request.getNumeroProcessoPensao();
		this.dataFimBeneficio = request.getDataFimBeneficio();
		this.dataLimiteRetroativo = request.getDataLimiteRetroativo();

		if (Objects.nonNull(request.getTipoCota()))
			this.tipoCota = TipoCotaEnum.getEnumByString(request.getTipoCota());

		this.numeroReserva = request.getNumeroReserva();
		this.agencia = new Agencia(request.getAgenciaId());
		this.tipoConta = DadoBancarioTipoContaEnum.getEnumByString(request.getTipoConta());
		this.numeroConta = request.getNumeroConta();
		this.digito = request.getDigito();
		this.operacao = request.getOperacao();

		this.setDataInicialIsencaoIr(request.getDataInicialIsencaoIr());
		this.setDataFinalIsencaoIr(request.getDataFinalIsencaoIr());
		this.setDataInicialIsencaoPrevidencia(request.getDataInicialIsencaoPrevidencia());
		this.setDataFinalIsencaoPrevidencia(request.getDataFinalIsencaoPrevidencia());

//		if (Objects.nonNull(request.getCondicaoIsencao()))
//			this.condicaoIsencao = CondicaoIsencaoEnum.getEnumByString(request.getCondicaoIsencao());
//
//		this.dataInicioIsencao = request.getDataInicioIsencao();
//		this.dataFimIsencao = request.getDataFimIsencao();
	}

	public Instant getDataInicialIsencaoIr() {
		return dataInicialIsencaoIr;
	}

	public void setDataInicialIsencaoIr(Instant dataInicialIsencaoIr) {
		this.dataInicialIsencaoIr = dataInicialIsencaoIr;
	}

	public Instant getDataFinalIsencaoIr() {
		return dataFinalIsencaoIr;
	}

	public void setDataFinalIsencaoIr(Instant dataFinalIsencaoIr) {
		this.dataFinalIsencaoIr = dataFinalIsencaoIr;
	}

	public Instant getDataInicialIsencaoPrevidencia() {
		return dataInicialIsencaoPrevidencia;
	}

	public void setDataInicialIsencaoPrevidencia(Instant dataInicialIsencaoPrevidencia) {
		this.dataInicialIsencaoPrevidencia = dataInicialIsencaoPrevidencia;
	}

	public Instant getDataFinalIsencaoPrevidencia() {
		return dataFinalIsencaoPrevidencia;
	}

	public void setDataFinalIsencaoPrevidencia(Instant dataFinalIsencaoPrevidencia) {
		this.dataFinalIsencaoPrevidencia = dataFinalIsencaoPrevidencia;
	}

	public PensaoPrevidenciariaPagamento(Long pensaoPagamentoId) {
		this.id = pensaoPagamentoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataPrimeiroPagamento() {
		return dataPrimeiroPagamento;
	}

	public void setDataPrimeiroPagamento(LocalDate dataPrimeiroPagamento) {
		this.dataPrimeiroPagamento = dataPrimeiroPagamento;
	}

	public TipoRateioEnum getTipoRateio() {
		return tipoRateio;
	}

	public void setTipoRateio(TipoRateioEnum tipoRateio) {
		this.tipoRateio = tipoRateio;
	}

	public TipoPensaoEnum getTipoPensao() {
		return tipoPensao;
	}

	public void setTipoPensao(TipoPensaoEnum tipoPensao) {
		this.tipoPensao = tipoPensao;
	}

	public Boolean getComParidade() {
		return comParidade;
	}

	public void setComParidade(Boolean comParidade) {
		this.comParidade = comParidade;
	}

	public String getNumeroProcessoPensao() {
		return numeroProcessoPensao;
	}

	public void setNumeroProcessoPensao(String numeroProcessoPensao) {
		this.numeroProcessoPensao = numeroProcessoPensao;
	}

	public LocalDate getDataFimBeneficio() {
		return dataFimBeneficio;
	}

	public void setDataFimBeneficio(LocalDate dataFimBeneficio) {
		this.dataFimBeneficio = dataFimBeneficio;
	}

	public LocalDate getDataLimiteRetroativo() {
		return dataLimiteRetroativo;
	}

	public void setDataLimiteRetroativo(LocalDate dataLimiteRetroativo) {
		this.dataLimiteRetroativo = dataLimiteRetroativo;
	}

	public TipoCotaEnum getTipoCota() {
		return tipoCota;
	}

	public void setTipoCota(TipoCotaEnum tipoCota) {
		this.tipoCota = tipoCota;
	}

	public Integer getNumeroReserva() {
		return numeroReserva;
	}

	public void setNumeroReserva(Integer numeroReserva) {
		this.numeroReserva = numeroReserva;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public DadoBancarioTipoContaEnum getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(DadoBancarioTipoContaEnum tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getDigito() {
		return digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getValorRateio() {
		return valorRateio;
	}

	public void setValorRateio(Double valorRateio) {
		this.valorRateio = valorRateio;
	}

}
