package com.rhlinkcon.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.controller.ProntuarioPericiaMedicaDto;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.model.ProntuarioPericiaMedica;
import com.rhlinkcon.model.TipoAcaoPericiaMedicaEnum;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EspecialidadeMedicaRepository;
import com.rhlinkcon.repository.consultaPericiaMedica.ConsultaPericiaMedicaRepository;
import com.rhlinkcon.repository.prontuarioPerciaMedica.ProntuarioPericiaMedicaRepository;

@Service
public class ProntuarioPericiaMedicaService extends GenericService<ProntuarioPericiaMedicaDto, Long> {

	@Autowired
	private ProntuarioPericiaMedicaRepository repository;
	
	@Autowired
	private ConsultaPericiaMedicaRepository consultaPericiaMedicaRepository;
	
	@Autowired
	private EspecialidadeMedicaRepository especialidadeMedicaRespository;
	
	@Override
	@Transactional
	public void create(ProntuarioPericiaMedicaDto dto) {
		
		
		if(dto.getConsultaPericiaMedicaId() != null) {
			ConsultaPericiaMedica consultaPericiaMedica = consultaPericiaMedicaRepository.findById(dto.getConsultaPericiaMedicaId())
					.orElseThrow(() -> new ResourceNotFoundException("ConsultaPericiaMedica", "id", dto.getConsultaPericiaMedicaId()));
			
			
			if(!dto.getPeriodoProximaPericia().equals("") && dto.getPeriodoProximaPericia() != null){
				calcularPeriodoProximaPericia(dto, consultaPericiaMedica);
			}
			
			ProntuarioPericiaMedica prontuarioPericiaMedica = new ProntuarioPericiaMedica(dto);
			repository.save(prontuarioPericiaMedica);
			
			
			if(dto.getObservacaoMedico().length() > 0){
				consultaPericiaMedica.setObservacaoMedica(dto.getObservacaoMedico());
			}
			
			if(dto.getAcao() == TipoAcaoPericiaMedicaEnum.REMARCAR_CLINICO_GERAL_PRESENCIAL.toString()){
				consultaPericiaMedica.setTipoProximaPericia("Presencial");
				if(especialidadeMedicaRespository.existsByNome("Clínico Geral")) {
					consultaPericiaMedica.setEspecialidadeMedicaProximaConsulta(especialidadeMedicaRespository.findByNome("Clínico Geral"));
				}
			}else if(dto.getAcao() == TipoAcaoPericiaMedicaEnum.REMARCAR_CLINICO_GERAL_NAO_PRESENCIAL.toString()) {
				consultaPericiaMedica.setTipoProximaPericia("Não Presencial");
				if(especialidadeMedicaRespository.existsByNome("Clínico Geral")) {
					consultaPericiaMedica.setEspecialidadeMedicaProximaConsulta(especialidadeMedicaRespository.findByNome("Clínico Geral"));
				}
			}else if(dto.getAcao() == TipoAcaoPericiaMedicaEnum.REMARCAR_CLINICO_GERAL_PRESENCIAL.toString()) {
				consultaPericiaMedica.setTipoProximaPericia("Presencial");
				if(especialidadeMedicaRespository.existsByNome("Clínico Geral")) {
					consultaPericiaMedica.setEspecialidadeMedicaProximaConsulta(especialidadeMedicaRespository.findByNome("Clínico Geral"));
				}
			}else if(dto.getAcao() == TipoAcaoPericiaMedicaEnum.REMARCAR_CLINICO_GERAL_NAO_PRESENCIAL.toString()) {
				consultaPericiaMedica.setTipoProximaPericia("Não Presencial");
				if(especialidadeMedicaRespository.existsByNome("Clínico Geral")) {
					consultaPericiaMedica.setEspecialidadeMedicaProximaConsulta(especialidadeMedicaRespository.findByNome("Clínico Geral"));
				}
			}else if(dto.getAcao() == TipoAcaoPericiaMedicaEnum.AGENDAR_ESPECIALISTA.toString()) {
				consultaPericiaMedica.setTipoProximaPericia("Não Presencial");
				consultaPericiaMedica.setEspecialidadeMedicaProximaConsulta(new EspecialidadeMedica(dto.getEspecialidadeMedicaId()));
			}
			
			
			
			consultaPericiaMedicaRepository.save(consultaPericiaMedica);
		}
		
	}

	
	/**
	 * @param dto
	 * @Regra O sistema deverá calcular a partir da data da consulta da perícia médica e realizar um novo agendamento baseado na seleção e 
	 * a primeira data possível deverá ser salva no atributo “periodo_prox_pericia” da tabela “prontuario_pericia_medica” 
	 * e também atualizado na consulta: tabela "consulta_pericia_medica" no atributo “data_proximo_agendamento_pericia_medica”.
	 */
	private void calcularPeriodoProximaPericia(ProntuarioPericiaMedicaDto dto, ConsultaPericiaMedica consultaPericiaMedica) {
		LocalDate dataPericia = consultaPericiaMedica.getAgendaMedicaData().getData();
		
		if(dto.getPeriodoProximaPericia().equals("6 meses")) {
			dto.setDataProximaPericia(dataPericia.plus(Period.ofMonths(6)));
			consultaPericiaMedica.setDataProximoAgendamento(dataPericia);
		}else if(dto.getPeriodoProximaPericia().equals("1 ano")) {
			dto.setDataProximaPericia(dataPericia.plus(Period.ofYears(1)));
			consultaPericiaMedica.setDataProximoAgendamento(dataPericia);
		}else if(dto.getPeriodoProximaPericia().equals("2 anos")) {
			dto.setDataProximaPericia(dataPericia.plus(Period.ofYears(2)));
			consultaPericiaMedica.setDataProximoAgendamento(dataPericia);
		}else if(dto.getPeriodoProximaPericia().equals("3 anos")) {
			dto.setDataProximaPericia(dataPericia.plus(Period.ofYears(3)));
			consultaPericiaMedica.setDataProximoAgendamento(dataPericia);
		}else if(dto.getPeriodoProximaPericia().equals("4 anos")) {
			dto.setDataProximaPericia(dataPericia.plus(Period.ofYears(4)));
			consultaPericiaMedica.setDataProximoAgendamento(dataPericia);
		}else if(dto.getPeriodoProximaPericia().equals("5 anos")) {
			dto.setDataProximaPericia(dataPericia.plus(Period.ofYears(5)));
			consultaPericiaMedica.setDataProximoAgendamento(dataPericia);
		}
	}

