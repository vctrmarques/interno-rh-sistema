package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaRequest;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class UnidadeFederativaService {

	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<DadoBasicoDto> getDadoBasicoList() {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();

		List<UnidadeFederativa> unidadeFederativaList = unidadeFederativaRepository.findAll();

		unidadeFederativaList.forEach(persistentId -> {
			dadoBasicoList.add(new DadoBasicoDto(persistentId));
		});

		return dadoBasicoList;
	}

	public void createUnidadeFederativa(UnidadeFederativaRequest unidadeFederativaRequest) {

		UnidadeFederativa unidadeFederativa = new UnidadeFederativa(unidadeFederativaRequest);

		unidadeFederativaRepository.save(unidadeFederativa);

	}

	public void updateUnidadeFederativa(UnidadeFederativaRequest unidadeFederativaRequest) {

		UnidadeFederativa unidadeFederativa = unidadeFederativaRepository.findById(unidadeFederativaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id",
						unidadeFederativaRequest.getId()));

		unidadeFederativa.setSigla(unidadeFederativaRequest.getSigla());
		unidadeFederativa.setEstado(unidadeFederativaRequest.getEstado());

		unidadeFederativaRepository.save(unidadeFederativa);

	}

	public void deleteUnidadeFederativa(Long id) {
		UnidadeFederativa unidadeFederativa = unidadeFederativaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id", id));

		unidadeFederativaRepository.delete(unidadeFederativa);
	}

	public PagedResponse<UnidadeFederativaResponse> getAllUnidadesFederativas(int page, int size, String sigla,
			String order) {
		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<UnidadeFederativa> unidadesFederativas = unidadeFederativaRepository.findBySiglaIgnoreCaseContaining(sigla,
				pageable);

		if (unidadesFederativas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), unidadesFederativas.getNumber(),
					unidadesFederativas.getSize(), unidadesFederativas.getTotalElements(),
					unidadesFederativas.getTotalPages(), unidadesFederativas.isLast());
		}

		List<UnidadeFederativaResponse> unidadesFederativasResponse = unidadesFederativas.map(unidadeFederativa -> {
			return new UnidadeFederativaResponse(unidadeFederativa);
		}).getContent();
		return new PagedResponse<>(unidadesFederativasResponse, unidadesFederativas.getNumber(),
				unidadesFederativas.getSize(), unidadesFederativas.getTotalElements(),
				unidadesFederativas.getTotalPages(), unidadesFederativas.isLast());

	}

	public UnidadeFederativaResponse getUnidadeFederativaById(Long unidadeFederativaId) {
		UnidadeFederativa unidadeFederativa = unidadeFederativaRepository.findById(unidadeFederativaId)
				.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id", unidadeFederativaId));

		Usuario userCreated = usuarioRepository.findById(unidadeFederativa.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", unidadeFederativa.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(unidadeFederativa.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", unidadeFederativa.getUpdatedBy()));

		UnidadeFederativaResponse unidadeFederativaResponse = new UnidadeFederativaResponse(unidadeFederativa,
				unidadeFederativa.getCreatedAt(), userCreated.getNome(), unidadeFederativa.getUpdatedAt(),
				userUpdated.getNome());

		return unidadeFederativaResponse;
	}

	public List<UnidadeFederativaResponse> getAllUnidadesFederativas() {

		List<UnidadeFederativa> unidadesFederativas = unidadeFederativaRepository.findAll();
		List<UnidadeFederativaResponse> unidadeFederativaResponse = new ArrayList<>();

		if (!unidadesFederativas.isEmpty()) {
			for (UnidadeFederativa unidadeFederativa : unidadesFederativas) {
				UnidadeFederativaResponse unidadesFederativasResp = new UnidadeFederativaResponse(unidadeFederativa);

				unidadeFederativaResponse.add(unidadesFederativasResp);
			}
			return unidadeFederativaResponse;
		}

		return null;
	}

}
