package com.rhlinkcon.payload.simuladorNivelSalarial;

import java.util.List;
import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class SimuladorNivelSalarialRequest {

	private Long id;

	@NotNull
	@NotBlank
	private String descricao;

	private Instant dataCompetencia;
	
	private String situacao;
	
	private boolean programarAjuste;
	
	@NotNull
	private String motivoAjuste;
	
	@NotNull
	private String tipoAjuste;	
	  
	@NotNull
	private Double valorAjuste;
	
	private Instant dataAjuste;
	
	private List<SimuladorNivelSalarialValoresRequest> simuladorNivelSalarialValoresList;
	
	private SimuladorNivelSalarialAcordoRequest acordo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instant getDataCompetencia() {
		return dataCompetencia;
	}

	public void setDataCompetencia(Instant dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public boolean isProgramarAjuste() {
		return programarAjuste;
	}

	public void setProgramarAjuste(boolean programarAjuste) {
		this.programarAjuste = programarAjuste;
	}

	public String getMotivoAjuste() {
		return motivoAjuste;
	}

	public void setMotivoAjuste(String motivoAjuste) {
		this.motivoAjuste = motivoAjuste;
	}

	public String getTipoAjuste() {
		return tipoAjuste;
	}

	public void setTipoAjuste(String tipoAjuste) {
		this.tipoAjuste = tipoAjuste;
	}

	public Double getValorAjuste() {
		return valorAjuste;
	}

	public void setValorAjuste(Double valorAjuste) {
		this.valorAjuste = valorAjuste;
	}

	public Instant getDataAjuste() {
		return dataAjuste;
	}

	public void setDataAjuste(Instant dataAjuste) {
		this.dataAjuste = dataAjuste;
	}

	public SimuladorNivelSalarialAcordoRequest getAcordo() {
		return acordo;
	}

	public void setAcordo(SimuladorNivelSalarialAcordoRequest acordo) {
		this.acordo = acordo;
	}

	public List<SimuladorNivelSalarialValoresRequest> getSimuladorNivelSalarialValoresList() {
		return simuladorNivelSalarialValoresList;
	}

	public void setSimuladorNivelSalarialValoresList(List<SimuladorNivelSalarialValoresRequest> simuladorNivelSalarialValoresList) {
		this.simuladorNivelSalarialValoresList = simuladorNivelSalarialValoresList;
	}
	
	
	
}
