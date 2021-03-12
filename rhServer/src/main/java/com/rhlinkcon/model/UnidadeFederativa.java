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
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Unidade Federativa")
@Table(name = "unidade_federativa")
public class UnidadeFederativa extends UserDateAudit {

	private static final long serialVersionUID = -1766570238861855024L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "sigla")
	private String sigla;

	@NotNull
	@Column(name = "estado")
	private String estado;

	public UnidadeFederativa() {
	}

	public UnidadeFederativa(Long id) {
		this.id = id;
	}

	public UnidadeFederativa(UnidadeFederativaRequest unidadeFederativaRequest) {
		this.setId(unidadeFederativaRequest.getId());
		this.setSigla(unidadeFederativaRequest.getSigla());
		this.setEstado(unidadeFederativaRequest.getEstado());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String getLabel() {
		return this.estado;
	}

}