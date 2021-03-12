package com.rhlinkcon.service;

import java.beans.Transient;
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
import org.springframework.stereotype.Service;

import com.rhlinkcon.controller.ConsultaPericiaMedicaDto;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.ConsultaPericiaMedicaFiltro;
import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.PacientePericiaMedica;
import com.rhlinkcon.model.PacientePericiaTelefone;
import com.rhlinkcon.model.StatusAgendamentoPericiaEnum;
import com.rhlinkcon.model.TipoAnaliseEnum;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.periciaMedica.PericiaMedicaResponse;
import com.rhlinkcon.repository.consultaPericiaMedica.ConsultaPericiaMedicaRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.pacientePericiaMedica.PacientePericiaMedicaRepository;
import com.rhlinkcon.repository.pacientePericiaMedica.PacientePericiaTelefoneRepository;
import com.rhlinkcon.repository.periciaMedica.PericiaMedicaRepository;
import com.rhlinkcon.util.Utils;

@Service
public class PacientePericiaMedicaService extends GenericService<PacientePericiaMedicaDto, Long> {

	@Autowired
	private PericiaMedicaRepository periciaMedicaRepository;

	@Autowired
	private PacientePericiaMedicaRepository pacientePericiaMedicaRepository;

	@Autowired
	private PacientePericiaTelefoneRepository pacientePericiaTelefoneRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ConsultaPericiaMedicaRepository consultaPericiaMedicaRepository;

	@Override
	public void create(PacientePericiaMedicaDto dto) {
		// TODO Auto-generated method stub

	}

	// Incrementa o não comparecimento
	public void updateComparecimento(String cpf) {

		PacientePericiaMedica pacientePericiaMedica = pacientePericiaMedicaRepository.getByCpf(cpf);

		Integer cont = pacientePericiaMedica.getContadorComparecimento() + 1;
		pacientePericiaMedica.setContadorComparecimento(cont);

		pacientePericiaMedicaRepository.save(pacientePericiaMedica);

	}

