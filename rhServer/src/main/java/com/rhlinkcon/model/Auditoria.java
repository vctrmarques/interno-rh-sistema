package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rhlinkcon.audit.UserDateAudit;

@Entity
@Table(name = "auditoria")
public class Auditoria extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4934096042818454354L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@Column(name = "entidade", nullable = false)
	private String entidade;

	@Column(name = "acao", nullable = false)
	@Enumerated(EnumType.STRING)
	private AcaoAuditoriaEnum acao;

	@Column(name = "id_entidade", nullable = false)
	private Long idObjectAuditado;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public AcaoAuditoriaEnum getAcao() {
		return acao;
	}

	public void setAcao(AcaoAuditoriaEnum acao) {
		this.acao = acao;
	}

	public Long getIdObjectAuditado() {
		return idObjectAuditado;
	}

	public void setIdObjectAuditado(Long idObjectAuditado) {
		this.idObjectAuditado = idObjectAuditado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
