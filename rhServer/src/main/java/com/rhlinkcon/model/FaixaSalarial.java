package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.faixaSalarial.FaixaSalarialRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Faixa Salarial")
@Table(name = "faixa_salarial")
public class FaixaSalarial extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8890044461078810760L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupo_salarial")
	private GrupoSalarial grupoSalarial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_classe_salarial")
	private ClasseSalarial classeSalarial;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "faixa_salarial_nivel", joinColumns = @JoinColumn(name = "id_faixa_salarial"), inverseJoinColumns = @JoinColumn(name = "id_nivel_salarial"))
	private Set<ReferenciaSalarial> niveisSalariais = new HashSet<>();

	public FaixaSalarial() {
	}

	public FaixaSalarial(Long id) {
		this.id = id;
	}

	public FaixaSalarial(FaixaSalarialRequest faixaSalarialRequest, ClasseSalarial classeSalarial,
			GrupoSalarial grupoSalarial) {
		this.setId(faixaSalarialRequest.getId());
		this.setClasseSalarial(classeSalarial);
		this.setGrupoSalarial(grupoSalarial);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GrupoSalarial getGrupoSalarial() {
		return grupoSalarial;
	}

	public void setGrupoSalarial(GrupoSalarial grupoSalarial) {
		this.grupoSalarial = grupoSalarial;
	}

	public ClasseSalarial getClasseSalarial() {
		return classeSalarial;
	}

	public void setClasseSalarial(ClasseSalarial classeSalarial) {
		this.classeSalarial = classeSalarial;
	}

	public Set<ReferenciaSalarial> getNiveisSalariais() {
		return niveisSalariais;
	}

	public void setNiveisSalariais(Set<ReferenciaSalarial> niveisSalariais) {
		this.niveisSalariais = niveisSalariais;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}