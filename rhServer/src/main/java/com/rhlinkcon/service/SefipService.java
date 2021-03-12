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
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Sefip;
import com.rhlinkcon.model.TipoSefipEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.sefip.SefipRequest;
import com.rhlinkcon.payload.sefip.SefipResponse;
import com.rhlinkcon.repository.SefipRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class SefipService {

	@Autowired
	private SefipRepository sefipRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createSefip(SefipRequest sefipRequest) {

		Sefip sefip = new Sefip(sefipRequest);

		sefipRepository.save(sefip);

	}

	public void updateSefip(SefipRequest sefipRequest) {

		Sefip sefip = sefipRepository.findById(sefipRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Sefip", "id", sefipRequest.getId()));

		sefip.setTipo(TipoSefipEnum.getEnumByString(sefipRequest.getTipo()));
		sefip.setDescricao(sefipRequest.getDescricao());
		sefip.setCodigo(sefipRequest.getCodigo());

		sefipRepository.save(sefip);

	}

	public void deleteSefip(Long id) {
		Sefip sefip = sefipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sefip", "id", id));
		sefipRepository.delete(sefip);
	}

	public PagedResponse<SefipResponse> getAllSefips(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Sefip> sefips = sefipRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (sefips.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), sefips.getNumber(), sefips.getSize(),
					sefips.getTotalElements(), sefips.getTotalPages(), sefips.isLast());
		}

		List<SefipResponse> sefipsResponse = sefips.map(sefip -> {
			return new SefipResponse(sefip);
		}).getContent();
		return new PagedResponse<>(sefipsResponse, sefips.getNumber(), sefips.getSize(), sefips.getTotalElements(),
				sefips.getTotalPages(), sefips.isLast());

	}
	
	public List<SefipResponse> getAllSefips() {
		List<Sefip> sefips = sefipRepository.findAll();

		List<SefipResponse> listSefipResponse = new ArrayList<>();
		for (Sefip sefip : sefips) {
			listSefipResponse.add(new SefipResponse(sefip));
		}
		return listSefipResponse;
	}

	public List<SefipResponse> searchAllByDescricaoAndTipo(String search, TipoSefipEnum tipo) {
		List<SefipResponse> itensResponses = new ArrayList<SefipResponse>();
		sefipRepository.findByDescricaoIgnoreCaseContainingAndTipo(search, tipo)
				.forEach(item -> itensResponses.add(new SefipResponse(item)));
		return itensResponses;
	}

	public SefipResponse getSefipById(Long sefipId) {
		Sefip sefip = sefipRepository.findById(sefipId)
				.orElseThrow(() -> new ResourceNotFoundException("Sefip", "id", sefipId));

		Usuario userCreated = usuarioRepository.findById(sefip.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", sefip.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(sefip.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", sefip.getUpdatedBy()));

		SefipResponse sefipResponse = new SefipResponse(sefip, sefip.getCreatedAt(), userCreated.getNome(),
				sefip.getUpdatedAt(), userUpdated.getNome());

		return sefipResponse;
	}

}
