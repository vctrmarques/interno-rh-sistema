package com.rhlinkcon.payload.grupoSalarial;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ClasseSalarial;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.classeSalarial.ClasseSalarialResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrupoSalarialResponse extends DadoCadastralResponse{

	private Long id;

	private String nome;

	private List<ClasseSalarialResponse> listClassesSalariais;
	
	public GrupoSalarialResponse(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public GrupoSalarialResponse(GrupoSalarial grupoSalarial) {
		setId(grupoSalarial.getId());
		setNome(grupoSalarial.getNome());
		if(!Objects.isNull(grupoSalarial.getClasseSalarial()) && grupoSalarial.getClasseSalarial().size() > 0) {
			List<ClasseSalarialResponse> classeSalarialResponse = new ArrayList<ClasseSalarialResponse>();
			for (ClasseSalarial classeSalarial : grupoSalarial.getClasseSalarial()) {
				classeSalarialResponse.add(new ClasseSalarialResponse(classeSalarial));
			}
			setClasseSalarial(classeSalarialResponse);
		}
	}
	
	public GrupoSalarialResponse(GrupoSalarial grupoSalarial, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		setId(grupoSalarial.getId());
		setNome(grupoSalarial.getNome());
		if(!Objects.isNull(grupoSalarial.getClasseSalarial()) && grupoSalarial.getClasseSalarial().size() > 0) {
			List<ClasseSalarialResponse> classeSalarialResponse = new ArrayList<ClasseSalarialResponse>();
			for (ClasseSalarial classeSalarial : grupoSalarial.getClasseSalarial()) {
				classeSalarialResponse.add(new ClasseSalarialResponse(classeSalarial));
			}
			setClasseSalarial(classeSalarialResponse);
		}
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

	public List<ClasseSalarialResponse> getlistClassesSalariais() {
		return listClassesSalariais;
	}

	public void setClasseSalarial(List<ClasseSalarialResponse> listClassesSalariais) {
		this.listClassesSalariais = listClassesSalariais;
	}

}
