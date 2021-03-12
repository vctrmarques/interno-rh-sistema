package com.rhlinkcon.model;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.service.AgendaMedicaDataDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Agenda Médica Data")
@Table(name = "agenda_medica_data")
@Getter
@Setter
public class AgendaMedicaData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agenda_medica_id", nullable = false)
	private AgendaMedica agendaMedica;
	
	@NotNull
	@Column(name = "dia_semana")
	private String diaSemana;
	
	@NotNull
	@Column(name = "horario")
	private Time horario;
	
	@NotNull
	@Column(name = "data")
	private LocalDate data;
	
	@Transient
	private String hora;
	
	@Transient
	private boolean checkHorario;
	
	public AgendaMedicaData() {}
	
	public AgendaMedicaData(Long id) {
		this.id = id;
	}

	
	public AgendaMedicaData(AgendaMedicaData agendaMedicaData, Long id) {
		setId(agendaMedicaData.getId());
		setData(agendaMedicaData.getData());
		setHorario(agendaMedicaData.getHorario());
		setDiaSemana(agendaMedicaData.getDiaSemana());
		setAgendaMedica(new AgendaMedica(id));
		
	}
	
	public AgendaMedicaData(AgendaMedicaDataDto dto, Long id) {
		setId(dto.getId());
		setData(dto.getData());
		setHorario(setHoraStringTime(dto.getHorario()));
		setDiaSemana(dto.getDiaSemana());
		setAgendaMedica(new AgendaMedica(id));
		
	}
	
	private Time setHoraStringTime(String horario) {
		DateFormat formato = new SimpleDateFormat("HH:mm");
		Time hora;
		try {
			hora = new Time(formato.parse(horario).getTime());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return hora;
	}
	
}
