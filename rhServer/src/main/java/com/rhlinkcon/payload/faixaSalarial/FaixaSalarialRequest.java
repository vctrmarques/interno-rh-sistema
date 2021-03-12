package com.rhlinkcon.payload.faixaSalarial;

import java.util.List;

import javax.validation.constraints.NotNull;

public class FaixaSalarialRequest {

	private Long id;
	
	@NotNull
	private Long grupoSalarialId;

	@NotNull
	private Long classeSalarialId;
	
	@NotNull	
	private List<Long> niveisSalariaisIds;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGrupoSalarialId() {
		return grupoSalarialId;
	}

	public void setGrupoSalarialId(Long grupoSalarialId) {
		this.grupoSalarialId = grupoSalarialId;
	}

	public Long getClasseSalarialId() {
		return classeSalarialId;
	}

	public void setClasseSalarialId(Long classeSalarialId) {
		this.classeSalarialId = classeSalarialId;
	}

	public List<Long> getNiveisSalariaisIds() {
		return niveisSalariaisIds;
	}

	public void setNiveisSalariaisIds(List<Long> niveisSalariaisIds) {
		this.niveisSalariaisIds = niveisSalariaisIds;
	}
	
}