package com.rhlinkcon.payload.simuladorNivelSalarial;

import java.util.List;
import java.time.Instant;

import com.rhlinkcon.model.SimuladorNivelSalarial;
import com.rhlinkcon.payload.DadoCadastralResponse;


public class SimuladorNivelSalarialResponse extends DadoCadastralResponse{

	private Long id;

	private String descricao;

	private Instant dataCompetencia;
	
	private String situacao;
	
	private String situacaoString;
	
	private boolean programarAjuste;
	
	private String motivoAjuste;
	
	private String motivoAjusteString;
	
	private String tipoAjuste;
	
	private String tipoAjusteString;	
	  
	private Double valorAjuste;
	
	private Instant dataAjuste;
	
	private Long quantidadeNiveis;
	
	private List<SimuladorNivelSalarialValoresResponse> simuladorNivelSalarialValoresList;
	
	private SimuladorNivelSalarialAcordoResponse acordo;
	
	public SimuladorNivelSalarialResponse() {
		
	}
	
	public SimuladorNivelSalarialResponse(SimuladorNivelSalarial simuladorNivelSalarial) {
		this.setId(simuladorNivelSalarial.getId());
		this.setDescricao(simuladorNivelSalarial.getDescricao());
		this.setDataCompetencia(simuladorNivelSalarial.getDataCompetencia());
		this.setSituacao(simuladorNivelSalarial.getSituacao().toString());
		this.setSituacaoString(simuladorNivelSalarial.getSituacao().getLabel());
		this.setProgramarAjuste(simuladorNivelSalarial.isProgramarAjuste());
		this.setMotivoAjuste(simuladorNivelSalarial.getMotivoAjuste().toString());
		this.setTipoAjuste(simuladorNivelSalarial.getTipoAjuste().toString());
		this.setMotivoAjusteString(simuladorNivelSalarial.getMotivoAjuste().getLabel());
		this.setTipoAjusteString(simuladorNivelSalarial.getTipoAjuste().getLabel());
		this.setValorAjuste(simuladorNivelSalarial.getValorAjuste());
		this.setDataAjuste(simuladorNivelSalarial.getDataAjuste());
		this.setCriadoEm(simuladorNivelSalarial.getCreatedAt());
		this.setAlteradoEm(simuladorNivelSalarial.getUpdatedAt());
	}

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

	
	public String getMotivoAjusteString() {
		return motivoAjusteString;
	}

	public void setMotivoAjusteString(String motivoAjusteString) {
		this.motivoAjusteString = motivoAjusteString;
	}

	public String getTipoAjusteString() {
		return tipoAjusteString;
	}

	public void setTipoAjusteString(String tipoAjusteString) {
		this.tipoAjusteString = tipoAjusteString;
	}
	

	public String getSituacaoString() {
		return situacaoString;
	}

	public void setSituacaoString(String situacaoString) {
		this.situacaoString = situacaoString;
	}

	
	public Long getQuantidadeNiveis() {
		return quantidadeNiveis;
	}

	public void setQuantidadeNiveis(Long quantidadeNiveis) {
		this.quantidadeNiveis = quantidadeNiveis;
	}

	public List<SimuladorNivelSalarialValoresResponse> getSimuladorNivelSalarialValoresList() {
		return simuladorNivelSalarialValoresList;
	}

	public void setSimuladorNivelSalarialValoresList(
			List<SimuladorNivelSalarialValoresResponse> simuladorNivelSalarialValoresList) {
		this.simuladorNivelSalarialValoresList = simuladorNivelSalarialValoresList;
	}

	public SimuladorNivelSalarialAcordoResponse getAcordo() {
		return acordo;
	}

	public void setAcordo(SimuladorNivelSalarialAcordoResponse acordo) {
		this.acordo = acordo;
	}
	
	
	
	
}