	@Override
	public void update(ProntuarioPericiaMedicaDto request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProntuarioPericiaMedicaDto getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ProntuarioPericiaMedicaDto> getByIdPacientePericaMedica(Long id) {
		List<ProntuarioPericiaMedica> prontuariosPericiasMedicas = repository.getConsultaProntuarioPericiaMedicaByPacientePericiaMedicaId(id);
		
		List<ProntuarioPericiaMedicaDto> prontuariosPericiasMedicasDto = new ArrayList<ProntuarioPericiaMedicaDto>();
		
		for (ProntuarioPericiaMedica prontuarioPericiaMedica : prontuariosPericiasMedicas) {
			ProntuarioPericiaMedicaDto dto = new ProntuarioPericiaMedicaDto(prontuarioPericiaMedica);
			prontuariosPericiasMedicasDto.add(dto);
		}
		
		return prontuariosPericiasMedicasDto;
	}
	
	public ProntuarioPericiaMedicaDto getByIdConsultaPericiaMedica(Long id) {
		ProntuarioPericiaMedica prontuarioPericiaMedica = repository.getConsultaProntuarioPericiaMedicaByConsultaPericiaMedicaId(id);
		ProntuarioPericiaMedicaDto prontuarioPericiaMedicaDto = new ProntuarioPericiaMedicaDto(prontuarioPericiaMedica);
		
		return prontuarioPericiaMedicaDto;
	}


	@Override
	public PagedResponse<ProntuarioPericiaMedicaDto> getPagedList(PagedRequest pagedRequest,
			ProntuarioPericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProntuarioPericiaMedicaDto> getList(ProntuarioPericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(ProntuarioPericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

}
