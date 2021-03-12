package com.rhlinkcon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Programação de Férias")
@Table(name = "ferias_programacao")
public class FeriasProgramacao extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@NotNull
	@Column(name = "exercicio_inicio")
	private Date exercicioInicio;

	@NotNull
	@Column(name = "exercicio_fim")
	private Date exercicioFim;

	@NotNull
	@Column(name = "data_limite")
	private Date dataLimite;

	@Column(name = "quant_faltas")
	private String quantFaltas;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_ferias")
	private FeriasProgramacaoTipoFeriasEnum tipoFerias;

	@NotNull
	@Column(name = "quant_dias")
	private String quantDias;

	@Column(name = "abono_peculiario")
	private Boolean abonoPeculiario;

	@Column(name = "decimo_terceiro")
	private Boolean decimoTerceiro;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private FeriasProgramacaoSituacaoEnum situacao;

	@Column(name = "mes_competencia_particao_um")
	private String mesCompetenciaParticaoUm;

	@Column(name = "dias_a_gozar_um")
	private String diasAGozarUm;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_processamento_um")
	private FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoUm;

	@Column(name = "data_inicial_um")
	private Date dataInicialUm;

	@Column(name = "data_final_um")
	private Date dataFinalUm;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao_um")
	private FeriasProgramacaoSituacaoEnum situacaoUm;

	@Column(name = "mes_competencia_particao_dois")
	private String mesCompetenciaParticaoDois;

	@Column(name = "dias_a_gozar_dois")
	private String diasAGozarDois;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_processamento_dois")
	private FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoDois;

	@Column(name = "data_inicial_dois")
	private Date dataInicialDois;

	@Column(name = "data_final_dois")
	private Date dataFinalDois;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao_dois")
	private FeriasProgramacaoSituacaoEnum situacaoDois;

	@Column(name = "mes_competencia_particao_tres")
	private String mesCompetenciaParticaoTres;

	@Column(name = "dias_a_gozar_tres")
	private String diasAGozarTres;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_processamento_tres")
	private FeriasProgramacaoTipoProcessamentoEnum tipoProcessamentoTres;

	@Column(name = "data_inicial_tres")
	private Date dataInicialTres;

	@Column(name = "data_final_tres")
	private Date dataFinalTres;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao_tres")
	private FeriasProgramacaoSituacaoEnum situacaoTres;

	@Column(name = "motivo")
	private String motivo;

	public FeriasProgramacao() {

	}

	public FeriasProgramacao(FeriasProgramacaoRequest feriasProgramacaoRequest) {

		this.setExercicioInicio(feriasProgramacaoRequest.getExercicioInicio());
		this.setExercicioFim(feriasProgramacaoRequest.getExercicioFim());
		this.setDataLimite(feriasProgramacaoRequest.getDataLimite());
		this.setQuantFaltas(feriasProgramacaoRequest.getQuantFaltas());
		this.setTipoFerias(feriasProgramacaoRequest.getTipoFerias());
		this.setQuantDias(feriasProgramacaoRequest.getQuantDias());
		this.setAbonoPeculiario(feriasProgramacaoRequest.getAbonoPeculiario());
		this.setDecimoTerceiro(feriasProgramacaoRequest.getDecimoTerceiro());
		this.setSituacao(feriasProgramacaoRequest.getSituacao());

		this.setMesCompetenciaParticaoUm(feriasProgramacaoRequest.getMesCompetenciaParticaoUm());
		this.setDiasAGozarUm(feriasProgramacaoRequest.getDiasAGozarUm());
		this.setTipoProcessamentoUm(feriasProgramacaoRequest.getTipoProcessamentoUm());
		this.setDataInicialUm(feriasProgramacaoRequest.getDataInicialUm());
		this.setDataFinalUm(feriasProgramacaoRequest.getDataFinalUm());
		this.setSituacaoUm(feriasProgramacaoRequest.getSituacaoUm());

		this.setMesCompetenciaParticaoDois(feriasProgramacaoRequest.getMesCompetenciaParticaoDois());
		this.setDiasAGozarDois(feriasProgramacaoRequest.getDiasAGozarDois());
		this.setTipoProcessamentoDois(feriasProgramacaoRequest.getTipoProcessamentoDois());
		this.setDataInicialDois(feriasProgramacaoRequest.getDataInicialDois());
		this.setDataFinalDois(feriasProgramacaoRequest.getDataFinalDois());
		this.setSituacaoDois(feriasProgramacaoRequest.getSituacaoDois());

		this.setMesCompetenciaParticaoTres(feriasProgramacaoRequest.getMesCompetenciaParticaoTres());
		this.setDiasAGozarTres(feriasProgramacaoRequest.getDiasAGozarTres());
		this.setTipoProcessamentoTres(feriasProgramacaoRequest.getTipoProcessamentoTres());
		this.setDataInicialTres(feriasProgramacaoRequest.getDataInicialTres());
		this.setDataFinalTres(feriasProgramacaoRequest.getDataFinalTres());
		this.setSituacaoTres(feriasProgramacaoRequest.getSituacaoTres());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
