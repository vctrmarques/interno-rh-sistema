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
import com.rhlinkcon.model.CategoriaDoenca;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.categoriaDoenca.CategoriaDoencaRequest;
import com.rhlinkcon.payload.categoriaDoenca.CategoriaDoencaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CategoriaDoencaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@Service
public class CategoriaDoencaService {

	@Autowired
	private CategoriaDoencaRepository CategoriaDoencaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<CategoriaDoencaResponse> searchAllByCodigoOrDescricao(String search) {
		List<CategoriaDoencaResponse> itensResponses = new ArrayList<CategoriaDoencaResponse>();
		CategoriaDoencaRepository.findByCodigoOrDescricaoAllIgnoreCaseContaining(search, search)
				.forEach(item -> itensResponses.add(new CategoriaDoencaResponse(item)));
		return itensResponses;
	}

	public CategoriaDoencaResponse getCategoriaDoencaResponseById(Long categoriaDoencaId) {
		CategoriaDoenca categoriaDoenca = CategoriaDoencaRepository.findById(categoriaDoencaId)
				.orElseThrow(() -> new ResourceNotFoundException("CategoriaDoenca", "id", categoriaDoencaId));

		CategoriaDoencaResponse categoriaDoencaResponse = new CategoriaDoencaResponse(categoriaDoenca);
		Usuario criadoPor = usuarioRepository.findById(categoriaDoenca.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", categoriaDoenca.getCreatedBy()));
		categoriaDoencaResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(categoriaDoenca.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", categoriaDoenca.getUpdatedBy()));
		categoriaDoencaResponse.setAlteradoPor(alteradoPor.getNome());

		return categoriaDoencaResponse;
	}

	public PagedResponse<CategoriaDoencaResponse> getAllCategoriaDoencas(int page, int size, String order,
			String descricao) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		Direction direction = Sort.Direction.ASC;
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CategoriaDoenca> categoriaDoencas = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			categoriaDoencas = CategoriaDoencaRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		} else {
			categoriaDoencas = CategoriaDoencaRepository.findAll(pageable);
		}

		if (categoriaDoencas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), categoriaDoencas.getNumber(),
					categoriaDoencas.getSize(), categoriaDoencas.getTotalElements(), categoriaDoencas.getTotalPages(),
					categoriaDoencas.isLast());
		}

		List<CategoriaDoencaResponse> categoriaDoencaResponses = categoriaDoencas.map(categoriaDoenca -> {
			return new CategoriaDoencaResponse(categoriaDoenca);
		}).getContent();
		return new PagedResponse<>(categoriaDoencaResponses, categoriaDoencas.getNumber(), categoriaDoencas.getSize(),
				categoriaDoencas.getTotalElements(), categoriaDoencas.getTotalPages(), categoriaDoencas.isLast());

	}

	public void deleteCategoriaDoenca(Long id) {
		CategoriaDoenca categoriaDoenca = CategoriaDoencaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CategoriaDoenca", "id", id));
		CategoriaDoencaRepository.delete(categoriaDoenca);
	}

	public void createCategoriaDoenca(CategoriaDoencaRequest categoriaDoencaRequest) {

		CategoriaDoenca categoriaDoenca = new CategoriaDoenca(categoriaDoencaRequest);
		
		CategoriaDoencaRepository.save(categoriaDoenca);

	}

	public void updateCategoriaDoenca(CategoriaDoencaRequest categoriaDoencaRequest) {

		CategoriaDoenca categoriaDoenca = CategoriaDoencaRepository.findById(categoriaDoencaRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("CategoriaDoenca", "id", categoriaDoencaRequest.getId()));

		categoriaDoenca.setCodigo(categoriaDoencaRequest.getCodigo());
		categoriaDoenca.setDescricao(categoriaDoencaRequest.getDescricao());
		categoriaDoenca.setAtivo(Utils.setBool(categoriaDoencaRequest.isAtivo()));

		CategoriaDoencaRepository.save(categoriaDoenca);

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
