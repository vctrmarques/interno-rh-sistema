package com.rhlinkcon.model;

import java.time.Instant;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.PeriodoCertidaoExSeguradoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Ex Segurado Período")
@Table(name = "certidao_ex_segurado_periodo")
public class PeriodoCertidaoExSegurado extends UserDateAudit {

	private static final long serialVersionUID = 7737542844920186293L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_segurado_id")
	@NotNull
	private CertidaoExSegurado certidaoExSegurado;

	@Column(name = "periodoInicio")
	@NotNull
	private Instant periodoInicio;

	@Column(name = "periodoFinal")
	@NotNull
	private Instant periodoFinal;

	@Column(name = "aproveitamento")
	@NotNull
	@NotBlank
	private String aproveitamento;

	public PeriodoCertidaoExSegurado() {
	}

	public PeriodoCertidaoExSegurado(PeriodoCertidaoExSeguradoRequest periodoCertidaoExSeguradoRequest) {
		this.setCertidaoExSegurado(new CertidaoExSegurado(periodoCertidaoExSeguradoRequest.getCertidaoExSeguradoId()));
		this.setPeriodoFinal(periodoCertidaoExSeguradoRequest.getPeriodoFinal());
		this.setPeriodoInicio(periodoCertidaoExSeguradoRequest.getPeriodoInicio());
		this.setAproveitamento(periodoCertidaoExSeguradoRequest.getAproveitamento());
		this.setId(periodoCertidaoExSeguradoRequest.getId());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoExSegurado getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSegurado certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
	}

	public Instant getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(Instant periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public String getAproveitamento() {
		return aproveitamento;
	}

	public void setAproveitamento(String aproveitamento) {
		this.aproveitamento = aproveitamento;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}