package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Referência Salarial")
@Table(name = "referencia_salarial")
public class ReferenciaSalarial extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8770252859250324943L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo", unique = true)
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Column(name = "valor")
	private Double valor;

	@Column(name = "nivel_referencia")
	private boolean nivelReferencia;

	@Column(name = "mes_ano_competencia")
	private String mesAnoCompetencia;

	public ReferenciaSalarial() {

	}

	public ReferenciaSalarial(Long id) {
		this.id = id;
	}

	public ReferenciaSalarial(ReferenciaSalarialRequest referenciaSalarialRequest) {
		this.setCodigo(referenciaSalarialRequest.getCodigo());
		this.setDescricao(referenciaSalarialRequest.getDescricao());
		this.setValor(referenciaSalarialRequest.getValor());
		this.setNivelReferencia(Utils.setBool(referenciaSalarialRequest.isNivelReferencia()));
		this.setMesAnoCompetencia(referenciaSalarialRequest.getMesAnoCompetencia());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public boolean isNivelReferencia() {
		return nivelReferencia;
	}

	public void setNivelReferencia(boolean nivelReferencia) {
		this.nivelReferencia = nivelReferencia;
	}

	public String getMesAnoCompetencia() {
		return mesAnoCompetencia;
	}

	public void setMesAnoCompetencia(String mesAnoCompetencia) {
		this.mesAnoCompetencia = mesAnoCompetencia;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
