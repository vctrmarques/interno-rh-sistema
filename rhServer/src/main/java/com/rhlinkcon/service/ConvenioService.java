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
//import com.rhlinkcon.model.ClassificacaoAgenteNocivo;
import com.rhlinkcon.model.Convenio;
import com.rhlinkcon.model.Usuario;
//import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoResponse;
import com.rhlinkcon.payload.convenio.ConvenioRequest;
import com.rhlinkcon.payload.convenio.ConvenioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ConvenioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ConvenioService {

	@Autowired
	private ConvenioRepository convenioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<ConvenioResponse> searchByCodigoOrDescricao(String search) {
		List<ConvenioResponse> itensResponses = new ArrayList<ConvenioResponse>();
		Integer searchInt = null;
		try {
			searchInt = Integer.parseInt(search);
		} catch (Exception e) {
			searchInt = null;
		}
		convenioRepository.findByIdOrDescricaoAllIgnoreCaseContaining(searchInt, search)
				.forEach(item -> itensResponses.add(new ConvenioResponse(item)));
		return itensResponses;
	}

	public void createConvenio(ConvenioRequest convenioRequest) {

		// Creating user's account
		Convenio convenio = new Convenio(convenioRequest);

		convenioRepository.save(convenio);

	}

	public void updateConvenio(ConvenioRequest convenioRequest) {

		// Updating user's account
		Convenio convenio = convenioRepository.findById(convenioRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Convenio", "id", convenioRequest.getId()));

		convenio.setDescricao(convenioRequest.getDescricao());

		convenioRepository.save(convenio);

	}

	public void deleteConvenio(Long id) {
		Convenio convenio = convenioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Convenio", "id", id));

		convenioRepository.delete(convenio);
	}

	public PagedResponse<ConvenioResponse> getAllConvenios(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Convenio> convenios = convenioRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (convenios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), convenios.getNumber(), convenios.getSize(),
					convenios.getTotalElements(), convenios.getTotalPages(), convenios.isLast());
		}

		List<ConvenioResponse> conveniosResponse = convenios.map(convenio -> {
			return new ConvenioResponse(convenio);
		}).getContent();
		return new PagedResponse<>(conveniosResponse, convenios.getNumber(), convenios.getSize(),
				convenios.getTotalElements(), convenios.getTotalPages(), convenios.isLast());

	}

	public ConvenioResponse getConvenioById(Long convenioId) {
		Convenio convenio = convenioRepository.findById(convenioId)
				.orElseThrow(() -> new ResourceNotFoundException("Convenio", "id", convenioId));

		Usuario userCreated = usuarioRepository.findById(convenio.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", convenio.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(convenio.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", convenio.getUpdatedBy()));

		ConvenioResponse convenioResponse = new ConvenioResponse(convenio, convenio.getCreatedAt(),
				userCreated.getNome(), convenio.getUpdatedAt(), userUpdated.getNome());

		return convenioResponse;
	}

	
	public List<ConvenioResponse> getAllConvenios() {

		List<Convenio> convenios = convenioRepository.findAll();
		List<ConvenioResponse> convenioResponse = new ArrayList<>();

		if (!convenios.isEmpty()) {
			for (Convenio convenio : convenios) {
				ConvenioResponse conveniosResp = new ConvenioResponse(convenio);

				convenioResponse.add(conveniosResp);
			}
			return convenioResponse;
		}

		return null;
	}

}
