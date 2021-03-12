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
import com.rhlinkcon.payload.classeSalarial.ClasseSalarialRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Classe Salarial")
@Table(name = "classe_salarial")
public class ClasseSalarial extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1719452797415218398L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupo_salarial")
	private GrupoSalarial grupoSalarial;

	public ClasseSalarial() {

	}

	public ClasseSalarial(Long id) {
		this.id = id;
	}

	public ClasseSalarial(ClasseSalarialRequest classeSalarialRequest, GrupoSalarial grupoSalarial) {
		setId(classeSalarialRequest.getId());
		setNome(classeSalarialRequest.getNome());
		setGrupoSalarial(grupoSalarial);
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

	public GrupoSalarial getGrupoSalarial() {
		return grupoSalarial;
	}

	public void setGrupoSalarial(GrupoSalarial grupoSalarial) {
		this.grupoSalarial = grupoSalarial;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
