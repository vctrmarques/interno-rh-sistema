package com.rhlinkcon.payload.historicoSituacaoFuncional;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoSituacaoFuncionalRequest {

	private Long id;

	@NotNull
	private Long funcionarioId;

	@NotNull
	private String tipoSituacaoFuncional;

	private Long funcaoId;

	private Long nivelSalarialId;

	private Long motivoId;

	private String ato;

	private Instant dataAto;

	private Instant dataDiarioOficial;

	private Instant dataInicio;

	private Instant dataFim;

	private String observacaoCancelamento;

	private Long filialDestinoId;

	private Long situacaoId;
	
	private String tipoExoneracaoDemissao;
	
	private Instant dataRetorno;
	
	private Long situacaoFuncionalId;
	
	private Long situacaoFuncionalAnteriorId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getTipoSituacaoFuncional() {
		return tipoSituacaoFuncional;
	}

	public void setTipoSituacaoFuncional(String tipoSituacaoFuncional) {
		this.tipoSituacaoFuncional = tipoSituacaoFuncional;
	}

	public Long getFuncaoId() {
		return funcaoId;
	}

	public void setFuncaoId(Long funcaoId) {
		this.funcaoId = funcaoId;
	}

	public Long getNivelSalarialId() {
		return nivelSalarialId;
	}

	public void setNivelSalarialId(Long nivelSalarialId) {
		this.nivelSalarialId = nivelSalarialId;
	}

	public Long getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(Long motivoId) {
		this.motivoId = motivoId;
	}

	public String getAto() {
		return ato;
	}

	public void setAto(String ato) {
		this.ato = ato;
	}

	public Instant getDataAto() {
		return dataAto;
	}

	public void setDataAto(Instant dataAto) {
		this.dataAto = dataAto;
	}

	public Instant getDataDiarioOficial() {
		return dataDiarioOficial;
	}

	public void setDataDiarioOficial(Instant dataDiarioOficial) {
		this.dataDiarioOficial = dataDiarioOficial;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataFim() {
		return dataFim;
	}

	public void setDataFim(Instant dataFim) {
		this.dataFim = dataFim;
	}

	public String getObservacaoCancelamento() {
		return observacaoCancelamento;
	}

	public void setObservacaoCancelamento(String observacaoCancelamento) {
		this.observacaoCancelamento = observacaoCancelamento;
	}

	public Long getFilialDestinoId() {
		return filialDestinoId;
	}

	public void setFilialDestinoId(Long filialDestinoId) {
		this.filialDestinoId = filialDestinoId;
	}

	public Long getSituacaoId() {
		return situacaoId;
	}

	public void setSituacaoId(Long situacaoId) {
		this.situacaoId = situacaoId;
	}

	public String getTipoExoneracaoDemissao() {
		return tipoExoneracaoDemissao;
	}

	public void setTipoExoneracaoDemissao(String tipoExoneracaoDemissao) {
		this.tipoExoneracaoDemissao = tipoExoneracaoDemissao;
	}

	public Instant getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Instant dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public Long getSituacaoFuncionalId() {
		return situacaoFuncionalId;
	}

	public void setSituacaoFuncionalId(Long situacaoFuncionalId) {
		this.situacaoFuncionalId = situacaoFuncionalId;
	}

	public Long getSituacaoFuncionalAnteriorId() {
		return situacaoFuncionalAnteriorId;
	}

	public void setSituacaoFuncionalAnteriorId(Long situacaoFuncionalAnteriorId) {
		this.situacaoFuncionalAnteriorId = situacaoFuncionalAnteriorId;
	}

	
}