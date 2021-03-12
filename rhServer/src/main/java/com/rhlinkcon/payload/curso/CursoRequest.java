package com.rhlinkcon.payload.curso;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.rhlinkcon.model.GrauAcademico;
import com.rhlinkcon.model.AreaFormacao;

public class CursoRequest {

	private Long id;

	@NotNull
	@NotBlank
	private String nomeCurso;
	
	private String codigoMec;
	
	private Long cargaHoraria;

	@NotNull
	private Long grauAcademicoId;
	
	@NotNull
	private Long areaFormacaoId;

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

	public Long getGrauAcademicoId() {
		return grauAcademicoId;
	}

	public void setGrauAcademicoId(Long grauAcademicoId) {
		this.grauAcademicoId = grauAcademicoId;
	}

	public Long getAreaFormacaoId() {
		return areaFormacaoId;
	}

	public void setAreaFormacaoId(Long areaFormacaoId) {
		this.areaFormacaoId = areaFormacaoId;
	}

	public Long getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Long cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

}