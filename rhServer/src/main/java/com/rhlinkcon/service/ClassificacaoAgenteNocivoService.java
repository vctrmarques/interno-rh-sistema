package com.rhlinkcon.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ClassificacaoAgenteNocivo;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoRequest;
import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ClassificacaoAgenteNocivoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class ClassificacaoAgenteNocivoService {

	@Autowired
	private ClassificacaoAgenteNocivoRepository agenteNocivoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createClassificacaoAgenteNocivo(ClassificacaoAgenteNocivoRequest agenteNocivoRequest) {

		// Creating user's account
		ClassificacaoAgenteNocivo agenteNocivo = new ClassificacaoAgenteNocivo(agenteNocivoRequest);

		agenteNocivoRepository.save(agenteNocivo);

	}

	public void updateClassificacaoAgenteNocivo(ClassificacaoAgenteNocivoRequest agenteNocivoRequest) {

		// Updating user's account
		ClassificacaoAgenteNocivo agenteNocivo = agenteNocivoRepository.findById(agenteNocivoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAgenteNocivo", "id", agenteNocivoRequest.getId()));

		agenteNocivo.setCodigo(agenteNocivoRequest.getCodigo());
		agenteNocivo.setDescricao(agenteNocivoRequest.getDescricao());
		agenteNocivo.setTempoExposicao(agenteNocivoRequest.getTempoExposicao());

		agenteNocivoRepository.save(agenteNocivo);

	}

	public void deleteClassificacaoAgenteNocivo(Long id) {
		ClassificacaoAgenteNocivo agenteNocivo = agenteNocivoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAgenteNocivo", "id", id));

		agenteNocivoRepository.delete(agenteNocivo);
	}

	public PagedResponse<ClassificacaoAgenteNocivoResponse> getAllClassificacoesAgentesNocivos(int page, int size, String descricao, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ClassificacaoAgenteNocivo> classificacoes = agenteNocivoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (classificacoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), classificacoes.getNumber(), classificacoes.getSize(),
					classificacoes.getTotalElements(), classificacoes.getTotalPages(), classificacoes.isLast());
		}

		List<ClassificacaoAgenteNocivoResponse> classificacoesResponse = classificacoes.map(classificacaoAgenteNocivoResponse -> {
			return new ClassificacaoAgenteNocivoResponse(classificacaoAgenteNocivoResponse);
		}).getContent();
		return new PagedResponse<>(classificacoesResponse, classificacoes.getNumber(), classificacoes.getSize(), classificacoes.getTotalElements(),
				classificacoes.getTotalPages(), classificacoes.isLast());

	}

	public ClassificacaoAgenteNocivoResponse getClassificacaoAgenteNocivoById(Long agenteNocivoId) {
		ClassificacaoAgenteNocivo classificacaoAgenteNocivo = agenteNocivoRepository.findById(agenteNocivoId)
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAgenteNocivo", "id", agenteNocivoId));

		Usuario userCreated = usuarioRepository.findById(classificacaoAgenteNocivo.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", classificacaoAgenteNocivo.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(classificacaoAgenteNocivo.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", classificacaoAgenteNocivo.getUpdatedBy()));

		ClassificacaoAgenteNocivoResponse classificacaoAgenteNocivoResponse = new ClassificacaoAgenteNocivoResponse(classificacaoAgenteNocivo, classificacaoAgenteNocivo.getCreatedAt(), 
											userCreated.getNome(), classificacaoAgenteNocivo.getUpdatedAt(), userUpdated.getNome());

		return classificacaoAgenteNocivoResponse;
	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

}
