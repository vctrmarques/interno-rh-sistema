package com.rhlinkcon.payload.folhaPagamento;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.FolhaPagamento;

public class FolhaPagamentoRequest {
	private Long id;

	@NotNull
	private Long tipoProcessamentoId;

	@NotNull
	private Long folhaCompetenciaId;

	@NotNull
	private Date periodoProcessamentoInicio;

	@NotNull
	private Date periodoProcessamentoFim;

	@NotNull
	private String status;

	@NotNull
	private Long filialId;

	private List<Long> lotacoes;

	public FolhaPagamentoRequest(FolhaPagamento folha) {
		this.id = folha.getId();
		this.tipoProcessamentoId = folha.getTipoProcessamento().getId();
		this.folhaCompetenciaId = folha.getFolhaCompetencia().getId();
		this.periodoProcessamentoInicio = folha.getPeriodoProcessamentoInicio();
		this.periodoProcessamentoFim = folha.getPeriodoProcessamentoFim();
		this.status = folha.getStatus().getLabel();
		this.filialId = folha.getFilial().getId();
	}

	public FolhaPagamentoRequest() {

	}

	public List<Long> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<Long> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTipoProcessamentoId() {
		return tipoProcessamentoId;
	}

	public void setTipoProcessamentoId(Long tipoProcessamentoId) {
		this.tipoProcessamentoId = tipoProcessamentoId;
	}

	public Long getFolhaCompetenciaId() {
		return folhaCompetenciaId;
	}

	public void setFolhaCompetenciaId(Long folhaCompetenciaId) {
		this.folhaCompetenciaId = folhaCompetenciaId;
	}

	public Date getPeriodoProcessamentoInicio() {
		return periodoProcessamentoInicio;
	}

	public void setPeriodoProcessamentoInicio(Date periodoProcessamentoInicio) {
		this.periodoProcessamentoInicio = periodoProcessamentoInicio;
	}

	public Date getPeriodoProcessamentoFim() {
		return periodoProcessamentoFim;
	}

	public void setPeriodoProcessamentoFim(Date periodoProcessamentoFim) {
		this.periodoProcessamentoFim = periodoProcessamentoFim;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getFilialId() {
		return filialId;
	}

	public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}

}
