package com.rhlinkcon.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.frequencia.FrequenciaRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.DateUtils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Frequência")
@Table(name = "frequencia")
public class Frequencia extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;

	private LocalDate data;

	@Column(name = "horas_trabalhadas")
	private LocalDateTime horasTrabalhadas;

	@Column(name = "hora_extra")
	private LocalDateTime horaExtra;

	@Column(name = "entrada_um")
	private LocalDateTime entradaUm;

	@Column(name = "saida_um")
	private LocalDateTime saidaUm;

	@Column(name = "entrada_dois")
	private LocalDateTime entradaDois;

	@Column(name = "saida_dois")
	private LocalDateTime saidaDois;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "falta_id")
	private Falta falta;

	public Frequencia(FrequenciaRequest frequenciaRequest) {
		this.id = frequenciaRequest.getId();
		this.funcionario = new Funcionario(frequenciaRequest.getFuncionarioId());
		this.entradaUm = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getEntradaUm());
		this.saidaUm = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getSaidaUm());
		this.entradaDois = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getEntradaDois());
		this.saidaDois = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getSaidaDois());
		if (Objects.nonNull(frequenciaRequest.getFaltaId()))
			this.falta = new Falta(frequenciaRequest.getFaltaId());
	}

	public Frequencia() {
		super();
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalDateTime getHorasTrabalhadas() {
		return horasTrabalhadas;
	}

	public void setHorasTrabalhadas(LocalDateTime horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}

	public LocalDateTime getHoraExtra() {
		return horaExtra;
	}

	public void setHoraExtra(LocalDateTime horaExtra) {
		this.horaExtra = horaExtra;
	}

	public LocalDateTime getEntradaUm() {
		return entradaUm;
	}

	public void setEntradaUm(LocalDateTime entradaUm) {
		this.entradaUm = entradaUm;
	}

	public LocalDateTime getSaidaUm() {
		return saidaUm;
	}

	public void setSaidaUm(LocalDateTime saidaUm) {
		this.saidaUm = saidaUm;
	}

	public LocalDateTime getEntradaDois() {
		return entradaDois;
	}

	public void setEntradaDois(LocalDateTime entradaDois) {
		this.entradaDois = entradaDois;
	}

	public LocalDateTime getSaidaDois() {
		return saidaDois;
	}

	public void setSaidaDois(LocalDateTime saidaDois) {
		this.saidaDois = saidaDois;
	}

	public Falta getFalta() {
		return falta;
	}

	public void setFalta(Falta falta) {
		this.falta = falta;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
