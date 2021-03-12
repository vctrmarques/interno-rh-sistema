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
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.NivelSalarialHistorico;
import com.rhlinkcon.model.NivelSalarialHistoricoOrigemAjusteEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.referenciaSalarial.NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse;
import com.rhlinkcon.payload.referenciaSalarial.NivelSalarialHistoricoResponse;
import com.rhlinkcon.repository.NivelSalarialHistoricoRepository;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class NivelSalarialHistoricoService {

	@Autowired
	private NivelSalarialHistoricoRepository nivelSalarialHistoricoRepository;

	@Autowired
	private ReferenciaSalarialRepository nivelSalarialRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public PagedResponse<NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse> getAllNivelSalarialHistoricosAgrupadosPorNivelSalarial(
			int page, int size, String order, String nivelSalarialDescricao) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse> nivelSalarialHistoricos = nivelSalarialHistoricoRepository
				.getAllNivelSalarialHistoricosAgrupadosPorNivelSalarial(nivelSalarialDescricao, pageable);

		if (nivelSalarialHistoricos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), nivelSalarialHistoricos.getNumber(),
					nivelSalarialHistoricos.getSize(), nivelSalarialHistoricos.getTotalElements(),
					nivelSalarialHistoricos.getTotalPages(), nivelSalarialHistoricos.isLast());
		}

		List<NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse> nivelSalarialHistoricoResponses = nivelSalarialHistoricos
				.map(nivelSalarialHistorico -> {
					return nivelSalarialHistorico;
				}).getContent();
		return new PagedResponse<>(nivelSalarialHistoricoResponses, nivelSalarialHistoricos.getNumber(),
				nivelSalarialHistoricos.getSize(), nivelSalarialHistoricos.getTotalElements(),
				nivelSalarialHistoricos.getTotalPages(), nivelSalarialHistoricos.isLast());

	}

	public PagedResponse<NivelSalarialHistoricoResponse> getAllNivelSalarialHistoricos(int page, int size, String order,
			String nivelSalarialId, String origemAjuste) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<NivelSalarialHistorico> nivelSalarialHistoricos = null;

		if (origemAjuste.equals("Todas")) {
			if (Utils.checkStr(nivelSalarialId)) {
				ReferenciaSalarial nivelSalarial = nivelSalarialRepository.findById(Long.valueOf(nivelSalarialId))
						.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id",
								Long.valueOf(nivelSalarialId)));
				nivelSalarialHistoricos = nivelSalarialHistoricoRepository.findByNivelSalarial(nivelSalarial, pageable);
			} else {
				nivelSalarialHistoricos = nivelSalarialHistoricoRepository.findAll(pageable);
			}
		} else {
			if (Utils.checkStr(nivelSalarialId)) {
				ReferenciaSalarial nivelSalarial = nivelSalarialRepository.findById(Long.valueOf(nivelSalarialId))
						.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id",
								Long.valueOf(nivelSalarialId)));
				nivelSalarialHistoricos = nivelSalarialHistoricoRepository.findByNivelSalarialAndOrigemAjuste(
						nivelSalarial, NivelSalarialHistoricoOrigemAjusteEnum.getEnumByString(origemAjuste), pageable);
			} else {
				nivelSalarialHistoricos = nivelSalarialHistoricoRepository.findByOrigemAjuste(
						NivelSalarialHistoricoOrigemAjusteEnum.getEnumByString(origemAjuste), pageable);
			}
		}

		if (nivelSalarialHistoricos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), nivelSalarialHistoricos.getNumber(),
					nivelSalarialHistoricos.getSize(), nivelSalarialHistoricos.getTotalElements(),
					nivelSalarialHistoricos.getTotalPages(), nivelSalarialHistoricos.isLast());
		}

		List<NivelSalarialHistoricoResponse> nivelSalarialHistoricoResponses = nivelSalarialHistoricos
				.map(nivelSalarialHistorico -> {
					NivelSalarialHistoricoResponse nivelSalarialHistoricoResponse = new NivelSalarialHistoricoResponse(
							nivelSalarialHistorico, Projecao.COMPLETA);

					Usuario criadoPor = usuarioRepository.findById(nivelSalarialHistorico.getCreatedBy()).orElseThrow(
							() -> new ResourceNotFoundException("User", "id", nivelSalarialHistorico.getCreatedBy()));
					nivelSalarialHistoricoResponse.setCriadoPor(criadoPor.getNome());

					Usuario alteradoPor = usuarioRepository.findById(nivelSalarialHistorico.getUpdatedBy()).orElseThrow(
							() -> new ResourceNotFoundException("User", "id", nivelSalarialHistorico.getUpdatedBy()));
					nivelSalarialHistoricoResponse.setAlteradoPor(alteradoPor.getNome());

					return nivelSalarialHistoricoResponse;
				}).getContent();
		return new PagedResponse<>(nivelSalarialHistoricoResponses, nivelSalarialHistoricos.getNumber(),
				nivelSalarialHistoricos.getSize(), nivelSalarialHistoricos.getTotalElements(),
				nivelSalarialHistoricos.getTotalPages(), nivelSalarialHistoricos.isLast());

	}

}
