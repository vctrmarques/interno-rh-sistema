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
import com.rhlinkcon.model.PatronalSindicatoEnum;
import com.rhlinkcon.model.Sindicato;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.sindicato.SindicatoRequest;
import com.rhlinkcon.payload.sindicato.SindicatoResponse;
import com.rhlinkcon.repository.SindicatoRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class SindicatoService {

	@Autowired
	private SindicatoRepository sindicatoRepository;
	
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createSindicato(SindicatoRequest sindicatoRequest) {

		Sindicato sindicato = new Sindicato(sindicatoRequest);
		
		sindicato.setUnidadeFederativa(unidadeFederativaRepository.findById(sindicatoRequest.getUnidadeFederativaId()).orElseThrow(
				() -> new ResourceNotFoundException("Sindicato", "id", sindicatoRequest.getUnidadeFederativaId())));

		sindicatoRepository.save(sindicato);

	}

	public void updateSindicato(SindicatoRequest sindicatoRequest) {

		Sindicato sindicato = sindicatoRepository.findById(sindicatoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Sindicato", "id", sindicatoRequest.getId()));

		sindicato.setDescricao(sindicatoRequest.getDescricao());
		sindicato.setMesBase(sindicatoRequest.getMesBase());
		sindicato.setPisoSalarial(sindicatoRequest.getPisoSalarial());
		sindicato.setCnpj(sindicatoRequest.getCnpj());
		sindicato.setCodigoEntidade(sindicatoRequest.getCodigoEntidade());
		sindicato.setDdd(sindicatoRequest.getDdd());
		sindicato.setTelefone(sindicatoRequest.getTelefone());
		sindicato.setPatronal(PatronalSindicatoEnum.getEnumByString(sindicatoRequest.getPatronal()));
		sindicato.setEndereco(sindicatoRequest.getEndereco());
		sindicato.setNumero(sindicatoRequest.getNumero());
		sindicato.setComplemento(sindicatoRequest.getComplemento());
		sindicato.setBairro(sindicatoRequest.getBairro());
		sindicato.setMunicipio(sindicatoRequest.getMunicipio());
		sindicato.setCep(sindicatoRequest.getCep());
		
		sindicato.setUnidadeFederativa(unidadeFederativaRepository.findById(sindicatoRequest.getUnidadeFederativaId()).orElseThrow(
				() -> new ResourceNotFoundException("Sindicato", "id", sindicatoRequest.getUnidadeFederativaId())));

		sindicatoRepository.save(sindicato);

	}

	public void deleteSindicato(Long id) {
		Sindicato sindicato = sindicatoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sindicato", "id", id));

		sindicatoRepository.delete(sindicato);
	}
	
	public List<SindicatoResponse> searchAllByDescricao(String search) {
		List<SindicatoResponse> itensResponses = new ArrayList<SindicatoResponse>();
		sindicatoRepository.findByDescricaoIgnoreCaseContaining(search).forEach(item -> itensResponses.add(new SindicatoResponse(item)));
		return itensResponses;
	}

	public PagedResponse<SindicatoResponse> getAllSindicatos(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Sindicato> sindicatos = sindicatoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (sindicatos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), sindicatos.getNumber(), sindicatos.getSize(),
					sindicatos.getTotalElements(), sindicatos.getTotalPages(), sindicatos.isLast());
		}

		List<SindicatoResponse> sindicatosResponse = sindicatos.map(sindicatoResponse -> {
			return new SindicatoResponse(sindicatoResponse);
		}).getContent();
		return new PagedResponse<>(sindicatosResponse, sindicatos.getNumber(), sindicatos.getSize(),
				sindicatos.getTotalElements(), sindicatos.getTotalPages(), sindicatos.isLast());

	}

	public SindicatoResponse getSindicatoById(Long sindicatoId) {
		Sindicato sindicato = sindicatoRepository.findById(sindicatoId)
				.orElseThrow(() -> new ResourceNotFoundException("Sindicato", "id", sindicatoId));

		Usuario userCreated = usuarioRepository.findById(sindicato.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", sindicato.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(sindicato.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", sindicato.getUpdatedBy()));

		SindicatoResponse sindicatoResponse = new SindicatoResponse(sindicato, sindicato.getCreatedAt(),
				userCreated.getNome(), sindicato.getUpdatedAt(), userUpdated.getNome());

		return sindicatoResponse;
		
	}

	public List<SindicatoResponse> getAllSindicatos() {

		List<Sindicato> sindicatos = sindicatoRepository.findAll();
		List<SindicatoResponse> sindicatosResponse = new ArrayList();

		if (!sindicatos.isEmpty()) {
			for (Sindicato sindicato: sindicatos) {
				sindicatosResponse.add(new SindicatoResponse(sindicato));
			}
			return sindicatosResponse;
		}

		return null;
	}

}
