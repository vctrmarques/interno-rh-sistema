package com.rhlinkcon.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
import com.rhlinkcon.filtro.CertidaoCompensacaoFiltro;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.CertidaoCompensacao;
import com.rhlinkcon.model.CertidaoCompensacaoAssinatura;
import com.rhlinkcon.model.CertidaoCompensacaoHistorico;
import com.rhlinkcon.model.CertidaoCompensacaoPeriodo;
import com.rhlinkcon.model.CertidaoCompensacaoTipoEnum;
import com.rhlinkcon.model.ClassificacaoCertidaoCompensacaoEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FundoCertidaoCompensacaoEnum;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.StatusCertidaoCompensacaoEnum;
import com.rhlinkcon.payload.certidaoCompensacao.CertidaoCompensacaoRequest;
import com.rhlinkcon.payload.certidaoCompensacao.CertidaoCompensacaoResponse;
import com.rhlinkcon.payload.certidaoCompensacaoAssinatura.CertidaoCompensacaoAssinaturaRequest;
import com.rhlinkcon.payload.certidaoCompensacaoPeriodo.CertidaoCompensacaoPeriodoRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.certidaoCompensacao.CertidaoCompensacaoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class CertidaoCompensacaoService {

	@Autowired
	private CertidaoCompensacaoPeriodoService periodoService;

	@Autowired
	private CertidaoCompensacaoAssinaturaService assinaturaService;

	@Autowired
	private CertidaoCompensacaoHistoricoService historicoService;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private CertidaoCompensacaoRepository repository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public PagedResponse<CertidaoCompensacaoResponse> getAll(int page, int size, String order,
			CertidaoCompensacaoFiltro certidaoCompensacaoFiltro) {
		Utils.validatePageNumberAndSize(page, size);
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CertidaoCompensacao> lista = repository.filtro(certidaoCompensacaoFiltro, pageable);

		if (lista.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lista.getNumber(), lista.getSize(),
					lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
		}

		List<CertidaoCompensacaoResponse> listaResponse = lista.map(item -> {
			return new CertidaoCompensacaoResponse(item, Projecao.MEDIA);
		}).getContent();

		return new PagedResponse<>(listaResponse, lista.getNumber(), lista.getSize(), lista.getTotalElements(),
				lista.getTotalPages(), lista.isLast());
	}

	@Transactional
	public CertidaoCompensacaoResponse retificar(Long id) {
		CertidaoCompensacao certidaoCompensacaoAtual = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CertidaoCompensacao", "id", id));

		if (certidaoCompensacaoAtual.getStatusAtual().equals(StatusCertidaoCompensacaoEnum.EXCLUIDA)
				|| certidaoCompensacaoAtual.getArquivada()
				|| certidaoCompensacaoAtual.getTipoCertidaoCompensacao().equals(CertidaoCompensacaoTipoEnum.RASCUNHO)) {
			throw new BadRequestException("Esta Certidão de Compensação não pode ser retificada.  Numero: "
					+ certidaoCompensacaoAtual.getNumero() + "/" + certidaoCompensacaoAtual.getAno());
		}
		certidaoCompensacaoAtual.setArquivada(true);

		// Arquiva a declaração a ser retificada.
		repository.save(certidaoCompensacaoAtual);

		// Cria a declaração retificada
		CertidaoCompensacao novaCertidaoCompensacao = new CertidaoCompensacao();

		novaCertidaoCompensacao.setArquivada(false);
		novaCertidaoCompensacao.setStatusAtual(certidaoCompensacaoAtual.getStatusAtual());
		novaCertidaoCompensacao.setAno(certidaoCompensacaoAtual.getAno());
		novaCertidaoCompensacao.setNumero(certidaoCompensacaoAtual.getNumero());
		novaCertidaoCompensacao.setTipoCertidaoCompensacao(CertidaoCompensacaoTipoEnum.RETIFICACAO);
		novaCertidaoCompensacao.setFuncionario(certidaoCompensacaoAtual.getFuncionario());
		if (certidaoCompensacaoAtual.getAnexos() != null && !certidaoCompensacaoAtual.getAnexos().isEmpty())
			novaCertidaoCompensacao.setAnexos(certidaoCompensacaoAtual.getAnexos());
		novaCertidaoCompensacao.setCertidaoCompensacao(certidaoCompensacaoAtual);
		novaCertidaoCompensacao.setNumeroRetificacao(getNumeroRetificacaoByAnoAndNumero(
				novaCertidaoCompensacao.getAno(), novaCertidaoCompensacao.getNumero()));

		// Classificações
		novaCertidaoCompensacao.setClassificacoes(new ArrayList<ClassificacaoCertidaoCompensacaoEnum>());
		for (ClassificacaoCertidaoCompensacaoEnum cccEnum : certidaoCompensacaoAtual.getClassificacoes()) {
			novaCertidaoCompensacao.getClassificacoes().add(cccEnum);
		}

		repository.save(novaCertidaoCompensacao);

		// Processamento de Períodos
		if (certidaoCompensacaoAtual.getPeriodos() != null && !certidaoCompensacaoAtual.getPeriodos().isEmpty())
			for (CertidaoCompensacaoPeriodo periodo : certidaoCompensacaoAtual.getPeriodos()) {
				CertidaoCompensacaoPeriodo novoPeriodo = new CertidaoCompensacaoPeriodo();
				novoPeriodo.setCertidaoCompensacao(novaCertidaoCompensacao);
				novoPeriodo.setPeriodoInicio(periodo.getPeriodoInicio());
				novoPeriodo.setPeriodoFim(periodo.getPeriodoFim());
				periodoService.create(novoPeriodo);
			}

		// Processamento de Assinaturas
		if (certidaoCompensacaoAtual.getAssinaturas() != null && !certidaoCompensacaoAtual.getAssinaturas().isEmpty())
			for (CertidaoCompensacaoAssinatura assinatura : certidaoCompensacaoAtual.getAssinaturas()) {
				CertidaoCompensacaoAssinatura novaAssinatura = new CertidaoCompensacaoAssinatura();
				novaAssinatura.setCertidaoCompensacao(novaCertidaoCompensacao);
				novaAssinatura.setFuncionario(assinatura.getFuncionario());
				novaAssinatura.setFuncao(assinatura.getFuncao());
				assinaturaService.create(novaAssinatura);
			}

		// Processa o histórico da declaração retificada
		createHistorico(novaCertidaoCompensacao, "");

		return new CertidaoCompensacaoResponse(novaCertidaoCompensacao, Projecao.BASICA);

	}

	@Transactional
	public CertidaoCompensacaoResponse create(CertidaoCompensacaoRequest request) {
		CertidaoCompensacao obj = new CertidaoCompensacao();

		obj.setArquivada(false);
		Calendar dataHoje = Calendar.getInstance();
		obj.setAno(dataHoje.get(Calendar.YEAR));
		obj.setNumero(getNumeroByAno(obj.getAno()));
		obj.setStatusAtual(StatusCertidaoCompensacaoEnum.CRIADA);
		obj.setTipoCertidaoCompensacao(isRascunho(request));

		if (Objects.nonNull(request.getLotacaoId()))
			obj.setLotacao(new Lotacao(request.getLotacaoId()));

		obj.setProcesso(request.getProcesso());

		// Funcionário
		if (request.getFuncionarioId() != null && request.getFuncionarioId() != 0) {
			Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));
			obj.setFuncionario(funcionario);

			try {
				if (getFundo(obj.getFuncionario().getId()))
					obj.setFundo(FundoCertidaoCompensacaoEnum.FUNFIN);
				else
					obj.setFundo(FundoCertidaoCompensacaoEnum.FUNPREV);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Salvando a alteração do número da ctps e série
			obj.getFuncionario().setNumeroCtps(request.getNumeroCtps());
			obj.getFuncionario().setSerieCtps(request.getSerieCtps());
			funcionarioRepository.save(obj.getFuncionario());
		}

		// Anexos
		if (Utils.checkList(request.getAnexos()))
			obj.setAnexos(anexoRepository.findAllByIdIn(request.getAnexos()));

		// Classificações
		obj.setClassificacoes(new ArrayList<ClassificacaoCertidaoCompensacaoEnum>());
		for (String classificacaoStr : request.getClassificacoes()) {
			ClassificacaoCertidaoCompensacaoEnum cccEnum = ClassificacaoCertidaoCompensacaoEnum
					.getEnumByString(classificacaoStr);
			obj.getClassificacoes().add(cccEnum);
		}

		repository.save(obj);

		// Criação de histórico
		createHistorico(obj, request.getObservacaoParaHistorico());

		// Processamento de Períodos
		for (CertidaoCompensacaoPeriodoRequest item : request.getPeriodos()) {
			item.setCertidaoCompensacaoId(obj.getId());
			periodoService.create(item);
		}

		// Processamento de Assinaturas
		if (Utils.checkSetList(request.getAssinaturas())) {
			for (CertidaoCompensacaoAssinaturaRequest item : request.getAssinaturas()) {
				item.setCertidaoCompensacaoId(obj.getId());
				assinaturaService.create(item);
			}
		}

		return new CertidaoCompensacaoResponse(obj, Projecao.BASICA);
	}

	private CertidaoCompensacaoTipoEnum isRascunho(CertidaoCompensacaoRequest request) {
		CertidaoCompensacaoTipoEnum rascunho = CertidaoCompensacaoTipoEnum.CERTIDAO_COMPENSACAO;

		if (request.getFuncionarioId() == null || request.getFuncionarioId() == 0)
			rascunho = CertidaoCompensacaoTipoEnum.RASCUNHO;

		if (!Utils.checkSetList(request.getPeriodos()))
			rascunho = CertidaoCompensacaoTipoEnum.RASCUNHO;

		if (!Utils.checkSetList(request.getAssinaturas()) || request.getAssinaturas().size() <= 1)
			rascunho = CertidaoCompensacaoTipoEnum.RASCUNHO;
		return rascunho;
	}

	private void createHistorico(CertidaoCompensacao obj, String obs) {
		CertidaoCompensacaoHistorico historico = new CertidaoCompensacaoHistorico();
		historico.setCertidaoCompensacao(obj);
		for (ClassificacaoCertidaoCompensacaoEnum item : obj.getClassificacoes()) {
			historico.setClassificacoes(new ArrayList<ClassificacaoCertidaoCompensacaoEnum>());
			historico.getClassificacoes().add(item);
		}
		historico.setFundo(obj.getFundo());
		historico.setStatusAtual(obj.getStatusAtual());
		historico.setObservacao(obs);

		if (obj.getTipoCertidaoCompensacao().equals(CertidaoCompensacaoTipoEnum.RASCUNHO)) {
			// Gravar histórico tipo "RASCUNHO".
			historico.setTipoCertidaoCompensacao(CertidaoCompensacaoTipoEnum.RASCUNHO);
		} else if (obj.getTipoCertidaoCompensacao().equals(CertidaoCompensacaoTipoEnum.CERTIDAO_COMPENSACAO)) {
			// Gravar histórico tipo "CERTIDAO_COMPENSACAO".
			historico.setTipoCertidaoCompensacao(CertidaoCompensacaoTipoEnum.CERTIDAO_COMPENSACAO);
		} else if (obj.getTipoCertidaoCompensacao().equals(CertidaoCompensacaoTipoEnum.RETIFICACAO)) {
			// Gravar histórico tipo "RETIFICACAO".
			historico.setTipoCertidaoCompensacao(CertidaoCompensacaoTipoEnum.RETIFICACAO);
		}

		historicoService.create(historico);
	}

	@Transactional
	public CertidaoCompensacaoResponse update(CertidaoCompensacaoRequest request) {

		CertidaoCompensacao obj = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("CertidaoCompensacao", "id", request.getId()));

		if (Objects.nonNull(request.getStatusAtual()))
			obj.setStatusAtual(StatusCertidaoCompensacaoEnum.getEnumByString(request.getStatusAtual()));

		if (Objects.nonNull(request.getLotacaoId()))
			obj.setLotacao(new Lotacao(request.getLotacaoId()));

		obj.setProcesso(request.getProcesso());

		if (!obj.getTipoCertidaoCompensacao().equals(CertidaoCompensacaoTipoEnum.RETIFICACAO))
			obj.setTipoCertidaoCompensacao(isRascunho(request));

		// Classificações
		obj.setClassificacoes(new ArrayList<ClassificacaoCertidaoCompensacaoEnum>());
		for (String classificacaoStr : request.getClassificacoes()) {
			ClassificacaoCertidaoCompensacaoEnum cccEnum = ClassificacaoCertidaoCompensacaoEnum
					.getEnumByString(classificacaoStr);
			obj.getClassificacoes().add(cccEnum);
		}

		// Processamento de Períodos
		List<Long> averbacoesExistentesIds = new ArrayList<Long>();
		obj.getPeriodos().forEach(averbacao -> averbacoesExistentesIds.add(averbacao.getId()));

		List<Long> averbacoesInseridasIds = new ArrayList<Long>();
		for (CertidaoCompensacaoPeriodoRequest item : request.getPeriodos()) {
			if (Objects.isNull(item.getId())) {
				item.setCertidaoCompensacaoId(obj.getId());
				periodoService.create(item);
			}
			averbacoesInseridasIds.add(item.getId());
		}

		for (Long averbacaoId : averbacoesExistentesIds) {
			if (!averbacoesInseridasIds.contains(averbacaoId)) {
				periodoService.delete(averbacaoId);
			}
		}

		// Funcionário
		if (request.getFuncionarioId() != null && request.getFuncionarioId() != 0) {
			Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));
			obj.setFuncionario(funcionario);
			try {
				if (getFundo(obj.getFuncionario().getId())) {
					obj.setFundo(FundoCertidaoCompensacaoEnum.FUNFIN);
				} else {
					obj.setFundo(FundoCertidaoCompensacaoEnum.FUNPREV);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Anexos
		if (Utils.checkList(request.getAnexos()))
			obj.setAnexos(anexoRepository.findAllByIdIn(request.getAnexos()));

		repository.save(obj);

		// Processamento de Assinaturas
		List<Long> assinaturasExistentesIds = new ArrayList<Long>();
		obj.getAssinaturas().forEach(assinatura -> assinaturasExistentesIds.add(assinatura.getId()));

		List<Long> assinaturasInseridasIds = new ArrayList<Long>();
		if (Utils.checkSetList(request.getAssinaturas())) {
			for (CertidaoCompensacaoAssinaturaRequest item : request.getAssinaturas()) {
				if (Objects.isNull(item.getId())) {
					item.setCertidaoCompensacaoId(obj.getId());
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
		createHistorico(obj, request.getObservacaoParaHistorico());

		return new CertidaoCompensacaoResponse(obj, Projecao.BASICA);

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

	/*
	 * [RN02.4] – Atributo “Fundo” O atributo “Fundo” da tabela
	 * “certidao_compensacao” é um ENUM que deve ser adicionado ao registro no ato
	 * de salvamento respeitando a seguinte regra:
	 * 
	 * FUNFIN – Nascidos após 31/12/1954 e admitidos até 31/04/2002. FUNPREV – O que
	 * não se enquadrar em FUNFIN será FUNPREV.
	 * 
	 */
	private boolean getFundo(Long funcionarioId) throws ParseException {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		return comparaDatas(funcionario.getDataNascimento(), funcionario.getDataAdmissao());

	}

	private boolean comparaDatas(Instant dataNascimento, Instant dataAdmissao) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

		Instant dataNascidos = Utils.getApenasData(sd.parse("31/12/1954")).toInstant();
		Instant dataAdmitidos = Utils.getApenasData(sd.parse("31/04/2002")).toInstant();

		int resultadoNascidos = dataNascimento.compareTo(dataNascidos);
		int resultadoAdmitido = dataAdmissao.compareTo(dataAdmitidos);

		return (resultadoNascidos > 0 && resultadoAdmitido <= 0);

	}

	public void deleteAnexo(Long id) {
		CertidaoCompensacao d = repository.findAllByAnexosId(id);
		if (!Objects.isNull(d)) {
			d.getAnexos().removeIf(a -> a.getId().equals(id));
			repository.save(d);
		}
		Anexo anexo = anexoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", id));
		anexoRepository.delete(anexo);

	}

	public CertidaoCompensacaoResponse getById(Long id) {

		CertidaoCompensacao d = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CertidaoCompensacao", "id", id));

		CertidaoCompensacaoResponse CertidaoCompensacaoResponse = new CertidaoCompensacaoResponse(d, Projecao.COMPLETA);

		if (Objects.nonNull(d.getCreatedBy()))
			CertidaoCompensacaoResponse.setCriadoPor(usuarioService.criadoPor(d));

		if (Objects.nonNull(d.getUpdatedBy()))
			CertidaoCompensacaoResponse.setAlteradoPor(usuarioService.alteradoPor(d));

		return CertidaoCompensacaoResponse;
	}

	public void delete(Long id) {
		CertidaoCompensacao obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CertidaoCompensacao", "id", id));

		obj.setStatusAtual(StatusCertidaoCompensacaoEnum.EXCLUIDA);

		repository.save(obj);

		createHistorico(obj, "");

		/*
		 * if(Utils.checkList(d.getAnexos())) { d.getAnexos().forEach(e ->
		 * anexoRepository.deleteById(e.getId())); }
		 * if(Utils.checkSetList(d.getPeriodos())) { d.getPeriodos().forEach(e ->
		 * periodoService.delete(e.getId())); }
		 * if(Utils.checkSetList(d.getAssinaturas())) { d.getAssinaturas().forEach(e ->
		 * assinaturaService.delete(e.getId())); }
		 * 
		 * repository.delete(d);
		 */
	}

	public void alteraStatus(Long id, String status) {
		CertidaoCompensacao obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CertidaoCompensacao", "id", id));
		obj.setStatusAtual(StatusCertidaoCompensacaoEnum.getEnumByString(status));

		repository.save(obj);

		createHistorico(obj, "");
	}

}
