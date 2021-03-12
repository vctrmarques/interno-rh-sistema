package com.rhlinkcon.service;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.AgendaMedicaDataFiltro;
import com.rhlinkcon.filtro.AgendaMedicaFiltro;
import com.rhlinkcon.model.AgendaMedica;
import com.rhlinkcon.model.AgendaMedicaData;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.agendaMedica.AgendaMedicaResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.medico.MedicoDto;
import com.rhlinkcon.repository.agendaMedica.AgendaMedicaDataRepository;
import com.rhlinkcon.repository.agendaMedica.AgendaMedicaRepository;
import com.rhlinkcon.repository.medico.MedicoRepository;
import com.rhlinkcon.util.Utils;

@Service
public class AgendaMedicaService extends GenericService<AgendaMedicaDto, Long> {

	@Autowired
	private AgendaMedicaRepository repository;

	@Autowired
	private AgendaMedicaDataRepository agendaMedicaDataRepository;

	@Autowired
	private AgendaMedicaDataRepository dataRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	@Transactional
	public void create(AgendaMedicaDto dto) {

		AgendaMedica agendaMedica = new AgendaMedica(dto);
		setEntidade(agendaMedica, dto);

		repository.save(agendaMedica);

		if (Utils.checkList(dto.getAgendaMedicaDataDto())) {
			for (AgendaMedicaDataDto e : dto.getAgendaMedicaDataDto()) {

				AgendaMedicaData agendaMedicaData = new AgendaMedicaData(e, agendaMedica.getId());
				dataRepository.save(agendaMedicaData);
			}
		}
	}

	@Override
	@Transactional
	public void update(AgendaMedicaDto dto) {

		AgendaMedica agendaMedica = repository.findById(dto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("AgendaMedica", "id", dto.getId()));

		Medico medico = medicoRepository.findById(dto.getMedicoId())
				.orElseThrow(() -> new ResourceNotFoundException("Medico", "id", dto.getId()));

		agendaMedica.setMedico(medico);
		agendaMedica.setDataInicio(dto.getDataInicial());
		agendaMedica.setDataFim(dto.getDataFinal());
		agendaMedica.setHoraInicial(dto.setHora(dto.getHoraInicial()));
		agendaMedica.setHoraFinal(dto.setHora(dto.getHoraFinal()));
		agendaMedica.setIntervalo(dto.getIntervalo());

		setEntidade(agendaMedica, dto);

		repository.save(agendaMedica);

		if (Utils.checkList(dto.getAgendaMedicaDataDto())) {
			if (Objects.nonNull(agendaMedica.getId())) {
				List<AgendaMedicaData> obj2 = dataRepository.findAllByAgendaMedicaId(agendaMedica.getId());
				for (AgendaMedicaData agendaMedicaData : obj2) {
					dataRepository.delete(agendaMedicaData);
				}

				for (AgendaMedicaDataDto ag : dto.getAgendaMedicaDataDto()) {
					AgendaMedicaData agendaMedicaData = new AgendaMedicaData(ag, agendaMedica.getId());
					dataRepository.save(agendaMedicaData);
				}
			}
		}
	}

