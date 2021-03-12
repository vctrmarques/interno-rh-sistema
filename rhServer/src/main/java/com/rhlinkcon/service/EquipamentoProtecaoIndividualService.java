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
import com.rhlinkcon.model.EquipamentoProtecaoIndividual;
import com.rhlinkcon.model.Usuario;
//import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoResponse;
import com.rhlinkcon.payload.equipamentoProtecaoIndividual.EquipamentoProtecaoIndividualRequest;
import com.rhlinkcon.payload.equipamentoProtecaoIndividual.EquipamentoProtecaoIndividualResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EquipamentoProtecaoIndividualRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class EquipamentoProtecaoIndividualService {

	@Autowired
	private EquipamentoProtecaoIndividualRepository equipamentoProtecaoIndividualRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createEquipamentoProtecaoIndividual(EquipamentoProtecaoIndividualRequest equipamentoProtecaoIndividualRequest) {
		validaCodigo(equipamentoProtecaoIndividualRequest);
		validaDescricao(equipamentoProtecaoIndividualRequest);
		// Creating user's account
		EquipamentoProtecaoIndividual equipamentoProtecaoIndividual = new EquipamentoProtecaoIndividual(equipamentoProtecaoIndividualRequest);

		equipamentoProtecaoIndividualRepository.save(equipamentoProtecaoIndividual);

	}

	public void validaCodigo(EquipamentoProtecaoIndividualRequest equipamentoProtecaoIndividualRequest) {
		if (equipamentoProtecaoIndividualRepository.existsByCodigo(equipamentoProtecaoIndividualRequest.getCodigo())) {
			throw new BadRequestException("Já existe um Equipamento de Proteção Individual com este código!");
		}
	}

	public void validaDescricao(EquipamentoProtecaoIndividualRequest equipamentoProtecaoIndividualRequest) {
		if (equipamentoProtecaoIndividualRepository.existsByDescricao(equipamentoProtecaoIndividualRequest.getDescricao())) {
			throw new BadRequestException("Este Equipamento de Proteção Individual já existe!");
		}
	}

	public void updateEquipamentoProtecaoIndividual(EquipamentoProtecaoIndividualRequest equipamentoProtecaoIndividualRequest) {

		// Updating user's account
		EquipamentoProtecaoIndividual equipamentoProtecaoIndividual = equipamentoProtecaoIndividualRepository
				.findById(equipamentoProtecaoIndividualRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("EquipamentoProtecaoIndividual", "id", equipamentoProtecaoIndividualRequest.getId()));

		if (!equipamentoProtecaoIndividual.getCodigo().equalsIgnoreCase(equipamentoProtecaoIndividualRequest.getCodigo()))
			validaCodigo(equipamentoProtecaoIndividualRequest);
		if (!equipamentoProtecaoIndividual.getDescricao().equalsIgnoreCase(equipamentoProtecaoIndividualRequest.getDescricao()))
			validaDescricao(equipamentoProtecaoIndividualRequest);

		equipamentoProtecaoIndividual.setCodigo(equipamentoProtecaoIndividualRequest.getCodigo());
		equipamentoProtecaoIndividual.setTipoProtecao(equipamentoProtecaoIndividualRequest.getTipoProtecao());
		equipamentoProtecaoIndividual.setDescricao(equipamentoProtecaoIndividualRequest.getDescricao());
		equipamentoProtecaoIndividual.setValidade(equipamentoProtecaoIndividualRequest.getValidade());
		equipamentoProtecaoIndividual.setCertificado(equipamentoProtecaoIndividualRequest.getCertificado());
		equipamentoProtecaoIndividual.setMinimo(equipamentoProtecaoIndividualRequest.getMinimo());
		equipamentoProtecaoIndividual.setLivre(equipamentoProtecaoIndividualRequest.getLivre());
		equipamentoProtecaoIndividual.setAtual(equipamentoProtecaoIndividualRequest.getAtual());
		equipamentoProtecaoIndividualRepository.save(equipamentoProtecaoIndividual);

	}

	public void deleteEquipamentoProtecaoIndividual(Long id) {
		EquipamentoProtecaoIndividual equipamentoProtecaoIndividual = equipamentoProtecaoIndividualRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EquipamentoProtecaoIndividual", "id", id));

		equipamentoProtecaoIndividualRepository.delete(equipamentoProtecaoIndividual);
	}

	public PagedResponse<EquipamentoProtecaoIndividualResponse> getAllEquipamentosProtecaoIndividual(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<EquipamentoProtecaoIndividual> equipamentosProtecaoIndividual = equipamentoProtecaoIndividualRepository
				.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (equipamentosProtecaoIndividual.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), equipamentosProtecaoIndividual.getNumber(), equipamentosProtecaoIndividual.getSize(),
					equipamentosProtecaoIndividual.getTotalElements(), equipamentosProtecaoIndividual.getTotalPages(), equipamentosProtecaoIndividual.isLast());
		}

		List<EquipamentoProtecaoIndividualResponse> equipamentosProtecaoIndividualResponse = equipamentosProtecaoIndividual
				.map(equipamentoProtecaoIndividual -> {
					return new EquipamentoProtecaoIndividualResponse(equipamentoProtecaoIndividual);
				}).getContent();
		return new PagedResponse<>(equipamentosProtecaoIndividualResponse, equipamentosProtecaoIndividual.getNumber(), equipamentosProtecaoIndividual.getSize(),
				equipamentosProtecaoIndividual.getTotalElements(), equipamentosProtecaoIndividual.getTotalPages(), equipamentosProtecaoIndividual.isLast());

	}

	public EquipamentoProtecaoIndividualResponse getEquipamentoProtecaoIndividualById(Long equipamentoProtecaoIndividualId) {
		EquipamentoProtecaoIndividual equipamentoProtecaoIndividual = equipamentoProtecaoIndividualRepository.findById(equipamentoProtecaoIndividualId)
				.orElseThrow(() -> new ResourceNotFoundException("EquipamentoProtecaoIndividual", "id", equipamentoProtecaoIndividualId));

		Usuario userCreated = usuarioRepository.findById(equipamentoProtecaoIndividual.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", equipamentoProtecaoIndividual.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(equipamentoProtecaoIndividual.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", equipamentoProtecaoIndividual.getUpdatedBy()));

		EquipamentoProtecaoIndividualResponse equipamentoProtecaoIndividualResponse = new EquipamentoProtecaoIndividualResponse(equipamentoProtecaoIndividual,
				equipamentoProtecaoIndividual.getCreatedAt(), userCreated.getNome(), equipamentoProtecaoIndividual.getUpdatedAt(), userUpdated.getNome());

		return equipamentoProtecaoIndividualResponse;
	}

}
