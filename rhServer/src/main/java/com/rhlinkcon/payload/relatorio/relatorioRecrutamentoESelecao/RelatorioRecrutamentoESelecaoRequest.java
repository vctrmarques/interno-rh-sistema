package com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao;

import java.time.Instant;

import com.rhlinkcon.payload.PagenetedRequest;
import com.rhlinkcon.util.Utils;

public class RelatorioRecrutamentoESelecaoRequest {

	private Boolean tipoSintetico;

	private Boolean tipoAnalitico;

	private Boolean concluidoAntes;

	private Boolean concluidoNoLimite;

	private Boolean concluidoDepois;

	private Boolean todosProcessos;

	private Boolean tempoAtendimentoVaga;

	private Boolean vagasAbertas;

	private Boolean efetivosAposContratoExperiencia;

	private String processoOuTermo;

	private Instant dataInicio;

	private Instant dataFim;

	private PagenetedRequest query;

	public RelatorioRecrutamentoESelecaoRequest() {
	}

	public String getProcessoOuTermo() {
		return processoOuTermo;
	}

	public void setProcessoOuTermo(String processoOuTermo) {
		this.processoOuTermo = processoOuTermo;
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

	public PagenetedRequest getQuery() {
		return query;
	}

	public void setQuery(PagenetedRequest query) {
		this.query = query;
	}

	public Boolean getTipoSintetico() {
		return Utils.setBool(tipoSintetico);
	}

	public void setTipoSintetico(Boolean tipoSintetico) {
		this.tipoSintetico = tipoSintetico;
	}

	public Boolean getTipoAnalitico() {
		return Utils.setBool(tipoAnalitico);
	}

	public void setTipoAnalitico(Boolean tipoAnalitico) {
		this.tipoAnalitico = tipoAnalitico;
	}

	public Boolean getConcluidoAntes() {
		return Utils.setBool(concluidoAntes);
	}

	public void setConcluidoAntes(Boolean concluidoAntes) {
		this.concluidoAntes = concluidoAntes;
	}

	public Boolean getConcluidoNoLimite() {
		return Utils.setBool(concluidoNoLimite);
	}

	public void setConcluidoNoLimite(Boolean concluidoNoLimite) {
		this.concluidoNoLimite = concluidoNoLimite;
	}

	public Boolean getConcluidoDepois() {
		return Utils.setBool(concluidoDepois);
	}

	public void setConcluidoDepois(Boolean concluidoDepois) {
		this.concluidoDepois = concluidoDepois;
	}

	public Boolean getTodosProcessos() {
		return Utils.setBool(todosProcessos);
	}

	public void setTodosProcessos(Boolean todosProcessos) {
		this.todosProcessos = todosProcessos;
	}

	public Boolean getTempoAtendimentoVaga() {
		return Utils.setBool(tempoAtendimentoVaga);
	}

	public void setTempoAtendimentoVaga(Boolean tempoAtendimentoVaga) {
		this.tempoAtendimentoVaga = tempoAtendimentoVaga;
	}

	public Boolean getVagasAbertas() {
		return Utils.setBool(vagasAbertas);
	}

	public void setVagasAbertas(Boolean vagasAbertas) {
		this.vagasAbertas = vagasAbertas;
	}

	public Boolean getEfetivosAposContratoExperiencia() {
		return Utils.setBool(efetivosAposContratoExperiencia);
	}

	public void setEfetivosAposContratoExperiencia(Boolean efetivosAposContratoExperiencia) {
		this.efetivosAposContratoExperiencia = efetivosAposContratoExperiencia;
	}
}
