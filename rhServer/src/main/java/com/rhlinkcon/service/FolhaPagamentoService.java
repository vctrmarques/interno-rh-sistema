package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.model.StatusFolhaEnum;
import com.rhlinkcon.model.TipoProcessamento;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoRequest;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoSituacaoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.FolhaCompetenciaRepository;
import com.rhlinkcon.repository.contracheque.ContrachequeRepository;
import com.rhlinkcon.repository.folhaPagamento.FolhaPagamentoRepository;
import com.rhlinkcon.repository.lancamento.LancamentoRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class FolhaPagamentoService {
	@Autowired
	private FolhaPagamentoRepository fPagamentoRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ContrachequeService contrachequeService;
	@Autowired
	private ContrachequeRepository contrachequeRepository;
	@Autowired
	private ExecutorService executorService;
	@Autowired
	private FolhaCompetenciaRepository folhaCompetenciaRepository;
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private PensionistaService pensionistaService;

	public void createFolhaPagamento(FolhaPagamentoRequest folhaPagamentoRequest) {

		// Checa a existência de uma folha com os parametros principais.
		boolean existeFolha = fPagamentoRepository.existsByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
				folhaPagamentoRequest.getFolhaCompetenciaId(), folhaPagamentoRequest.getTipoProcessamentoId(),
				folhaPagamentoRequest.getFilialId());

		if (existeFolha) {
			throw new ServiceException(
					"Já existe uma folha de cálculo cadastrada para o tipo de processamento, filial e competência informados.");

		} else {

			// Cria a folha, caso ela não exista.
			FolhaPagamento folhaPagamento = new FolhaPagamento();

			FolhaCompetencia folhaCompetencia = folhaCompetenciaRepository
					.getOne(folhaPagamentoRequest.getFolhaCompetenciaId());

			folhaPagamento.setTipoProcessamento(new TipoProcessamento(folhaPagamentoRequest.getTipoProcessamentoId()));
			folhaPagamento.setFolhaCompetencia(folhaCompetencia);
			folhaPagamento.setFilial(new EmpresaFilial(folhaPagamentoRequest.getFilialId()));
			folhaPagamento.setPeriodoProcessamentoInicio(folhaPagamentoRequest.getPeriodoProcessamentoInicio());
			folhaPagamento.setPeriodoProcessamentoFim(folhaPagamentoRequest.getPeriodoProcessamentoFim());
			folhaPagamento.setStatus(StatusFolhaEnum.getEnumByString(folhaPagamentoRequest.getStatus()));
			if (Utils.checkList(folhaPagamentoRequest.getLotacoes())) {
				for (Long lotacaoId : folhaPagamentoRequest.getLotacoes()) {
					folhaPagamento.getLotacoes().add(new Lotacao(lotacaoId));
				}
			}

			// Seta situação pendente de processamento.
			folhaPagamento.setSituacao(SituacaoProcessamentoEnum.PENDENTE);

			// Seta quantidade de processamentos a serem realizados.
			List<Funcionario> funcionarios = funcionarioService
					.getFuncionariosAptosPraFolhaByFolhaPagamento(folhaPagamento);

			List<Pensionista> pensionistas = pensionistaService
					.getPensionistasAptosPraFolhaByFolhaPagamento(folhaPagamento);

			folhaPagamento.setProcessamentos(new Long(funcionarios.size()) + new Long(pensionistas.size()));

			fPagamentoRepository.save(folhaPagamento);

			// Iniciando processamento assíncrono.
			Runnable runnable = () -> {
				contrachequeService.processarContracheques(folhaPagamento.getId());
			};
			executorService.submit(runnable);

		}

	}

	public void reprocessarFolha(Long folhaPagamentoId) {

		FolhaPagamento folhaPagamento = fPagamentoRepository.findById(folhaPagamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("FolhaPagamento", "id", folhaPagamentoId));

		// Checa o status da folha
		if (folhaPagamento.getStatus().equals(StatusFolhaEnum.BLOQUEADO)) {
			throw new ServiceException("Não é possível recalcular uma folha de pagamento bloqueada.");
		}

		// Efetuando validação da Situação da folha
		if (folhaPagamento.getSituacao().equals(SituacaoProcessamentoEnum.PENDENTE))
			throw new ServiceException(
					"Não é possível recalcular uma folha de pagamento com outro recalculo em andamento.");

		// Seta quantidade de processamentos a serem realizados.
		List<Funcionario> funcionarios = funcionarioService
				.getFuncionariosAptosPraFolhaByFolhaPagamento(folhaPagamento);

		List<Pensionista> pensionistas = pensionistaService
				.getPensionistasAptosPraFolhaByFolhaPagamento(folhaPagamento);

		// Atualizando o numeros de contracheques a serem reprocessados.
		folhaPagamento.setProcessamentos(new Long(funcionarios.size()) + new Long(pensionistas.size()));

		// Atualizando a situação da folha para Pendente, situaçao de início de
		// recalculo / processamento.
		folhaPagamento.setSituacao(SituacaoProcessamentoEnum.PENDENTE);

		fPagamentoRepository.save(folhaPagamento);

		contrachequeRepository.updateSituacaoParaPendenteByFolhaPag(folhaPagamento);

		// Iniciando processamento assíncrono.
		Runnable runnable = () -> {
			contrachequeService.processarContracheques(folhaPagamento.getId());
		};
		executorService.submit(runnable);

	}

	public void updateFolhaPagamento(FolhaPagamentoRequest folhaPagamentoRequest) {

		FolhaPagamento folhaPagamento = fPagamentoRepository.findById(folhaPagamentoRequest.getId()).orElseThrow(
				() -> new ResourceNotFoundException("FolhaPagamento", "id", folhaPagamentoRequest.getId()));

		// Efetuando validação da Situação da folha
		if (folhaPagamento.getSituacao().equals(SituacaoProcessamentoEnum.PENDENTE))
			throw new ServiceException("Não é possível atualizar uma folha de pagamento com recalculo em andamento.");

		if (StatusFolhaEnum.getEnumByString(folhaPagamentoRequest.getStatus()).equals(StatusFolhaEnum.BLOQUEADO))
			if (folhaPagamento.getSituacao().equals(SituacaoProcessamentoEnum.FALHO))
				throw new ServiceException("Não é possível bloquear uma folha de pagamento com recalculo falho.");

		folhaPagamento.setStatus(StatusFolhaEnum.getEnumByString(folhaPagamentoRequest.getStatus()));
		folhaPagamento.setPeriodoProcessamentoInicio(folhaPagamentoRequest.getPeriodoProcessamentoInicio());
		folhaPagamento.setPeriodoProcessamentoFim(folhaPagamentoRequest.getPeriodoProcessamentoFim());

		fPagamentoRepository.save(folhaPagamento);
	}

	public List<FolhaPagamentoResponse> listFolhasPorCompetencia(Long competenciaId) {
		List<FolhaPagamento> lista = fPagamentoRepository.findAllByFolhaCompetenciaId(competenciaId);
		List<FolhaPagamentoResponse> response = lista.stream().map(f -> getFolhaPagamentoResponse(f))
				.collect(Collectors.toList());
		return response;
	};

	private FolhaPagamentoResponse getFolhaPagamentoResponse(FolhaPagamento folhaPagamento) {
		FolhaPagamentoResponse response = new FolhaPagamentoResponse(folhaPagamento);
		response.setAlteradoPor(usuarioService.alteradoPor(folhaPagamento));
		response.setCriadoPor(usuarioService.alteradoPor(folhaPagamento));
		return response;
	}

	public FolhaPagamentoResponse retornaFolhaPagamento(Long id) {
		FolhaPagamento folha = fPagamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("FolhaPagamento", "id", id));
		FolhaPagamentoResponse folhaPagamentoResponse = new FolhaPagamentoResponse(folha);
		folhaPagamentoResponse.setCriadoPor(usuarioService.criadoPor(folha));
		folhaPagamentoResponse.setAlteradoPor(usuarioService.alteradoPor(folha));
		return folhaPagamentoResponse;
	}

	public void deleteFolha(Long id) {
		FolhaPagamento folha = fPagamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("FolhaPagamento", "id", id));

		// Checa o status da folha
		if (folha.getStatus().equals(StatusFolhaEnum.BLOQUEADO)) {
			throw new ServiceException("Não é possível excluir uma folha processada com status bloqueado.");
		}

		// Efetuando validação da Situação da folha
		if (folha.getSituacao().equals(SituacaoProcessamentoEnum.PENDENTE))
			throw new ServiceException("Não é possível excluir uma folha de pagamento com recalculo em andamento.");

		// Busca contracheques atrelados a folha para exclusão
		Optional<List<Contracheque>> contracheques = contrachequeRepository.findByFolhaPagamento(folha);

		// Checa se foram retornados contracheques para a folha em questão
		if (contracheques.isPresent()) {
			List<Contracheque> list = contracheques.get();
			for (int i = 0; i < list.size(); i++) {
				Contracheque contracheque = list.get(i);

				// Apaga o contracheque em questão
				lancamentoRepository.deleteByContracheque(contracheque);
				contrachequeRepository.delete(contracheque);
			}
		}

		// Apaga a folha
		fPagamentoRepository.delete(folha);
	}

	public Long getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(Long folhaCompetenciaId,
			Long tipoProcessamentoId, Long filialId) {
		return fPagamentoRepository.getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(folhaCompetenciaId,
				tipoProcessamentoId, filialId);
	}

	public List<FolhaPagamentoResponse> getLotacoesFilter(Long filialId, Long lotacaoId, Long competenciaId) {
		// List<Long> lotacoes = Utils.decodeLongList(lotacaoes);
		List<FolhaPagamento> list = fPagamentoRepository.filterFolhaPagamento(filialId, lotacaoId, competenciaId);
		List<FolhaPagamentoResponse> response = list.stream().map(e -> new FolhaPagamentoResponse(e))
				.collect(Collectors.toList());
		return response;
	}

	public FolhaPagamentoSituacaoDto getFolhaPagamentoSituacao(Long folhaPagamentoId) {
		Long count = contrachequeRepository.countByFolhaPagamentoIdAndSituacao(folhaPagamentoId,
				SituacaoProcessamentoEnum.CONCLUIDO);
		String situacao = fPagamentoRepository.findSituacaoById(folhaPagamentoId);
		FolhaPagamentoSituacaoDto folhaPagamentoSituacaoDto = FolhaPagamentoSituacaoDto.builder().count(count)
				.situacao(SituacaoProcessamentoEnum.valueOf(situacao).getLabel()).build();
		return folhaPagamentoSituacaoDto;

	}

	/*
	 * Consulta utilizada na listagem de folha de pagamento na tela de cadastri
	 * arquivo de remessa pagamento
	 * 
	 * Autor: Rodrigo Leite
	 */
	public PagedResponse<FolhaPagamentoResponse> getAllbyFiltros(int page, int size, String order, Long processamentoId,
			Long filialId) {
		Utils.validatePageNumberAndSize(page, size);

		Pageable pageable = Utils.getPageRequest(page, size, order);

		StatusFolhaEnum status = StatusFolhaEnum.BLOQUEADO;

		Page<FolhaPagamento> lista = fPagamentoRepository.findAllByStatusAndTipoProcessamentoIdAndFilialId(status,
				processamentoId, filialId, pageable);

		if (lista.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lista.getNumber(), lista.getSize(),
					lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
		}

		List<FolhaPagamentoResponse> listaResponse = lista.map(item -> {
			return new FolhaPagamentoResponse(item);
		}).getContent();

		return new PagedResponse<>(listaResponse, lista.getNumber(), lista.getSize(), lista.getTotalElements(),
				lista.getTotalPages(), lista.isLast());
	}

	public List<EmpresaFilialResponse> getAllEmpresasFiliaisDaCompetencia(Long id) {
		List<FolhaPagamento> folhas = fPagamentoRepository.findByFolhaCompetenciaId(id);
		List<EmpresaFilialResponse> listaResponse = new ArrayList<>();

		for (FolhaPagamento f : folhas) {
			listaResponse.add(new EmpresaFilialResponse(f.getFilial(), Projecao.BASICA));
		}
		return listaResponse;
	}

}
