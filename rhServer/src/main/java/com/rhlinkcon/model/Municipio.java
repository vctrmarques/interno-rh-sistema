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
import com.rhlinkcon.payload.municipio.MunicipioRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Município")
@Table(name = "municipio")
public class Municipio extends UserDateAudit {

	private static final long serialVersionUID = -3252198840003906939L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "regiao_fiscal")
	private String regiaoFiscal;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidade_federativa")
	private UnidadeFederativa uf;

	@NotNull
	@Column(name = "cep")
	private String cep;

	@NotNull
	@Column(name = "naturalidade")
	private String naturalidade;

	@Column(name = "codigo_ibge")
	private Long codigoIbge;

	public Municipio() {
	}

	public Municipio(Long id) {
		this.id = id;
	}

	public Municipio(MunicipioRequest municipioRequest) {
		this.setRegiaoFiscal(municipioRequest.getRegiaoFiscal());
		this.setDescricao(municipioRequest.getDescricao());
		this.setUf(new UnidadeFederativa(municipioRequest.getUfId()));
		this.setCep(municipioRequest.getCep());
		this.setNaturalidade(municipioRequest.getNaturalidade());
		this.setCodigoIbge(municipioRequest.getCodigoIbge());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegiaoFiscal() {
		return regiaoFiscal;
	}

	public void setRegiaoFiscal(String regiaoFiscal) {
		this.regiaoFiscal = regiaoFiscal;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	@Override
	public String getLabel() {
		return this.descricao;
	}

	public Long getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(Long codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

}