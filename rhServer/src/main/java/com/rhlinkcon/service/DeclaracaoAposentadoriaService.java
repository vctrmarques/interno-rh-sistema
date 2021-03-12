package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.DeclaracaoAposentadoriaFiltro;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.DeclaracaoAposentadoria;
import com.rhlinkcon.model.DeclaracaoAposentadoriaAssinatura;
import com.rhlinkcon.model.DeclaracaoAposentadoriaAverbacao;
import com.rhlinkcon.model.DeclaracaoAposentadoriaTipoEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.HistoricoDeclaracaoAposentadoria;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.declaracaoAposentadoria.DeclaracaoAposentadoriaRequest;
import com.rhlinkcon.payload.declaracaoAposentadoria.DeclaracaoAposentadoriaResponse;
import com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura.DeclaracaoAposentadoriaAssinaturaRequest;
import com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura.DeclaracaoAposentadoriaAssinaturaResponse;
import com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao.DeclaracaoAposentadoriaAverbacaoRequest;
import com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao.DeclaracaoAposentadoriaAverbacaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.HistoricoDeclaracaoAposentadoriaRepository;
import com.rhlinkcon.repository.declaracaoAposentadoria.DeclaracaoAposentadoriaRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class DeclaracaoAposentadoriaService {

	@Autowired
	private DeclaracaoAposentadoriaAverbacaoService averbacaoService;

	@Autowired
	private DeclaracaoAposentadoriaAssinaturaService assinaturaService;

	@Autowired
	private DeclaracaoAposentadoriaRepository repository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HistoricoDeclaracaoAposentadoriaRepository historicoDeclaracaoAposentadoriaRepository;

	public PagedResponse<DeclaracaoAposentadoriaResponse> getAll(int page, int size, String descricao, String numero,
			String ano, String order) {
		Utils.validatePageNumberAndSize(page, size);
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<DeclaracaoAposentadoria> lista = repository
				.filtro(new DeclaracaoAposentadoriaFiltro(numero, ano, descricao), pageable);

		if (lista.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lista.getNumber(), lista.getSize(),
					lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
		}

		List<DeclaracaoAposentadoriaResponse> listaResponse = lista.map(item -> {
			return new DeclaracaoAposentadoriaResponse(item, Projecao.MEDIA);
		}).getContent();

		return new PagedResponse<>(listaResponse, lista.getNumber(), lista.getSize(), lista.getTotalElements(),
				lista.getTotalPages(), lista.isLast());
	}

	@Transactional
	public DeclaracaoAposentadoriaResponse retificar(Long id) {
		DeclaracaoAposentadoria declaracaoAtual = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoAposentadoria", "id", id));

		if (declaracaoAtual.getExcluida() || declaracaoAtual.getArquivada()
				|| declaracaoAtual.getTipoDeclaracao().equals(DeclaracaoAposentadoriaTipoEnum.RASCUNHO)) {
			throw new BadRequestException("Esta Declaração de Aposentadoria não pode ser retificada.  Numero: "
					+ declaracaoAtual.getNumero() + "/" + declaracaoAtual.getAno());
		}
		declaracaoAtual.setArquivada(true);

		// Arquiva a declaração a ser retificada.
		repository.save(declaracaoAtual);

		// Processa o histórico da declaração arquivada
		processaHistorico(declaracaoAtual);

		// Cria a declaração retificada
		DeclaracaoAposentadoria novaDeclaracao = new DeclaracaoAposentadoria();

		novaDeclaracao.setExcluida(false);
		novaDeclaracao.setArquivada(false);
		novaDeclaracao.setAno(declaracaoAtual.getAno());
		novaDeclaracao.setNumero(declaracaoAtual.getNumero());
		novaDeclaracao.setTipoDeclaracao(DeclaracaoAposentadoriaTipoEnum.RETIFICACAO);
		novaDeclaracao.setFuncionario(declaracaoAtual.getFuncionario());
		if (declaracaoAtual.getAnexos() != null && !declaracaoAtual.getAnexos().isEmpty())
			novaDeclaracao.setAnexos(declaracaoAtual.getAnexos());
		novaDeclaracao.setDeclaracaoAposentadoria(declaracaoAtual);
		novaDeclaracao.setNumeroRetificacao(
				getNumeroRetificacaoByAnoAndNumero(novaDeclaracao.getAno(), novaDeclaracao.getNumero()));

		repository.save(novaDeclaracao);

		// Processamento de Períodos
		if (declaracaoAtual.getAverbacoes() != null && !declaracaoAtual.getAverbacoes().isEmpty())
			for (DeclaracaoAposentadoriaAverbacao averbacao : declaracaoAtual.getAverbacoes()) {
				DeclaracaoAposentadoriaAverbacao novaAverbacao = new DeclaracaoAposentadoriaAverbacao();
				novaAverbacao.setDeclaracaoAposentadoria(novaDeclaracao);
				novaAverbacao.setEmpregador(averbacao.getEmpregador());
				novaAverbacao.setPeriodoInicio(averbacao.getPeriodoInicio());
				novaAverbacao.setPeriodoFim(averbacao.getPeriodoFim());
				novaAverbacao.setFonteInf(averbacao.getFonteInf());
				novaAverbacao.setObservacao(averbacao.getObservacao());
				novaAverbacao.setAverbado(averbacao.getAverbado());
				averbacaoService.create(novaAverbacao);
			}

		// Processamento de Assinaturas
		if (declaracaoAtual.getAssinaturas() != null && !declaracaoAtual.getAssinaturas().isEmpty())
			for (DeclaracaoAposentadoriaAssinatura assinatura : declaracaoAtual.getAssinaturas()) {
				DeclaracaoAposentadoriaAssinatura novaAssinatura = new DeclaracaoAposentadoriaAssinatura();
				novaAssinatura.setDeclaracaoAposentadoria(novaDeclaracao);
				novaAssinatura.setFuncionario(assinatura.getFuncionario());
				novaAssinatura.setFuncao(assinatura.getFuncao());
				assinaturaService.create(novaAssinatura);
			}

		// Processa o histórico da declaração retificada
		processaHistorico(novaDeclaracao);

		return new DeclaracaoAposentadoriaResponse(novaDeclaracao, Projecao.BASICA);

	}

	@Transactional
	public DeclaracaoAposentadoriaResponse create(DeclaracaoAposentadoriaRequest request) {
		DeclaracaoAposentadoria obj = new DeclaracaoAposentadoria();

		obj.setExcluida(false);
		obj.setArquivada(false);
		Calendar dataHoje = Calendar.getInstance();
		obj.setAno(dataHoje.get(Calendar.YEAR));
		obj.setNumero(getNumeroByAno(obj.getAno()));
		obj.setTipoDeclaracao(isRascunho(request));

		// Funcionário
		if (request.getFuncionarioId() != null && request.getFuncionarioId() != 0) {
			Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));
			obj.setFuncionario(funcionario);
		}

		// Anexos
		if (Utils.checkList(request.getAnexos())) {
			obj.setAnexos(anexoRepository.findAllByIdIn(request.getAnexos()));
		}

		repository.save(obj);

		// Processamento de Períodos
		for (DeclaracaoAposentadoriaAverbacaoRequest item : request.getAverbacoes()) {
			item.setDeclaracaoAposentadoriaId(obj.getId());
			averbacaoService.create(item);
		}

		// Processamento de Assinaturas
		if (Utils.checkSetList(request.getAssinaturas())) {
			for (DeclaracaoAposentadoriaAssinaturaRequest item : request.getAssinaturas()) {
				item.setDeclaracaoAposentadoriaId(obj.getId());
				assinaturaService.create(item);
			}
		}

		// Processa o histórico
		processaHistorico(obj);

		return new DeclaracaoAposentadoriaResponse(obj, Projecao.BASICA);
	}

	private DeclaracaoAposentadoriaTipoEnum isRascunho(DeclaracaoAposentadoriaRequest request) {
		DeclaracaoAposentadoriaTipoEnum rascunho = DeclaracaoAposentadoriaTipoEnum.DECLARACAO;

		if (request.getFuncionarioId() == null || request.getFuncionarioId() == 0)
			rascunho = DeclaracaoAposentadoriaTipoEnum.RASCUNHO;

		if (!Utils.checkSetList(request.getAssinaturas()) || request.getAssinaturas().size() <= 1)
			rascunho = DeclaracaoAposentadoriaTipoEnum.RASCUNHO;
		return rascunho;
	}

	private void processaHistorico(DeclaracaoAposentadoria obj) {
		if (obj.getTipoDeclaracao().equals(DeclaracaoAposentadoriaTipoEnum.RASCUNHO)) {

			// Gravar histórico tipo "RASCUNHO".
			HistoricoDeclaracaoAposentadoria historico = new HistoricoDeclaracaoAposentadoria();
			historico.setDeclaracaoAposentadoria(obj);
			historico.setTipoDeclaracao(DeclaracaoAposentadoriaTipoEnum.RASCUNHO);
			historicoDeclaracaoAposentadoriaRepository.save(historico);
		} else if (obj.getTipoDeclaracao().equals(DeclaracaoAposentadoriaTipoEnum.DECLARACAO)) {

			// Gravar histórico tipo "DECLARAÇÃO".
			HistoricoDeclaracaoAposentadoria historico = new HistoricoDeclaracaoAposentadoria();
			historico.setDeclaracaoAposentadoria(obj);
			historico.setTipoDeclaracao(DeclaracaoAposentadoriaTipoEnum.DECLARACAO);
			historicoDeclaracaoAposentadoriaRepository.save(historico);
		} else if (obj.getTipoDeclaracao().equals(DeclaracaoAposentadoriaTipoEnum.RETIFICACAO)) {

			// Gravar histórico tipo "RETIFICACAO".
			HistoricoDeclaracaoAposentadoria historico = new HistoricoDeclaracaoAposentadoria();
			historico.setDeclaracaoAposentadoria(obj);
			historico.setTipoDeclaracao(DeclaracaoAposentadoriaTipoEnum.RETIFICACAO);
			historicoDeclaracaoAposentadoriaRepository.save(historico);
		}
	}

	@Transactional
	public DeclaracaoAposentadoriaResponse update(DeclaracaoAposentadoriaRequest request) {

		DeclaracaoAposentadoria obj = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoAposentadoria", "id", request.getId()));

		if (!obj.getTipoDeclaracao().equals(DeclaracaoAposentadoriaTipoEnum.RETIFICACAO))
			obj.setTipoDeclaracao(isRascunho(request));

		// Processamento de Períodos
		List<Long> averbacoesExistentesIds = new ArrayList<Long>();
		obj.getAverbacoes().forEach(averbacao -> averbacoesExistentesIds.add(averbacao.getId()));

		List<Long> averbacoesInseridasIds = new ArrayList<Long>();
		for (DeclaracaoAposentadoriaAverbacaoRequest item : request.getAverbacoes()) {
			if (Objects.isNull(item.getId())) {
				item.setDeclaracaoAposentadoriaId(obj.getId());
				averbacaoService.create(item);
			}
			averbacoesInseridasIds.add(item.getId());
		}

		for (Long averbacaoId : averbacoesExistentesIds) {
			if (!averbacoesInseridasIds.contains(averbacaoId)) {
				averbacaoService.delete(averbacaoId);
			}
		}

		// Funcionário
		if (request.getFuncionarioId() != null && request.getFuncionarioId() != 0) {
			Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));
			obj.setFuncionario(funcionario);
		}

		// Anexos
		if (Utils.checkList(request.getAnexos())) {
			obj.setAnexos(anexoRepository.findAllByIdIn(request.getAnexos()));
		}

		repository.save(obj);

		// Processamento de Assinaturas
		List<Long> assinaturasExistentesIds = new ArrayList<Long>();
		obj.getAssinaturas().forEach(assinatura -> assinaturasExistentesIds.add(assinatura.getId()));

		List<Long> assinaturasInseridasIds = new ArrayList<Long>();
		if (Utils.checkSetList(request.getAssinaturas())) {
			for (DeclaracaoAposentadoriaAssinaturaRequest item : request.getAssinaturas()) {
				if (Objects.isNull(item.getId())) {
					item.setDeclaracaoAposentadoriaId(obj.getId());
					assinaturaService.create(item);
				}
				assinaturasInseridasIds.add(item.getId());
			}
		}

		for (Long assinaturaId : assinaturasExistentesIds) {
			if (!assinaturasInseridasIds.contains(assinaturaId)) {
				assinaturaService.delete(assinaturaId);
			}
		}

		// Processa o histórico
		processaHistorico(obj);

		return new DeclaracaoAposentadoriaResponse(obj, Projecao.BASICA);

	}

	private Long getNumeroByAno(Integer ano) {
		Long numero = repository.maxNumeroByAno(ano);
		if (Objects.isNull(numero))
			numero = Long.valueOf(0);
		return numero + 1;
	}

	private Long getNumeroRetificacaoByAnoAndNumero(Integer ano, Long numero) {
		Long numeroRetificacao = repository.maxNumeroRetificacaoByAnoAndNumero(ano, numero);
		if (Objects.isNull(numeroRetificacao))
			numeroRetificacao = Long.valueOf(0);
		return numeroRetificacao + 1;
	}

	public void deleteAnexo(Long id) {
		DeclaracaoAposentadoria d = repository.findAllByAnexosId(id);
		if (!Objects.isNull(d)) {
			d.getAnexos().removeIf(a -> a.getId().equals(id));
			repository.save(d);
		}
		Anexo anexo = anexoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", id));
		anexoRepository.delete(anexo);

	}

	public DeclaracaoAposentadoriaResponse getById(Long id) {

		DeclaracaoAposentadoria d = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoAposentadoria", "id", id));

		DeclaracaoAposentadoriaResponse declaracaoAposentadoriaResponse = new DeclaracaoAposentadoriaResponse(d,
				Projecao.COMPLETA);

		if (Utils.checkList(d.getAnexos())) {
			declaracaoAposentadoriaResponse.setAnexos(new ArrayList<>());
			d.getAnexos().forEach(e -> declaracaoAposentadoriaResponse.getAnexos().add(new AnexoResponse(e)));
		}
		if (Utils.checkSetList(d.getAverbacoes())) {
			declaracaoAposentadoriaResponse.setAverbacoes(new ArrayList<>());
			d.getAverbacoes().forEach(e -> declaracaoAposentadoriaResponse.getAverbacoes()
					.add(new DeclaracaoAposentadoriaAverbacaoResponse(e)));
		}
		if (Utils.checkSetList(d.getAssinaturas())) {
			declaracaoAposentadoriaResponse.setAssinaturas(new ArrayList<>());
			d.getAssinaturas().forEach(e -> declaracaoAposentadoriaResponse.getAssinaturas()
					.add(new DeclaracaoAposentadoriaAssinaturaResponse(e)));
		}

		if (Objects.nonNull(d.getCreatedBy()))
			declaracaoAposentadoriaResponse.setCriadoPor(usuarioService.criadoPor(d));

		if (Objects.nonNull(d.getUpdatedBy()))
			declaracaoAposentadoriaResponse.setAlteradoPor(usuarioService.alteradoPor(d));

		return declaracaoAposentadoriaResponse;
	}

	public void delete(Long id) {
		DeclaracaoAposentadoria d = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoAposentadoria", "id", id));

		d.setExcluida(true);

		repository.save(d);

//		if (Utils.checkList(d.getAnexos())) {
//			d.getAnexos().forEach(e -> anexoRepository.deleteById(e.getId()));
//		}
//		if (Utils.checkSetList(d.getAverbacoes())) {
//			d.getAverbacoes().forEach(e -> averbacaoService.delete(e.getId()));
//		}
//		if (Utils.checkSetList(d.getAssinaturas())) {
//			d.getAssinaturas().forEach(e -> assinaturaService.delete(e.getId()));
//		}

	}

}
