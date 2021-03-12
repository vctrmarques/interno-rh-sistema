package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.DeclaracaoExServidorFiltro;
import com.rhlinkcon.model.DeclaracaoExServidor;
import com.rhlinkcon.model.DeclaracaoExServidorDadosFuncionais;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.StatusDeclaracaoExServidorEnum;
import com.rhlinkcon.payload.declaracaoExServidor.DeclaracaoExServidorDadosFuncionaisRequest;
import com.rhlinkcon.payload.declaracaoExServidor.DeclaracaoExServidorDadosFuncionaisResponse;
import com.rhlinkcon.payload.declaracaoExServidor.DeclaracaoExServidorRequest;
import com.rhlinkcon.payload.declaracaoExServidor.DeclaracaoExServidorResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.declaracaoExServidor.DeclaracaoExServidorRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class DeclaracaoExServidorService {

	@Autowired
	private DeclaracaoExServidorRepository repository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private DeclaracaoExServidorDadosFuncionaisService dadosFuncionaisService;

	public PagedResponse<DeclaracaoExServidorResponse> getAll(int page, int size, String order,
			DeclaracaoExServidorFiltro declaracaoExServidorFiltro) {
		Utils.validatePageNumberAndSize(page, size);
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<DeclaracaoExServidor> lista = repository.filtro(declaracaoExServidorFiltro, pageable);

		if (lista.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lista.getNumber(), lista.getSize(),
					lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
		}

		List<DeclaracaoExServidorResponse> listaResponse = lista.map(item -> {
			return new DeclaracaoExServidorResponse(item, Projecao.MEDIA);
		}).getContent();

		return new PagedResponse<>(listaResponse, lista.getNumber(), lista.getSize(), lista.getTotalElements(),
				lista.getTotalPages(), lista.isLast());
	}

	@Transactional
	public DeclaracaoExServidorResponse create(@Valid DeclaracaoExServidorRequest request) {
		DeclaracaoExServidor obj = new DeclaracaoExServidor();

		if (Objects.nonNull(request.getId()) && request.getId() != 0) {
			obj = repository.findById(request.getId())
					.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoExServidor", "id", request.getId()));

			if (obj.getStatus().equals(StatusDeclaracaoExServidorEnum.ARQUIVADO))
				throw new BadRequestException("Nâo é permitido editar declaração ex-servidor arquivada.");
		}

		if (request.isRascunho()) {
			obj.setStatus(StatusDeclaracaoExServidorEnum.RASCUNHO);
		} else {
			obj.setStatus(StatusDeclaracaoExServidorEnum.AGUARDANDO_IMPRESSAO);
		}

		if (Objects.nonNull(request.getFuncionarioId())) {
			Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));
			obj.setFuncionario(funcionario);
		}
		if (Objects.nonNull(request.getResponsavelId())) {
			Funcionario responsavel = funcionarioRepository.findById(request.getResponsavelId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getResponsavelId()));
			obj.setResponsavel(responsavel);
		}
		if (Objects.nonNull(request.getDirigenteId())) {
			Funcionario dirigente = funcionarioRepository.findById(request.getDirigenteId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getDirigenteId()));
			obj.setDirigente(dirigente);
		}

		repository.save(obj);

		// Dados Funcionais
		if (Utils.checkSetList(request.getDadosFuncionais())) {
			List<Long> listDadosFuncionaisIds = new ArrayList<Long>();

			if (Utils.checkSetList(obj.getDadosFuncionais())) {
				obj.getDadosFuncionais()
						.forEach(dadosFuncionais -> listDadosFuncionaisIds.add(dadosFuncionais.getId()));
			}

			List<Long> dadosFuncionaisIds = new ArrayList<Long>();

			for (DeclaracaoExServidorDadosFuncionaisRequest item : request.getDadosFuncionais()) {
				if (Objects.isNull(item.getId())) {
					item.setDeclaracaoExServidorId(obj.getId());
					dadosFuncionaisService.create(item);
				}
				dadosFuncionaisIds.add(item.getId());
			}

			for (Long dadosFuncionaisId : listDadosFuncionaisIds) {
				if (!dadosFuncionaisIds.contains(dadosFuncionaisId)) {
					dadosFuncionaisService.delete(dadosFuncionaisId);
				}
			}
		}

		return new DeclaracaoExServidorResponse(obj, Projecao.BASICA);

	}

	public void delete(Long id) {
		DeclaracaoExServidor d = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoExServidor", "id", id));

		if (d.getStatus().equals(StatusDeclaracaoExServidorEnum.ARQUIVADO))
			throw new BadRequestException("Nâo é permitido excluir declaração ex-servidor arquivada.");

		List<DeclaracaoExServidorDadosFuncionais> dados = dadosFuncionaisService.findByDeclaracaoId(id);

		if (Utils.checkList(dados)) {
			for (DeclaracaoExServidorDadosFuncionais item : dados) {
				dadosFuncionaisService.delete(item.getId());
			}
		}

		repository.deleteById(id);
	}

	public void arquivar(Long id) {
		DeclaracaoExServidor d = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoExServidor", "id", id));
		d.setStatus(StatusDeclaracaoExServidorEnum.ARQUIVADO);
		repository.save(d);
	}

	public DeclaracaoExServidorResponse getById(Long id) {
		DeclaracaoExServidor d = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoExServidor", "id", id));

		DeclaracaoExServidorResponse response = new DeclaracaoExServidorResponse(d, Projecao.COMPLETA);

		if (Utils.checkSetList(d.getDadosFuncionais())) {
			response.setDadosFuncionais(new ArrayList<>());
			d.getDadosFuncionais().forEach(
					e -> response.getDadosFuncionais().add(new DeclaracaoExServidorDadosFuncionaisResponse(e)));
		}

		return response;
	}

}
