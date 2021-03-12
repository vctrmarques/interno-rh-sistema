package com.rhlinkcon.model;

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
import com.rhlinkcon.payload.recisaoContrato.RecisaoContratoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@SuppressWarnings("serial")
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Rescisão de Contrato")
@Table(name = "recisao_contrato")
public class RecisaoContrato extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@NotNull
	@Column(name = "ferias_vencidas")
	private Boolean feriasVencidas;

	@NotNull
	@Column(name = "aviso_previo")
	private Boolean avisoPrevio;

	@Column(name = "data_aviso")
	private Instant dataAviso;

	@Column(name = "dias_aviso")
	private Integer diasAviso;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "motivo_id")
	private MotivoDesligamento motivo;

	@NotNull
	@Column(name = "data_desligamento")
	private Instant dataDesligamento;

	@Column(name = "data_homologacao")
	private Instant dataHomologacao;

	@Column(name = "data_pagamento")
	private Instant dataPagamento;

	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	private RecisaoContratoEnum status;

	public RecisaoContrato() {
	}

	public RecisaoContrato(RecisaoContratoRequest recisaoContratoRequest) {
		setFeriasVencidas(recisaoContratoRequest.getFeriasVencidas());
		setAvisoPrevio(recisaoContratoRequest.getAvisoPrevio());
		setDataAviso(recisaoContratoRequest.getDataAviso());
		setDiasAviso(recisaoContratoRequest.getDiasAviso());
		setDataDesligamento(recisaoContratoRequest.getDataDesligamento());
		setDataHomologacao(recisaoContratoRequest.getDataDesligamento());
		setDataPagamento(recisaoContratoRequest.getDataPagamento());
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

	public Boolean getFeriasVencidas() {
		return feriasVencidas;
	}

	public void setFeriasVencidas(Boolean feriasVencidas) {
		this.feriasVencidas = feriasVencidas;
	}

	public Boolean getAvisoPrevio() {
		return avisoPrevio;
	}

	public void setAvisoPrevio(Boolean avisoPrevio) {
		this.avisoPrevio = avisoPrevio;
	}

	public Instant getDataAviso() {
		return dataAviso;
	}

	public void setDataAviso(Instant dataAviso) {
		this.dataAviso = dataAviso;
	}

	public Integer getDiasAviso() {
		return diasAviso;
	}

	public void setDiasAviso(Integer diasAviso) {
		this.diasAviso = diasAviso;
	}

	public MotivoDesligamento getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoDesligamento motivo) {
		this.motivo = motivo;
	}

	public Instant getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Instant dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public Instant getDataHomologacao() {
		return dataHomologacao;
	}

	public void setDataHomologacao(Instant dataHomologacao) {
		this.dataHomologacao = dataHomologacao;
	}

	public Instant getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Instant dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public RecisaoContratoEnum getStatus() {
		return status;
	}

	public void setStatus(RecisaoContratoEnum status) {
		this.status = status;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}