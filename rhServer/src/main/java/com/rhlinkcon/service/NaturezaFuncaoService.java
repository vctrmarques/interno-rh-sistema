package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.NaturezaFuncao;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.naturezaFuncao.NaturezaFuncaoRequest;
import com.rhlinkcon.payload.naturezaFuncao.NaturezaFuncaoResponse;
import com.rhlinkcon.repository.NaturezaFuncaoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class NaturezaFuncaoService {

	@Autowired
	private NaturezaFuncaoRepository naturezaFuncaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;


	public void createNaturezaFuncao(NaturezaFuncaoRequest naturezaFuncaoRequest) {
		NaturezaFuncao naturezaFuncao = new NaturezaFuncao();
		naturezaFuncao.setDescricao(naturezaFuncaoRequest.getDescricao());

		naturezaFuncaoRepository.save(naturezaFuncao);
		
	}

	public void updateNaturezaFuncao(NaturezaFuncaoRequest naturezaFuncaoRequest) {

		NaturezaFuncao naturezaFuncao = naturezaFuncaoRepository.findById(naturezaFuncaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("NaturezaFuncao", "id", naturezaFuncaoRequest.getId()));

		naturezaFuncao.setDescricao(naturezaFuncaoRequest.getDescricao());
		naturezaFuncaoRepository.save(naturezaFuncao);
		
	}

	@Transactional
	public void deleteNaturezaFuncao(Long id) {
		
		NaturezaFuncao naturezaFuncao = naturezaFuncaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("NaturezaFuncao", "id", id));
		naturezaFuncaoRepository.delete(naturezaFuncao);
	}
	
	public List<NaturezaFuncaoResponse> searchAllByDescricao(String search) {
		List<NaturezaFuncaoResponse> itensResponses = new ArrayList<NaturezaFuncaoResponse>();
		naturezaFuncaoRepository.findByDescricaoIgnoreCaseContaining(search).forEach(item -> itensResponses.add(new NaturezaFuncaoResponse(item)));
		return itensResponses;
	}

	public PagedResponse<NaturezaFuncaoResponse> getAllNaturezasFuncoes(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<NaturezaFuncao> naturezasFuncoes = null;

		if (Utils.checkStr(descricao) ) {
			naturezasFuncoes = naturezaFuncaoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		}else {
			naturezasFuncoes = naturezaFuncaoRepository.findAll(pageable);
		}

		if (naturezasFuncoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), naturezasFuncoes.getNumber(), naturezasFuncoes.getSize(),
					naturezasFuncoes.getTotalElements(), naturezasFuncoes.getTotalPages(), naturezasFuncoes.isLast());
		}

		List<NaturezaFuncaoResponse> naturezasFuncoesResponse = naturezasFuncoes.map(naturezaFuncao -> {
			return new NaturezaFuncaoResponse(naturezaFuncao);
		}).getContent();
		return new PagedResponse<>(naturezasFuncoesResponse, naturezasFuncoes.getNumber(), naturezasFuncoes.getSize(),
				naturezasFuncoes.getTotalElements(), naturezasFuncoes.getTotalPages(), naturezasFuncoes.isLast());

	}

	public NaturezaFuncaoResponse getNaturezaFuncaoById(Long naturezaFuncaoId) {
		NaturezaFuncao naturezaFuncao = naturezaFuncaoRepository.findById(naturezaFuncaoId)
				.orElseThrow(() -> new ResourceNotFoundException("NaturezaFuncao", "id", naturezaFuncaoId));

		Usuario userCreated = usuarioRepository.findById(naturezaFuncao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", naturezaFuncao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(naturezaFuncao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", naturezaFuncao.getUpdatedBy()));

		NaturezaFuncaoResponse naturezaFuncaoResponse = new NaturezaFuncaoResponse(naturezaFuncao, naturezaFuncao.getCreatedAt(),
				userCreated.getNome(), naturezaFuncao.getUpdatedAt(), userUpdated.getNome());

		return naturezaFuncaoResponse;
	}
	
	public List<NaturezaFuncaoResponse> getAllNaturezaFuncoes() {
		List<NaturezaFuncao> naturezaFuncaoList = naturezaFuncaoRepository.findAll();

		List<NaturezaFuncaoResponse> naturezaFuncaoResponseList = new ArrayList<>();
		for (NaturezaFuncao naturezaFuncao : naturezaFuncaoList) {
			naturezaFuncaoResponseList.add(new NaturezaFuncaoResponse(naturezaFuncao));
		}
		return naturezaFuncaoResponseList;
	}
	
}
