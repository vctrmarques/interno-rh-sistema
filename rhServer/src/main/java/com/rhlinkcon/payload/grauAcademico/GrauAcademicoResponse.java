package com.rhlinkcon.payload.grauAcademico;

import java.time.Instant;

import com.rhlinkcon.model.GrauAcademico;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class GrauAcademicoResponse extends DadoCadastralResponse {

	private Long id;

	private String nome;


	public GrauAcademicoResponse(GrauAcademico grauAcademico) {
		this.setId(grauAcademico.getId());
		this.setNome(grauAcademico.getNome());
	}

	public GrauAcademicoResponse(GrauAcademico grauAcademico, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(grauAcademico.getId());
		this.setNome(grauAcademico.getNome());
		
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
