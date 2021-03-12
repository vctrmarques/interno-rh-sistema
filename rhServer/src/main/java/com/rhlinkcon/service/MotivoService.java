package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.EventoMotivoEnum;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Motivo;
import com.rhlinkcon.model.TipoMotivoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.motivo.MotivoRequest;
import com.rhlinkcon.payload.motivo.MotivoResponse;
import com.rhlinkcon.repository.MotivoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class MotivoService {

	@Autowired
	private MotivoRepository motivoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createMotivo(MotivoRequest motivoRequest) {

		Motivo motivo = new Motivo(motivoRequest);

		motivoRepository.save(motivo);

	}

	public void updateMotivo(MotivoRequest motivoRequest) {

		Motivo motivo = motivoRepository.findById(motivoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Motivo", "id", motivoRequest.getId()));

		motivo.setDescricao(motivoRequest.getDescricao());
		
		if (Strings.isNotEmpty(motivoRequest.getTipo()))
			motivo.setTipo(TipoMotivoEnum.getEnumByString(motivoRequest.getTipo()));
		else
			motivo.setTipo(null);
		
		if (Strings.isNotEmpty(motivoRequest.getEvento()))
			motivo.setEvento(EventoMotivoEnum.getEnumByString(motivoRequest.getEvento()));
		else
			motivo.setEvento(null);
		motivoRepository.save(motivo);

	}

	public void deleteMotivo(Long id) {
		Motivo motivo = motivoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Motivo", "id", id));

		motivoRepository.delete(motivo);
	}

	public PagedResponse<MotivoResponse> getAllMotivos(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Motivo> motivos = motivoRepository.findByDescricaoIgnoreCaseContaining(descricao,
				pageable);

		if (motivos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), motivos.getNumber(), motivos.getSize(),
					motivos.getTotalElements(), motivos.getTotalPages(), motivos.isLast());
		}

		List<MotivoResponse> motivosResponse = motivos.map(motivo -> {
			return new MotivoResponse(motivo);
		}).getContent();
		return new PagedResponse<>(motivosResponse, motivos.getNumber(), motivos.getSize(),
				motivos.getTotalElements(), motivos.getTotalPages(), motivos.isLast());

	}

	public MotivoResponse getMotivoById(Long motivoId) {
		Motivo motivo = motivoRepository.findById(motivoId)
				.orElseThrow(() -> new ResourceNotFoundException("Motivo", "id", motivoId));

		Usuario userCreated = usuarioRepository.findById(motivo.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", motivo.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(motivo.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", motivo.getUpdatedBy()));

		MotivoResponse motivoResponse = new MotivoResponse(motivo, motivo.getCreatedAt(),
				userCreated.getNome(), motivo.getUpdatedAt(), userUpdated.getNome());

		return motivoResponse;
	}

	public List<MotivoResponse> getAllMotivos() {

		List<Motivo> motivos = motivoRepository.findAll();
		List<MotivoResponse> motivosResponse = new ArrayList<>();

		if (!motivos.isEmpty()) {
			for (Motivo motivo: motivos) {
				motivosResponse.add(new MotivoResponse(motivo));
			}
			return motivosResponse;
		}

		return null;
	}

}
