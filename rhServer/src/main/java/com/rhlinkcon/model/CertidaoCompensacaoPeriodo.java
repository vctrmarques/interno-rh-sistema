package com.rhlinkcon.model;

import java.time.LocalDate;

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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoCompensacaoPeriodo.CertidaoCompensacaoPeriodoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Compensação Período")
@Table(name = "certidao_compensacao_periodo")
public class CertidaoCompensacaoPeriodo extends UserDateAudit {

	private static final long serialVersionUID = 3839461867126994213L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_compensacao_id", nullable = false)
	private CertidaoCompensacao certidaoCompensacao;

	@Column(name = "periodo_inicio", nullable = false)
	private LocalDate periodoInicio;

	@Column(name = "periodo_fim", nullable = false)
	private LocalDate periodoFim;

	public CertidaoCompensacaoPeriodo() {
	}

	public CertidaoCompensacaoPeriodo(CertidaoCompensacaoPeriodoRequest request) {
		this.id = request.getId();
		this.certidaoCompensacao = new CertidaoCompensacao(request.getCertidaoCompensacaoId());
		this.periodoInicio = request.getPeriodoInicio();
		this.periodoFim = request.getPeriodoFim();
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

	public LocalDate getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(LocalDate periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public LocalDate getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(LocalDate periodoFim) {
		this.periodoFim = periodoFim;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
