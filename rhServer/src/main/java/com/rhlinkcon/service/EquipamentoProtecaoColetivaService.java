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
import com.rhlinkcon.model.EquipamentoProtecaoColetiva;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.equipamentoProtecaoColetiva.EquipamentoProtecaoColetivaRequest;
import com.rhlinkcon.payload.equipamentoProtecaoColetiva.EquipamentoProtecaoColetivaResponse;
import com.rhlinkcon.payload.equipamentoProtecaoIndividual.EquipamentoProtecaoIndividualRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EquipamentoProtecaoColetivaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class EquipamentoProtecaoColetivaService {

	@Autowired
	private EquipamentoProtecaoColetivaRepository equipamentoProtecaoColetivaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createEquipamentoProtecaoColetiva(EquipamentoProtecaoColetivaRequest equipamentoProtecaoColetivaRequest) {
		validaCodigo(equipamentoProtecaoColetivaRequest);
		validaDescricao(equipamentoProtecaoColetivaRequest);

		// Creating user's account
		EquipamentoProtecaoColetiva equipamentoProtecaoColetiva = new EquipamentoProtecaoColetiva(equipamentoProtecaoColetivaRequest);

		equipamentoProtecaoColetivaRepository.save(equipamentoProtecaoColetiva);

	}
	
	public void validaCodigo(EquipamentoProtecaoColetivaRequest equipamentoProtecaoColetivaRequest) {
		if (equipamentoProtecaoColetivaRepository.existsByCodigo(equipamentoProtecaoColetivaRequest.getCodigo())) {
			throw new BadRequestException("Já existe um Equipamento de Proteção Coletivo com este código!");
		}
	}

	public void validaDescricao(EquipamentoProtecaoColetivaRequest equipamentoProtecaoColetivaRequest) {
		if (equipamentoProtecaoColetivaRepository.existsByDescricao(equipamentoProtecaoColetivaRequest.getDescricao())) {
			throw new BadRequestException("Este Equipamento de Proteção Coletiva já existe!");
		}
	}

	public void updateEquipamentoProtecaoColetiva(EquipamentoProtecaoColetivaRequest equipamentoProtecaoColetivaRequest) {
		// Updating user's account
		EquipamentoProtecaoColetiva equipamentoProtecaoColetiva = equipamentoProtecaoColetivaRepository.findById(equipamentoProtecaoColetivaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("EquipamentoProtecaoColetiva", "id", equipamentoProtecaoColetivaRequest.getId()));

		if (!equipamentoProtecaoColetiva.getCodigo().equalsIgnoreCase(equipamentoProtecaoColetivaRequest.getCodigo()))
			validaCodigo(equipamentoProtecaoColetivaRequest);
		if (!equipamentoProtecaoColetiva.getDescricao().equalsIgnoreCase(equipamentoProtecaoColetivaRequest.getDescricao()))
			validaDescricao(equipamentoProtecaoColetivaRequest);
		
		equipamentoProtecaoColetiva.setCodigo(equipamentoProtecaoColetivaRequest.getCodigo());
		equipamentoProtecaoColetiva.setTipoProtecao(equipamentoProtecaoColetivaRequest.getTipoProtecao());
		equipamentoProtecaoColetiva.setDescricao(equipamentoProtecaoColetivaRequest.getDescricao());
		equipamentoProtecaoColetiva.setValidade(equipamentoProtecaoColetivaRequest.getValidade());
		equipamentoProtecaoColetiva.setCertificado(equipamentoProtecaoColetivaRequest.getCertificado());
		equipamentoProtecaoColetiva.setReferencia(equipamentoProtecaoColetivaRequest.getReferencia());
		equipamentoProtecaoColetiva.setMinimo(equipamentoProtecaoColetivaRequest.getMinimo());
		equipamentoProtecaoColetiva.setLivre(equipamentoProtecaoColetivaRequest.getLivre());
		equipamentoProtecaoColetiva.setAtual(equipamentoProtecaoColetivaRequest.getAtual());

		equipamentoProtecaoColetivaRepository.save(equipamentoProtecaoColetiva);

	}

	public void deleteEquipamentoProtecaoColetiva(Long id) {
		EquipamentoProtecaoColetiva equipamentoProtecaoColetiva = equipamentoProtecaoColetivaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EquipamentoProtecaoColetiva", "id", id));

		equipamentoProtecaoColetivaRepository.delete(equipamentoProtecaoColetiva);
	}

	public PagedResponse<EquipamentoProtecaoColetivaResponse> getAllEquipamentosProtecaoColetiva(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<EquipamentoProtecaoColetiva> equipamentosProtecaoColetiva = equipamentoProtecaoColetivaRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (equipamentosProtecaoColetiva.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), equipamentosProtecaoColetiva.getNumber(), equipamentosProtecaoColetiva.getSize(),
					equipamentosProtecaoColetiva.getTotalElements(), equipamentosProtecaoColetiva.getTotalPages(), equipamentosProtecaoColetiva.isLast());
		}

		List<EquipamentoProtecaoColetivaResponse> equipamentosProtecaoColetivaResponse = equipamentosProtecaoColetiva.map(equipamentoProtecaoColetiva -> {
			return new EquipamentoProtecaoColetivaResponse(equipamentoProtecaoColetiva);
		}).getContent();
		return new PagedResponse<>(equipamentosProtecaoColetivaResponse, equipamentosProtecaoColetiva.getNumber(), equipamentosProtecaoColetiva.getSize(), equipamentosProtecaoColetiva.getTotalElements(),
				equipamentosProtecaoColetiva.getTotalPages(), equipamentosProtecaoColetiva.isLast());

	}

	public EquipamentoProtecaoColetivaResponse getEquipamentoProtecaoColetivaById(Long equipamentoProtecaoColetivaId) {
		EquipamentoProtecaoColetiva equipamentoProtecaoColetiva = equipamentoProtecaoColetivaRepository.findById(equipamentoProtecaoColetivaId)
				.orElseThrow(() -> new ResourceNotFoundException("EquipamentoProtecaoColetiva", "id", equipamentoProtecaoColetivaId));

		Usuario userCreated = usuarioRepository.findById(equipamentoProtecaoColetiva.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", equipamentoProtecaoColetiva.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(equipamentoProtecaoColetiva.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", equipamentoProtecaoColetiva.getUpdatedBy()));

		EquipamentoProtecaoColetivaResponse equipamentoProtecaoColetivaResponse = new EquipamentoProtecaoColetivaResponse(equipamentoProtecaoColetiva, equipamentoProtecaoColetiva.getCreatedAt(), 
											userCreated.getNome(), equipamentoProtecaoColetiva.getUpdatedAt(), userUpdated.getNome());

		return equipamentoProtecaoColetivaResponse;
	}


}
