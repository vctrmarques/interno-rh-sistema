package com.rhlinkcon.payload.generico;

import java.util.List;

import com.rhlinkcon.payload.areaFormacao.AreaFormacaoRequest;
import com.rhlinkcon.payload.grauAcademico.GrauAcademicoRequest;

public class FiltroRequest {

	private List<AreaFormacaoRequest> areasFormacao;
	
	private List<GrauAcademicoRequest> grausAcademicos;
	
	private Integer page;
	
	private Integer size;
	
	private String order;
	
	private String nomeCursoBusca;
	
	private String codigoMecBusca;

	public List<AreaFormacaoRequest> getAreasFormacao() {
		return areasFormacao;
	}

	public void setAreasFormacao(List<AreaFormacaoRequest> areasFormacao) {
		this.areasFormacao = areasFormacao;
	}

	public List<GrauAcademicoRequest> getGrausAcademicos() {
		return grausAcademicos;
	}

	public void setGrausAcademicos(List<GrauAcademicoRequest> grausAcademicos) {
		this.grausAcademicos = grausAcademicos;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getNomeCursoBusca() {
		return nomeCursoBusca;
	}

	public void setNomeCursoBusca(String nomeCursoBusca) {
		this.nomeCursoBusca = nomeCursoBusca;
	}

	public String getCodigoMecBusca() {
		return codigoMecBusca;
	}

	public void setCodigoMecBusca(String codigoMecBusca) {
		this.codigoMecBusca = codigoMecBusca;
	}

}