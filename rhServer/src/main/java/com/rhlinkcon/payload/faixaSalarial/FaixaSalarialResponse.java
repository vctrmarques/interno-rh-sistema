package com.rhlinkcon.payload.faixaSalarial;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.FaixaSalarial;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.classeSalarial.ClasseSalarialResponse;
import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialResponse;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;

public class FaixaSalarialResponse extends DadoCadastralResponse {

	private Long id;

	private GrupoSalarialResponse grupoSalarial;

	private ClasseSalarialResponse classeSalarial;

	private List<ReferenciaSalarialResponse> niveisSalariais;

	public FaixaSalarialResponse() {
		this.setNiveisSalariais(new ArrayList<ReferenciaSalarialResponse>());
	}

	public FaixaSalarialResponse(FaixaSalarial faixaSalarial) {
		this.setId(faixaSalarial.getId());
		this.setGrupoSalarial(new GrupoSalarialResponse(faixaSalarial.getGrupoSalarial()));
		this.setClasseSalarial(new ClasseSalarialResponse(faixaSalarial.getClasseSalarial()));
		if(!Objects.isNull(faixaSalarial.getNiveisSalariais()) && faixaSalarial.getNiveisSalariais().size() > 0) {
			this.setNiveisSalariais(new ArrayList<ReferenciaSalarialResponse>());
			for (ReferenciaSalarial nivelSalarial : faixaSalarial.getNiveisSalariais()) {
				this.getNiveisSalariais().add(new ReferenciaSalarialResponse(nivelSalarial));
			}
		}
	}

	public FaixaSalarialResponse(FaixaSalarial faixaSalarial, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(faixaSalarial.getId());
		this.setGrupoSalarial(new GrupoSalarialResponse(faixaSalarial.getGrupoSalarial()));
		this.setClasseSalarial(new ClasseSalarialResponse(faixaSalarial.getClasseSalarial()));
		if(!Objects.isNull(faixaSalarial.getNiveisSalariais()) && faixaSalarial.getNiveisSalariais().size() > 0) {
			this.setNiveisSalariais(new ArrayList<ReferenciaSalarialResponse>());
			for (ReferenciaSalarial nivelSalarial : faixaSalarial.getNiveisSalariais()) {
				this.getNiveisSalariais().add(new ReferenciaSalarialResponse(nivelSalarial));
			}
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

	public GrupoSalarialResponse getGrupoSalarial() {
		return grupoSalarial;
	}

	public void setGrupoSalarial(GrupoSalarialResponse grupoSalarial) {
		this.grupoSalarial = grupoSalarial;
	}

	public ClasseSalarialResponse getClasseSalarial() {
		return classeSalarial;
	}

	public void setClasseSalarial(ClasseSalarialResponse classeSalarial) {
		this.classeSalarial = classeSalarial;
	}

	public List<ReferenciaSalarialResponse> getNiveisSalariais() {
		return niveisSalariais;
	}

	public void setNiveisSalariais(List<ReferenciaSalarialResponse> niveisSalariais) {
		this.niveisSalariais = niveisSalariais;
	}

}
