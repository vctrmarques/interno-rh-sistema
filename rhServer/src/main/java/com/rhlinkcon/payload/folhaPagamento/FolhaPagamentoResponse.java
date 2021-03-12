package com.rhlinkcon.payload.folhaPagamento;

import java.util.Date;

import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoResponse;

public class FolhaPagamentoResponse extends DadoCadastralResponse {

	private Long id;

	private TipoProcessamentoResponse tipoProcessamento;

	private Date periodoProcessamentoInicio;

	private Date periodoProcessamentoFim;

	private String status;

	private String situacao;

	private EmpresaFilialResponse filial;

	private FolhaCompetenciaResponse folhaCompetencia;

	private Long processamentos;

	public FolhaPagamentoResponse(FolhaPagamento folha) {
		this.id = folha.getId();
		this.tipoProcessamento = new TipoProcessamentoResponse(folha.getTipoProcessamento());
		this.periodoProcessamentoInicio = folha.getPeriodoProcessamentoInicio();
		this.periodoProcessamentoFim = folha.getPeriodoProcessamentoFim();
		this.folhaCompetencia = new FolhaCompetenciaResponse(folha.getFolhaCompetencia());
		this.status = folha.getStatus().getLabel();
		this.filial = new EmpresaFilialResponse(folha.getFilial());
		this.processamentos = folha.getProcessamentos();
		this.setCriadoEm(folha.getCreatedAt());
		this.setAlteradoEm(folha.getUpdatedAt());
		this.situacao = folha.getSituacao().getLabel();
	}

	public FolhaPagamentoResponse() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoProcessamentoResponse getTipoProcessamento() {
		return tipoProcessamento;
	}

	public void setTipoProcessamento(TipoProcessamentoResponse tipoProcessamento) {
		this.tipoProcessamento = tipoProcessamento;
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

	public EmpresaFilialResponse getFilial() {
		return filial;
	}

	public void setFilial(EmpresaFilialResponse filial) {
		this.filial = filial;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public FolhaCompetenciaResponse getFolhaCompetencia() {
		return folhaCompetencia;
	}

	public void setFolhaCompetencia(FolhaCompetenciaResponse folhaCompetencia) {
		this.folhaCompetencia = folhaCompetencia;
	}

	public Long getProcessamentos() {
		return processamentos;
	}

	public void setProcessamentos(Long processamentos) {
		this.processamentos = processamentos;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
