package com.rhlinkcon.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.rhlinkcon.model.AgendaMedica;
import com.rhlinkcon.model.AgendaMedicaData;
import com.rhlinkcon.model.EspecialidadeMedica;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaMedicaRelatorioDto {
	
	
	
	private AgendaMedicaDto agendaMedicaDto;
	
	//##################################################

	private Long id;

	private String nomeMedico;
	
	private String diaSemana;

	private LocalDate data;

	private List<AgendaMedicaData> agendaMedicaData;
	
	private List<String> nmEspecialidade = new ArrayList<String>();

	private List<AgendaMedicaRelatorioDto> agendaMedicaList;

	public AgendaMedicaRelatorioDto(AgendaMedica agendaMedica) {
		setId(agendaMedica.getId());
		setNomeMedico(agendaMedica.getMedico().getNome());
		for (EspecialidadeMedica especialidadeMedica : agendaMedica.getMedico().getEspecialidadesMedicas()) {
			nmEspecialidade.add(especialidadeMedica.getNome());
		}
		setAgendaMedicaList(litAgentaMedicaDto(agendaMedica));
	}

	public AgendaMedicaRelatorioDto() {}

	public List<AgendaMedicaRelatorioDto> litAgentaMedicaDto(AgendaMedica agendaMedica) {
		List<AgendaMedicaRelatorioDto> listaAgendaMedicaDto = new ArrayList<AgendaMedicaRelatorioDto>();
		TreeSet<LocalDate> dataSet = new TreeSet<LocalDate>();

		for (AgendaMedicaData agendaMedicaData : agendaMedica.getAgendaMedicaData()) {
			// Retira os valroes repetidos e ordena.
			dataSet.add(agendaMedicaData.getData());
		}

		for (LocalDate data : dataSet) {
			List<AgendaMedicaData> listaAgendaMedicaData = new ArrayList<>();
			AgendaMedicaRelatorioDto agendaMedicaRelatorioDto = new AgendaMedicaRelatorioDto();

			String diaSemana = getTraduzNomeSemana(data.getDayOfWeek().toString());

			montarAgendaMedicaData(listaAgendaMedicaData, agendaMedica.getAgendaMedicaData(), diaSemana);

			agendaMedicaRelatorioDto.setId(agendaMedica.getId());
			agendaMedicaRelatorioDto.setNomeMedico(agendaMedica.getMedico().getNome());
			for (EspecialidadeMedica especialidadeMedica : agendaMedica.getMedico().getEspecialidadesMedicas()) {
				agendaMedicaRelatorioDto.nmEspecialidade.add(especialidadeMedica.getNome());
			}
			agendaMedicaRelatorioDto.setDiaSemana(diaSemana);
			agendaMedicaRelatorioDto.setData(data);
			agendaMedicaRelatorioDto.setAgendaMedicaData(listaAgendaMedicaData);

			listaAgendaMedicaDto.add(agendaMedicaRelatorioDto);
		}

		return listaAgendaMedicaDto;
	}

	private void montarAgendaMedicaData(List<AgendaMedicaData> listaAgendaMedicaData, List<AgendaMedicaData> list,
			String diaSemana) {

		for (AgendaMedicaData agendaMedicaData : list) {
			AgendaMedicaData ag = new AgendaMedicaData();

			if (diaSemana.contentEquals(agendaMedicaData.getDiaSemana())) {
				ag.setId(agendaMedicaData.getId());
				ag.setDiaSemana(agendaMedicaData.getDiaSemana());
				ag.setHora(agendaMedicaData.getHorario().toString());
				ag.setData(agendaMedicaData.getData());
				listaAgendaMedicaData.add(ag);
			}
		}

	}

	private String getTraduzNomeSemana(String semana) {

		String semanaPortugues = null;

		if (semana.contentEquals("SUNDAY")) {
			semanaPortugues = "Domingo";
		} else if (semana.contentEquals("MONDAY")) {
			semanaPortugues = "segunda-feira";
		}
		if (semana.contentEquals("TUESDAY")) {
			semanaPortugues = "terça-feira";
		}
		if (semana.contentEquals("WEDNESDAY")) {
			semanaPortugues = "quarta-feira";
		}
		if (semana.contentEquals("THURSDAY")) {
			semanaPortugues = "quinta-feira";
		}
		if (semana.contentEquals("FRIDAY")) {
			semanaPortugues = "sexta-feira";
		}
		if (semana.contentEquals("SATURDAY")) {
			semanaPortugues = "sabado";
		}

		return semanaPortugues;
	}

}
