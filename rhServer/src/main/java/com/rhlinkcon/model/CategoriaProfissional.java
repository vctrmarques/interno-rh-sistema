package com.rhlinkcon.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.categoriaProfissional.CategoriaProfissionalRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Categoria Profissional")
@Table(name = "categoria_profissional")
public class CategoriaProfissional extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5446511314599709888L;

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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "categoria_profissional_verba", joinColumns = @JoinColumn(name = "categoria_profissional_id"), inverseJoinColumns = @JoinColumn(name = "verba_id"))
	private List<Verba> verbas;

	@Transient
	private boolean ativo;

	public CategoriaProfissional() {
	}

	public CategoriaProfissional(CategoriaProfissionalRequest categoriaProfissionalRequest) {
		this.setCodigo(categoriaProfissionalRequest.getCodigo());
		this.setDescricao(categoriaProfissionalRequest.getDescricao());
		if (!categoriaProfissionalRequest.getVerbasId().isEmpty()) {
			List<Verba> vbList = new ArrayList<Verba>();
			for (int i = 0; i < categoriaProfissionalRequest.getVerbasId().size(); i++) {
				Verba vb = new Verba(categoriaProfissionalRequest.getVerbasId().get(i));
				vbList.add (vb);
			}
			setVerbas(vbList);
		}
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

	public List<Verba> getVerbas() {
		return verbas;
	}

	public void setVerbas(List<Verba> verbas) {
		this.verbas = verbas;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
