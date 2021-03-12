package com.rhlinkcon.model;

import java.time.LocalDate;
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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.controller.ConsultaPericiaMedicaDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Consulta Perícia Médica")
@Table(name = "consulta_pericia_medica")
public class ConsultaPericiaMedica  extends UserDateAudit {

	private static final long serialVersionUID = -7163976330234501815L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_pericia_medica_id")
	private PacientePericiaMedica pacientePericiaMedica;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agenda_medica_data_id")
	private AgendaMedicaData agendaMedicaData;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "especialidade_medica_id")
	private EspecialidadeMedica especialidadeMedica;
	
	private boolean compareceu;
	
	@Column(name = "observacao_medico")
	private String observacaoMedica;
	
	@Column(name = "observacao_assistente")
	private String observacaoAssitente;
	
	@NotNull
	@Column(name = "tipo_pericia")
	private String tipoPericia;
	
	@Column(name = "tipo_proxima_pericia")
	private String tipoProximaPericia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "especialidade_medica_proxima_pericia_id")
	private EspecialidadeMedica especialidadeMedicaProximaConsulta;	
	
	@Column(name = "data_proximo_agendamento_pericia_medica")
	private LocalDate dataProximoAgendamento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medico_id")
	private Medico medico;
	
	public ConsultaPericiaMedica() {}
	
	public ConsultaPericiaMedica(Long id) {
		this.id = id;
	}
	//contrustor para inserir na base de dados.
	public ConsultaPericiaMedica(ConsultaPericiaMedicaDto dto) {
		setId(dto.getId());
		setPacientePericiaMedica(new PacientePericiaMedica(dto.getPacientePericiaMedicaId()));
		setAgendaMedicaData(new AgendaMedicaData(dto.getAgendaMedicaDataId()));
		setEspecialidadeMedica(new EspecialidadeMedica(dto.getEspecialidadeMedicaId()));
		setObservacaoMedica(dto.getObservacaoMedico());
		setObservacaoAssitente(dto.getObservacaoAssistente());
		setCompareceu(true);
		setTipoPericia(dto.getTipoPericia());
		if (Objects.nonNull(dto.getTipoProximaPericia())) {
			setTipoProximaPericia(dto.getTipoProximaPericia());
		}
		if (Objects.nonNull(dto.getEspecialidadeMedicaProximaConsultaId())) {
			setEspecialidadeMedicaProximaConsulta(new EspecialidadeMedica(dto.getEspecialidadeMedicaProximaConsultaId()));
		}
		if (Objects.nonNull(dto.getDataProximaConsulta())) {
			setDataProximoAgendamento(dto.getDataProximaConsulta());
		}
	}

	@Override
	public String getLabel() {
		return null;
	}

}
