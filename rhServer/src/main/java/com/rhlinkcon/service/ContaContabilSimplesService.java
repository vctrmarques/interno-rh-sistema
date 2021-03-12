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
import com.rhlinkcon.model.ContaContabilSimples;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoResponse;
import com.rhlinkcon.payload.contaContabilSimples.ContaContabilSimplesRequest;
import com.rhlinkcon.payload.contaContabilSimples.ContaContabilSimplesResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ContaContabilSimplesRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@Service
public class ContaContabilSimplesService {

	@Autowired
	private ContaContabilSimplesRepository contaContabilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createContaContabilSimples(ContaContabilSimplesRequest contaContabilRequest) {
		if (contaContabilRepository.existsByDescricao(contaContabilRequest.getDescricao()))
			throw new BadRequestException("Esta Conta Contábil já existe!");
		if (contaContabilRepository.existsByCodigo(contaContabilRequest.getCodigo()))
			throw new BadRequestException("Este Código já esta cadastrado no sistema!");
		ContaContabilSimples contaContabil = new ContaContabilSimples(contaContabilRequest);
		contaContabilRepository.save(contaContabil);
	}

	public void updateContaContabilSimples(ContaContabilSimplesRequest contaContabilRequest) {

		// Updating user's account
		ContaContabilSimples contaContabil = contaContabilRepository.findById(contaContabilRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ContaContabilSimples", "id", contaContabilRequest.getId()));

		contaContabil.setCodigo(contaContabilRequest.getCodigo());
		contaContabil.setDescricao(contaContabilRequest.getDescricao());

		contaContabilRepository.save(contaContabil);

	}

	public void deleteContaContabilSimples(Long id) {
		ContaContabilSimples contaContabil = contaContabilRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ContaContabilSimples", "id", id));

		contaContabilRepository.delete(contaContabil);
	}

	public PagedResponse<ContaContabilSimplesResponse> getAllContasContabeisSimples(int page, int size, String descricao, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ContaContabilSimples> contasContabeis = contaContabilRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (contasContabeis.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), contasContabeis.getNumber(), contasContabeis.getSize(),
					contasContabeis.getTotalElements(), contasContabeis.getTotalPages(), contasContabeis.isLast());
		}

		List<ContaContabilSimplesResponse> contasContabeisResponse = contasContabeis.map(contaContabilSimples -> {
			return new ContaContabilSimplesResponse(contaContabilSimples);
		}).getContent();
		return new PagedResponse<>(contasContabeisResponse, contasContabeis.getNumber(), contasContabeis.getSize(), contasContabeis.getTotalElements(),
				contasContabeis.getTotalPages(), contasContabeis.isLast());

	}

	public ContaContabilSimplesResponse getContaContabilSimplesById(Long contaContabilId) {
		ContaContabilSimples contaContabilSimples = contaContabilRepository.findById(contaContabilId)
				.orElseThrow(() -> new ResourceNotFoundException("ContaContabilSimples", "id", contaContabilId));

		Usuario userCreated = usuarioRepository.findById(contaContabilSimples.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", contaContabilSimples.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(contaContabilSimples.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", contaContabilSimples.getUpdatedBy()));

		ContaContabilSimplesResponse contaContabilSimplesResponse = new ContaContabilSimplesResponse(contaContabilSimples, contaContabilSimples.getCreatedAt(), 
											userCreated.getNome(), contaContabilSimples.getUpdatedAt(), userUpdated.getNome());

		return contaContabilSimplesResponse;
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
