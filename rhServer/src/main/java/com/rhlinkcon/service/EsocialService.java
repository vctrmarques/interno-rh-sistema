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
import com.rhlinkcon.model.Esocial;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.esocial.EsocialRequest;
import com.rhlinkcon.payload.esocial.EsocialResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EsocialRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class EsocialService {

	@Autowired
	private EsocialRepository esocialRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public EsocialResponse getById(Long id) {
		Esocial esocial = esocialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Esocial", "id", id));

		EsocialResponse esocialResponse = new EsocialResponse(esocial);
		Usuario criadoPor = usuarioRepository.findById(esocial.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", esocial.getCreatedBy()));
		esocialResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(esocial.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", esocial.getUpdatedBy()));
		esocialResponse.setAlteradoPor(alteradoPor.getNome());

		return esocialResponse;
	}

	public void delete(Long id) {
		Esocial esocial = esocialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Esocial", "id", id));
		esocialRepository.delete(esocial);
	}

	public void create(EsocialRequest esocialRequest) {

		Esocial esocial = new Esocial();
		esocial.setDescricao(esocialRequest.getDescricao());
		esocialRepository.save(esocial);

	}

	public void update(EsocialRequest esocialRequest) {

		Esocial esocial = esocialRepository.findById(esocialRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Esocial", "id", esocialRequest.getId()));

		esocial.setDescricao(esocialRequest.getDescricao());

		esocialRepository.save(esocial);

	}

	public List<EsocialResponse> searchAllByDescricao(String search) {
		List<EsocialResponse> itensResponses = new ArrayList<EsocialResponse>();
		esocialRepository.findByDescricaoIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new EsocialResponse(item)));
		return itensResponses;
	}

	public PagedResponse<EsocialResponse> getAllEsocial(int page, int size, String order, String descricao) {
		validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Esocial> esocials = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			esocials = esocialRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		} else {
			esocials = esocialRepository.findAll(pageable);
		}

		if (esocials.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), esocials.getNumber(), esocials.getSize(),
					esocials.getTotalElements(), esocials.getTotalPages(), esocials.isLast());
		}

		List<EsocialResponse> esocialResponses = esocials.map(esocial -> {
			return new EsocialResponse(esocial);
		}).getContent();
		return new PagedResponse<>(esocialResponses, esocials.getNumber(), esocials.getSize(),
				esocials.getTotalElements(), esocials.getTotalPages(), esocials.isLast());

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
