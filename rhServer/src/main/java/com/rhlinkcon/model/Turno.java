package com.rhlinkcon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.turno.TurnoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Turno")
@Table(name = "turno")
public class Turno extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8770252859250324943L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "codigo", unique = true)
	private String codigo;

	@NotNull
	@Column(name = "data_inicio")
	private Date dataInicio;

	@NotNull
	@Column(name = "data_fim")
	private Date dataFim;

	@Column(name = "horario_flexivel")
	private boolean horarioFlexivel;

	@Column(name = "horario_flexivel_inicio")
	private Date horarioFlexivelInicio;

	@Column(name = "horario_flexivel_fim")
	private Date horarioFlexivelFim;

	@Column(name = "jornada")
	private Date jornada;

	@Column(name = "intervalo_flexivel")
	private boolean intervaloFlexivel;

	@Column(name = "intervalo")
	private Date intervalo;

	@Column(name = "intervalo_flexivel_inicio")
	private Date intervaloFlexivelInicio;

	@Column(name = "intervalo_flexivel_fim")
	private Date intervaloFlexivelFim;

	public Turno(Long id) {
		this.id = id;
	}

	public Turno() {
	}

	public Turno(TurnoRequest turnoRequest) {
		this.setCodigo(turnoRequest.getCodigo());
		this.setDataInicio(turnoRequest.getDataInicio());
		this.setDataFim(turnoRequest.getDataFim());
		this.setHorarioFlexivel(Utils.setBool(turnoRequest.isHorarioFlexivel()));
		this.setJornada(turnoRequest.getJornada());
		this.setHorarioFlexivelInicio(turnoRequest.getHorarioFlexivelInicio());
		this.setHorarioFlexivelFim(turnoRequest.getHorarioFlexivelFim());
		this.setIntervaloFlexivel(Utils.setBool(turnoRequest.isIntervaloFlexivel()));
		this.setIntervalo(turnoRequest.getIntervalo());
		this.setIntervaloFlexivelInicio(turnoRequest.getIntervaloFlexivelInicio());
		this.setIntervaloFlexivelFim(turnoRequest.getIntervaloFlexivelFim());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public boolean isHorarioFlexivel() {
		return horarioFlexivel;
	}

	public void setHorarioFlexivel(boolean horarioFlexivel) {
		this.horarioFlexivel = horarioFlexivel;
	}

	public Date getHorarioFlexivelInicio() {
		return horarioFlexivelInicio;
	}

	public void setHorarioFlexivelInicio(Date horarioFlexivelInicio) {
		this.horarioFlexivelInicio = horarioFlexivelInicio;
	}

	public Date getHorarioFlexivelFim() {
		return horarioFlexivelFim;
	}

	public void setHorarioFlexivelFim(Date horarioFlexivelFim) {
		this.horarioFlexivelFim = horarioFlexivelFim;
	}

	public Date getJornada() {
		return jornada;
	}

	public void setJornada(Date jornada) {
		this.jornada = jornada;
	}

	public boolean isIntervaloFlexivel() {
		return intervaloFlexivel;
	}

	public void setIntervaloFlexivel(boolean intervaloFlexivel) {
		this.intervaloFlexivel = intervaloFlexivel;
	}

	public Date getIntervaloFlexivelInicio() {
		return intervaloFlexivelInicio;
	}

	public void setIntervaloFlexivelInicio(Date intervaloFlexivelInicio) {
		this.intervaloFlexivelInicio = intervaloFlexivelInicio;
	}

	public Date getIntervaloFlexivelFim() {
		return intervaloFlexivelFim;
	}

	public void setIntervaloFlexivelFim(Date intervaloFlexivelFim) {
		this.intervaloFlexivelFim = intervaloFlexivelFim;
	}

	public Date getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Date intervalo) {
		this.intervalo = intervalo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
