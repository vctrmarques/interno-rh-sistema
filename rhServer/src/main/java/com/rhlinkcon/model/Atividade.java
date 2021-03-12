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
import com.rhlinkcon.payload.atividade.AtividadeRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Atividade")
@Table(name = "atividade")
public class Atividade extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "observacao")
	private String observacao;

	public Atividade() {
	}

	public Atividade(AtividadeRequest atividadeRequest) {
		this.setCodigo(atividadeRequest.getCodigo());
		this.setDescricao(atividadeRequest.getDescricao());
		this.setObservacao(atividadeRequest.getObservacao());
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}