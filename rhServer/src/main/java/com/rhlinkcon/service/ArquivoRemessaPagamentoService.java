package com.rhlinkcon.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.ArquivoRemessaPagamentoFiltro;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AnexoPastaEnum;
import com.rhlinkcon.model.ArquivoRemessaHistoricoSituacao;
import com.rhlinkcon.model.ArquivoRemessaPagamento;
import com.rhlinkcon.model.ArquivoRemessaPagamentoSituacaoEnum;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.ParametroGlobal;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaPagamentoRequest;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaPagamentoResponse;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaRelatorioListaResponse;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaRelatorioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.arquivoRemessaPagamento.ArquivoRemessaPagamentoRepository;
import com.rhlinkcon.repository.folhaPagamento.FolhaPagamentoRepository;
import com.rhlinkcon.util.Utils;
import com.rhlinkcon.util.arquivoRemessa.Remessa;
import com.rhlinkcon.util.arquivoRemessa.Titulo;

@Service
public class ArquivoRemessaPagamentoService {

	@Autowired
	private FolhaPagamentoRepository folhaPagamentoRepository;

	@Autowired
	private ArquivoRemessaPagamentoRepository repository;

	@Autowired
	private ParametroGlobalService parametroService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ArquivoRemessaHistoricoSituacaoService historicoSituacaoService;

	@Autowired
	private ContrachequeService contrachequeService;

	@Autowired
	private EmpresaFilialService empresaFilialService;

	@Autowired
	private AnexoService anexoService;

	public PagedResponse<ArquivoRemessaPagamentoResponse> getAll(int page, int size, String order,
			ArquivoRemessaPagamentoFiltro arquivoRemessaFiltro) {
		Utils.validatePageNumberAndSize(page, size);

		Pageable pageable = Utils.getPageRequest(page, size, order);

		Page<ArquivoRemessaPagamento> lista = repository.filtro(arquivoRemessaFiltro, pageable);

		if (lista.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lista.getNumber(), lista.getSize(),
					lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
		}

		List<ArquivoRemessaPagamentoResponse> listaResponse = lista.map(item -> {
			return new ArquivoRemessaPagamentoResponse(item);
		}).getContent();

		return new PagedResponse<>(listaResponse, lista.getNumber(), lista.getSize(), lista.getTotalElements(),
				lista.getTotalPages(), lista.isLast());
	}

	@Transactional
	public void create(@Valid ArquivoRemessaPagamentoRequest request) {

		List<ArquivoRemessaPagamento> lista = repository.findAllByFolhaPagamentoId(request.getFolhaPagamentoId());
		boolean podeSalvar = true;
		if (Utils.checkList(lista)) {
			for (ArquivoRemessaPagamento a : lista) {
				if (a.getSituacao() != ArquivoRemessaPagamentoSituacaoEnum.REJEITADO
						|| a.getSituacao() != ArquivoRemessaPagamentoSituacaoEnum.CANCELADO) {
					podeSalvar = false;
				}
			}
		}

		if (podeSalvar) {
			ArquivoRemessaPagamento obj = loadArquivoRemessa(request);
			repository.save(obj);
			createHistoricoSituacao(obj);
		} else {
			throw new BadRequestException(
					"Não foi possível realizar a operação. Já existe um arquivo para a competência selecionada!");
		}

	}

	private void createHistoricoSituacao(ArquivoRemessaPagamento obj) {
		ArquivoRemessaHistoricoSituacao historico = new ArquivoRemessaHistoricoSituacao();
		historico.setArquivoRemessa(obj);
		historico.setSituacao(obj.getSituacao());

		historicoSituacaoService.create(historico);
	}

	private ArquivoRemessaPagamento loadArquivoRemessa(@Valid ArquivoRemessaPagamentoRequest request) {

		ArquivoRemessaPagamento obj = new ArquivoRemessaPagamento();

		obj.setId(request.getId());

		FolhaPagamento folha = folhaPagamentoRepository.findById(request.getFolhaPagamentoId()).orElseThrow(
				() -> new ResourceNotFoundException("FolhaPagamento", "id", request.getFolhaPagamentoId()));
		obj.setFolhaPagamento(folha);

		obj.setNumeroRemessa(gerarNumeracaoRemessa(folha.getFolhaCompetencia().getAnoCompetencia()));

		obj.setDataPagamento(request.getDataPagamento());

		if (Objects.isNull(request.getSituacao())) {
			obj.setSituacao(ArquivoRemessaPagamentoSituacaoEnum.EM_ABERTO);
		} else {
			obj.setSituacao(ArquivoRemessaPagamentoSituacaoEnum.getEnumByString(request.getSituacao()));
		}

		return obj;
	}

	@Transactional
	public void update(@Valid ArquivoRemessaPagamentoRequest request) {
		ArquivoRemessaPagamento obj = loadArquivoRemessaUpdate(request);

		repository.save(obj);

		createHistoricoSituacao(obj);
	}

