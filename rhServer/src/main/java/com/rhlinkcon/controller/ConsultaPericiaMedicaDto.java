package com.rhlinkcon.controller;

import java.time.LocalDate;
import java.util.Objects;

import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.service.PacientePericiaMedicaDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaPericiaMedicaDto extends DadoCadastralResponse {

	private Long id;
	
	private Long pacientePericiaMedicaId;
	
	private Long especialidadeMedicaId;
	
	private Long agendaMedicaDataId;
	
	private LocalDate dataConsulta;
	
	private boolean compareceu;
	
	private String observacaoMedico;
	
	private String observacaoAssistente;
	
	private String nomeEspecialidadeMedica;
	
	private String horario;
	
	private String nomeMedico;
	
	private String tipoPericia;
	
	private String tipoProximaPericia;
	
	private Long especialidadeMedicaProximaConsultaId;
	
	private LocalDate dataProximaConsulta;
	
	PacientePericiaMedicaDto pacientePericiaMedicaDto;
	
	public ConsultaPericiaMedicaDto() {}
	
	public ConsultaPericiaMedicaDto(ConsultaPericiaMedica consultaPericiaMedica) {
		setId(consultaPericiaMedica.getId());
		setHorario(consultaPericiaMedica.getAgendaMedicaData().getHorario().toString());
		setDataConsulta(consultaPericiaMedica.getAgendaMedicaData().getData());
		setEspecialidadeMedicaId(consultaPericiaMedica.getEspecialidadeMedica().getId());
		setNomeEspecialidadeMedica(consultaPericiaMedica.getEspecialidadeMedica().getNome());
		setNomeMedico("");
		setCompareceu(consultaPericiaMedica.isCompareceu());
		setObservacaoMedico(consultaPericiaMedica.getObservacaoMedica());
		setTipoPericia(consultaPericiaMedica.getTipoPericia());
		setTipoProximaPericia(consultaPericiaMedica.getTipoProximaPericia());
		if(Objects.nonNull(consultaPericiaMedica.getEspecialidadeMedicaProximaConsulta())){
			setEspecialidadeMedicaProximaConsultaId(consultaPericiaMedica.getEspecialidadeMedicaProximaConsulta().getId());
		}
		if(Objects.nonNull(consultaPericiaMedica.getDataProximoAgendamento())){
			setDataProximaConsulta(consultaPericiaMedica.getDataProximoAgendamento());
		}
	}
	
}
