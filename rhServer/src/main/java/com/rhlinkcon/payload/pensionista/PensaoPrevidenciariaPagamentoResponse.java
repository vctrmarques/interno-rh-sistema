package com.rhlinkcon.payload.pensionista;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import com.rhlinkcon.model.PensaoPrevidenciariaPagamento;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.agencia.AgenciaResponse;
import com.rhlinkcon.util.Projecao;

public class PensaoPrevidenciariaPagamentoResponse extends DadoCadastralResponse {

	private Long id;

	private LocalDate dataPrimeiroPagamento;

	private String tipoRateio;

	private Double valorRateio;

	private String tipoPensao;

	private Boolean comParidade;

	private String numeroProcessoPensao;

	private LocalDate dataFimBeneficio;

	private LocalDate dataLimiteRetroativo;

	private String tipoCota;

	private Integer numeroReserva;

	private AgenciaResponse agencia;

	private String tipoConta;

	private String numeroConta;

	private Instant dataInicialIsencaoIr;

	private Instant dataFinalIsencaoIr;

	private Instant dataInicialIsencaoPrevidencia;

	private Instant dataFinalIsencaoPrevidencia;

//	private String condicaoIsencao;
//
//	private LocalDate dataInicioIsencao;
//
//	private LocalDate dataFimIsencao;

	private String digito;

	private String operacao;

	public PensaoPrevidenciariaPagamentoResponse(PensaoPrevidenciariaPagamento response) {
		this.id = response.getId();
		this.dataPrimeiroPagamento = response.getDataPrimeiroPagamento();
		if (Objects.nonNull(response.getTipoRateio())) {
			this.tipoRateio = response.getTipoRateio().name();
		}
		this.valorRateio = response.getValorRateio();
		this.tipoPensao = response.getTipoPensao().name();
		this.comParidade = response.getComParidade();
		this.numeroProcessoPensao = response.getNumeroProcessoPensao();
		this.dataFimBeneficio = response.getDataFimBeneficio();
		this.dataLimiteRetroativo = response.getDataLimiteRetroativo();
		if (Objects.nonNull(response.getTipoCota())) {
			this.tipoCota = response.getTipoCota().name();
		}
		this.numeroReserva = response.getNumeroReserva();
		this.agencia = new AgenciaResponse(response.getAgencia(), Projecao.COMPLETA);
		this.tipoConta = response.getTipoConta().name();
		this.numeroConta = response.getNumeroConta();
		this.digito = response.getDigito();
		this.operacao = response.getOperacao();

		this.setDataInicialIsencaoIr(response.getDataInicialIsencaoIr());
		this.setDataFinalIsencaoIr(response.getDataFinalIsencaoIr());
		this.setDataInicialIsencaoPrevidencia(response.getDataInicialIsencaoPrevidencia());
		this.setDataFinalIsencaoPrevidencia(response.getDataFinalIsencaoPrevidencia());

//		if (Objects.nonNull(response.getCondicaoIsencao())) {
//			this.condicaoIsencao = response.getCondicaoIsencao().name();
//		}
//
//		this.dataInicioIsencao = response.getDataInicioIsencao();
//		this.dataFimIsencao = response.getDataFimIsencao();
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

	public String getTipoRateio() {
		return tipoRateio;
	}

	public void setTipoRateio(String tipoRateio) {
		this.tipoRateio = tipoRateio;
	}

	public String getTipoPensao() {
		return tipoPensao;
	}

	public void setTipoPensao(String tipoPensao) {
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

	public String getTipoCota() {
		return tipoCota;
	}

	public void setTipoCota(String tipoCota) {
		this.tipoCota = tipoCota;
	}

	public Integer getNumeroReserva() {
		return numeroReserva;
	}

	public void setNumeroReserva(Integer numeroReserva) {
		this.numeroReserva = numeroReserva;
	}

	public AgenciaResponse getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaResponse agencia) {
		this.agencia = agencia;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
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

	public String getDigito() {
		return digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Double getValorRateio() {
		return valorRateio;
	}

	public void setValorRateio(Double valorRateio) {
		this.valorRateio = valorRateio;
	}

}
