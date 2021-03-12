package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.naturezaJuridica.NaturezaJuridicaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Natureza Jurídica")
@Table(name = "natureza_juridica")
public class NaturezaJuridica extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "grupamento")
	private GrupamentoNaturezaEnum grupamento;

	public NaturezaJuridica() {
	}

	public NaturezaJuridica(NaturezaJuridicaRequest naturezaRequest) {
		this.setCodigo(naturezaRequest.getCodigo());
		this.setNome(naturezaRequest.getNome());
		this.setGrupamento(GrupamentoNaturezaEnum.getEnumByString(naturezaRequest.getGrupamento()));
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupamentoNaturezaEnum getGrupamento() {
		return grupamento;
	}

	public void setGrupamento(GrupamentoNaturezaEnum grupamento) {
		this.grupamento = grupamento;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}