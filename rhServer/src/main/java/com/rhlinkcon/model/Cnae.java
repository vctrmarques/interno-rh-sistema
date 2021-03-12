package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.cnae.CnaeRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "CNAE")
@Table(name = "cnae")
public class Cnae extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo_secao")
	private String codigoSecao;

	@NotBlank
	@NotNull
	@Column(name = "nome_secao")
	private String nomeSecao;

	@NotNull
	@Column(name = "codigo_classe")
	private Long codigoClasse;

	@NotBlank
	@NotNull
	@Column(name = "nome_classe")
	private String nomeClasse;

	public Cnae() {
	}

	public Cnae(CnaeRequest cnaeRequest) {
		this.setCodigoSecao(cnaeRequest.getCodigoSecao());
		this.setNomeSecao(cnaeRequest.getNomeSecao());
		this.setCodigoClasse(cnaeRequest.getCodigoClasse());
		this.setNomeClasse(cnaeRequest.getNomeClasse());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoSecao() {
		return codigoSecao;
	}

	public void setCodigoSecao(String codigoSecao) {
		this.codigoSecao = codigoSecao;
	}

	public String getNomeSecao() {
		return nomeSecao;
	}

	public void setNomeSecao(String nomeSecao) {
		this.nomeSecao = nomeSecao;
	}

	public Long getCodigoClasse() {
		return codigoClasse;
	}

	public void setCodigoClasse(Long codigoClasse) {
		this.codigoClasse = codigoClasse;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
