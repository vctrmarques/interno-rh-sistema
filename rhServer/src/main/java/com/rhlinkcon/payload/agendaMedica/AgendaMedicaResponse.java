package com.rhlinkcon.payload.agendaMedica;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.AgendaMedica;
import com.rhlinkcon.model.AgendaMedicaData;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.payload.especialidadeMedica.EspecialidadeMedicaDto;
import com.rhlinkcon.payload.medico.MedicoDto;
import com.rhlinkcon.util.Projecao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgendaMedicaResponse {
	
	private Long id;
	
	private String diaSemana;

	private LocalDate data;
	
	private boolean semanal;
	
	private String agendamento;
	
	private boolean checkeAll;
	
	private boolean checkeAllClean;
	
	private MedicoDto medico;
	
	private String periodicidade;
	
	private String tooltip;
	
	private List<EspecialidadeMedica> especialidadesMedicas;
	
	private List<AgendaMedicaData> agendaMedicaData;
	
	public AgendaMedicaResponse() {}
	
	public AgendaMedicaResponse(AgendaMedica agendaMedica) {
		setMedico(new MedicoDto(agendaMedica.getMedico(),Projecao.MEDIA));
		setEspecialidadesMedicas(agendaMedica.getEspecialidadesMedicas());
		setAgendaMedicaData(agendaMedica.getAgendaMedicaData());
	}
	
	

}
