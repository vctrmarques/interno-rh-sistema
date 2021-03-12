package com.rhlinkcon.payload.curso;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Curso;
import com.rhlinkcon.payload.grauAcademico.GrauAcademicoResponse;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.areaFormacao.AreaFormacaoResponse;


public class CursoResponse extends DadoCadastralResponse {

	private Long id;

	private String nomeCurso;

	private String codigoMec;

	private Long cargaHoraria;
	
	private GrauAcademicoResponse grauAcademico;

	private AreaFormacaoResponse areaFormacao;
	
	public CursoResponse() {
		
	}

	public CursoResponse(Curso curso) {
		this.setId(curso.getId());
		this.setNomeCurso(curso.getNomeCurso());
		this.setCodigoMec(curso.getCodigoMec());
		this.setCargaHoraria(curso.getCargaHoraria());

		if (curso.getGrauAcademico() != null)
			this.setGrauAcademico(new GrauAcademicoResponse(curso.getGrauAcademico()));

		
		if (curso.getAreaFormacao() != null)
			this.setAreaFormacao(new AreaFormacaoResponse(curso.getAreaFormacao()));
		
	}

	public CursoResponse(Curso curso, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		this.setId(curso.getId());
		this.setNomeCurso(curso.getNomeCurso());
		this.setCodigoMec(curso.getCodigoMec());
		this.setCargaHoraria(curso.getCargaHoraria());

		if (curso.getGrauAcademico() != null)
			this.setGrauAcademico(new GrauAcademicoResponse(curso.getGrauAcademico()));
		
		if (curso.getAreaFormacao() != null)
			this.setAreaFormacao(new AreaFormacaoResponse(curso.getAreaFormacao()));

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
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

	public GrauAcademicoResponse getGrauAcademico() {
		return grauAcademico;
	}

	public void setGrauAcademico(GrauAcademicoResponse grauAcademico) {
		this.grauAcademico = grauAcademico;
	}
	
	public AreaFormacaoResponse getAreaFormacao() {
		return areaFormacao;
	}

	public void setAreaFormacao(AreaFormacaoResponse areaFormacao) {
		this.areaFormacao = areaFormacao;
	}

	public Long getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Long cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
}
