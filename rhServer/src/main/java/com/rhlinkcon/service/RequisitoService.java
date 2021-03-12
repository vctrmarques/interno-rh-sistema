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
import com.rhlinkcon.model.DadoComparativoEnum;
import com.rhlinkcon.model.Requisito;
import com.rhlinkcon.model.TipoComparacaoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.requisito.RequisitoRequest;
import com.rhlinkcon.payload.requisito.RequisitoResponse;
import com.rhlinkcon.repository.RequisitoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class RequisitoService {

	@Autowired
	private RequisitoRepository RequisitoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void createRequisito(RequisitoRequest RequisitoRequest) {

		Requisito requisito = new Requisito(RequisitoRequest); 
		
		RequisitoRepository.save(requisito);

	}

	public void updateRequisito(RequisitoRequest requisitoRequest) {

		Requisito requisito = RequisitoRepository.findById(requisitoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Requisito", "id", requisitoRequest.getId()));


		requisito.setDescricao(requisitoRequest.getDescricao());
		requisito.setDadoComparativo(DadoComparativoEnum.getEnumByString(requisitoRequest.getDadoComparativo()));
		requisito.setComparacao(TipoComparacaoEnum.getEnumByString(requisitoRequest.getComparacao()));
		requisito.setValor(requisitoRequest.getValor());
		requisito.setInicioIntervalo(requisitoRequest.getInicioIntervalo());
		requisito.setFimIntervalo(requisitoRequest.getFimIntervalo());
		
		RequisitoRepository.save(requisito);

	}

	public void deleteRequisito(Long id) {
		Requisito requisito = RequisitoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Requisito", "id",  id));
		
		RequisitoRepository.delete(requisito);
	}

	public PagedResponse<RequisitoResponse> getAllRequisitos(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Requisito> requisitos = RequisitoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (requisitos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), requisitos.getNumber(), requisitos.getSize(),
					requisitos.getTotalElements(), requisitos.getTotalPages(), requisitos.isLast());
		}

		List<RequisitoResponse> requisitosResponse = requisitos.map(requisito -> {
			return new RequisitoResponse(requisito);
		}).getContent();
		return new PagedResponse<>(requisitosResponse, requisitos.getNumber(), requisitos.getSize(),
				requisitos.getTotalElements(), requisitos.getTotalPages(), requisitos.isLast());

	}

	public RequisitoResponse getRequisitoById(Long requisitoId) {
		Requisito requisito = RequisitoRepository.findById(requisitoId)
				.orElseThrow(() -> new ResourceNotFoundException("Requisito", "id", requisitoId));

		Usuario userCreated = usuarioRepository.findById(requisito.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", requisito.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(requisito.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", requisito.getUpdatedBy()));

		RequisitoResponse requisitoResponse = new RequisitoResponse(requisito, requisito.getCreatedAt(),
				userCreated.getNome(), requisito.getUpdatedAt(), userUpdated.getNome());

		return requisitoResponse;
	}

}
