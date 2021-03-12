package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.SubCategoriaDoenca;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.subCategoriaDoenca.SubCategoriaDoencaRequest;
import com.rhlinkcon.payload.subCategoriaDoenca.SubCategoriaDoencaResponse;
import com.rhlinkcon.repository.SubCategoriaDoencaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class SubCategoriaDoencaService {

	@Autowired
	private SubCategoriaDoencaRepository subCategoriaDoencaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<SubCategoriaDoencaResponse> searchByCodigoOrDescricao(String search) {
		List<SubCategoriaDoencaResponse> itensResponses = new ArrayList<SubCategoriaDoencaResponse>();
		subCategoriaDoencaRepository.findByCodigoOrDescricaoAllIgnoreCaseContaining(search, search)
				.forEach(item -> itensResponses.add(new SubCategoriaDoencaResponse(item)));
		return itensResponses;
	}

	public SubCategoriaDoencaResponse getSubCategoriaDoencaById(Long subCategoriaDoencaId) {
		SubCategoriaDoenca subCategoriaDoenca = subCategoriaDoencaRepository.findById(subCategoriaDoencaId)
				.orElseThrow(() -> new ResourceNotFoundException("SubCategoriaDoenca", "id", subCategoriaDoencaId));

		SubCategoriaDoencaResponse subCategoriaDoencaResponse = new SubCategoriaDoencaResponse(subCategoriaDoenca);
		Usuario criadoPor = usuarioRepository.findById(subCategoriaDoenca.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", subCategoriaDoenca.getCreatedBy()));
		subCategoriaDoencaResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(subCategoriaDoenca.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", subCategoriaDoenca.getUpdatedBy()));
		subCategoriaDoencaResponse.setAlteradoPor(alteradoPor.getNome());

		return subCategoriaDoencaResponse;
	}

	public PagedResponse<SubCategoriaDoencaResponse> getAllSubCategoriaDoencas(int page, int size, String order,
			String nome) {
		validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<SubCategoriaDoenca> subCategoriaDoencas = null;

		if (!Objects.isNull(nome) && !nome.isEmpty()) {
			subCategoriaDoencas = subCategoriaDoencaRepository.findByDescricaoIgnoreCaseContaining(nome, pageable);
		} else {
			subCategoriaDoencas = subCategoriaDoencaRepository.findAll(pageable);
		}

		if (subCategoriaDoencas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), subCategoriaDoencas.getNumber(),
					subCategoriaDoencas.getSize(), subCategoriaDoencas.getTotalElements(),
					subCategoriaDoencas.getTotalPages(), subCategoriaDoencas.isLast());
		}

		List<SubCategoriaDoencaResponse> subCategoriaDoencaResponses = subCategoriaDoencas.map(subCategoriaDoenca -> {
			return new SubCategoriaDoencaResponse(subCategoriaDoenca);
		}).getContent();
		return new PagedResponse<>(subCategoriaDoencaResponses, subCategoriaDoencas.getNumber(),
				subCategoriaDoencas.getSize(), subCategoriaDoencas.getTotalElements(),
				subCategoriaDoencas.getTotalPages(), subCategoriaDoencas.isLast());

	}

	public void deleteSubCategoriaDoenca(Long id) {
		SubCategoriaDoenca subCategoriaDoenca = subCategoriaDoencaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("SubCategoriaDoenca", "id", id));
		subCategoriaDoencaRepository.delete(subCategoriaDoenca);
	}

	public void createSubCategoriaDoenca(SubCategoriaDoencaRequest subCategoriaDoencaRequest) {

		SubCategoriaDoenca subCategoriaDoenca = new SubCategoriaDoenca(subCategoriaDoencaRequest);

		subCategoriaDoencaRepository.save(subCategoriaDoenca);

	}

	public void updateSubCategoriaDoenca(SubCategoriaDoencaRequest subCategoriaDoencaRequest) {

		SubCategoriaDoenca subCategoriaDoenca = subCategoriaDoencaRepository.findById(subCategoriaDoencaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("SubCategoriaDoenca", "id",
						subCategoriaDoencaRequest.getId()));

		subCategoriaDoenca.setCodigo(subCategoriaDoencaRequest.getCodigo());
		subCategoriaDoenca.setDescricao(subCategoriaDoencaRequest.getDescricao());

		subCategoriaDoencaRepository.save(subCategoriaDoenca);

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
