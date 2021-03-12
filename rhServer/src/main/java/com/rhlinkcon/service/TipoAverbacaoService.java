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
import com.rhlinkcon.model.FormaAverbacaoEnum;
import com.rhlinkcon.model.Sefip;
import com.rhlinkcon.model.DeducaoAverbacaoEnum;
import com.rhlinkcon.model.TipoAverbacao;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.sefip.SefipResponse;
import com.rhlinkcon.payload.tipoAverbacao.TipoAverbacaoRequest;
import com.rhlinkcon.payload.tipoAverbacao.TipoAverbacaoResponse;
import com.rhlinkcon.repository.TipoAverbacaoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TipoAverbacaoService {

	@Autowired
	private TipoAverbacaoRepository tipoAverbacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	

	public void createTipoAverbacao(TipoAverbacaoRequest tipoAverbacaoRequest) {

		TipoAverbacao tipoAverbacao = new TipoAverbacao(tipoAverbacaoRequest);

		tipoAverbacaoRepository.save(tipoAverbacao);

	}

	public void updateTipoAverbacao(TipoAverbacaoRequest tipoAverbacaoRequest) {

		TipoAverbacao tipoAverbacao = tipoAverbacaoRepository.findById(tipoAverbacaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("TipoAverbacao", "id", tipoAverbacaoRequest.getId()));

		tipoAverbacao.setFormaAverbacao(FormaAverbacaoEnum.getEnumByString(tipoAverbacaoRequest.getFormaAverbacao()));
		tipoAverbacao.setDeducaoAverbacao(DeducaoAverbacaoEnum.getEnumByString(tipoAverbacaoRequest.getDeducaoAverbacao()));
		tipoAverbacao.setDescricao(tipoAverbacaoRequest.getDescricao());
		tipoAverbacao.setCodigo(tipoAverbacaoRequest.getCodigo());

		tipoAverbacaoRepository.save(tipoAverbacao);

	}

	public void deleteTipoAverbacao(Long id) {
		TipoAverbacao tipoAverbacao = tipoAverbacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TipoAverbacao", "id", id));
		tipoAverbacaoRepository.delete(tipoAverbacao);
	}

	public PagedResponse<TipoAverbacaoResponse> getAllTiposAverbacoes(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<TipoAverbacao> tiposAverbacoes = tipoAverbacaoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (tiposAverbacoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tiposAverbacoes.getNumber(), tiposAverbacoes.getSize(),
					tiposAverbacoes.getTotalElements(), tiposAverbacoes.getTotalPages(), tiposAverbacoes.isLast());
		}

		List<TipoAverbacaoResponse> tiposAverbacoesResponse = tiposAverbacoes.map(tipoAverbacao -> {
			return new TipoAverbacaoResponse(tipoAverbacao);
		}).getContent();
		return new PagedResponse<>(tiposAverbacoesResponse, tiposAverbacoes.getNumber(), tiposAverbacoes.getSize(),
				tiposAverbacoes.getTotalElements(), tiposAverbacoes.getTotalPages(), tiposAverbacoes.isLast());

	}
	
	public List<TipoAverbacaoResponse> getAllTiposAverbacoes() {
		List<TipoAverbacao> tiposAverbacoes = tipoAverbacaoRepository.findAll();

		List<TipoAverbacaoResponse> listTipoAverbacaoResponse = new ArrayList<>();
		for (TipoAverbacao tipoAverbacao : tiposAverbacoes) {
			listTipoAverbacaoResponse.add(new TipoAverbacaoResponse(tipoAverbacao));
		}
		return listTipoAverbacaoResponse;
	}

	public TipoAverbacaoResponse getTipoAverbacaoById(Long tipoAverbacaoId) {
		TipoAverbacao tipoAverbacao = tipoAverbacaoRepository.findById(tipoAverbacaoId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoAverbacao", "id", tipoAverbacaoId));

		Usuario userCreated = usuarioRepository.findById(tipoAverbacao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoAverbacao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(tipoAverbacao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoAverbacao.getUpdatedBy()));

		TipoAverbacaoResponse tipoAverbacaoResponse = new TipoAverbacaoResponse(tipoAverbacao, tipoAverbacao.getCreatedAt(),
				userCreated.getNome(), tipoAverbacao.getUpdatedAt(), userUpdated.getNome());

		return tipoAverbacaoResponse;
	}

}
