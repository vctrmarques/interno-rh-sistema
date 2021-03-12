package com.rhlinkcon.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.funcionarioExercicio.FuncionarioExercicioRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Funcionário Exercício")
@Table(name = "funcionario_exercicio")
public class FuncionarioExercicio extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classificacao_ato_id")
	private ClassificacaoAto classificacaoAto;

	@NotNull
	@Column(name = "exercicio")
	private String exercicio;

	@NotNull
	@Column(name = "data_inicio")
	private Instant dataInicio;

	@NotNull
	@Column(name = "data_fim")
	private Instant dataFim;

	@NotNull
	@Column(name = "processo")
	private String processo;

	@NotNull
	@Column(name = "num_diario_oficial")
	private String numDiarioOficial;

	@NotNull
	@Column(name = "data_diario_oficial")
	private Instant dataDiarioOficial;

	@NotNull
	@Column(name = "numero_ato")
	private String numeroAto;

	@NotNull
	@Column(name = "ano_ato")
	private String anoAto;

	@NotNull
	@Column(name = "data_ato")
	private Instant dataAto;

	public FuncionarioExercicio() {

	}

	public FuncionarioExercicio(FuncionarioExercicioRequest funcionarioExercicioRequest) {

		if (Utils.checkStr(funcionarioExercicioRequest.getExercicio()))
			this.setExercicio(funcionarioExercicioRequest.getExercicio());

		this.setDataInicio(funcionarioExercicioRequest.getDataInicio());

		this.setDataFim(funcionarioExercicioRequest.getDataFim());

		if (Utils.checkStr(funcionarioExercicioRequest.getProcesso()))
			this.setProcesso(funcionarioExercicioRequest.getProcesso());

		if (Utils.checkStr(funcionarioExercicioRequest.getNumDiarioOficial()))
			this.setNumDiarioOficial(funcionarioExercicioRequest.getNumDiarioOficial());

		this.setDataDiarioOficial(funcionarioExercicioRequest.getDataDiarioOficial());

		if (Utils.checkStr(funcionarioExercicioRequest.getNumeroAto()))
			this.setNumeroAto(funcionarioExercicioRequest.getNumeroAto());

		if (Utils.checkStr(funcionarioExercicioRequest.getAnoAto()))
			this.setAnoAto(funcionarioExercicioRequest.getAnoAto());

		this.setDataAto(funcionarioExercicioRequest.getDataAto());

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

	public ClassificacaoAto getClassificacaoAto() {
		return classificacaoAto;
	}

	public void setClassificacaoAto(ClassificacaoAto classificacaoAto) {
		this.classificacaoAto = classificacaoAto;
	}

	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
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

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getNumDiarioOficial() {
		return numDiarioOficial;
	}

	public void setNumDiarioOficial(String numDiarioOficial) {
		this.numDiarioOficial = numDiarioOficial;
	}

	public Instant getDataDiarioOficial() {
		return dataDiarioOficial;
	}

	public void setDataDiarioOficial(Instant dataDiarioOficial) {
		this.dataDiarioOficial = dataDiarioOficial;
	}

	public String getNumeroAto() {
		return numeroAto;
	}

	public void setNumeroAto(String numeroAto) {
		this.numeroAto = numeroAto;
	}

	public String getAnoAto() {
		return anoAto;
	}

	public void setAnoAto(String anoAto) {
		this.anoAto = anoAto;
	}

	public Instant getDataAto() {
		return dataAto;
	}

	public void setDataAto(Instant dataAto) {
		this.dataAto = dataAto;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
