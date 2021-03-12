package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Processo;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.processo.ProcessoRequest;
import com.rhlinkcon.payload.processo.ProcessoResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.ProcessoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class ProcessoService {
	@Autowired
	private ProcessoRepository processoRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private AnexoRepository anexoRepository;

	public PagedResponse<ProcessoResponse> getAllProcessos(int page, int size, String order, String nomeFuncionario) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Processo> processos = null;

		if (Utils.checkStr(nomeFuncionario))
			processos = processoRepository.findAllByFuncionarioNomeIgnoreCaseContaining(nomeFuncionario, pageable);
		else
			processos = processoRepository.findAll(pageable);

		if (processos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), processos.getNumber(), processos.getSize(), processos.getTotalElements(),
					processos.getTotalPages(), processos.isLast());
		}

		List<ProcessoResponse> processoResponse = processos.map(processo -> {
			return new ProcessoResponse(processo, Projecao.COMPLETA);
		}).getContent();
		return new PagedResponse<>(processoResponse, processos.getNumber(), processos.getSize(), processos.getTotalElements(), processos.getTotalPages(),
				processos.isLast());
	}

	public ProcessoResponse getProcessoById(Long processoId) {
		Processo processo = processoRepository.findById(processoId).orElseThrow(() -> new ResourceNotFoundException("CrmCrea", "id", processoId));
		ProcessoResponse processoResponse = new ProcessoResponse(processo, Projecao.COMPLETA);
		processoResponse.setCriadoPor(usuarioService.criadoPor(processo));
		processoResponse.setAlteradoPor(usuarioService.alteradoPor(processo));
		return processoResponse;
	}

	public void createProcesso(@Valid ProcessoRequest processoRequest) {
		List<Anexo> anexos = new ArrayList<>();
		Funcionario funcionario = funcionarioRepository.findById(processoRequest.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", processoRequest.getFuncionarioId()));
		
		if(!Objects.isNull(processoRequest.getAnexos()))
			anexos = anexoRepository.findAllByIdIn(processoRequest.getAnexos());
		

		Processo processo = new Processo(processoRequest.getId(), funcionario, processoRequest.getNumeroProcesso(), processoRequest.getAssunto(),
				processoRequest.getDataInicio(), processoRequest.getDataFim(), processoRequest.getRequerente(), processoRequest.getAtoPortaria(),
				processoRequest.getDataAto(), processoRequest.getDoe(), processoRequest.getDataDoe(), processoRequest.getImpactoFinanceiro(),
				processoRequest.getTipoReflexo(), processoRequest.getInicioImpactoFinanco(), processoRequest.getFimImpactoFinanco(), anexos,
				processoRequest.getSituacao());

		processoRepository.save(processo);
	}

	public void deleteProcesso(Long id) {
		Processo processo = processoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo", "id", id));
		if (!processo.getAnexos().isEmpty()) {
			processo.getAnexos().forEach(a -> deleteAnexo(a.getId()));
		}
		processoRepository.delete(processo);
	}

	public void deleteAnexo(Long id) {
		Processo processo = processoRepository.findAllByAnexosId(id);
		if (!Objects.isNull(processo)) {
			processo.getAnexos().removeIf(a -> a.getId().equals(id));
			processoRepository.save(processo);
		}
		Anexo anexo = anexoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", id));
		anexoRepository.delete(anexo);
	}

	public PagedResponse<FuncionarioResponse> getUsuarioComProcessos(int page, int size, String order, String nomeFuncionario) {
		Utils.validatePageNumberAndSize(page, size);
		Page<Funcionario> funcionarios = null;
		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		if (Utils.checkStr(nomeFuncionario)) {
			funcionarios = funcionarioRepository.findByFuncionarioCountProcessoLikeFuncionarioNome(pageable, nomeFuncionario);
		} else {
			funcionarios = funcionarioRepository.findByFuncionarioCountProcesso(pageable);

		}
		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();

		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse(funcionario.getId(), funcionario.getNome(), funcionario.getMatricula(),
					funcionario.getProcessos().stream().map(processo -> new ProcessoResponse(processo, Projecao.BASICA)).collect(Collectors.toList()));
			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(), funcionarios.getTotalElements(),
				funcionarios.getTotalPages(), funcionarios.isLast());
	}

}
