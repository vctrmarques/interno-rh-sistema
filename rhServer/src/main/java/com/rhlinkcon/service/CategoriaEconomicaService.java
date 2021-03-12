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
import com.rhlinkcon.model.Atividade;
import com.rhlinkcon.model.CategoriaEconomica;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.atividade.AtividadeResponse;
import com.rhlinkcon.payload.categoriaEconomica.CategoriaEconomicaRequest;
import com.rhlinkcon.payload.categoriaEconomica.CategoriaEconomicaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CategoriaEconomicaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class CategoriaEconomicaService {

	@Autowired
	private CategoriaEconomicaRepository categoriaEconomicaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createCategoriaEconomica(CategoriaEconomicaRequest categoriaEconomicaRequest) {

		// Creating user's account
		CategoriaEconomica categoriaEconomica = new CategoriaEconomica(categoriaEconomicaRequest);

		categoriaEconomicaRepository.save(categoriaEconomica);

	}

	public void updateCategoriaEconomica(CategoriaEconomicaRequest categoriaEconomicaRequest) {

		// Updating user's account
		CategoriaEconomica categoriaEconomica = categoriaEconomicaRepository.findById(categoriaEconomicaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("CategoriaEconomica", "id", categoriaEconomicaRequest.getId()));

		categoriaEconomica.setCodigo(categoriaEconomicaRequest.getCodigo());
		categoriaEconomica.setDescricao(categoriaEconomicaRequest.getDescricao());

		categoriaEconomicaRepository.save(categoriaEconomica);

	}

	public void deleteCategoriaEconomica(Long id) {
		CategoriaEconomica categoriaEconomica = categoriaEconomicaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CategoriaEconomica", "id", id));

		categoriaEconomicaRepository.delete(categoriaEconomica);
	}

	public PagedResponse<CategoriaEconomicaResponse> getAllCategoriasEconomicas(int page, int size, String descricao, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CategoriaEconomica> categorias = categoriaEconomicaRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (categorias.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), categorias.getNumber(), categorias.getSize(),
					categorias.getTotalElements(), categorias.getTotalPages(), categorias.isLast());
		}

		List<CategoriaEconomicaResponse> categoriasResponse = categorias.map(categoriaEconomica -> {
			return new CategoriaEconomicaResponse(categoriaEconomica);
		}).getContent();
		return new PagedResponse<>(categoriasResponse, categorias.getNumber(), categorias.getSize(), categorias.getTotalElements(),
				categorias.getTotalPages(), categorias.isLast());

	}

	public CategoriaEconomicaResponse getCategoriaEconomicaById(Long categoriaEconomicaId) {
		CategoriaEconomica categoriaEconomica = categoriaEconomicaRepository.findById(categoriaEconomicaId)
				.orElseThrow(() -> new ResourceNotFoundException("CategoriaEconomica", "id", categoriaEconomicaId));

		Usuario userCreated = usuarioRepository.findById(categoriaEconomica.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", categoriaEconomica.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(categoriaEconomica.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", categoriaEconomica.getUpdatedBy()));

		CategoriaEconomicaResponse categoriaEconomicaResponse = new CategoriaEconomicaResponse(categoriaEconomica, categoriaEconomica.getCreatedAt(), 
											userCreated.getNome(), categoriaEconomica.getUpdatedAt(), userUpdated.getNome());

		return categoriaEconomicaResponse;
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
