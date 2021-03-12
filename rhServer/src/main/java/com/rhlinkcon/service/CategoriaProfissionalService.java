package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.CategoriaProfissional;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.categoriaProfissional.CategoriaProfissionalRequest;
import com.rhlinkcon.payload.categoriaProfissional.CategoriaProfissionalResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CategoriaProfissionalRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CategoriaProfissionalService {

	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public CategoriaProfissionalResponse getCategoriaProfissionalById(Long categoriaProfissionalId) {
		CategoriaProfissional categoriaProfissional = categoriaProfissionalRepository.findById(categoriaProfissionalId)
				.orElseThrow(
						() -> new ResourceNotFoundException("CategoriaProfissional", "id", categoriaProfissionalId));

		CategoriaProfissionalResponse categoriaProfissionalResponse = new CategoriaProfissionalResponse(
				categoriaProfissional);
		Usuario criadoPor = usuarioRepository.findById(categoriaProfissional.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", categoriaProfissional.getCreatedBy()));
		categoriaProfissionalResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(categoriaProfissional.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", categoriaProfissional.getUpdatedBy()));
		categoriaProfissionalResponse.setAlteradoPor(alteradoPor.getNome());

		return categoriaProfissionalResponse;
	}

	public List<CategoriaProfissionalResponse> searchAllByDescricao(String search) {
		List<CategoriaProfissionalResponse> itensResponses = new ArrayList<CategoriaProfissionalResponse>();
		categoriaProfissionalRepository.findByDescricaoIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new CategoriaProfissionalResponse(item)));
		return itensResponses;
	}

	public PagedResponse<CategoriaProfissionalResponse> getAllCategoriasProfissionais(int page, int size, String order,
			String descricao) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CategoriaProfissional> categoriaProfissionals = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			categoriaProfissionals = categoriaProfissionalRepository.findByDescricaoIgnoreCaseContaining(descricao,
					pageable);
		} else {
			categoriaProfissionals = categoriaProfissionalRepository.findAll(pageable);
		}

		if (categoriaProfissionals.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), categoriaProfissionals.getNumber(),
					categoriaProfissionals.getSize(), categoriaProfissionals.getTotalElements(),
					categoriaProfissionals.getTotalPages(), categoriaProfissionals.isLast());
		}

		List<CategoriaProfissionalResponse> categoriaProfissionalResponses = categoriaProfissionals
				.map(categoriaProfissional -> {
					return new CategoriaProfissionalResponse(categoriaProfissional);
				}).getContent();
		return new PagedResponse<>(categoriaProfissionalResponses, categoriaProfissionals.getNumber(),
				categoriaProfissionals.getSize(), categoriaProfissionals.getTotalElements(),
				categoriaProfissionals.getTotalPages(), categoriaProfissionals.isLast());

	}

	public void deleteCategoriaProfissional(Long id) {
		CategoriaProfissional categoriaProfissional = categoriaProfissionalRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CategoriaProfissional", "id", id));
		categoriaProfissionalRepository.delete(categoriaProfissional);
	}

	public void createCategoriaProfissional(CategoriaProfissionalRequest categoriaProfissionalRequest) {

		CategoriaProfissional categoriaProfissional = new CategoriaProfissional(categoriaProfissionalRequest);
		
		categoriaProfissionalRepository.save(categoriaProfissional);

	}

	public void updateCategoriaProfissional(CategoriaProfissionalRequest categoriaProfissionalRequest) {
		
		CategoriaProfissional categoriaProfissional = categoriaProfissionalRepository
				.findById(categoriaProfissionalRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("CategoriaProfissional", "id",
						categoriaProfissionalRequest.getId()));
		
		
		categoriaProfissional.setCodigo(categoriaProfissionalRequest.getCodigo());
		categoriaProfissional.setDescricao(categoriaProfissionalRequest.getDescricao());
		if(Objects.nonNull(categoriaProfissionalRequest.getVerbasId())) {
			List<Verba> verbas = categoriaProfissionalRequest.getVerbasId().stream().map(vId->
				new Verba(vId)
			).collect(Collectors.toList());
			categoriaProfissional.setVerbas(verbas);
		}
		
		categoriaProfissionalRepository.save(categoriaProfissional);

	}

	public List<CategoriaProfissionalResponse> getAllCategoriasProfissionais() {
		List<CategoriaProfissional> categoriasProfissionais = categoriaProfissionalRepository.findAll();

		List<CategoriaProfissionalResponse> categoriasProfissionaisResponse = new ArrayList<>();
		for (CategoriaProfissional categoriaProfissional : categoriasProfissionais) {
			categoriasProfissionaisResponse.add(new CategoriaProfissionalResponse(categoriaProfissional));
		}
		return categoriasProfissionaisResponse;
	}
}
