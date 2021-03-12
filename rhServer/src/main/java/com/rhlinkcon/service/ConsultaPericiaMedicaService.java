package com.rhlinkcon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rhlinkcon.controller.ConsultaPericiaMedicaDto;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.ConsultaPericiaMedicaFiltro;
import com.rhlinkcon.model.AgendaMedicaData;
import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.model.PacientePericiaMedica;
import com.rhlinkcon.model.StatusAgendamentoPericiaEnum;
import com.rhlinkcon.model.TipoAnaliseEnum;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.periciaMedica.PericiaMedicaResponse;
import com.rhlinkcon.repository.agendaMedica.AgendaMedicaDataRepository;
import com.rhlinkcon.repository.consultaPericiaMedica.ConsultaPericiaMedicaRepository;
import com.rhlinkcon.repository.pacientePericiaMedica.PacientePericiaMedicaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ConsultaPericiaMedicaService extends GenericService<ConsultaPericiaMedicaDto, Long> {

	@Autowired
	private ConsultaPericiaMedicaRepository repository;
	
	@Autowired
	private AgendaMedicaDataRepository agendaMedicaDataRepository;
	
	@Autowired
	private PacientePericiaMedicaRepository pacientePericiaMedicaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void create(ConsultaPericiaMedicaDto dto) {
		
		ConsultaPericiaMedica consultaPericiaMedica = new ConsultaPericiaMedica(dto);
		setEntidade(consultaPericiaMedica, dto);
		
		repository.save(consultaPericiaMedica);
		
	}
	
	public ResponseEntity<?> updateNaoCompareceu(String cpf) {
		ResponseEntity<?> retorno = null;
		if (pacientePericiaMedicaRepository.existsByCpf(cpf)) {
			PacientePericiaMedica pacientePeciciaMedica = pacientePericiaMedicaRepository.getByCpf(cpf);
			Integer cont = pacientePeciciaMedica.getContadorComparecimento() + 1;
			pacientePeciciaMedica.setContadorComparecimento(cont);
			pacientePericiaMedicaRepository.save(pacientePeciciaMedica);

			ConsultaPericiaMedica consultaPericiaMedica = repository.getConsultaPericiaMedicaById(pacientePeciciaMedica.getId());
			
			if(consultaPericiaMedica != null) {
				consultaPericiaMedica.setCompareceu(false);
				repository.save(consultaPericiaMedica);
				
				retorno = Utils.created(true, "Consulta Perícia Médica alterada com sucesso!"); 
			}
		}else {
			retorno = Utils.created(true, "Naõ exite Consulta de Perícia Médica para este CPF!"); 
		}
		
		return retorno;
	}

	
	public ResponseEntity<?> updateRecusouPericia(Long id) {

		ConsultaPericiaMedica consultaPericiaMedica = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ConsultaPericiaMedica", "id", id));
		
		consultaPericiaMedica.setCompareceu(false);
		consultaPericiaMedica.getPacientePericiaMedica().setStatus(StatusAgendamentoPericiaEnum.NAO_AGENDADO);
		Integer cont = consultaPericiaMedica.getPacientePericiaMedica().getContadorComparecimento() + 1;
		consultaPericiaMedica.getPacientePericiaMedica().setContadorComparecimento(cont);
		
		repository.save(consultaPericiaMedica);
		
		return Utils.created(true, "Consulta Perícia Médica alterada com sucesso!"); 
	}
	
	@Override
	public void update(ConsultaPericiaMedicaDto request) {
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public ConsultaPericiaMedicaDto getByIdConsulta(Long id) {
		List<String> telefone = new ArrayList<String>();
		
		ConsultaPericiaMedica consultaPericiaMedica = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ConsultaPericiaMedica", "id", id));
		ConsultaPericiaMedicaDto consultaPericiaMedicaDto = new ConsultaPericiaMedicaDto(consultaPericiaMedica);
		
		PacientePericiaMedicaDto pacientePericiaMedicaDto = new PacientePericiaMedicaDto();
		pacientePericiaMedicaDto.setId(consultaPericiaMedica.getPacientePericiaMedica().getId());
		pacientePericiaMedicaDto.setCpf(consultaPericiaMedica.getPacientePericiaMedica().getCpf());
		pacientePericiaMedicaDto.setPaciente(consultaPericiaMedica.getPacientePericiaMedica().getNome());
		pacientePericiaMedicaDto.setTipoAnalise(TipoAnaliseEnum.REVISAO_DE_APOSENTADORIA.toString());
		pacientePericiaMedicaDto.setNumeroProcesso(consultaPericiaMedica.getPacientePericiaMedica().getNumeroProcesso());
		pacientePericiaMedicaDto.setNomeMae(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getNomeMae());
		pacientePericiaMedicaDto.setDataNascimento(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getDataNascimento());
		pacientePericiaMedicaDto.setSexo(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getSexo());
		
		consultaPericiaMedicaDto.setPacientePericiaMedicaDto(pacientePericiaMedicaDto);		
		
		if(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getTelefoneComercial() != null) {
			telefone.add(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getTelefoneComercial());
		}
		if(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getTelefoneOpcional() != null) {
			telefone.add(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getTelefoneOpcional());
		}
		if(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getTelefonePrincipal() != null) {
			telefone.add(consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getTelefonePrincipal());
		}
		if(!telefone.isEmpty()) {
			pacientePericiaMedicaDto.setTelefone(telefone);
		}
		
		return consultaPericiaMedicaDto;
	}

	public PagedResponse<PericiaMedicaResponse> getAllConsultaPericiaMedica(int page, int size,
			ConsultaPericiaMedicaFiltro consultaPericiaMedicaFiltro, String order) {

		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ConsultaPericiaMedica> consultasPericiasMedicas = repository.filtro(consultaPericiaMedicaFiltro, pageable);

		if (consultasPericiasMedicas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), consultasPericiasMedicas.getNumber(), consultasPericiasMedicas.getSize(),
					consultasPericiasMedicas.getTotalElements(), consultasPericiasMedicas.getTotalPages(), consultasPericiasMedicas.isLast());
		}

		List<PericiaMedicaResponse> periciasMedicasResponse = new ArrayList<>();

		String tipoConsulta = "";
		for (ConsultaPericiaMedica consultaPericiaMedica : consultasPericiasMedicas) {
			PericiaMedicaResponse consultaPericiaMedicaResponse = new PericiaMedicaResponse();
			consultaPericiaMedicaResponse.setId(consultaPericiaMedica.getId());
			consultaPericiaMedicaResponse.setNome(consultaPericiaMedica.getPacientePericiaMedica().getNome());
			
			if(consultaPericiaMedica.getPacientePericiaMedica().getContadorComparecimento() >= 3) {
				tipoConsulta = "Não presencial";
			}else {
				tipoConsulta = "Presencial";
			}
			LocalDate dataHoje = convertToLocalDateViaInstant(new Date());
			LocalDate dataConsultaPericiaMedica = consultaPericiaMedica.getAgendaMedicaData().getData();
			consultaPericiaMedicaResponse.setPaginaVisualizar(dataConsultaPericiaMedica.isBefore(dataHoje));
			
			consultaPericiaMedicaResponse.setHorario(consultaPericiaMedica.getAgendaMedicaData().getHorario());
			consultaPericiaMedicaResponse.setTipoConsulta(tipoConsulta);
			consultaPericiaMedicaResponse.setTipoAnalise(TipoAnaliseEnum.getEnumByLabel(TipoAnaliseEnum.REVISAO_DE_APOSENTADORIA.toString()));
			
			/**
			 * 	Regra do status do atendimento para perícia médica. São divididos em três: 

				1 - Quando não houver nada relacionado ao atendimento, ou seja, quando nenhuma ação no registro relacionado consulta tiver sido realizada, um hífen deverá ser exibido na respectiva linha.

				2 - Quando a ação de “Iniciar atendimento” tiver sido acessada, ou seja, o registro dessa consulta originou uma perícia, a linha deverá exibir “Compareceu” (atualizar valor do atributo “compareceu” da tabela “Consulta_pericia_medica” para “1”).

				3 – Quando a ação de “Cancelar atendimento” tenha sido utilizada, o respectivo campo deverá exibir “Não compareceu” (atualizar valor do atributo “compareceu” da tabela “Consulta_pericia_medica” para “0”).

			 */
			
			ConsultaPericiaMedica cpm = repository.getConsultaPericiaMedicaById(consultaPericiaMedica.getId());
			
			if(cpm == null) {
				consultaPericiaMedicaResponse.setStatus("-");
			}else {
				if(consultaPericiaMedica.isCompareceu()) {
					consultaPericiaMedicaResponse.setStatus("Compareceu");
				}else {
					consultaPericiaMedicaResponse.setStatus("Não Compareceu");
				}
			}
			consultaPericiaMedicaResponse.setNomeMedico(consultaPericiaMedicaFiltro.getNomeMedico());
			
			periciasMedicasResponse.add(consultaPericiaMedicaResponse);
		}
		
		return new PagedResponse<>(periciasMedicasResponse, consultasPericiasMedicas.getNumber(), consultasPericiasMedicas.getSize(),
				consultasPericiasMedicas.getTotalElements(), consultasPericiasMedicas.getTotalPages(), consultasPericiasMedicas.isLast());
}

		
	@Override
	public PagedResponse<ConsultaPericiaMedicaDto> getPagedList(PagedRequest pagedRequest,
			ConsultaPericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaPericiaMedicaDto> getList(ConsultaPericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(ConsultaPericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsultaPericiaMedicaDto getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void setEntidade(ConsultaPericiaMedica consultaPericiaMedica, ConsultaPericiaMedicaDto dto) {
		if (Objects.nonNull(dto.getAgendaMedicaDataId())) {
			AgendaMedicaData agendaMedicaData = agendaMedicaDataRepository.findById(dto.getAgendaMedicaDataId())
					.orElseThrow(() -> new ResourceNotFoundException("AgendaMedicaData", "id", dto.getAgendaMedicaDataId()));
			
			consultaPericiaMedica.setMedico(new Medico(agendaMedicaData.getAgendaMedica().getMedico().getId()));
		}
	}
	
	private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
}
