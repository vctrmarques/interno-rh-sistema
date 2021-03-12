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
import com.rhlinkcon.model.HistoricoContabil;
import com.rhlinkcon.model.TomadorServico;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.historicoContabil.HistoricoContabilRequest;
import com.rhlinkcon.payload.historicoContabil.HistoricoContabilResponse;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoResponse;
import com.rhlinkcon.repository.HistoricoContabilRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class HistoricoContabilService {

	@Autowired
	private HistoricoContabilRepository historicoContabilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void createHistoricoContabil(HistoricoContabilRequest historicoContabilRequest) {

		HistoricoContabil historicoContabil = new HistoricoContabil(historicoContabilRequest); 
		
		historicoContabilRepository.save(historicoContabil);

	}

	public void updateHistoricoContabil(HistoricoContabilRequest historicoContabilRequest) {

		HistoricoContabil historicoContabil = historicoContabilRepository.findById(historicoContabilRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("HistoricoContabil", "id", historicoContabilRequest.getId()));


		historicoContabil.setDescricao(historicoContabil.getDescricao());
		historicoContabil.setCodigo(historicoContabil.getCodigo());

		
		historicoContabilRepository.save(historicoContabil);

	}

	public void deleteHistoricoContabil(Long id) {
		HistoricoContabil historicoContabil = historicoContabilRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("HistoricoContabil", "id",  id));
		
		historicoContabilRepository.delete(historicoContabil);
	}

	public PagedResponse<HistoricoContabilResponse> getAllHistoricosContabeis(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<HistoricoContabil> historicosContabeis = historicoContabilRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (historicosContabeis.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), historicosContabeis.getNumber(), historicosContabeis.getSize(),
					historicosContabeis.getTotalElements(), historicosContabeis.getTotalPages(), historicosContabeis.isLast());
		}

		List<HistoricoContabilResponse> historicosContabeisResponse = historicosContabeis.map(historicoContabil -> {
			return new HistoricoContabilResponse(historicoContabil);
		}).getContent();
		return new PagedResponse<>(historicosContabeisResponse, historicosContabeis.getNumber(), historicosContabeis.getSize(),
				historicosContabeis.getTotalElements(), historicosContabeis.getTotalPages(), historicosContabeis.isLast());

	}

	public HistoricoContabilResponse getHistoricoContabilById(Long historicoContabilId) {
		HistoricoContabil historicoContabil = historicoContabilRepository.findById(historicoContabilId)
				.orElseThrow(() -> new ResourceNotFoundException("HistoricoContabil", "id", historicoContabilId));

		Usuario userCreated = usuarioRepository.findById(historicoContabil.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", historicoContabil.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(historicoContabil.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", historicoContabil.getUpdatedBy()));

		HistoricoContabilResponse historicoContabilResponse = new HistoricoContabilResponse(historicoContabil, historicoContabil.getCreatedAt(),
				userCreated.getNome(), historicoContabil.getUpdatedAt(), userUpdated.getNome());

		return historicoContabilResponse;
	}

}
