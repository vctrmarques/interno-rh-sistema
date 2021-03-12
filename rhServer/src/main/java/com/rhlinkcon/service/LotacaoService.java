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
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.TipoContaLotacaoEnum;
import com.rhlinkcon.model.TipoLotacaoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoRequest;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.repository.CentroCustoRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class LotacaoService {

	@Autowired
	private LotacaoRepository lotacaoRepository;

	@Autowired
	private CentroCustoRepository centroCustoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createLotacao(LotacaoRequest lotacaoRequest) {

		Lotacao lotacao = new Lotacao(lotacaoRequest);

		if (lotacaoRequest.getCentroCustoId() != null)
			lotacao.setCentroCusto(centroCustoRepository.findById(lotacaoRequest.getCentroCustoId()).orElseThrow(
					() -> new ResourceNotFoundException("Lotacao", "id", lotacaoRequest.getCentroCustoId())));
		else
			lotacao.setCentroCusto(null);
		
		lotacaoRepository.save(lotacao);

	}

	public void updateLotacao(LotacaoRequest lotacaoRequest) {

		// Updating user's account
		Lotacao lotacao = lotacaoRepository.findById(lotacaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Lotacao", "id", lotacaoRequest.getId()));

		lotacao.setDescricao(lotacaoRequest.getDescricao());
		if (lotacaoRequest.getCentroCustoId() != null)
			lotacao.setCentroCusto(centroCustoRepository.findById(lotacaoRequest.getCentroCustoId()).orElseThrow(
					() -> new ResourceNotFoundException("CentroCusto", "id", lotacaoRequest.getCentroCustoId())));
		else
			lotacao.setCentroCusto(null);
		lotacao.setDescricaoCompleta(lotacaoRequest.getDescricaoCompleta());
		lotacao.setNivel(lotacaoRequest.getNivel());
		if(Strings.isNotEmpty(lotacaoRequest.getTipo()))
			lotacao.setTipo(TipoLotacaoEnum.getEnumByString(lotacaoRequest.getTipo()));
		lotacao.setEfetivo(lotacaoRequest.getEfetivo());

		if (Utils.checkStr(lotacaoRequest.getTipoConta()))
			lotacao.setTipoConta(TipoContaLotacaoEnum.getEnumByString(lotacaoRequest.getTipoConta()));
		else
			lotacao.setTipoConta(null);
		
		lotacao.setNumeroConta(lotacaoRequest.getNumeroConta());
		lotacao.setVigenciaFinal(lotacaoRequest.getVigenciaFinal());
		lotacao.setVigenciaInicial(lotacaoRequest.getVigenciaInicial());

		lotacaoRepository.save(lotacao);

	}

	public void deleteLotacao(Long id) {
		Lotacao lotacao = lotacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", id));
		lotacao.setExcluida(true);
		lotacaoRepository.save(lotacao);
	}

	public PagedResponse<LotacaoResponse> getAllLotacoes(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Lotacao> lotacoes = lotacaoRepository.findByDescricaoIgnoreCaseContainingAndExcluidaIsFalse(descricao, pageable);

		if (lotacoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lotacoes.getNumber(), lotacoes.getSize(),
					lotacoes.getTotalElements(), lotacoes.getTotalPages(), lotacoes.isLast());
		}

		List<LotacaoResponse> lotacoesResponse = lotacoes.map(lotacao -> {
			return new LotacaoResponse(lotacao);
		}).getContent();
		return new PagedResponse<>(lotacoesResponse, lotacoes.getNumber(), lotacoes.getSize(),
				lotacoes.getTotalElements(), lotacoes.getTotalPages(), lotacoes.isLast());

	}

	public LotacaoResponse getLotacaoById(Long lotacaoId) {
		Lotacao lotacao = lotacaoRepository.findById(lotacaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Lotacao", "id", lotacaoId));

		Usuario userCreated = usuarioRepository.findById(lotacao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", lotacao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(lotacao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", lotacao.getUpdatedBy()));

		LotacaoResponse lotacaoResponse = new LotacaoResponse(lotacao, lotacao.getCreatedAt(), userCreated.getNome(),
				lotacao.getUpdatedAt(), userUpdated.getNome());

		return lotacaoResponse;
	}

	public List<LotacaoResponse> getAllLotacoes() {

		List<Lotacao> lotacoes = lotacaoRepository.findAll();
		List<LotacaoResponse> lotacoesResponse = new ArrayList();

		if (!lotacoes.isEmpty()) {
			for (Lotacao lotacao: lotacoes) {
				lotacoesResponse.add(new LotacaoResponse(lotacao));
			}
			return lotacoesResponse;
		}

		return null;
	}
	
	public List<LotacaoResponse> getAllLotacoesFindByDescricao(String search) {

		List<Lotacao> lotacoes = lotacaoRepository.findByDescricaoIgnoreCaseContaining(search);
		List<LotacaoResponse> lotacoesResponse = new ArrayList();

		if (!lotacoes.isEmpty()) {
			for (Lotacao lotacao: lotacoes) {
				lotacoesResponse.add(new LotacaoResponse(lotacao));
			}
			return lotacoesResponse;
		}

		return null;
	}	

}
