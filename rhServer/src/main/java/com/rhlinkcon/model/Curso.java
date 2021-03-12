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
import com.rhlinkcon.payload.curso.CursoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Curso")
@Table(name = "curso")
public class Curso extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nomeCurso")
	private String nomeCurso;

	@NotNull
	@Column(name = "CodigoMec")
	private String codigoMec;

	@NotNull
	@Column(name = "carga_horaria")
	private Long cargaHoraria;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "id_grau_academico")
	private GrauAcademico grauAcademico;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area_formacao")
	private AreaFormacao areaFormacao;

	public Curso(Long id) {
		this.id = id;
	}

	public Curso() {

	}

	public Curso(CursoRequest cursoRequest) {
		this.setId(cursoRequest.getId());
		this.setNomeCurso(cursoRequest.getNomeCurso());
		this.setCodigoMec(cursoRequest.getCodigoMec());
		this.setCargaHoraria(cursoRequest.getCargaHoraria());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getCodigoMec() {
		return codigoMec;
	}

	public void setCodigoMec(String codigoMec) {
		this.codigoMec = codigoMec;
	}

	public GrauAcademico getGrauAcademico() {
		return grauAcademico;
	}

	public void setGrauAcademico(GrauAcademico grauAcademico) {
		this.grauAcademico = grauAcademico;
	}

	public AreaFormacao getAreaFormacao() {
		return areaFormacao;
	}

	public void setAreaFormacao(AreaFormacao areaFormacao) {
		this.areaFormacao = areaFormacao;
	}

	public Long getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Long cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}