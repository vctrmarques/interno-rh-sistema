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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.classificacaoInternacionalDoenca.ClassificacaoInternacionalDoencaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Classificação Internacional de Doença")
@Table(name = "classificacao_internacional_doenca")
public class ClassificacaoInternacionalDoenca extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5716778865809907375L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "codigo", unique = true)
	private String codigo;

	@NotBlank
	@NotNull
	@Column(name = "descricao", unique = true)
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_doenca_id")
	private CategoriaDoenca categoriaDoenca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_categoria_doenca_id")
	private SubCategoriaDoenca subCategoriaDoenca;

	public ClassificacaoInternacionalDoenca() {
	}

	public ClassificacaoInternacionalDoenca(Long id) {
		this.id = id;
	}

	public ClassificacaoInternacionalDoenca(
			ClassificacaoInternacionalDoencaRequest classificacaoInternacionalDoencaRequest) {
		this.setCodigo(classificacaoInternacionalDoencaRequest.getCodigo());
		this.setDescricao(classificacaoInternacionalDoencaRequest.getDescricao());
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

	public CategoriaDoenca getCategoriaDoenca() {
		return categoriaDoenca;
	}

	public void setCategoriaDoenca(CategoriaDoenca categoriaDoenca) {
		this.categoriaDoenca = categoriaDoenca;
	}

	public SubCategoriaDoenca getSubCategoriaDoenca() {
		return subCategoriaDoenca;
	}

	public void setSubCategoriaDoenca(SubCategoriaDoenca subCategoriaDoenca) {
		this.subCategoriaDoenca = subCategoriaDoenca;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
