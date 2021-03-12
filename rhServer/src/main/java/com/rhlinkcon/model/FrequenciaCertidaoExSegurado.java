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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.FrequenciaCertidaoExSeguradoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Frequência Certidão Ex Servidor")
@Table(name = "frequencia_certidao_ex_segurado")
public class FrequenciaCertidaoExSegurado extends UserDateAudit {

	private static final long serialVersionUID = 4120853690663081166L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_segurado_id")
	@NotNull
	private CertidaoExSegurado certidaoExSegurado;

	@Column(name = "ano")
	private Integer ano;

	@Column(name = "tempo_bruto")
	private Integer tempoBruto;

	@Column(name = "faltas")
	private Integer faltas;

	@Column(name = "licencas")
	private Integer licencas;

	@Column(name = "licencas_sem_venc")
	private Integer licencasSemVenc;

	@Column(name = "suspensoes")
	private Integer suspensoes;

	@Column(name = "disponibilidade")
	private Integer disponibilidade;

	@Column(name = "outros")
	private Integer outros;

	@Column(name = "tempo_liquido")
	private Integer tempoLiquido;

	public FrequenciaCertidaoExSegurado() {
	}

	public FrequenciaCertidaoExSegurado(Long id) {
		this.id = id;
	}

	public FrequenciaCertidaoExSegurado(FrequenciaCertidaoExSeguradoRequest request) {

		this.setId(request.getId());
		this.setAno(request.getAno());
		this.setTempoBruto(request.getTempoBruto());
		this.setLicencas(request.getLicencas());
		this.setLicencasSemVenc(request.getLicencasSemVenc());
		this.setTempoLiquido(request.getTempoLiquido());
		this.setDisponibilidade(request.getDisponibilidade());
		this.setSuspensoes(request.getSuspensoes());
		this.setFaltas(request.getFaltas());
		this.setOutros(request.getOutros());

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

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getTempoBruto() {
		return tempoBruto;
	}

	public void setTempoBruto(Integer tempoBruto) {
		this.tempoBruto = tempoBruto;
	}

	public Integer getFaltas() {
		return faltas;
	}

	public void setFaltas(Integer faltas) {
		this.faltas = faltas;
	}

	public Integer getLicencas() {
		return licencas;
	}

	public void setLicencas(Integer licencas) {
		this.licencas = licencas;
	}

	public Integer getLicencasSemVenc() {
		return licencasSemVenc;
	}

	public void setLicencasSemVenc(Integer licencasSemVenc) {
		this.licencasSemVenc = licencasSemVenc;
	}

	public Integer getSuspensoes() {
		return suspensoes;
	}

	public void setSuspensoes(Integer suspensoes) {
		this.suspensoes = suspensoes;
	}

	public Integer getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(Integer disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public Integer getOutros() {
		return outros;
	}

	public void setOutros(Integer outros) {
		this.outros = outros;
	}

	public Integer getTempoLiquido() {
		return tempoLiquido;
	}

	public void setTempoLiquido(Integer tempoLiquido) {
		this.tempoLiquido = tempoLiquido;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}