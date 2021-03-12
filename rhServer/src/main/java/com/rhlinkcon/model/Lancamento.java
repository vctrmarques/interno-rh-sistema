package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Lancamento")
@Table(name = "folha_pagamento_contracheque_verba")
public class Lancamento extends UserDateAudit {

	private static final long serialVersionUID = -190446777281682696L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha_pagamento_contracheque_id")
	private Contracheque contracheque;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verba_id")
	private Verba verba;

	@Column(name = "valor_referencia")
	private Double valorReferencia;

	@Column(name = "valor")
	private Double valor;

	// TODO ANALIASR
	@Column(name = "adicionado_manualmente")
	private Boolean adicionadoManualmente;

	// TODO ANALIASR
	@Transient
	private boolean verbaEspecifica;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pensao_alimenticia_id")
	private PensaoAlimenticia pensaoAlimenticia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_verba_id")
	private FuncionarioVerba funcionarioVerba;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pensionista_verba_id")
	private PensionistaVerba pensionistaVerba;

	@Column(name = "numero_parcela")
	private Integer numeroParcela;

	public Lancamento() {
	}

	public Long getId() {
		return id;
	}

	public Contracheque getContracheque() {
		return contracheque;
	}

	public void setContracheque(Contracheque contracheque) {
		this.contracheque = contracheque;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Verba getVerba() {
		return verba;
	}

	public void setVerba(Verba verba) {
		this.verba = verba;
	}

	public Double getValorReferencia() {
		return valorReferencia;
	}

	public void setValorReferencia(Double valorReferencia) {
		this.valorReferencia = valorReferencia;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Boolean getAdicionadoManualmente() {
		return adicionadoManualmente;
	}

	public void setAdicionadoManualmente(Boolean adicionadoManualmente) {
		this.adicionadoManualmente = adicionadoManualmente;
	}

	public boolean isVerbaEspecifica() {
		return verbaEspecifica;
	}

	public void setVerbaEspecifica(boolean verbaEspecifica) {
		this.verbaEspecifica = verbaEspecifica;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public PensaoAlimenticia getPensaoAlimenticia() {
		return pensaoAlimenticia;
	}

	public void setPensaoAlimenticia(PensaoAlimenticia pensaoAlimenticia) {
		this.pensaoAlimenticia = pensaoAlimenticia;
	}

	public FuncionarioVerba getFuncionarioVerba() {
		return funcionarioVerba;
	}

	public void setFuncionarioVerba(FuncionarioVerba funcionarioVerba) {
		this.funcionarioVerba = funcionarioVerba;
	}

	public PensionistaVerba getPensionistaVerba() {
		return pensionistaVerba;
	}

	public void setPensionistaVerba(PensionistaVerba pensionistaVerba) {
		this.pensionistaVerba = pensionistaVerba;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

}
