package com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.rhlinkcon.model.RequisicaoPessoal;

public class RelatorioRecrutamentoESelecaoResponse {
	
	private boolean tipoSintetico;

	private Instant dataInicio;
	private Instant dataFim;

	private Long totalConcluidosAntes;
	private Long totalConcluidosNoLimite;
	private Long totalConcluidosApos;
	private Long totalAte10diasDataLimite;
	private Long totalAlem10diasDataLimite;

	private Long totalProcessos;
	private Long mediaDiasTempoAtendimento;
	private Long totalVagasAbertas;
	private Long totalEfetivadosAposContratoExperiencia;

	private String statusTempoAtendimento;

	private List<RequisicaoPessoal> listaRequisicaoPessoal;

	public RelatorioRecrutamentoESelecaoResponse() {
		this.listaRequisicaoPessoal = new ArrayList<>();
		this.dataInicio = Instant.now();
		this.dataFim = Instant.now();
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

	public String getStatusTempoAtendimento() {
		return statusTempoAtendimento;
	}

	public void setStatusTempoAtendimento(String statusTempoAtendimento) {
		this.statusTempoAtendimento = statusTempoAtendimento;
	}

	public Long getTotalConcluidosAntes() {
		return totalConcluidosAntes;
	}

	public void setTotalConcluidosAntes(Long totalConcluidosAntes) {
		this.totalConcluidosAntes = totalConcluidosAntes;
	}

	public Long getTotalConcluidosNoLimite() {
		return totalConcluidosNoLimite;
	}

	public void setTotalConcluidosNoLimite(Long totalConcluidosNoLimite) {
		this.totalConcluidosNoLimite = totalConcluidosNoLimite;
	}

	public Long getTotalAte10diasDataLimite() {
		return totalAte10diasDataLimite;
	}

	public void setTotalAte10diasDataLimite(Long totalAte10diasDataLimite) {
		this.totalAte10diasDataLimite = totalAte10diasDataLimite;
	}

	public Long getTotalAlem10diasDataLimite() {
		return totalAlem10diasDataLimite;
	}

	public void setTotalAlem10diasDataLimite(Long totalAlem10diasDataLimite) {
		this.totalAlem10diasDataLimite = totalAlem10diasDataLimite;
	}

	public Long getTotalConcluidosApos() {
		return totalConcluidosApos;
	}

	public void setTotalConcluidosApos(Long totalConcluidosApos) {
		this.totalConcluidosApos = totalConcluidosApos;
	}

	public Long getTotalProcessos() {
		return totalProcessos;
	}

	public void setTotalProcessos(Long totalProcessos) {
		this.totalProcessos = totalProcessos;
	}

	public Long getMediaDiasTempoAtendimento() {
		return mediaDiasTempoAtendimento;
	}

	public void setMediaDiasTempoAtendimento(Long mediaDiasTempoAtendimento) {
		this.mediaDiasTempoAtendimento = mediaDiasTempoAtendimento;
	}

	public Long getTotalVagasAbertas() {
		return totalVagasAbertas;
	}

	public void setTotalVagasAbertas(Long totalVagasAbertas) {
		this.totalVagasAbertas = totalVagasAbertas;
	}

	public Long getTotalEfetivadosAposContratoExperiencia() {
		return totalEfetivadosAposContratoExperiencia;
	}

	public void setTotalEfetivadosAposContratoExperiencia(Long totalEfetivadosAposContratoExperiencia) {
		this.totalEfetivadosAposContratoExperiencia = totalEfetivadosAposContratoExperiencia;
	}

	public List<RequisicaoPessoal> getListaRequisicaoPessoal() {
		return listaRequisicaoPessoal;
	}

	public void setListaRequisicaoPessoal(List<RequisicaoPessoal> listaRequisicaoPessoal) {
		this.listaRequisicaoPessoal = listaRequisicaoPessoal;
	}

	public boolean isTipoSintetico() {
		return tipoSintetico;
	}

	public void setTipoSintetico(boolean tipoSintetico) {
		this.tipoSintetico = tipoSintetico;
	}

}