	private ArquivoRemessaPagamento loadArquivoRemessaUpdate(@Valid ArquivoRemessaPagamentoRequest request) {

		ArquivoRemessaPagamento arp = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ArquivoRemessaPagamento", "id", request.getId()));

		if (Objects.nonNull(request.getDataPagamento()))
			arp.setDataPagamento(request.getDataPagamento());

		if (Objects.nonNull(request.getSituacao()))
			arp.setSituacao(ArquivoRemessaPagamentoSituacaoEnum.getEnumByString(request.getSituacao()));

		if (Objects.nonNull(request.getMotivo()))
			arp.setMotivo(request.getMotivo());

		return arp;
	}

	public void delete(Long id) {
		ArquivoRemessaPagamento obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ArquivoRemessaPagamento", "id", id));

		repository.delete(obj);
	}

	/***
	 * Método que gera um novo número de remessa baseado no ano de cadastro do
	 * mesmo.
	 * 
	 * @param numero
	 * @return
	 */
	private Integer gerarNumeracaoRemessa(Integer ano) {
		Integer numero = repository.maxNumeroByAno(ano);

		ParametroGlobal parametroGlobal = parametroService
				.getParametroByChaveAndAtivo(ParametroGlobalChaveEnum.NUMERO_REMESSA, true);

		Integer parametro = 0;
		if (Objects.nonNull(parametroGlobal)) {
			parametro = Integer.valueOf(parametroGlobal.getValor());
		}

		if (Objects.isNull(numero)) {
			if (Objects.isNull(parametro)) {
				numero = 0;
			} else {
				numero = parametro;
			}
		} else {
			if (!Objects.isNull(parametro) && (parametro > numero)) {
				numero = parametro;
			}
		}
		return numero + 1;
	}

	public ArquivoRemessaPagamentoResponse getById(Long id) {
		ArquivoRemessaPagamento obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ArquivoRemessaPagamento", "id", id));

		ArquivoRemessaPagamentoResponse response = new ArquivoRemessaPagamentoResponse(obj);

		DadoCadastralResponse auditoria = usuarioService.preencheAuditoria(obj);

		response.setAlteradoPor(auditoria.getAlteradoPor());
		response.setCriadoPor(auditoria.getCriadoPor());
		response.setCriadoEm(obj.getCreatedAt());
		response.setAlteradoEm(obj.getUpdatedAt());

		return response;
	}

	public ArquivoRemessaRelatorioResponse getRelatoriobyArquivoRemessaId(Long arquivoRemessaId) {

		ArquivoRemessaPagamento obj = repository.findById(arquivoRemessaId)
				.orElseThrow(() -> new ResourceNotFoundException("ArquivoRemessaPagamento", "id", arquivoRemessaId));

		ArquivoRemessaRelatorioResponse response = loadResponseRelatorio(obj);

		return response;
	}

	private ArquivoRemessaRelatorioResponse loadResponseRelatorio(ArquivoRemessaPagamento obj) {
		DadoCadastralResponse auditoria = usuarioService.preencheAuditoria(obj);

		ArquivoRemessaRelatorioResponse response = new ArquivoRemessaRelatorioResponse();

		EmpresaFilial matriz = empresaFilialService.getEmpresaMatriz();

		if (Objects.nonNull(matriz) && Objects.nonNull(matriz.getCodigoEmpregador()))
			response.setCodigoSecretaria(mountZeroEsquerda(matriz.getCodigoEmpregador().toString(), 5));

		response.setUsuario(auditoria.getCriadoPor());
		response.setProcessamento(obj.getFolhaPagamento().getFolhaCompetencia().getMesCompetencia().toString() + "/"
				+ obj.getFolhaPagamento().getFolhaCompetencia().getAnoCompetencia().toString() + " - "
				+ obj.getFolhaPagamento().getTipoProcessamento().getCodigo());

		response.setEmpresaFilial(obj.getFolhaPagamento().getFilial().getNomeFilial());
		if (Objects.nonNull(obj.getFolhaPagamento().getFilial())
				&& Objects.nonNull(obj.getFolhaPagamento().getFilial().getCodigoConvenio()))
			response.setCodigoConvenio(obj.getFolhaPagamento().getFilial().getCodigoConvenio().toString());

		if (Objects.nonNull(obj.getNumeroRemessa()))
			response.setNumeroRemessa(obj.getNumeroRemessa().toString());

		response.setDataPagamento(obj.getDataPagamento());
		response.setDataAtual(LocalDate.now());

		List<Contracheque> lista = contrachequeService.listaTodosPorFolhaId(obj.getFolhaPagamento().getId());

		response.setLista(new ArrayList<>());
		Double somaLiquidoServidores = 0.0;
		Double somaLiquidoPensaoAlim = 0.0;

		for (Contracheque cc : lista) {
			response.getLista().add(loadListaRelatorio(cc));
			somaLiquidoServidores = somaLiquidoServidores + Utils.roundValue(cc.getValorLiquido());
		}

		response.setTotalValorServidores(Utils.roundValue(somaLiquidoServidores));
		response.setTotalValorPensaoAlim(Utils.roundValue(somaLiquidoPensaoAlim));
		response.setTotalValor(Utils.roundValue(somaLiquidoServidores + somaLiquidoPensaoAlim));

		String total = String.valueOf(lista.size());
		response.setTotalServidores(mountZeroEsquerda(total, 4));

		// implementar pensao alimenticia
		response.setTotalPensaoAlim(mountZeroEsquerda("0", 4));
		response.setTotal(mountZeroEsquerda("0", 4));

		return response;
	}

