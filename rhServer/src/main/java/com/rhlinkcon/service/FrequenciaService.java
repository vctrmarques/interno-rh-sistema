package com.rhlinkcon.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Frequencia;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.payload.frequencia.FrequenciaRequest;
import com.rhlinkcon.payload.frequencia.FrequenciaResponse;
import com.rhlinkcon.payload.frequencia.ProjectionFrequencia;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.FrequenciaRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.DateUtils;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class FrequenciaService {
	@Autowired
	private FrequenciaRepository frequenciaRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public void create(FrequenciaRequest frequenciaRequest) {
		if(Objects.isNull(frequenciaRequest.getFuncionarioId())) {
			if(frequenciaRepository.existsFrequenciaHoje(frequenciaRequest.getFuncionarioId())) {
				throw new BadRequestException("Ja existe um registro para este funcionário hoje");
			}
		}
		

		Frequencia frequencia = new Frequencia(frequenciaRequest);

		LocalDateTime dia = getHorasTrabalhadas(frequenciaRequest);

		Duration cargaHoraria = getCargaHoraria(frequenciaRequest.getFuncionarioId());

		Duration dh = Duration.ofHours(dia.getHour());
		Duration ds = Duration.ofMinutes(dia.getMinute());

		if ((dh.getSeconds() + ds.getSeconds()) > cargaHoraria.getSeconds()) {
			frequencia.setHoraExtra(dia.minus(cargaHoraria));
		}

		frequencia.setHorasTrabalhadas(dia);
		if(Objects.isNull(frequenciaRequest.getId())) {
			frequencia.setData(LocalDate.now());			
		} else {
			LocalDateTime dataHora = LocalDateTime.ofInstant(frequenciaRequest.getEntradaUm().toInstant(), ZoneId.systemDefault());
			frequencia.setData(dataHora.toLocalDate());
		}
		frequenciaRepository.save(frequencia);
	}

	private LocalDateTime getHorasTrabalhadas(FrequenciaRequest frequenciaRequest) {
		LocalDateTime marcacaoUm = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getEntradaUm());
		LocalDateTime marcacaoDois = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getSaidaUm());
		LocalDateTime marcacaoTres = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getEntradaDois());
		LocalDateTime marcacaoQuatro = DateUtils.convertDateToLocalDateTime(frequenciaRequest.getSaidaDois());

		LocalDateTime primeiroTurno = marcacaoDois.minusHours(marcacaoUm.getHour()).minusMinutes(marcacaoUm.getMinute()).minusSeconds(marcacaoUm.getSecond());
		LocalDateTime segundoTurno = marcacaoQuatro.minusHours(marcacaoTres.getHour()).minusMinutes(marcacaoTres.getMinute())
				.minusSeconds(marcacaoTres.getSecond());

		LocalDateTime dia = primeiroTurno.plusHours(segundoTurno.getHour()).plusMinutes(segundoTurno.getMinute()).plusSeconds(segundoTurno.getSecond());
		return dia;
	}

	private Duration getCargaHoraria(Long funcionarioId) {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		if (Objects.nonNull(funcionario.getJornadaTrabalho())) {
			Duration intervalo = null;
			Duration cargaHoraria = null;

			if (funcionario.getJornadaTrabalho().isIntervaloFlexivel()) {
				LocalDateTime intervaloFlexivel = DateUtils.convertDateToLocalDateTime(funcionario.getJornadaTrabalho().getIntervalo());
				intervalo = Duration.ofHours(intervaloFlexivel.getHour());
			} else {
				LocalDateTime intervaloIni = DateUtils.convertDateToLocalDateTime(funcionario.getJornadaTrabalho().getIntervaloFlexivelInicio());
				LocalDateTime intervaloFim = DateUtils.convertDateToLocalDateTime(funcionario.getJornadaTrabalho().getIntervaloFlexivelFim());
				intervalo = Duration.between(intervaloIni, intervaloFim);
			}
			if (funcionario.getJornadaTrabalho().isHorarioFlexivel()) {
				LocalDateTime jornadaFlexivel = DateUtils.convertDateToLocalDateTime(funcionario.getJornadaTrabalho().getJornada());
				cargaHoraria = Duration.ofHours(jornadaFlexivel.getHour());
			} else {
				LocalDateTime cHDataIni = DateUtils.convertDateToLocalDateTime(funcionario.getJornadaTrabalho().getHorarioFlexivelInicio());
				LocalDateTime cHDataFim = DateUtils.convertDateToLocalDateTime(funcionario.getJornadaTrabalho().getHorarioFlexivelFim());
				cargaHoraria = Duration.between(cHDataIni, cHDataFim);
			}

			cargaHoraria = cargaHoraria.minus(intervalo);
			return cargaHoraria;
		}

		return null;
	}

	public List<FrequenciaResponse> frequenciaHoje(Long funcionarioId) {
		List<Frequencia> frequencias = frequenciaRepository.findAllByEntradaUmTodayAndFuncionarioId(funcionarioId);
		List<FrequenciaResponse> frequenciaResponse = frequencias.stream().map(f -> new FrequenciaResponse(f, Projecao.MEDIA)).collect(Collectors.toList());
		return frequenciaResponse;
	}

	public Boolean contemFrequenciaHoje(Long funcionarioId) {
		return frequenciaRepository.existsFrequenciaHoje(funcionarioId);
	}

	public PagedResponse<FuncionarioResponse> getAllFrequencias(int page, int size, String search, Integer pAno) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Funcionario> funcionarios = null;

		if (Utils.checkNumber(search)) {
			funcionarios = funcionarioRepository.findByNomeIgnoreCaseContainingOrMatricula(search, Integer.valueOf(search), pageable);
		} else if (Utils.checkStr(search)) {
			funcionarios = funcionarioRepository.findByNomeIgnoreCaseContaining(search, pageable);
		} else {
			funcionarios = funcionarioRepository.findAll(pageable);
		}

		Page<FuncionarioResponse> response = funcionarios.map(funcionario -> new FuncionarioResponse(funcionario.getId(), funcionario.getNome()));

		if (!response.getContent().isEmpty()) {
			response.getContent().forEach(func -> {
				List<ProjectionFrequencia> frequencias = frequenciaRepository.findFrequenciaByAnoAndFuncionarioId(pAno, func.getId());
				List<FrequenciaResponse> fResponse = new ArrayList<>();
				frequencias.forEach(f -> {
					Duration horasTrabalhadas = Objects.isNull(f.getCargaHoraria()) ? null : Duration.ofSeconds(f.getCargaHoraria());
					Duration horaExtra = Objects.isNull(f.getHoraExtra()) ? null : Duration.ofSeconds(f.getHoraExtra());
					fResponse.add(new FrequenciaResponse(horasTrabalhadas, horaExtra, MesEnum.getEnumByInteger(f.getMes()), f.getAno().toString()));
				});
				func.setFrequencias(fResponse);
			});
		}
		return new PagedResponse<>(response.getContent(), response.getNumber(), response.getSize(), response.getTotalElements(), response.getTotalPages(),
				response.isLast());
	}

	public FuncionarioResponse getFrequencias(Long funcionarioId, Integer ano, Integer mes) {
		Funcionario funcionarioQuery = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));
		
		FuncionarioResponse funcionarioResponse = new FuncionarioResponse(funcionarioQuery);
		
		if (Objects.nonNull(funcionarioQuery)) {
			List<Frequencia> frequenciasQuery = frequenciaRepository.findAllByFuncionarioIdAndAnoAndMes(funcionarioId, ano, mes);
			List<FrequenciaResponse> frequencias = new ArrayList<>();
			frequenciasQuery.forEach(freq ->{
				FrequenciaResponse frequenciaResponse = new FrequenciaResponse(freq, Projecao.COMPLETA);
				frequenciaResponse.setAlteradoPor(usuarioService.alteradoPor(freq));
				frequenciaResponse.setCriadoPor(usuarioService.criadoPor(freq));
				frequencias.add(frequenciaResponse);
			});
			funcionarioResponse.setFrequencias(frequencias);
		}
		return funcionarioResponse;
	}
}
