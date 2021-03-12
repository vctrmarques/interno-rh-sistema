package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ProcessoFuncao;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.processoFuncao.ProcessoFuncaoRequest;
import com.rhlinkcon.payload.processoFuncao.ProcessoFuncaoResponse;
import com.rhlinkcon.repository.ProcessoFuncaoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ProcessoFuncaoService {

	@Autowired
	private ProcessoFuncaoRepository processoFuncaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createProcessoFuncao(ProcessoFuncaoRequest processoFuncaoRequest) {

		ProcessoFuncao processoFuncao = new ProcessoFuncao(processoFuncaoRequest);
		processoFuncao.setDescricao(processoFuncaoRequest.getDescricao());

		processoFuncaoRepository.save(processoFuncao);

	}

	public void updateProcessoFuncao(ProcessoFuncaoRequest processoFuncaoRequest) {

		ProcessoFuncao processoFuncao = processoFuncaoRepository.findById(processoFuncaoRequest.getId()).orElseThrow(
				() -> new ResourceNotFoundException("ProcessoFuncao", "id", processoFuncaoRequest.getId()));

		processoFuncao.setDescricao(processoFuncaoRequest.getDescricao());
		processoFuncaoRepository.save(processoFuncao);
	}

	@Transactional
	public void deleteProcessoFuncao(Long id) {
		ProcessoFuncao processoFuncao = processoFuncaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ProcessoFuncao", "id", id));
		processoFuncaoRepository.delete(processoFuncao);
	}

	public List<ProcessoFuncaoResponse> searchAllByDescricao(String search) {
		List<ProcessoFuncaoResponse> itensResponses = new ArrayList<ProcessoFuncaoResponse>();
		processoFuncaoRepository.findByDescricaoIgnoreCaseContaining(search).forEach(item -> itensResponses.add(new ProcessoFuncaoResponse(item)));
		return itensResponses;
	}
	
	public PagedResponse<ProcessoFuncaoResponse> getAllProcessosFuncoes(int page, int size, String descricao,
			String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ProcessoFuncao> processosFuncoes = null;

		if (Utils.checkStr(descricao)) {
			processosFuncoes = processoFuncaoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		} else {
			processosFuncoes = processoFuncaoRepository.findAll(pageable);
		}

		if (processosFuncoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), processosFuncoes.getNumber(),
					processosFuncoes.getSize(), processosFuncoes.getTotalElements(), processosFuncoes.getTotalPages(),
					processosFuncoes.isLast());
		}

		List<ProcessoFuncaoResponse> processosFuncoesResponse = processosFuncoes.map(processoFuncao -> {
			return new ProcessoFuncaoResponse(processoFuncao);
		}).getContent();
		return new PagedResponse<>(processosFuncoesResponse, processosFuncoes.getNumber(), processosFuncoes.getSize(),
				processosFuncoes.getTotalElements(), processosFuncoes.getTotalPages(), processosFuncoes.isLast());

	}

	public ProcessoFuncaoResponse getProcessoFuncaoById(Long processoFuncaoId) {
		ProcessoFuncao processoFuncao = processoFuncaoRepository.findById(processoFuncaoId)
				.orElseThrow(() -> new ResourceNotFoundException("ProcessoFuncao", "id", processoFuncaoId));

		Usuario userCreated = usuarioRepository.findById(processoFuncao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", processoFuncao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(processoFuncao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", processoFuncao.getUpdatedBy()));

		ProcessoFuncaoResponse processoFuncaoResponse = new ProcessoFuncaoResponse(processoFuncao,
				processoFuncao.getCreatedAt(), userCreated.getNome(), processoFuncao.getUpdatedAt(),
				userUpdated.getNome());

		return processoFuncaoResponse;
	}
	
	public List<ProcessoFuncaoResponse> getAllProcessosFuncoes() {
		List<ProcessoFuncao> processosFuncoes = processoFuncaoRepository.findAll();

		List<ProcessoFuncaoResponse> processosFuncoesResponse = new ArrayList<>();
		for (ProcessoFuncao processoFuncao : processosFuncoes) {
			processosFuncoesResponse.add(new ProcessoFuncaoResponse(processoFuncao));
		}
		return processosFuncoesResponse;
	}
}