	@Override
	public void delete(Long id) {
		AgendaMedica agendaMedica = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("AgendaMedica", "id", id));
		repository.delete(agendaMedica);
	}

	@Override
	public PagedResponse<AgendaMedicaDto> getPagedList(PagedRequest pagedRequest, AgendaMedicaDto requestFilter) {
		return null;
	}

	@Override
	public List<AgendaMedicaDto> getList(AgendaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(AgendaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param dtInicial
	 * @param dtFinal
	 * @param personalizar
	 * @param segunda
	 * @param terca
	 * @param quarta
	 * @param quinta
	 * @param sexta
	 * @param sabado
	 * @param domingo
	 * @param horaInicial
	 * @param horaFinal
	 * @param intervalo
	 * 
	 *                     Este método serve para montar os dados que serão exibidos
	 *                     no greed de insert no frontEnd.
	 * 
	 * @return
	 */
	public List<AgendaMedicaResponse> calcularAgenda(String dtInicial, String dtFinal, boolean semanal, boolean segunda,
			boolean terca, boolean quarta, boolean quinta, boolean sexta, boolean sabado, boolean domingo,
			String horaInicial, String horaFinal, int intervalo) {

		List<AgendaMedicaResponse> listaAgendaMedicaResponse = new ArrayList<>();

		LocalDate inicio = LocalDate.parse(dtInicial);

		LocalDate fim = LocalDate.parse(dtFinal);
		
		
		boolean isBefore = inicio.isBefore(fim);
		System.out.println("inicio is antes fim :: " + isBefore);
		 
		boolean isAfter = inicio.isAfter(fim);
		System.out.println("inicio is depois fim :: " + isAfter);
		 
		boolean isEqual = inicio.isEqual(fim);
		System.out.println("inicio is equal to fim :: " + isEqual);
		
		long diferencaInicio = 0;
		long diferencaEmMes = ChronoUnit.MONTHS.between(inicio, fim);
		diferencaEmMes++;
		

		Set<LocalDate> feriados = new HashSet<>();
		feriados.addAll(getFeriadosFixos(inicio.getYear()));
		feriados.addAll(getFeriadosMoveis(inicio.getYear()));

		int diaInicio = inicio.getDayOfMonth();
		int anoInicio = inicio.getYear();
		int mesInicio = inicio.getMonthValue();

		int diaFim = fim.getDayOfMonth();
		// int anoFim = fim.getYear();
		int mesFim = fim.getMonthValue();

		List<LocalDate> listaDosDiasDosMes = new ArrayList<>();

		// laço de repetição para capturar todos os dias entre as datas de início e fim
		// vindas da tela.
		do {

			YearMonth anoMes = YearMonth.of(anoInicio, mesInicio);
			int diasFinal = anoMes.lengthOfMonth();

			if (mesInicio == mesFim) {
				diasFinal = diaFim;
			}

			for (int dia = diaInicio; dia <= diasFinal; dia++) {
				LocalDate data = anoMes.atDay(dia);

				listaDosDiasDosMes.add(data);
			}

			if(mesInicio == 12) {
				// caso seja dezembro, muda pra Janeiro e soma 1 ano a mais no atributo do ano
				mesInicio = 1;
				anoInicio++;
			}else {
				++mesInicio;
			}
			
			++diferencaInicio;
			
			diaInicio = 1;
		//} while (mesInicio <= mesFim);
		} while (diferencaInicio < diferencaEmMes);

		// ## APLICAÇÃO DA REGRA

		/*
		 * Se o usuário selecionar “Semanal”, o sistema vai exibir um modal no frontEnd,
		 * o usuário oderá escolher um dia ou mais da semana. Exemplo: se o usuário
		 * escolher segunda e terça, o sistema vai criar toda segunda e terça do mês de
		 * início até o mês final do horário de início ao horário final e com o
		 * intervalo de acordo com o escolhido na tela.
		 */
		if (semanal) {
			List<DayOfWeek> listWeek = new ArrayList<>();

			if (domingo) {
				listWeek.add(DayOfWeek.SUNDAY);
			}
			if (segunda) {
				listWeek.add(DayOfWeek.MONDAY);
			}
			if (terca) {
				listWeek.add(DayOfWeek.TUESDAY);
			}
			if (quarta) {
				listWeek.add(DayOfWeek.WEDNESDAY);
			}
			if (quinta) {
				listWeek.add(DayOfWeek.THURSDAY);
			}
			if (sexta) {
				listWeek.add(DayOfWeek.FRIDAY);
			}
			if (sabado) {
				listWeek.add(DayOfWeek.SATURDAY);
			}

			for (int i = 0; i < listaDosDiasDosMes.size(); i++) {
				LocalDate data = listaDosDiasDosMes.get(i);
				List<AgendaMedicaData> listaAgendaMedicaData = new ArrayList<>();

				if (listWeek.contains(data.getDayOfWeek())) {
					montarAgendaMedicaData(listaAgendaMedicaData, horaInicial, horaFinal, data, intervalo);

					AgendaMedicaResponse agendaMedicaResponse = new AgendaMedicaResponse();
					agendaMedicaResponse.setDiaSemana(getTraduzNomeSemana(data.getDayOfWeek().toString()));
					agendaMedicaResponse.setData(data);
					agendaMedicaResponse.setSemanal(semanal);
					agendaMedicaResponse.setAgendaMedicaData(listaAgendaMedicaData);
					agendaMedicaResponse.setCheckeAll(true);
					agendaMedicaResponse.setCheckeAllClean(true);

					listaAgendaMedicaResponse.add(agendaMedicaResponse);
				}

			}

		} else {
			for (int i = 0; i < listaDosDiasDosMes.size(); i++) {
				LocalDate data = listaDosDiasDosMes.get(i);
				List<AgendaMedicaData> listaAgendaMedicaData = new ArrayList<>();
				if (data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY
						&& !feriados.contains(data)) {
					montarAgendaMedicaData(listaAgendaMedicaData, horaInicial, horaFinal, data, intervalo);

					AgendaMedicaResponse agendaMedicaResponse = new AgendaMedicaResponse();
					agendaMedicaResponse.setDiaSemana(getTraduzNomeSemana(data.getDayOfWeek().toString()));
					agendaMedicaResponse.setData(data);
					agendaMedicaResponse.setSemanal(semanal);
					agendaMedicaResponse.setAgendaMedicaData(listaAgendaMedicaData);
					agendaMedicaResponse.setCheckeAll(true);
					agendaMedicaResponse.setCheckeAllClean(true);

					listaAgendaMedicaResponse.add(agendaMedicaResponse);
				}
			}
		}

		// ## FIM APLICAÇÃO DA REGRA

		return listaAgendaMedicaResponse;
	}

	public PagedResponse<AgendaMedicaResponse> getAllAgendaMedica(int page, int size,
			AgendaMedicaFiltro agendaMedicaFiltro, String order) {
		Utils.validatePageNumberAndSize(page, size);
		Set<String> diaSemanaSet = new HashSet<String>();

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<AgendaMedica> agendaMedicas = repository.filtro(agendaMedicaFiltro, pageable);

		if (agendaMedicas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), agendaMedicas.getNumber(), agendaMedicas.getSize(),
					agendaMedicas.getTotalElements(), agendaMedicas.getTotalPages(), agendaMedicas.isLast());
		}

		List<AgendaMedicaResponse> agendaMedicasResponse = new ArrayList<AgendaMedicaResponse>();

		for (AgendaMedica agendaMedica : agendaMedicas) {
			String agendamento = "";
			String periodicidade = "";
			String tooltip = "";
			String and = "";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			AgendaMedicaResponse agendaMedicaResponse = new AgendaMedicaResponse();

			Medico medico = medicoRepository.findById(agendaMedica.getMedico().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Medico", "id", agendaMedica.getMedico().getId()));

			if (agendaMedica.isSemanal()) {
				for (AgendaMedicaData agendaMedicaData : agendaMedica.getAgendaMedicaData()) {
					diaSemanaSet.add(agendaMedicaData.getDiaSemana());
				}

				for (String diaSemana : diaSemanaSet) {
					agendamento = agendamento + and + diaSemana;
					and = ",";
				}

				tooltip = "Toda " + reordenar(agendamento);

				agendamento = "Semanal";
			} else {
				tooltip = "Todos os dias úteis";
				agendamento = "Padrão";
			}
			periodicidade = " de " + agendaMedica.getDataInicio().format(formatter) + " até  o dia "
					+ agendaMedica.getDataFim().format(formatter);

			agendaMedicaResponse.setId(agendaMedica.getId());
			agendaMedicaResponse.setMedico(new MedicoDto(medico));
			agendaMedicaResponse.setEspecialidadesMedicas(agendaMedica.getEspecialidadesMedicas());
			agendaMedicaResponse.setAgendamento(agendamento);
			agendaMedicaResponse.setPeriodicidade(periodicidade);
			agendaMedicaResponse.setTooltip(tooltip);

			agendaMedicasResponse.add(agendaMedicaResponse);
		}

		return new PagedResponse<>(agendaMedicasResponse, agendaMedicas.getNumber(), agendaMedicas.getSize(),
				agendaMedicas.getTotalElements(), agendaMedicas.getTotalPages(), agendaMedicas.isLast());
	}

	@Override
	public AgendaMedicaDto getById(Long id) {
		AgendaMedica agendaMedica = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("AgendaMedica", "id", id));

		AgendaMedicaDto dto = new AgendaMedicaDto(agendaMedica);

		usuarioService.setDadoCadastral(dto, agendaMedica);

		return dto;
	}

	public List<AgendaMedicaRelatorioDto> getAllAgendaMedicaDataPericiaMedica(Long especialidadeMedicaId, String data, boolean isMaiorQue) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dataAgenda = LocalDate.parse(data, formatter);

		AgendaMedicaDataFiltro agendaMedicaDataFiltro = new AgendaMedicaDataFiltro();
		agendaMedicaDataFiltro.setEspecialidadeMedicaId(especialidadeMedicaId);
		agendaMedicaDataFiltro.setDataAgenda(dataAgenda);
		agendaMedicaDataFiltro.setUsarMaiorQuePraDataAgendaPericiaMedica(isMaiorQue);

		return getAllAgendaMedicaDataRelatorio(agendaMedicaDataFiltro);
	}

	public List<AgendaMedicaRelatorioDto> getAllAgendaMedicaDataRelatorio(
			AgendaMedicaDataFiltro agendaMedicaDataFiltro) {
		List<AgendaMedicaRelatorioDto> retornoList = new ArrayList<AgendaMedicaRelatorioDto>();
		List<AgendaMedica> listAgendaMedicas = new ArrayList<AgendaMedica>();

		AgendaMedicaFiltro filtro2 = new AgendaMedicaFiltro();
		filtro2.setDataAgenda(agendaMedicaDataFiltro.getDataAgenda());
		filtro2.setEspecialidadeMedicaList(new ArrayList<Long>());
		filtro2.getEspecialidadeMedicaList().add(agendaMedicaDataFiltro.getEspecialidadeMedicaId());

		List<AgendaMedica> agendaMedicas = repository.filtro(filtro2);
		
		for (AgendaMedica agendaMedica : agendaMedicas) {
			if (listAgendaMedicas.size() > 0) {
				boolean repetido = false;
				for (AgendaMedica ag : listAgendaMedicas) {
					if (ag.getId() == agendaMedica.getId()) {
						repetido = true;
					}
				}
				if (!repetido) {
					listAgendaMedicas.add(agendaMedica);
				}
			} else {
				listAgendaMedicas.add(agendaMedica);
			}

		}

		for (AgendaMedica agendaMedica : listAgendaMedicas) {
			List<String> nomesEspecialidades = new ArrayList<String>();

			AgendaMedicaDto dto = new AgendaMedicaDto(agendaMedica);
			AgendaMedicaRelatorioDto relatorioDto = new AgendaMedicaRelatorioDto();
			relatorioDto.setAgendaMedicaDto(dto);
			relatorioDto.setNomeMedico(dto.getMedico().getNome());

			for (EspecialidadeMedica especialidadeMedica : agendaMedica.getEspecialidadesMedicas()) {
				nomesEspecialidades.add(especialidadeMedica.getNome());
			}
			relatorioDto.setNmEspecialidade(nomesEspecialidades);

			retornoList.add(relatorioDto);
		}

		List<AgendaMedicaDto> agendaMedicasFinal = new ArrayList<AgendaMedicaDto>();

		for (AgendaMedicaRelatorioDto a : retornoList) {
			List<AgendaMedicaDto> amd = a.getAgendaMedicaDto().getAgendaMedicaList();
			for (AgendaMedicaDto amdl : amd) {
				if (Objects.nonNull(filtro2.getDataAgenda())) {
					if(agendaMedicaDataFiltro.isUsarMaiorQuePraDataAgendaPericiaMedica()){
						if (amdl.getData().equals(filtro2.getDataAgenda()) || amdl.getData().isAfter(filtro2.getDataAgenda())) {
							agendaMedicasFinal.add(amdl);
						}
					}else {
						if (amdl.getData().equals(filtro2.getDataAgenda())) {
							agendaMedicasFinal.add(amdl);
							break;
						}
					}
				}
			}
			a.getAgendaMedicaDto().getAgendaMedicaList().clear();
			a.getAgendaMedicaDto().getAgendaMedicaList().addAll(agendaMedicasFinal);
		}
		
		return retornoList;

	}

	public List<AgendaMedicaRelatorioDto> getAllAgendaMedicaRelatorio(AgendaMedicaFiltro agendaMedicaFiltro) {
		List<AgendaMedicaRelatorioDto> retornoList = new ArrayList<AgendaMedicaRelatorioDto>();
		List<AgendaMedica> listAgendaMedicas = new ArrayList<AgendaMedica>();

		// Consulta
		List<AgendaMedica> agendaMedicas = repository.filtro(agendaMedicaFiltro);

		for (AgendaMedica agendaMedica : agendaMedicas) {
			if (listAgendaMedicas.size() > 0) {
				boolean repetido = false;
				for (AgendaMedica ag : listAgendaMedicas) {
					if (ag.getId() == agendaMedica.getId()) {
						repetido = true;
					}
				}
				if (!repetido) {
					listAgendaMedicas.add(agendaMedica);
				}
			} else {
				listAgendaMedicas.add(agendaMedica);
			}

		}

		for (AgendaMedica agendaMedica : listAgendaMedicas) {
			List<String> nomesEspecialidades = new ArrayList<String>();

			AgendaMedicaDto dto = new AgendaMedicaDto(agendaMedica);
			AgendaMedicaRelatorioDto relatorioDto = new AgendaMedicaRelatorioDto();
			relatorioDto.setAgendaMedicaDto(dto);
			relatorioDto.setNomeMedico(dto.getMedico().getNome());

			for (EspecialidadeMedica especialidadeMedica : agendaMedica.getEspecialidadesMedicas()) {
				nomesEspecialidades.add(especialidadeMedica.getNome());
			}
			relatorioDto.setNmEspecialidade(nomesEspecialidades);

			retornoList.add(relatorioDto);
		}

		return retornoList;

	}

	private String reordenar(String dias) {
		String retorno = "";
		if (dias.contains("segunda-feira"))
			retorno += "segunda,";
		if (dias.contains("terça-feira"))
			retorno += "terça,";
		if (dias.contains("quarta-feira"))
			retorno += "quarta,";
		if (dias.contains("quinta-feira"))
			retorno += "quinta,";
		if (dias.contains("sexta-feira"))
			retorno += "sexta,";
		if (dias.contains("sabado"))
			retorno += "sabado,";
		if (dias.contains("domingo"))
			retorno += "domingo,";
		retorno = retorno.substring(0, retorno.length() - 1);
		return retorno;
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

	private MesEnum getTraduzNomeMes(String mes) {

		MesEnum mesPortugues = null;

		if (mes.contentEquals("JANUARY")) {
			mesPortugues = MesEnum.JANEIRO;
		} else if (mes.contentEquals("FEBRUARY")) {
			mesPortugues = MesEnum.FEVEREIRO;
		}
		if (mes.contentEquals("MARCH")) {
			mesPortugues = MesEnum.MARCO;
		}
		if (mes.contentEquals("APRIL")) {
			mesPortugues = MesEnum.ABRIL;
		}
		if (mes.contentEquals("MAY")) {
			mesPortugues = MesEnum.MAIO;
		}
		if (mes.contentEquals("JUNE")) {
			mesPortugues = MesEnum.JUNHO;
		}
		if (mes.contentEquals("JULY")) {
			mesPortugues = MesEnum.JULHO;
		}
		if (mes.contentEquals("AUGUST")) {
			mesPortugues = MesEnum.AGOSTO;
		}
		if (mes.contentEquals("SEPTEMBER")) {
			mesPortugues = MesEnum.SETEMBRO;
		}
		if (mes.contentEquals("OCTOBER")) {
			mesPortugues = MesEnum.OUTUBRO;
		}
		if (mes.contentEquals("NOVEMBER")) {
			mesPortugues = MesEnum.NOVEMBRO;
		}
		if (mes.contentEquals("DECEMBER")) {
			mesPortugues = MesEnum.DEZEMBRO;
		}

		return mesPortugues;
	}

	// feriados que acontecem todo ano na mesma data, gerar lista para o ano
	// específico
	private Set<LocalDate> getFeriadosFixos(int year) {
		Set<LocalDate> dates = new HashSet<>();

		// 1 de Janeiro - Confraternização Universal
		dates.add(LocalDate.of(year, 1, 1));

		// 21 de Abril - Tirandentes
		dates.add(LocalDate.of(year, 4, 21));

		// 1 de Maio - Trabalho
		dates.add(LocalDate.of(year, 5, 1));

		// 7 de setembro - Independência
		dates.add(LocalDate.of(year, 9, 7));

		// 12 de Outubro - Padroeira do Brasil
		dates.add(LocalDate.of(year, 10, 12));

		// 02 de Novembro - Finados
		dates.add(LocalDate.of(year, 11, 02));

		// 15 de Novembro - Proclamação da República
		dates.add(LocalDate.of(year, 11, 15));

		// 25 DE Dezembro - Natal
		dates.add(LocalDate.of(year, 12, 25));

		return dates;
	}

	// calcula páscoa, carnaval, corpus christi e sexta-feira santa
	private Set<LocalDate> getFeriadosMoveis(int year) {
		Set<LocalDate> dates = new HashSet<>();

		LocalDate pascoa;
		LocalDate carnaval;
		LocalDate corpusChristi;
		LocalDate sextaFeiraSanta;

		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int month = (h + l - 7 * m + 114) / 31;
		int day = ((h + l - 7 * m + 114) % 31) + 1;

		pascoa = LocalDate.of(year, month, day);

		// Carnaval 47 dias antes da pascoa (sempre cai na terça)
		carnaval = pascoa.minusDays(47);

		// CorpusChristi 60 dias apos a pascoa
		corpusChristi = pascoa.plusDays(60);

		sextaFeiraSanta = pascoa.minusDays(2);

		// páscoa cai sempre no domingo, entao não precisaria adicionar como feriado
		// dates.add(pascoa);

		// carnaval: adicionar um dia antes e depois (emenda de segunda e quarta-feira
		// de cinzas)
		dates.add(carnaval);
		dates.add(carnaval.minusDays(1)); // emenda a segunda-feira
		dates.add(carnaval.plusDays(1)); // quarta-feira de cinzas

		// corpus christi, emendar (adicionar a sexta)
		dates.add(corpusChristi);

		dates.add(sextaFeiraSanta);

		return dates;
	}

	private void montarAgendaMedicaData(List<AgendaMedicaData> listaAgendaMedicaData, String horaInicial,
			String horaFinal, LocalDate data, int intervalo) {
		java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
		String horarioInicialIncrementavel = horaInicial;
		java.text.SimpleDateFormat formatador = new java.text.SimpleDateFormat("HH:mm");

		try {

			// hora Fim
			Date dataHoraioFinal = formatador.parse(horaFinal);
			Date dataInicio;
			Date horarioIncrementada;

			do {

				dataInicio = formatador.parse(horarioInicialIncrementavel);
				gc.setTime(dataInicio);

				if (listaAgendaMedicaData.isEmpty()) {
					AgendaMedicaData ag = new AgendaMedicaData();
					ag.setDiaSemana(getTraduzNomeSemana(data.getDayOfWeek().toString()));
					ag.setHora(formatador.format(gc.getTime()));
					ag.setData(data);
					ag.setCheckHorario(true);
					listaAgendaMedicaData.add(ag);
				}

				gc.add(java.util.Calendar.MINUTE, intervalo);
				horarioInicialIncrementavel = formatador.format(gc.getTime());
				horarioIncrementada = formatador.parse(horarioInicialIncrementavel);

				AgendaMedicaData ag = new AgendaMedicaData();
				ag.setDiaSemana(getTraduzNomeSemana(data.getDayOfWeek().toString()));
				ag.setHora(horarioInicialIncrementavel);

				// ag.setMes(getTraduzNomeMes(data.getMonth().name()));
				// ag.setAno(data.getYear());

				ag.setData(data);
				ag.setCheckHorario(true);
				listaAgendaMedicaData.add(ag);

			} while (horarioIncrementada.getTime() < dataHoraioFinal.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void setEntidade(AgendaMedica agendaMedica, AgendaMedicaDto agendaMedicaDto) {
		if (Objects.nonNull(agendaMedicaDto.getEspecialidadeMedicaId())) {
			agendaMedica.setEspecialidadesMedicas(new ArrayList<>());

			for (Long id : agendaMedicaDto.getEspecialidadeMedicaId()) {
				agendaMedica.getEspecialidadesMedicas().add(new EspecialidadeMedica(id));
			}
		}
	}

}
