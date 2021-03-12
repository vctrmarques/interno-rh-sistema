package com.rhlinkcon.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.repository.FolhaCompetenciaRepository;
import com.rhlinkcon.repository.FuncionarioVerbaRepository;
import com.rhlinkcon.repository.PensionistaVerbaRepository;
import com.rhlinkcon.repository.folhaPagamento.FolhaPagamentoRepository;
import com.rhlinkcon.repository.lancamento.LancamentoRepository;

@Service
public class FolhaCompetenciaService {
	@Autowired
	private FolhaCompetenciaRepository folhaCompetenciaRepository;
	@Autowired
	private FolhaPagamentoRepository folhaPagamentoRepository;
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private FuncionarioVerbaRepository funcionarioVerbaRepository;
	@Autowired
	private PensionistaVerbaRepository pensionistaVerbaRepository;

	private synchronized FolhaCompetenciaResponse createFolhaCompetencia(LocalDate mesAno) {

		if (Objects.isNull(mesAno))
			mesAno = LocalDate.now();

		Integer mes = mesAno.getMonthValue();
		Integer ano = mesAno.getYear();

		FolhaCompetencia competencia = folhaCompetenciaRepository
				.findFolhaCompetenciaByMesCompetenciaAndAnoCompetencia(mes, ano).orElse(null);

		if (Objects.isNull(competencia)) {
			competencia = new FolhaCompetencia();
			competencia.setAnoCompetencia(ano);
			competencia.setMesCompetencia(mes);
			competencia.setInicioCompetencia(new Date());
			// TODO Por causa da pandemia não é pra ser checado recadastramento.
			competencia.setChecarRecad(false);
			competencia.setChecarRecadAmpLegal("Suspenso por causa da pandemia.");
			folhaCompetenciaRepository.save(competencia);
		}

		FolhaCompetenciaResponse response = new FolhaCompetenciaResponse(competencia);

		return response;
	}

	public FolhaCompetenciaResponse getCompetenciaAtual() {

		Optional<FolhaCompetencia> competencia = folhaCompetenciaRepository
				.findFirstByFimCompetenciaIsNullOrderByAnoCompetenciaDescMesCompetenciaDesc();

		FolhaCompetenciaResponse competenciaResponse = null;

		boolean criarNovaCompetencia = false;
		if (competencia.isPresent()) {
			if (fechamentoCompetenciaMaiorIgualHoje(competencia.get())) {
				competenciaResponse = new FolhaCompetenciaResponse(competencia.get());
			} else {
				fecharFolhaCompetencia(competencia.get().getId());
				criarNovaCompetencia = true;
			}
		} else {
			criarNovaCompetencia = true;
		}

		if (criarNovaCompetencia) {
			competencia = folhaCompetenciaRepository
					.findFirstByFimCompetenciaIsNotNullOrderByAnoCompetenciaDescMesCompetenciaDesc();
			if (competencia.isPresent()) {
				LocalDate localDate = LocalDate.parse("01/" + competencia.get().getMesAnoCompetencia(),
						DateTimeFormatter.ofPattern("d/MM/yyyy"));
				localDate = localDate.plusMonths(1);
				competenciaResponse = createFolhaCompetencia(localDate);
			} else {
				competenciaResponse = createFolhaCompetencia(null);
			}
		}

		return competenciaResponse;
	}

	public Boolean existeCompetenciaMes() {
		return folhaCompetenciaRepository.existsFolhaCompetenciaByMesCompetenciaAndAnoCompetencia(
				LocalDate.now().getMonthValue(), LocalDate.now().getYear());
	}

	public boolean fechamentoCompetenciaMaiorIgualHoje(FolhaCompetencia folhaCompetencia) {
		if (Objects.nonNull(folhaCompetencia.getProgramacaoFechamento())) {
			LocalDate localDate = LocalDate.now();
			return folhaCompetencia.getProgramacaoFechamento().isAfter(localDate)
					|| folhaCompetencia.getProgramacaoFechamento().isEqual(localDate);
		}
		return true;
	}

	public FolhaCompetenciaResponse getCompetenciaAtualFechada() {
		FolhaCompetencia competencia = folhaCompetenciaRepository
				.findFirstByFimCompetenciaIsNotNullOrderByAnoCompetenciaDescMesCompetenciaDesc()
				.orElseThrow(() -> new ResourceNotFoundException("Folha Competencia", "id", null));
		FolhaCompetenciaResponse response = new FolhaCompetenciaResponse(competencia);
		return response;
	}

	public List<FolhaCompetenciaResponse> verificaSeExisteAlgumaCompetenciaBloqueada() {
		List<FolhaCompetencia> lista = folhaCompetenciaRepository.findIdByFimCompetenciaIsNotNull();
		List<FolhaCompetenciaResponse> response = lista.stream()
				.map(competencia -> new FolhaCompetenciaResponse(competencia)).collect(Collectors.toList());

		return response;
	}

	public List<FolhaCompetenciaResponse> retornaCompetenciasPorAno(Integer ano) {
		List<FolhaCompetencia> lista = folhaCompetenciaRepository.findAllByAnoCompetencia(ano);
		List<FolhaCompetenciaResponse> response = lista.stream()
				.map(competencia -> new FolhaCompetenciaResponse(competencia)).collect(Collectors.toList());
		return response;
	}

	public void programarFecharFolhaCompetencia(Long id, LocalDate programacaoFechamento) {
		FolhaCompetencia competencia = folhaCompetenciaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Folha Competencia", "id", null));
		competencia.setProgramacaoFechamento(programacaoFechamento);
		folhaCompetenciaRepository.save(competencia);
	}

