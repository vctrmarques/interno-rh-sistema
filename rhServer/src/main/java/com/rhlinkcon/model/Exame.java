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
import com.rhlinkcon.payload.exame.ExameRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Exame")
@Table(name = "exame")
public class Exame extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "categoria")
	private CategoriaExameEnum categoria;

	public Exame() {

	}

	public Exame(ExameRequest exameRequest) {
		this.setCodigo(exameRequest.getCodigo());
		this.setDescricao(exameRequest.getDescricao());

		if (!exameRequest.getCategoria().isEmpty())
			this.setCategoria(CategoriaExameEnum.getEnumByString(exameRequest.getCategoria()));
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

	public CategoriaExameEnum getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaExameEnum categoria) {
		this.categoria = categoria;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}