package com.rhlinkcon.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Grupo Salarial")
@Table(name = "grupo_salarial")
public class GrupoSalarial extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5044857587558174028L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@OneToMany(mappedBy = "grupoSalarial", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ClasseSalarial> listClassesSalariais;

	public GrupoSalarial() {
	}

	public GrupoSalarial(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<ClasseSalarial> getClasseSalarial() {
		return listClassesSalariais;
	}

	public void setClasseSalarial(Set<ClasseSalarial> listClasseSalariais) {
		this.listClassesSalariais = listClasseSalariais;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
