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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.GrupamentoNaturezaEnum;
import com.rhlinkcon.model.NaturezaJuridica;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.naturezaJuridica.NaturezaJuridicaRequest;
import com.rhlinkcon.payload.naturezaJuridica.NaturezaJuridicaResponse;
import com.rhlinkcon.repository.NaturezaJuridicaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class NaturezaJuridicaService {

	@Autowired
	private NaturezaJuridicaRepository naturezaJuridicaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	

	public void createNaturezaJuridica(NaturezaJuridicaRequest naturezaJuridicaRequest) {

		NaturezaJuridica naturezaJuridica = new NaturezaJuridica(naturezaJuridicaRequest);

		naturezaJuridicaRepository.save(naturezaJuridica);

	}

	public void updateNaturezaJuridica(NaturezaJuridicaRequest naturezaJuridicaRequest) {

		NaturezaJuridica naturezaJuridica = naturezaJuridicaRepository.findById(naturezaJuridicaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("NaturezaJuridica", "id", naturezaJuridicaRequest.getId()));

		naturezaJuridica.setGrupamento(GrupamentoNaturezaEnum.getEnumByString(naturezaJuridicaRequest.getGrupamento()));
		naturezaJuridica.setNome(naturezaJuridicaRequest.getNome());
		naturezaJuridica.setCodigo(naturezaJuridicaRequest.getCodigo());

		naturezaJuridicaRepository.save(naturezaJuridica);

	}

	public void deleteNaturezaJuridica(Long id) {
		NaturezaJuridica naturezaJuridica = naturezaJuridicaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("NaturezaJuridica", "id", id));
		naturezaJuridicaRepository.delete(naturezaJuridica);
	}

	public PagedResponse<NaturezaJuridicaResponse> getAllNaturezasJuridicas(int page, int size, String nome, String codigo, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<NaturezaJuridica> naturezasJuridicas = null;

		if (!codigo.isEmpty() && !nome.isEmpty()) {
			naturezasJuridicas = naturezaJuridicaRepository.findByNomeOrCodigo(nome, codigo, pageable);
		}
		else
		if (Utils.checkStr(nome) ) {
			naturezasJuridicas = naturezaJuridicaRepository.findByNomeIgnoreCaseContaining(nome, pageable);
		}
		else
		if (Utils.checkStr(codigo)) {
			naturezasJuridicas = naturezaJuridicaRepository.findByCodigo(Integer.parseInt(codigo), pageable);
		}
		else {
			naturezasJuridicas = naturezaJuridicaRepository.findAll(pageable);
		}
		
		if (naturezasJuridicas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), naturezasJuridicas.getNumber(), naturezasJuridicas.getSize(),
					naturezasJuridicas.getTotalElements(), naturezasJuridicas.getTotalPages(), naturezasJuridicas.isLast());
		}

		List<NaturezaJuridicaResponse> naturezasJuridicasResponse = naturezasJuridicas.map(naturezaJuridica -> {
			return new NaturezaJuridicaResponse(naturezaJuridica);
		}).getContent();
		return new PagedResponse<>(naturezasJuridicasResponse, naturezasJuridicas.getNumber(), naturezasJuridicas.getSize(),
				naturezasJuridicas.getTotalElements(), naturezasJuridicas.getTotalPages(), naturezasJuridicas.isLast());

	}

	public NaturezaJuridicaResponse getNaturezaJuridicaById(Long naturezaJuridicaId) {
		NaturezaJuridica naturezaJuridica = naturezaJuridicaRepository.findById(naturezaJuridicaId)
				.orElseThrow(() -> new ResourceNotFoundException("NaturezaJuridica", "id", naturezaJuridicaId));

		Usuario userCreated = usuarioRepository.findById(naturezaJuridica.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", naturezaJuridica.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(naturezaJuridica.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", naturezaJuridica.getUpdatedBy()));

		NaturezaJuridicaResponse naturezaJuridicaResponse = new NaturezaJuridicaResponse(naturezaJuridica, naturezaJuridica.getCreatedAt(),
				userCreated.getNome(), naturezaJuridica.getUpdatedAt(), userUpdated.getNome());

		return naturezaJuridicaResponse;
	}

}
