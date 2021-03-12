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
import com.rhlinkcon.payload.nacionalidade.NacionalidadeRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Nacionalidade")
@Table(name = "nacionalidade")
public class Nacionalidade extends UserDateAudit {

	private static final long serialVersionUID = 4010927546010540963L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "sigla")
	private String sigla;

	@NotNull
	@Column(name = "nacionalidade_feminina")
	private String nacionalidadeFeminina;

	@NotNull
	@Column(name = "nacionalidade_masculina")
	private String nacionalidadeMasculina;

	@Column(name = "codigo_siprev")
	private Long codigoSiprev;

	public Nacionalidade() {
	}

	public Nacionalidade(Long id) {
		this.id = id;
	}

	public Nacionalidade(NacionalidadeRequest nacionalidadeRequest) {
		this.setSigla(nacionalidadeRequest.getSigla());
		this.setNacionalidadeFeminina(nacionalidadeRequest.getNacionalidadeFeminina());
		this.setNacionalidadeMasculina(nacionalidadeRequest.getNacionalidadeMasculina());
		this.setCodigoSiprev(nacionalidadeRequest.getCodigoSiprev());
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

	public String getNacionalidadeFeminina() {
		return nacionalidadeFeminina;
	}

	public void setNacionalidadeFeminina(String nacionalidadeFeminina) {
		this.nacionalidadeFeminina = nacionalidadeFeminina;
	}

	public String getNacionalidadeMasculina() {
		return nacionalidadeMasculina;
	}

	public void setNacionalidadeMasculina(String nacionalidadeMasculina) {
		this.nacionalidadeMasculina = nacionalidadeMasculina;
	}

	@Override
	public String getLabel() {
		return this.nacionalidadeFeminina;
	}

	public Long getCodigoSiprev() {
		return codigoSiprev;
	}

	public void setCodigoSiprev(Long codigoSiprev) {
		this.codigoSiprev = codigoSiprev;
	}

}