package com.rhlinkcon.service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.rhlinkcon.model.AgendaMedica;
import com.rhlinkcon.model.AgendaMedicaData;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.medico.MedicoDto;
import com.rhlinkcon.util.Projecao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaMedicaDto extends DadoCadastralResponse {

	private Long id;

	private LocalDate dataInicial;

	private LocalDate dataFinal;

	private String horaInicial;

	private String horaFinal;

	private Integer intervalo;

	private long medicoId;

	private MedicoDto medico;

	private boolean semanal;

	private boolean domingo;
	private boolean segunda;
	private boolean terca;
	private boolean quarta;
	private boolean quinta;
	private boolean sexta;
	private boolean sabado;

	private List<Long> especialidadeMedicaId;

	private List<AgendaMedicaDataDto> agendaMedicaDataDto;
	
	
	private String diaSemana;

	private LocalDate data;
	
	private String agendamento;
	
	private boolean checkeAll;
	
	private boolean checkeAllClean;
	
	private List<EspecialidadeMedica> especialidadesMedicas;
	
	private List<AgendaMedicaData> agendaMedicaData;
	
	private List<AgendaMedicaDto> agendaMedicaList;
	
	public Time setHora(String horario) {
		DateFormat formato = new SimpleDateFormat("HH:mm");
		Time hora;
		try {
			hora = new Time(formato.parse(horario).getTime());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return hora;
	}

	public AgendaMedicaDto(){}

	public AgendaMedicaDto(Long id){
		setId(id);
	}

	public AgendaMedicaDto(AgendaMedica agendaMedica){
		setId(agendaMedica.getId());
		setMedico(new MedicoDto(agendaMedica.getMedico(), Projecao.MEDIA));
		setDataInicial(agendaMedica.getDataInicio());
		setDataFinal(agendaMedica.getDataFim());
		setHoraInicial(agendaMedica.getHoraInicial().toString());
		setHoraFinal(agendaMedica.getHoraFinal().toString());
		setIntervalo(agendaMedica.getIntervalo());
		setSemanal(agendaMedica.isSemanal());
		setAgendaMedicaList(litAgentaMedicaDto(agendaMedica));

		especialidadeMedicaId = new ArrayList<Long>();
		for(EspecialidadeMedica em : agendaMedica.getEspecialidadesMedicas()) {
			especialidadeMedicaId.add(em.getId());
		} 

		Set<String> diaSemanaSet = new HashSet<String>();
		if(agendaMedica.isSemanal()) {
			for (AgendaMedicaData agendaMedicaData : agendaMedica.getAgendaMedicaData()) {
				diaSemanaSet.add(agendaMedicaData.getDiaSemana());
			}

			for (String dia : diaSemanaSet) {

				if(dia.contentEquals("Domingo")) {
					domingo = true;
					continue;
				}else if(dia.contentEquals("segunda-feira")) {
					segunda = true;
					continue;
				}if(dia.contentEquals("terça-feira")) {
					terca = true;
					continue;
				}if(dia.contentEquals("quarta-feira")) {
					quarta = true;
					continue;
				}if(dia.contentEquals("quinta-feira")) {
					quinta = true;
					continue;
				}if(dia.contentEquals("sexta-feira")) {
					sexta = true;
					continue;
				}if(dia.contentEquals("sabado")) {
					sabado = true;


				}
			}
		}
	}
	
	private List<AgendaMedicaDto> litAgentaMedicaDto(AgendaMedica  agendaMedica) {
		List<AgendaMedicaDto> listaAgendaMedicaDto = new ArrayList<>();
		TreeSet<LocalDate> dataSet = new TreeSet<LocalDate>();

		for (AgendaMedicaData agendaMedicaData : agendaMedica.getAgendaMedicaData()) {
			//Retira os valroes repetidos e ordena.
			dataSet.add(agendaMedicaData.getData());
		}
		
		for (LocalDate data : dataSet) {
			List<AgendaMedicaData> listaAgendaMedicaData = new ArrayList<>();
			AgendaMedicaDto agendaMedicaDto = new AgendaMedicaDto();
			

			String diaSemana = getTraduzNomeSemana(data.getDayOfWeek().toString());
			
			montarAgendaMedicaData(listaAgendaMedicaData, agendaMedica.getAgendaMedicaData(), diaSemana, data);

			agendaMedicaDto.setDiaSemana(diaSemana);
			agendaMedicaDto.setData(data);
			agendaMedicaDto.setSemanal(semanal);
			agendaMedicaDto.setAgendaMedicaData(listaAgendaMedicaData);
			agendaMedicaDto.setCheckeAll(true);
			agendaMedicaDto.setCheckeAllClean(true);

			listaAgendaMedicaDto.add(agendaMedicaDto);
		}

		return listaAgendaMedicaDto;
	}

	private void montarAgendaMedicaData(List<AgendaMedicaData> listaAgendaMedicaData,List<AgendaMedicaData> list, String diaSemana, LocalDate data) {
		
		for (AgendaMedicaData agendaMedicaData : list) {
			AgendaMedicaData ag = new AgendaMedicaData();
			
			
			if(diaSemana.contentEquals(agendaMedicaData.getDiaSemana()) &&  agendaMedicaData.getData().isEqual(data)) {
				
				ag.setId(agendaMedicaData.getId());
				ag.setDiaSemana(agendaMedicaData.getDiaSemana());
				ag.setHora(agendaMedicaData.getHorario().toString());
				ag.setData(agendaMedicaData.getData());
				ag.setCheckHorario(true);
				listaAgendaMedicaData.add(ag);
			}
		}
		
	}	

	private String getTraduzNomeSemana(String semana) {

		String semanaPortugues = null;

		if(semana.contentEquals("SUNDAY")) {
			semanaPortugues = "Domingo";
		}else if(semana.contentEquals("MONDAY")) {
			semanaPortugues = "segunda-feira";
		}if(semana.contentEquals("TUESDAY")) {
			semanaPortugues = "terça-feira";
		}if(semana.contentEquals("WEDNESDAY")) {
			semanaPortugues = "quarta-feira";
		}if(semana.contentEquals("THURSDAY")) {
			semanaPortugues = "quinta-feira";
		}if(semana.contentEquals("FRIDAY")) {
			semanaPortugues = "sexta-feira";
		}if(semana.contentEquals("SATURDAY")) {
			semanaPortugues = "sabado";
		}

		return semanaPortugues;
	}
}