	private ArquivoRemessaRelatorioListaResponse loadListaRelatorio(Contracheque cc) {
		ArquivoRemessaRelatorioListaResponse response = new ArquivoRemessaRelatorioListaResponse();

		response.setCodigoFilial(cc.getFolhaPagamento().getFilial().getCodigoEmpregador().toString());
		if (Objects.nonNull(cc.getFuncionario())) {
			response.setMatricula(mountZeroEsquerda(cc.getFuncionario().getMatricula().toString(), 8));
			response.setNome(cc.getFuncionario().getNome());
			response.setConta(mountZeroEsquerda(cc.getFuncionario().getNumeroConta(), 11) + "-"
					+ cc.getFuncionario().getDigitoConta());
			response.setAgencia(mountZeroEsquerda(cc.getFuncionario().getAgencia(), 4));
		} else {
			response.setMatricula(mountZeroEsquerda(cc.getPensionista().getMatricula().toString(), 8));
			response.setNome(cc.getPensionista().getNome());
			response.setConta(
					mountZeroEsquerda(cc.getPensionista().getPensaoPagamento().getNumeroConta().toString(), 11) + "-"
							+ cc.getPensionista().getPensaoPagamento().getDigito());
			response.setAgencia(
					mountZeroEsquerda(cc.getPensionista().getPensaoPagamento().getAgencia().getNumero().toString(), 4));
		}

		response.setValorLiquido(Utils.roundValue(cc.getValorLiquido()));
		response.setCodigoLotacao("S/N");

		return response;
	}

	private String mountZeroEsquerda(String string, int max) {
		StringBuilder sb = new StringBuilder(string);
		for (int i = string.length(); i <= max; i++) {
			sb.insert(0, '0');
		}
		return sb.toString();
	}

	@Transactional
	public List<AnexoResponse> getAllOrCreateByArquivoRemessaId(Long arquivoRemessaId) throws IOException {
		ArquivoRemessaPagamento obj = repository.findById(arquivoRemessaId)
				.orElseThrow(() -> new ResourceNotFoundException("ArquivoRemessaPagamento", "id", arquivoRemessaId));
		List<AnexoResponse> anexos = new ArrayList<>();

		if (obj.getSituacao().equals(ArquivoRemessaPagamentoSituacaoEnum.EM_ABERTO)
				|| obj.getSituacao().equals(ArquivoRemessaPagamentoSituacaoEnum.ERRO_GERACAO_ARQUIVO)
				|| obj.getSituacao().equals(ArquivoRemessaPagamentoSituacaoEnum.REJEITADO)
				|| obj.getSituacao().equals(ArquivoRemessaPagamentoSituacaoEnum.CANCELADO)) {

			obj.setSituacao(ArquivoRemessaPagamentoSituacaoEnum.ARQUIVO_PROCESSAMENTO);
			repository.save(obj);
			createHistoricoSituacao(obj);

			List<Contracheque> lista = contrachequeService.listaTodosPorFolhaId(obj.getFolhaPagamento().getId());
			List<Titulo> titulos = new ArrayList<>();

			for (Contracheque fp : lista) {
				Double valor = Utils.roundValue(fp.getValorLiquido());
				if (Objects.nonNull(fp.getFuncionario())) {
					titulos.add(new Titulo(fp.getFuncionario(), valor));
				} else if (Objects.nonNull(fp.getPensionista())) {
					titulos.add(new Titulo(fp.getPensionista(), valor));
				}
			}

			EmpresaFilial empresa = empresaFilialService.getEmpresaMatriz();

			Remessa remessa = new Remessa(obj.getNumeroRemessa(), empresa, titulos, obj.getDataPagamento());

			FlatFile<Record> record = remessa.gerarRemessa();

			Anexo anexo = anexoService.createAnexo(record, AnexoPastaEnum.ARQUIVO_REMESSA, obj.getNumeroRemessa());

			if (Objects.nonNull(anexo)) {

				obj.setAnexos(new ArrayList<>());
				obj.getAnexos().add(anexo);

				obj.setSituacao(ArquivoRemessaPagamentoSituacaoEnum.ARQUIVO_GERADO);

				repository.save(obj);
				createHistoricoSituacao(obj);

				obj.getAnexos().forEach(e -> anexos.add(new AnexoResponse(e)));
			} else {
				obj.setSituacao(ArquivoRemessaPagamentoSituacaoEnum.ERRO_GERACAO_ARQUIVO);
				repository.save(obj);
				createHistoricoSituacao(obj);
			}

		} else {
			if (Utils.checkList(obj.getAnexos())) {
				obj.getAnexos().forEach(e -> anexos.add(new AnexoResponse(e)));
			}
		}

		return anexos;
	}

}
