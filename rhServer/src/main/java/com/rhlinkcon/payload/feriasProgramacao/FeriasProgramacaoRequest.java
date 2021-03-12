package com.rhlinkcon.payload.feriasProgramacao;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.FeriasProgramacaoSituacaoEnum;
import com.rhlinkcon.model.FeriasProgramacaoTipoFeriasEnum;
import com.rhlinkcon.model.FeriasProgramacaoTipoProcessamentoEnum;

public class FeriasProgramacaoRequest {

	@NotNull
	private FeriasProgramacaoSituacaoEnum situacao;

	@NotNull
	private Long funcionarioId;

	@NotNull
	private Date exercicioInicio;

	@NotNull
	private Date exercicioFim;

	@NotNull
	private Date dataLimite;

	@NotNull
	private String quantFaltas;

	@NotNull
	private FeriasProgramacaoTipoFeriasEnum tipoFerias;

	@NotNull
	private String quantDias;

	private Boolean abonoPeculiario;

	private Boolean decimoTerceiro;

	private Long id;

	private String mesCompetenciaParticaoUm;

	private String diasAGozarUm;

	private FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoUm;

	private Date dataInicialUm;

	private Date dataFinalUm;

	private FeriasProgramacaoSituacaoEnum situacaoUm;

	private String mesCompetenciaParticaoDois;

	private String diasAGozarDois;

	private FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoDois;

	private Date dataInicialDois;

	private Date dataFinalDois;

	private FeriasProgramacaoSituacaoEnum situacaoDois;

	private String mesCompetenciaParticaoTres;

	private String diasAGozarTres;

	private FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoTres;

	private Date dataInicialTres;

	private Date dataFinalTres;

	private FeriasProgramacaoSituacaoEnum situacaoTres;

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

	public Date getExercicioInicio() {
		return exercicioInicio;
	}

	public void setExercicioInicio(Date exercicioInicio) {
		this.exercicioInicio = exercicioInicio;
	}

	public Date getExercicioFim() {
		return exercicioFim;
	}

	public void setExercicioFim(Date exercicioFim) {
		this.exercicioFim = exercicioFim;
	}

	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}

	public String getQuantFaltas() {
		return quantFaltas;
	}

	public void setQuantFaltas(String quantFaltas) {
		this.quantFaltas = quantFaltas;
	}

	public FeriasProgramacaoTipoFeriasEnum getTipoFerias() {
		return tipoFerias;
	}

	public void setTipoFerias(FeriasProgramacaoTipoFeriasEnum tipoFerias) {
		this.tipoFerias = tipoFerias;
	}

	public String getQuantDias() {
		return quantDias;
	}

	public void setQuantDias(String quantDias) {
		this.quantDias = quantDias;
	}

	public Boolean getAbonoPeculiario() {
		return abonoPeculiario;
	}

	public void setAbonoPeculiario(Boolean abonoPeculiario) {
		this.abonoPeculiario = abonoPeculiario;
	}

	public Boolean getDecimoTerceiro() {
		return decimoTerceiro;
	}

	public void setDecimoTerceiro(Boolean decimoTerceiro) {
		this.decimoTerceiro = decimoTerceiro;
	}

	public FeriasProgramacaoSituacaoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(FeriasProgramacaoSituacaoEnum situacao) {
		this.situacao = situacao;
	}

	public String getMesCompetenciaParticaoUm() {
		return mesCompetenciaParticaoUm;
	}

	public void setMesCompetenciaParticaoUm(String mesCompetenciaParticaoUm) {
		this.mesCompetenciaParticaoUm = mesCompetenciaParticaoUm;
	}

	public String getDiasAGozarUm() {
		return diasAGozarUm;
	}

	public void setDiasAGozarUm(String diasAGozarUm) {
		this.diasAGozarUm = diasAGozarUm;
	}

	public FeriasProgramacaoTipoProcessamentoEnum getTipoProcessamentoUm() {
		return tipoProcessamentoUm;
	}

	public void setTipoProcessamentoUm(FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoUm) {
		this.tipoProcessamentoUm = tipoProcessamentoUm;
	}

	public Date getDataInicialUm() {
		return dataInicialUm;
	}

	public void setDataInicialUm(Date dataInicialUm) {
		this.dataInicialUm = dataInicialUm;
	}

	public Date getDataFinalUm() {
		return dataFinalUm;
	}

	public void setDataFinalUm(Date dataFinalUm) {
		this.dataFinalUm = dataFinalUm;
	}

	public FeriasProgramacaoSituacaoEnum getSituacaoUm() {
		return situacaoUm;
	}

	public void setSituacaoUm(FeriasProgramacaoSituacaoEnum situacaoUm) {
		this.situacaoUm = situacaoUm;
	}

	public String getMesCompetenciaParticaoDois() {
		return mesCompetenciaParticaoDois;
	}

	public void setMesCompetenciaParticaoDois(String mesCompetenciaParticaoDois) {
		this.mesCompetenciaParticaoDois = mesCompetenciaParticaoDois;
	}

	public String getDiasAGozarDois() {
		return diasAGozarDois;
	}

	public void setDiasAGozarDois(String diasAGozarDois) {
		this.diasAGozarDois = diasAGozarDois;
	}

	public FeriasProgramacaoTipoProcessamentoEnum getTipoProcessamentoDois() {
		return tipoProcessamentoDois;
	}

	public void setTipoProcessamentoDois(FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoDois) {
		this.tipoProcessamentoDois = tipoProcessamentoDois;
	}

	public Date getDataInicialDois() {
		return dataInicialDois;
	}

	public void setDataInicialDois(Date dataInicialDois) {
		this.dataInicialDois = dataInicialDois;
	}

	public Date getDataFinalDois() {
		return dataFinalDois;
	}

	public void setDataFinalDois(Date dataFinalDois) {
		this.dataFinalDois = dataFinalDois;
	}

	public FeriasProgramacaoSituacaoEnum getSituacaoDois() {
		return situacaoDois;
	}

	public void setSituacaoDois(FeriasProgramacaoSituacaoEnum situacaoDois) {
		this.situacaoDois = situacaoDois;
	}

	public String getMesCompetenciaParticaoTres() {
		return mesCompetenciaParticaoTres;
	}

	public void setMesCompetenciaParticaoTres(String mesCompetenciaParticaoTres) {
		this.mesCompetenciaParticaoTres = mesCompetenciaParticaoTres;
	}

	public String getDiasAGozarTres() {
		return diasAGozarTres;
	}

	public void setDiasAGozarTres(String diasAGozarTres) {
		this.diasAGozarTres = diasAGozarTres;
	}

	public FeriasProgramacaoTipoProcessamentoEnum getTipoProcessamentoTres() {
		return tipoProcessamentoTres;
	}

	public void setTipoProcessamentoTres(FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoTres) {
		this.tipoProcessamentoTres = tipoProcessamentoTres;
	}

	public Date getDataInicialTres() {
		return dataInicialTres;
	}

	public void setDataInicialTres(Date dataInicialTres) {
		this.dataInicialTres = dataInicialTres;
	}

	public Date getDataFinalTres() {
		return dataFinalTres;
	}

	public void setDataFinalTres(Date dataFinalTres) {
		this.dataFinalTres = dataFinalTres;
	}

	public FeriasProgramacaoSituacaoEnum getSituacaoTres() {
		return situacaoTres;
	}

	public void setSituacaoTres(FeriasProgramacaoSituacaoEnum situacaoTres) {
		this.situacaoTres = situacaoTres;
	}

}