	@Override
	public void update(PacientePericiaMedicaDto request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PacientePericiaMedicaDto getById(Long id) {

		List<String> telefone = new ArrayList<String>();
		PacientePericiaMedica pacientePericiaMedica = pacientePericiaMedicaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PacientePericiaMedica", "id", id));

		PacientePericiaMedicaDto pacientePericiaMedicaDto = new PacientePericiaMedicaDto();
		pacientePericiaMedicaDto.setId(pacientePericiaMedica.getId());
		pacientePericiaMedicaDto.setCpf(pacientePericiaMedica.getCpf());
		pacientePericiaMedicaDto.setPaciente(pacientePericiaMedica.getNome());
		pacientePericiaMedicaDto.setTipoAnalise(pacientePericiaMedica == null ? TipoAnaliseEnum.APOSENTADORIA_POR_INVALIDEZ.toString(): TipoAnaliseEnum.REVISAO_DE_APOSENTADORIA.toString());
		pacientePericiaMedicaDto.setNumeroProcesso(null);
		pacientePericiaMedicaDto.setTelefone(null);

		if (pacientePericiaMedica != null) {
			pacientePericiaMedicaDto.setNumeroProcesso(pacientePericiaMedica.getNumeroProcesso());
			for (PacientePericiaTelefone ppt : pacientePericiaMedica.getPacientePericiaTelefone()) {
				telefone.add(ppt.getTelefone());
			}
			pacientePericiaMedicaDto.setTelefone(telefone);
		}

		List<ConsultaPericiaMedica> consultaPericiaMedica = periciaMedicaRepository
				.getConsultaPericiaMedicaByPacientePericiaMedicaId(pacientePericiaMedica.getId());

		if (consultaPericiaMedica.isEmpty() && pacientePericiaMedica.getContadorComparecimento() < 3) {
			pacientePericiaMedicaDto.setContador(0);

		} else {
			pacientePericiaMedicaDto.setContador(pacientePericiaMedica.getContadorComparecimento());
			
			List<ConsultaPericiaMedicaDto> listDto = new ArrayList<ConsultaPericiaMedicaDto>();
			for (ConsultaPericiaMedica consulta : consultaPericiaMedica) {
				
				if(pacientePericiaMedica.getContadorComparecimento() >= 3) {
					consulta.setTipoPericia("Não Presencial");
				}
				
				ConsultaPericiaMedicaDto dto = new ConsultaPericiaMedicaDto(consulta);
				listDto.add(dto);
			}
			pacientePericiaMedicaDto.setConsultasPericiasMedicasDto(listDto);
		}

		return pacientePericiaMedicaDto;
	}

	@Override
	public PagedResponse<PacientePericiaMedicaDto> getPagedList(PagedRequest pagedRequest,
			PacientePericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PacientePericiaMedicaDto> getList(PacientePericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(PacientePericiaMedicaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transient
	public List<PericiaMedicaResponse> getAllFuncionariosBySituacaoFuncional(int page, int size,
			FuncionarioFiltro funcionarioFiltro, String order) {

		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		/**
		 * lê da tabela Funcionario e utiliza pra gravar na tabela de Paciente Perícia
		 * Média
		 */
		Page<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro, pageable);
		List<ConsultaPericiaMedica> periciasMedicas = periciaMedicaRepository.findAll();

		List<PericiaMedicaResponse> periciasMedicasResponse = new ArrayList<>();

		for (Funcionario funcionario : funcionarios) {
			List<String> telefoneList = new ArrayList<>();
			
			if (Objects.nonNull(funcionario.getTelefonePrincipal())
					|| Objects.nonNull(funcionario.getTelefoneOpcional())
					|| Objects.nonNull(funcionario.getTelefoneComercial())) {

				if (Objects.nonNull(funcionario.getTelefonePrincipal())) {
					telefoneList.add(funcionario.getTelefonePrincipal());
				}
				if (Objects.nonNull(funcionario.getTelefoneOpcional())) {
					telefoneList.add(funcionario.getTelefoneOpcional());
				}
				if (Objects.nonNull(funcionario.getTelefoneComercial())) {
					telefoneList.add(funcionario.getTelefoneComercial());
				}
				
				//periciaMedicaResponse.setTelefones(telefoneList);
			}
			
			//só irá salvar na base de 'paciente_pericia_medica' se não exister o paciente com esse CPF
			if (!pacientePericiaMedicaRepository.existsByCpf(funcionario.getCpf())) {
				PacientePericiaMedica ppm = new PacientePericiaMedica();
				ppm.setFuncionario(new Funcionario(funcionario.getId()));
				ppm.setCid("0");
				ppm.setCpf(funcionario.getCpf());
				ppm.setNome(funcionario.getNome());
				ppm.setTipoAnalise(TipoAnaliseEnum.getEnumByString(TipoAnaliseEnum.REVISAO_DE_APOSENTADORIA.toString()));
				ppm.setStatus(StatusAgendamentoPericiaEnum.getEnumByString(StatusAgendamentoPericiaEnum.NAO_AGENDADO.toString()));
				ppm.setContadorComparecimento(0);
				pacientePericiaMedicaRepository.save(ppm);

				if (!telefoneList.isEmpty()) {
					for (String telefone : telefoneList) {
						PacientePericiaTelefone ppt = new PacientePericiaTelefone();
						ppt.setPacientePericiaMedica(new PacientePericiaMedica(ppm.getId()));
						ppt.setTelefone(telefone);

						pacientePericiaTelefoneRepository.save(ppt);
					}
				}
			} 
				
		}
		
		List<PacientePericiaMedica> pacientesPericiasMedicas = pacientePericiaMedicaRepository.findAllByUpdatedAt();
		
		if(!pacientesPericiasMedicas.isEmpty() && pacientesPericiasMedicas != null) {
			for(PacientePericiaMedica pacientePericiaMedica : pacientesPericiasMedicas) {
				PericiaMedicaResponse periciaMedicaResponse = new PericiaMedicaResponse();
				periciaMedicaResponse.setId(pacientePericiaMedica.getId());
				periciaMedicaResponse.setCpf(pacientePericiaMedica.getCpf());
				periciaMedicaResponse.setNome(pacientePericiaMedica.getNome());
				periciaMedicaResponse.setTipoAnalise(TipoAnaliseEnum.getEnumByLabel(TipoAnaliseEnum.REVISAO_DE_APOSENTADORIA.toString()));

				periciaMedicaResponse.setHorario(null);
				periciaMedicaResponse.setEspecialidade("");
				periciaMedicaResponse.setContadorComparecimento(pacientePericiaMedica.getContadorComparecimento());

				if (!periciasMedicas.isEmpty()) {
					for (ConsultaPericiaMedica consultaPericiaMedica : periciasMedicas) {
						if (consultaPericiaMedica.getPacientePericiaMedica().getFuncionario().getId().equals(pacientePericiaMedica.getId())) {
							periciaMedicaResponse.setHorario(consultaPericiaMedica.getAgendaMedicaData().getHorario());
							periciaMedicaResponse.setEspecialidade(consultaPericiaMedica.getEspecialidadeMedica().getNome());
							break;
						}
					}
				}

				List<String> telefoneList = new ArrayList<>();
				List<PacientePericiaTelefone> pacienteTelefoneList =  pacientePericiaMedica.getPacientePericiaTelefone();
				for(PacientePericiaTelefone pacientePericiaTelefone : pacienteTelefoneList) {
					telefoneList.add(pacientePericiaTelefone.getTelefone());
				}
				periciaMedicaResponse.setTelefones(telefoneList);
				
				periciasMedicasResponse.add(periciaMedicaResponse);
			}

		}

		return periciasMedicasResponse;

	}

	public PagedResponse<PericiaMedicaResponse> getAllPacientePericiaMedica(int page, int size,
			ConsultaPericiaMedicaFiltro consultaPericiaMedicaFiltro, String order) {

		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ConsultaPericiaMedica> consultasPericiasMedicas = consultaPericiaMedicaRepository.filtro(consultaPericiaMedicaFiltro, pageable);

		if (consultasPericiasMedicas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), consultasPericiasMedicas.getNumber(),
					consultasPericiasMedicas.getSize(), consultasPericiasMedicas.getTotalElements(),
					consultasPericiasMedicas.getTotalPages(), consultasPericiasMedicas.isLast());
		}

		List<PericiaMedicaResponse> periciasMedicasResponse = new ArrayList<>();

		for (ConsultaPericiaMedica consultaPericiaMedica : consultasPericiasMedicas) {
			List<String> telefoneList = new ArrayList<>();
			Date dataAtual = new Date();
			LocalDate localDate = dataAtual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			/*
			 * Pacientes que tiverem alguma consulta marcada no ano corrente, receberão o
			 * status “Agendado”, isso se a data atual, for inferior a data da consulta, do
			 * contrário a regra RN03.1 deverá modificar o “Status” do paciente.
			 */
			if (consultaPericiaMedica.getAgendaMedicaData().getData().getYear() == localDate.getYear()) {

				boolean isBefore = localDate.isBefore(consultaPericiaMedica.getAgendaMedicaData().getData());

				if (isBefore) {
					consultaPericiaMedica.getPacientePericiaMedica().setStatus(StatusAgendamentoPericiaEnum.AGENDADO);
					consultaPericiaMedicaRepository.save(consultaPericiaMedica);
				}

				PericiaMedicaResponse consultaPericiaMedicaResponse = new PericiaMedicaResponse(consultaPericiaMedica);
				periciasMedicasResponse.add(consultaPericiaMedicaResponse);
			}

		}

		return new PagedResponse<>(periciasMedicasResponse, consultasPericiasMedicas.getNumber(),
				consultasPericiasMedicas.getSize(), consultasPericiasMedicas.getTotalElements(),
				consultasPericiasMedicas.getTotalPages(), consultasPericiasMedicas.isLast());
	}

}
