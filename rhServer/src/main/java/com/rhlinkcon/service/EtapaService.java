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
//import com.rhlinkcon.model.ClassificacaoAgenteNocivo;
import com.rhlinkcon.model.Etapa;
import com.rhlinkcon.model.ProcessoEtapaEnum;
import com.rhlinkcon.model.Usuario;
//import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoResponse;
import com.rhlinkcon.payload.etapa.EtapaRequest;
import com.rhlinkcon.payload.etapa.EtapaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EtapaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class EtapaService {

	@Autowired
	private EtapaRepository etapaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createEtapa(EtapaRequest etapaRequest) {

		// Creating user's account
		Etapa etapa = new Etapa(etapaRequest);

		etapaRepository.save(etapa);

	}

	public void updateEtapa(EtapaRequest etapaRequest) {

		// Updating user's account
		Etapa etapa = etapaRepository.findById(etapaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Etapa", "id", etapaRequest.getId()));

		etapa.setCodigo(etapaRequest.getCodigo());
		etapa.setDescricao(etapaRequest.getDescricao());

		if (!etapaRequest.getProcesso().isEmpty())
			etapa.setProcesso(ProcessoEtapaEnum.getEnumByString(etapaRequest.getProcesso()));

		etapaRepository.save(etapa);

	}

	public void deleteEtapa(Long id) {
		Etapa etapa = etapaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Etapa", "id", id));

		etapaRepository.delete(etapa);
	}

	public PagedResponse<EtapaResponse> getAllEtapas(int page, int size, String descricao, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Etapa> etapas = etapaRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (etapas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), etapas.getNumber(), etapas.getSize(),
					etapas.getTotalElements(), etapas.getTotalPages(), etapas.isLast());
		}

		List<EtapaResponse> etapasResponse = etapas.map(etapa -> {
			return new EtapaResponse(etapa);
		}).getContent();
		return new PagedResponse<>(etapasResponse, etapas.getNumber(), etapas.getSize(), etapas.getTotalElements(),
				etapas.getTotalPages(), etapas.isLast());

	}

	public EtapaResponse getEtapaById(Long etapaId) {
		Etapa etapa = etapaRepository.findById(etapaId)
				.orElseThrow(() -> new ResourceNotFoundException("Etapa", "id", etapaId));

		Usuario userCreated = usuarioRepository.findById(etapa.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", etapa.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(etapa.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", etapa.getUpdatedBy()));

		EtapaResponse etapaResponse = new EtapaResponse(etapa, etapa.getCreatedAt(), 
											userCreated.getNome(), etapa.getUpdatedAt(), userUpdated.getNome());

		return etapaResponse;
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
