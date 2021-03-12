package com.rhlinkcon.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Compensação Histórico")
@Table(name = "certidao_compensacao_historico")
public class CertidaoCompensacaoHistorico extends UserDateAudit {

	private static final long serialVersionUID = -16459036469063569L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_compensacao_id", nullable = false)
	private CertidaoCompensacao certidaoCompensacao;

	@NotNull
	@ElementCollection
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "certidao_compensacao_historico_classificacao", joinColumns = @JoinColumn(name = "certidao_compensacao_historico_id"))
	@Column(name = "classificacao", nullable = false)
	private List<ClassificacaoCertidaoCompensacaoEnum> classificacoes;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusCertidaoCompensacaoEnum statusAtual;

	@Enumerated(EnumType.STRING)
	private FundoCertidaoCompensacaoEnum fundo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_certidao_compensacao")
	private CertidaoCompensacaoTipoEnum tipoCertidaoCompensacao;

	private String observacao;

	public CertidaoCompensacaoTipoEnum getTipoCertidaoCompensacao() {
		return tipoCertidaoCompensacao;
	}

	public void setTipoCertidaoCompensacao(CertidaoCompensacaoTipoEnum tipoCertidaoCompensacao) {
		this.tipoCertidaoCompensacao = tipoCertidaoCompensacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoCompensacao getCertidaoCompensacao() {
		return certidaoCompensacao;
	}

	public void setCertidaoCompensacao(CertidaoCompensacao certidaoCompensacao) {
		this.certidaoCompensacao = certidaoCompensacao;
	}

	public List<ClassificacaoCertidaoCompensacaoEnum> getClassificacoes() {
		return classificacoes;
	}

	public void setClassificacoes(List<ClassificacaoCertidaoCompensacaoEnum> classificacoes) {
		this.classificacoes = classificacoes;
	}

	public StatusCertidaoCompensacaoEnum getStatusAtual() {
		return statusAtual;
	}

	public void setStatusAtual(StatusCertidaoCompensacaoEnum statusAtual) {
		this.statusAtual = statusAtual;
	}

	public FundoCertidaoCompensacaoEnum getFundo() {
		return fundo;
	}

	public void setFundo(FundoCertidaoCompensacaoEnum fundo) {
		this.fundo = fundo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