	public boolean cancelarProgramacaoFechamentoCompetecia(Long id) {
		FolhaCompetencia competencia = folhaCompetenciaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Folha Competencia", "id", null));
		if (Objects.isNull(competencia.getFimCompetencia())) {
			competencia.setProgramacaoFechamento(null);
			folhaCompetenciaRepository.save(competencia);
			return true;
		} else {
			return false;
		}
	}

	public void fecharFolhaCompetencia(Long id) {
		FolhaCompetencia competencia = folhaCompetenciaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Folha Competencia", "id", null));
		Date dataAgora = new Date();
		competencia.setFimCompetencia(dataAgora);

		// Efetuando validação da Situação da folha
		List<FolhaPagamento> folhaPagamentoList = folhaPagamentoRepository
				.findByFolhaCompetenciaId(competencia.getId());

		for (FolhaPagamento folhaPagamento : folhaPagamentoList) {
			if (folhaPagamento.getSituacao().equals(SituacaoProcessamentoEnum.PENDENTE)
					|| folhaPagamento.getSituacao().equals(SituacaoProcessamentoEnum.FALHO))
				throw new ServiceException(
						"Não é possível fechar uma competência com folhas de pagamento com status Pendente ou Falha.");
		}

		folhaCompetenciaRepository.updateAllStatusFolhaPagamento(id);
		folhaCompetenciaRepository.save(competencia);

		// Somando uma parcela paga nas verbas parceladas de funcionário e pensionistas
		for (FolhaPagamento folhaPagamento : folhaPagamentoList) {

			// Buscando as verbas de funcionário pagas
			List<FuncionarioVerba> funcionarioVerbaList = lancamentoRepository
					.getFuncionarioVerbasPagasByFolhaPagamentoId(folhaPagamento.getId());

			if (Objects.nonNull(funcionarioVerbaList) && !funcionarioVerbaList.isEmpty()) {
				// Incrementando as parcelas pagas em 1.
				funcionarioVerbaList.forEach(funcVerba -> {
					funcVerba.setParcelasPagas(funcVerba.getParcelasPagas() + 1);
				});

				// Salvando as alterações.
				funcionarioVerbaRepository.saveAll(funcionarioVerbaList);
			}

			// Buscando as verbas de pensionista pagas
			List<PensionistaVerba> pensionistaVerbaList = lancamentoRepository
					.getPensionistaVerbasPagasByFolhaPagamentoId(folhaPagamento.getId());

			if (Objects.nonNull(pensionistaVerbaList) && !pensionistaVerbaList.isEmpty()) {
				// Incrementando as parcelas pagas em 1.
				pensionistaVerbaList.forEach(pensVerba -> {
					pensVerba.setParcelasPagas(pensVerba.getParcelasPagas() + 1);
				});

				// Salvando as alterações.
				pensionistaVerbaRepository.saveAll(pensionistaVerbaList);
			}

		}

	}

	public Object retornaAnosCompetenciasFechadas() {
//		return folhaCompetenciaRepository.findAnosCompetenciasFechadas();
		return folhaCompetenciaRepository.findAnosCompetencias();
	}

	public List<FolhaCompetenciaResponse> competenciasPorAno(Integer ano) {
		List<FolhaCompetencia> list = folhaCompetenciaRepository.findAllByAnoCompetenciaAndFimCompetenciaIsNotNull(ano);
		List<FolhaCompetenciaResponse> response = list.stream().map(f -> new FolhaCompetenciaResponse(f))
				.collect(Collectors.toList());
		return response;
	}

	public List<FolhaCompetenciaResponse> competenciasPorAnoFolhaBloqueadaConcluida(Integer ano) {
//		List<FolhaCompetencia> list = folhaCompetenciaRepository.competenciasPorAnoFolhaBloqueadaConcluida(ano);
		List<FolhaCompetencia> list = folhaCompetenciaRepository.competenciasPorAnoFolhaBloqueadaConcluidaAndCompetenciaNaoFechada(ano);
		List<FolhaCompetenciaResponse> response = list.stream().map(f -> new FolhaCompetenciaResponse(f))
				.collect(Collectors.toList());
		return response;
	}

//	Job para executar o fechamento de competencia

	public void executarProgramacaoFechamentoCompetenciaJob() {

		LocalDate agora = LocalDate.now();

		List<FolhaCompetencia> folhaCompetencias = folhaCompetenciaRepository
				.findByFimCompetenciaIsNullAndProgramacaoFechamentoNotNull();

		if (Objects.nonNull(folhaCompetencias) && !folhaCompetencias.isEmpty()) {
			for (FolhaCompetencia folhaCompetencia : folhaCompetencias) {
				if (folhaCompetencia.getProgramacaoFechamento().isBefore(agora)
						|| folhaCompetencia.getProgramacaoFechamento().isEqual(agora)) {
					fecharFolhaCompetencia(folhaCompetencia.getId());
				}
			}
		}
	}

	public FolhaCompetenciaResponse findByMesCompetenciaAndAnoCompetencia(Integer mes, Integer ano) {
		FolhaCompetencia fc = folhaCompetenciaRepository.findFolhaCompetenciaByMesCompetenciaAndAnoCompetencia(mes, ano)
				.orElse(null);
		return Objects.nonNull(fc) ? new FolhaCompetenciaResponse(fc) : null;
	}

}
