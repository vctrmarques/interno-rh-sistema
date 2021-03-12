package com.rhlinkcon.model;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.service.AgendaMedicaDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
//@EntityListeners(AuditListener.class)
//@AuditLabelClass(label = "Agenda Médica")
@Table(name = "agenda_medica")
@Getter
@Setter
public class AgendaMedica extends UserDateAudit {

	private static final long serialVersionUID = 3099548721253416330L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medico_id")
	private Medico medico;
	
	@NotNull
	@Column(name = "data_inicio")
	private LocalDate dataInicio;
	
	@NotNull
	@Column(name = "data_fim")
	private LocalDate dataFim;
	
	@NotNull
	@Column(name = "hora_inicial")
	private Time horaInicial;
	
	@NotNull
	@Column(name = "hora_final")
	private Time horaFinal;
	
	@NotNull
	private Integer intervalo;
	
	@NotNull
	private boolean semanal;
	
	@ElementCollection
	@OneToMany(mappedBy = "agendaMedica", orphanRemoval = true)
	private List<AgendaMedicaData> agendaMedicaData;
	
	@ManyToMany
	@JoinTable(name = "agenda_medica_especialidade_medica", joinColumns = {
			@JoinColumn(name = "agenda_medica_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "especialidade_medica_id", referencedColumnName = "id") })
	private List<EspecialidadeMedica> especialidadesMedicas;

	@Override
	public String getLabel() {
		return null;
	}
	
	public AgendaMedica() {}
	
	public AgendaMedica(Long id) {
		this.id = id;
	}
	
	public AgendaMedica(AgendaMedicaDto dto) {
		setId(dto.getId());
		setMedico(new Medico(dto.getMedicoId()));
		setDataInicio(dto.getDataInicial());
		setDataFim(dto.getDataFinal());
		setHoraInicial(dto.setHora(dto.getHoraInicial()));
		setHoraFinal(dto.setHora(dto.getHoraFinal()));
		setIntervalo(dto.getIntervalo());
		setSemanal(dto.isSemanal());
	}
	

}
