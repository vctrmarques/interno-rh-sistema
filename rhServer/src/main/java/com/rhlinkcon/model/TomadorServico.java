package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Tomador de Serviço")
@Table(name = "tomador_servico")
public class TomadorServico extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5379415609405983145L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_inscricao")
	private TipoInscricaoEnum tipoInscricao;

	@NotNull
	@Column(name = "razao_social")
	private String razaoSocial;

	@NotNull
	@Embedded
	private Endereco endereco;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_codigo_recolhimento")
	private CodigoRecolhimento codigoRecolhimento;

	@NotNull
	@Size(min = 14, max = 14)
	@Column(name = "cnpj")
	private String cnpj;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_codigo_pagamento_gps")
	private CodigoPagamentoGps codigoPagamentoGps;

	@OneToOne(fetch = FetchType.LAZY)
	private Compensacao compensacao;

	public TomadorServico() {
	}

	public TomadorServico(TomadorServicoRequest tomadorServicoRequest) {
		this.setTipoInscricao(TipoInscricaoEnum.getEnumByString(tomadorServicoRequest.getTipoInscricao()));
		this.setEndereco(new Endereco(tomadorServicoRequest.getEndereco()));
		this.setRazaoSocial(tomadorServicoRequest.getRazaoSocial());
		this.setCnpj(tomadorServicoRequest.getCnpj());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoInscricaoEnum getTipoInscricao() {
		return tipoInscricao;
	}

	public void setTipoInscricao(TipoInscricaoEnum tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public CodigoRecolhimento getCodigoRecolhimento() {
		return codigoRecolhimento;
	}

	public void setCodigoRecolhimento(CodigoRecolhimento codigoRecolhimento) {
		this.codigoRecolhimento = codigoRecolhimento;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public CodigoPagamentoGps getCodigoPagamentoGps() {
		return codigoPagamentoGps;
	}

	public void setCodigoPagamentoGps(CodigoPagamentoGps codigoPagamentoGps) {
		this.codigoPagamentoGps = codigoPagamentoGps;
	}

	public Compensacao getCompensacao() {
		return compensacao;
	}

	public void setCompensacao(Compensacao compensacao) {
		this.compensacao = compensacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}