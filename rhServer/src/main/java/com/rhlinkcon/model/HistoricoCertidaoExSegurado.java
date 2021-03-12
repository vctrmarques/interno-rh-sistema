package com.rhlinkcon.model;

import java.time.Instant;
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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.HistoricoCertidaoExSeguradoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Histórico de Certidão Ex Segurado")
@Table(name = "historico_certidao_ex_segurado")
public class HistoricoCertidaoExSegurado extends UserDateAudit {

	private static final long serialVersionUID = -5189767770086819709L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_segurado_id")
	private CertidaoExSegurado certidaoExSegurado;

	@Enumerated(EnumType.STRING)
	private StatusSituacaoCertidaoExSeguradoEnum status;

	@Column(name = "observacao")
	private String observacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_funcionario")
	private Funcionario funcionario;

	@Column(name = "numero_certidao")
	private Long numeroCertidao;

	@Column(name = "ano_certidao")
	private Integer anoCertidao;

	@Column(name = "numero_retificacao")
	private Long numeroRetificacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_lotacao")
	private Lotacao lotacao;

	@Column(name = "data_exoneracao")
	private Instant dataExoneracao;

	@Column(name = "fonte_informacao")
	private String fonteInformacao;

	public HistoricoCertidaoExSegurado() {
	}

	public HistoricoCertidaoExSegurado(Long id) {
		this.id = id;
	}

	public HistoricoCertidaoExSegurado(HistoricoCertidaoExSeguradoRequest tempoEspecialCertidaoExSeguradoRequest) {
		this.setId(tempoEspecialCertidaoExSeguradoRequest.getId());
		this.setCertidaoExSegurado(
				new CertidaoExSegurado(tempoEspecialCertidaoExSeguradoRequest.getCertidaoExSeguradoId()));
		Utils.setValueFields(tempoEspecialCertidaoExSeguradoRequest, this, "status", "observacao");
	}

	public HistoricoCertidaoExSegurado(CertidaoExSegurado certidaoExSegurado) {
		if (Objects.nonNull(certidaoExSegurado)) {

			setCertidaoExSegurado(certidaoExSegurado);

			if (Objects.nonNull(certidaoExSegurado.getFuncionario()))
				setFuncionario(certidaoExSegurado.getFuncionario());

			setNumeroCertidao(Long.valueOf(certidaoExSegurado.getNumeroCertidao()));
			setNumeroRetificacao(certidaoExSegurado.getNumeroRetificacao());

			setAnoCertidao(certidaoExSegurado.getAnoCertidao());

			setStatus(certidaoExSegurado.getStatusSituacaoCertidao());

			if (Objects.nonNull(certidaoExSegurado.getLotacao()))
				setLotacao(certidaoExSegurado.getLotacao());

			setDataExoneracao(certidaoExSegurado.getDataExoneracao());

			setFonteInformacao(certidaoExSegurado.getFonteInformacao());
		}
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Long getNumeroCertidao() {
		return numeroCertidao;
	}

	public void setNumeroCertidao(Long numeroCertidao) {
		this.numeroCertidao = numeroCertidao;
	}

	public Integer getAnoCertidao() {
		return anoCertidao;
	}

	public void setAnoCertidao(Integer anoCertidao) {
		this.anoCertidao = anoCertidao;
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public Instant getDataExoneracao() {
		return dataExoneracao;
	}

	public void setDataExoneracao(Instant dataExoneracao) {
		this.dataExoneracao = dataExoneracao;
	}

	public String getFonteInformacao() {
		return fonteInformacao;
	}

	public void setFonteInformacao(String fonteInformacao) {
		this.fonteInformacao = fonteInformacao;
	}

	public StatusSituacaoCertidaoExSeguradoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusSituacaoCertidaoExSeguradoEnum status) {
		this.status = status;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}