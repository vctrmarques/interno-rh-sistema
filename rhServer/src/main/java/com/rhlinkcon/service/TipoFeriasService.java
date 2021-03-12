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
import com.rhlinkcon.model.DecimoTerceiroEnum;
import com.rhlinkcon.model.ColetivoEnum;
import com.rhlinkcon.model.LicencaPremioEnum;
import com.rhlinkcon.model.TipoFerias;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tipoFerias.TipoFeriasRequest;
import com.rhlinkcon.payload.tipoFerias.TipoFeriasResponse;
import com.rhlinkcon.repository.TipoFeriasRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TipoFeriasService {

	@Autowired
	private TipoFeriasRepository tipoFeriasRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	

	public void createTipoFerias(TipoFeriasRequest tipoFeriasRequest) {

		TipoFerias tipoFerias = new TipoFerias(tipoFeriasRequest);

		tipoFeriasRepository.save(tipoFerias);

	}

	public void updateTipoFerias(TipoFeriasRequest tipoFeriasRequest) {

		TipoFerias tipoFerias = tipoFeriasRepository.findById(tipoFeriasRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("TipoFerias", "id", tipoFeriasRequest.getId()));

		tipoFerias.setDescricao(tipoFeriasRequest.getDescricao());
		tipoFerias.setAbono(tipoFeriasRequest.getAbono());
		tipoFerias.setDias(tipoFeriasRequest.getDias());
		tipoFerias.setSaldo(tipoFeriasRequest.getSaldo());
		tipoFerias.setDecimoTerceiro(DecimoTerceiroEnum.getEnumByString(tipoFeriasRequest.getDecimoTerceiro()));
		tipoFerias.setColetivo(ColetivoEnum.getEnumByString(tipoFeriasRequest.getColetivo()));
		tipoFerias.setLicencaPremio(LicencaPremioEnum.getEnumByString(tipoFeriasRequest.getLicencaPremio()));

		tipoFeriasRepository.save(tipoFerias);

	}

	public void deleteTipoFerias(Long id) {
		TipoFerias tipoFerias = tipoFeriasRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TipoFerias", "id", id));
		tipoFeriasRepository.delete(tipoFerias);
	}

	public PagedResponse<TipoFeriasResponse> getAllTiposFerias(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<TipoFerias> tiposFerias = tipoFeriasRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (tiposFerias.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tiposFerias.getNumber(), tiposFerias.getSize(),
					tiposFerias.getTotalElements(), tiposFerias.getTotalPages(), tiposFerias.isLast());
		}

		List<TipoFeriasResponse> tiposFeriasResponse = tiposFerias.map(tipoFerias -> {
			return new TipoFeriasResponse(tipoFerias);
		}).getContent();
		return new PagedResponse<>(tiposFeriasResponse, tiposFerias.getNumber(), tiposFerias.getSize(),
				tiposFerias.getTotalElements(), tiposFerias.getTotalPages(), tiposFerias.isLast());

	}

	public TipoFeriasResponse getTipoFeriasById(Long tipoFeriasId) {
		TipoFerias tipoFerias = tipoFeriasRepository.findById(tipoFeriasId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoFerias", "id", tipoFeriasId));

		Usuario userCreated = usuarioRepository.findById(tipoFerias.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoFerias.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(tipoFerias.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoFerias.getUpdatedBy()));

		TipoFeriasResponse tipoFeriasResponse = new TipoFeriasResponse(tipoFerias, tipoFerias.getCreatedAt(),
				userCreated.getNome(), tipoFerias.getUpdatedAt(), userUpdated.getNome());

		return tipoFeriasResponse;
	}

}
