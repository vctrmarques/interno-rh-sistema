package com.rhlinkcon.service;

import com.rhlinkcon.filtro.AvaliacaoDesempenhoFiltro;
import com.rhlinkcon.model.AvaliacaoDesempenho;
import com.rhlinkcon.model.AvaliacaoDesempenhoItem;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoItemRequest;
import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoRequest;
import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CargoRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.avaliacaoDesempenho.AvaliacaoDesempenhoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AvaliacaoDesempenhoService {

	@Autowired
	private AvaliacaoDesempenhoRepository avaliacaoDesempenhoRepository;

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private LotacaoRepository lotacaoRepository;

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Boolean existsByNome(String nome) {
		return avaliacaoDesempenhoRepository.existsByNome(nome);
	}

	public Boolean existsByNomeAndIdNot(String nome, Long id) {
		return avaliacaoDesempenhoRepository.existsByNomeAndIdNot(nome, id);
	}

	public Boolean existsByCodAvaliacao(String codAvaliacao) {
		return avaliacaoDesempenhoRepository.existsByCodAvaliacao(codAvaliacao);
	}

	public Boolean existsByCodAvaliacaoAndIdNot(String codAvaliacao, Long id) {
		return avaliacaoDesempenhoRepository.existsByCodAvaliacaoAndIdNot(codAvaliacao, id);
	}

	public AvaliacaoDesempenhoResponse getById(Long id) {
		Optional<AvaliacaoDesempenho> avaliacaoDesempenho = avaliacaoDesempenhoRepository.findById(id);
		AvaliacaoDesempenhoResponse response;
		if (avaliacaoDesempenho.isPresent()) {
			response = new AvaliacaoDesempenhoResponse(avaliacaoDesempenho.get());
			response.setCriadoEm(avaliacaoDesempenho.get().getCreatedAt());
			response.setAlteradoEm(avaliacaoDesempenho.get().getUpdatedAt());
			if (Objects.nonNull(avaliacaoDesempenho.get().getCreatedBy())) {
				Optional<Usuario> usuario = usuarioRepository.findById(avaliacaoDesempenho.get().getCreatedBy());
				usuario.ifPresent(u -> response.setCriadoPor(u.getNome()));
				usuario = usuarioRepository.findById(avaliacaoDesempenho.get().getUpdatedBy());
				usuario.ifPresent(u -> response.setAlteradoPor(u.getNome()));
			}
		} else {
			throw new ObjectNotFoundException(AvaliacaoDesempenho.class, "");
		}
		return response;
	}

	public List<AvaliacaoDesempenhoResponse> getAll() {
		List<AvaliacaoDesempenho> avaliacoes = avaliacaoDesempenhoRepository.findAll();
		List<AvaliacaoDesempenhoResponse> listAvaliacoesResponse = new ArrayList<>();
		for (AvaliacaoDesempenho avaliacao : avaliacoes) {
			listAvaliacoesResponse.add(new AvaliacaoDesempenhoResponse(avaliacao));
		}
		return listAvaliacoesResponse;
	}

	public PagedResponse<AvaliacaoDesempenhoResponse> getAll(int page, int size, String order, AvaliacaoDesempenhoFiltro avaliacaoDesempenhoFiltro) {
		Utils.validatePageNumberAndSize(page, size);
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);
		Page<AvaliacaoDesempenho> avaliacoes = avaliacaoDesempenhoRepository.findAll(pageable);
		if (Objects.nonNull(avaliacaoDesempenhoFiltro.getNome())) {
			avaliacoes = avaliacaoDesempenhoRepository.findByNomeIgnoreCaseContaining(avaliacaoDesempenhoFiltro.getNome(), pageable);
		}
		if (avaliacoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), avaliacoes.getNumber(), avaliacoes.getSize(),
					avaliacoes.getTotalElements(), avaliacoes.getTotalPages(), avaliacoes.isLast());
		}
		List<AvaliacaoDesempenhoResponse> avaliacoesResponses = avaliacoes.map(avaliacao -> {
			return new AvaliacaoDesempenhoResponse(avaliacao);
		}).getContent();
		return new PagedResponse<>(avaliacoesResponses, avaliacoes.getNumber(), avaliacoes.getSize(), avaliacoes.getTotalElements(),
				avaliacoes.getTotalPages(), avaliacoes.isLast());
	}

	private AvaliacaoDesempenho createEntity(AvaliacaoDesempenhoRequest avaliacaoDesempenhoRequest) {
		AvaliacaoDesempenho avaliacaoDesempenho = new AvaliacaoDesempenho();
		avaliacaoDesempenho.setNome(avaliacaoDesempenhoRequest.getNome());
		avaliacaoDesempenho.setModelo(avaliacaoDesempenhoRequest.getModelo());
		avaliacaoDesempenho.setCodAvaliacao(avaliacaoDesempenhoRequest.getCodAvaliacao());
		//
		Optional<EmpresaFilial> empresaFilial = empresaFilialRepository.findById(avaliacaoDesempenhoRequest.getEmpresaFilialId());
		empresaFilial.ifPresent(avaliacaoDesempenho::setEmpresaFilial);
		avaliacaoDesempenho.setLotacoes(lotacaoRepository.findAllById(avaliacaoDesempenhoRequest.getLotacoesIds()));
		avaliacaoDesempenho.setCargos(cargoRepository.findAllById(avaliacaoDesempenhoRequest.getCargosIds()));
		avaliacaoDesempenho.setFuncoes(funcaoRepository.findAllById(avaliacaoDesempenhoRequest.getFuncoesIds()));
		//
		avaliacaoDesempenho.setItens(new ArrayList<>());
		AvaliacaoDesempenhoItem item = new AvaliacaoDesempenhoItem();
		AvaliacaoDesempenhoItemRequest itemRequest = avaliacaoDesempenhoRequest.getItem();
		item.setAvaliacaoDesempenho(avaliacaoDesempenho);
		item.setCodItemAvaliacao(itemRequest.getCodItemAvaliacao());
		item.setNome(itemRequest.getNome());
		item.setSeqAvaliacao(itemRequest.getSeqAvaliacao());
		item.setDescricao(itemRequest.getDescricao());
		item.setTipoQuestao(itemRequest.getTipoQuestao());
		item.setFormaAvaliacao(itemRequest.getFormaAvaliacao());
		avaliacaoDesempenho.getItens().add(item);
		return avaliacaoDesempenho;
	}

	public void create(AvaliacaoDesempenhoRequest avaliacaoDesempenhoRequest) {
		avaliacaoDesempenhoRepository.save(createEntity(avaliacaoDesempenhoRequest));
	}

	public void update(AvaliacaoDesempenhoRequest avaliacaoDesempenhoRequest) {
		AvaliacaoDesempenho avaliacaoDesempenho = createEntity(avaliacaoDesempenhoRequest);
		avaliacaoDesempenho.setId(avaliacaoDesempenhoRequest.getId());
		avaliacaoDesempenhoRepository.save(avaliacaoDesempenho);
	}

	public void delete(Long id) {
		avaliacaoDesempenhoRepository.deleteById(id);
	}
}