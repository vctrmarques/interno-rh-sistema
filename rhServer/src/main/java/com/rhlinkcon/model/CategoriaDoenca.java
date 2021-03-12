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
import com.rhlinkcon.payload.categoriaDoenca.CategoriaDoencaRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Categoria de Doença")
@Table(name = "categoria_doenca")
public class CategoriaDoenca extends UserDateAudit {

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

	@NotBlank
	@NotNull
	@Column(name = "descricao", unique = true)
	private String descricao;

	@Column(name = "ativo")
	private boolean ativo;

	public CategoriaDoenca() {

	}

	public CategoriaDoenca(CategoriaDoencaRequest categoriaDoencaRequest) {
		this.setCodigo(categoriaDoencaRequest.getCodigo());
		this.setDescricao(categoriaDoencaRequest.getDescricao());
		this.setAtivo(Utils.setBool(categoriaDoencaRequest.isAtivo()));
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
