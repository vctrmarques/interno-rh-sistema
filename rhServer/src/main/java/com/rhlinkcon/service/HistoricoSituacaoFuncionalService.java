package com.rhlinkcon.service;

import java.util.ArrayList;
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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.HistoricoSituacaoFuncional;
import com.rhlinkcon.model.Motivo;
import com.rhlinkcon.model.MotivoAfastamento;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.TipoHistoricoSituacaoFuncionalEnum;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalRequest;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.ProjectionSituacaoFuncional;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.HistoricoSituacaoFuncionalRepository;
import com.rhlinkcon.repository.MotivoAfastamentoRepository;
import com.rhlinkcon.repository.MotivoRepository;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class HistoricoSituacaoFuncionalService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private HistoricoSituacaoFuncionalRepository historicoSituacaoFuncionalRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private ReferenciaSalarialRepository nivelSalarialRepository;

	@Autowired
	private MotivoRepository motivoRepository;

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private MotivoAfastamentoRepository situacaoRepository;

	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	public PagedResponse<HistoricoSituacaoFuncionalResponse> getAllHistoricoSituacoesByFuncionarioIdAndTipoSituacaoFuncional(
			int page, int size, Long funcionarioId, String tipoSituacao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<HistoricoSituacaoFuncional> situacoesFuncionais = historicoSituacaoFuncionalRepository
				.getAllSituacoesByFuncionarioIdAndTipoHistoricoSituacaoFuncional(funcionarioId,
						TipoHistoricoSituacaoFuncionalEnum.valueOf(tipoSituacao), pageable);

		if (situacoesFuncionais.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), situacoesFuncionais.getNumber(),
					situacoesFuncionais.getSize(), situacoesFuncionais.getTotalElements(),
					situacoesFuncionais.getTotalPages(), situacoesFuncionais.isLast());
		}

		List<HistoricoSituacaoFuncionalResponse> situacoesFuncionaisResponse = situacoesFuncionais
				.map(situacaoFuncional -> {
					return new HistoricoSituacaoFuncionalResponse(situacaoFuncional);
				}).getContent();

		return new PagedResponse<>(situacoesFuncionaisResponse, situacoesFuncionais.getNumber(),
				situacoesFuncionais.getSize(), situacoesFuncionais.getTotalElements(),
				situacoesFuncionais.getTotalPages(), situacoesFuncionais.isLast());
	}

	public List<HistoricoSituacaoFuncionalResponse> getAllHistoricoSituacoesByFuncionarioIdAndTipoSituacaoFuncional(
			Long funcionarioId, String tipoSituacao) {

		List<HistoricoSituacaoFuncional> situacoesFuncionais = new ArrayList<>();

		situacoesFuncionais = historicoSituacaoFuncionalRepository
				.getAllSituacoesByFuncionarioIdAndTipoHistoricoSituacaoFuncional(funcionarioId,
						TipoHistoricoSituacaoFuncionalEnum.valueOf(tipoSituacao));

		List<HistoricoSituacaoFuncionalResponse> situacoesFuncionaisResponse = new ArrayList<>();

		if (!situacoesFuncionais.isEmpty()) {
			for (HistoricoSituacaoFuncional historicoSituacaoFuncional : situacoesFuncionais) {
				situacoesFuncionaisResponse.add(new HistoricoSituacaoFuncionalResponse(historicoSituacaoFuncional));
			}
			return situacoesFuncionaisResponse;
		}

		return situacoesFuncionaisResponse;
	}

	public HistoricoSituacaoFuncionalResponse getPrimeiraSituacaoFuncionalByFuncionario(Long funcionarioId) {

		Long id = historicoSituacaoFuncionalRepository
				.getPrimeiroHistoricoSituacaoFuncionalIdByFuncionario(funcionarioId);

		if (Objects.nonNull(id)) {
			HistoricoSituacaoFuncional primeiraSituacalFuncional = historicoSituacaoFuncionalRepository.findById(id)
					.get();
			if (Objects.nonNull(primeiraSituacalFuncional))
				return new HistoricoSituacaoFuncionalResponse(primeiraSituacalFuncional);
		}

		return new HistoricoSituacaoFuncionalResponse();
	}

	public HistoricoSituacaoFuncional createHistoricoSituacaoFuncional(
			HistoricoSituacaoFuncionalRequest historicoSituacaoFuncionalRequest) {

		List<HistoricoSituacaoFuncional> situacoes = new ArrayList<>();

		situacoes = historicoSituacaoFuncionalRepository
				.findByFuncionarioId(historicoSituacaoFuncionalRequest.getFuncionarioId());

		if (TipoHistoricoSituacaoFuncionalEnum
				.valueOf(historicoSituacaoFuncionalRequest
						.getTipoSituacaoFuncional()) == TipoHistoricoSituacaoFuncionalEnum.PROMOCAO
				|| TipoHistoricoSituacaoFuncionalEnum.valueOf(historicoSituacaoFuncionalRequest
						.getTipoSituacaoFuncional()) == TipoHistoricoSituacaoFuncionalEnum.NOMEACAO_COMISSIONADO)
			if (!situacoes.isEmpty())
				for (HistoricoSituacaoFuncional historicoSituacaoFuncional : situacoes) {
					if ((historicoSituacaoFuncional
							.getTipoHistoricoSituacaoFuncional() == TipoHistoricoSituacaoFuncionalEnum.PROMOCAO
							|| historicoSituacaoFuncional
									.getTipoHistoricoSituacaoFuncional() == TipoHistoricoSituacaoFuncionalEnum.NOMEACAO_COMISSIONADO)
							&& Objects.isNull(historicoSituacaoFuncional.getDataFim())) {
						historicoSituacaoFuncional.setDataFim(historicoSituacaoFuncionalRequest.getDataInicio());
						historicoSituacaoFuncionalRepository.save(historicoSituacaoFuncional);
					}
				}

		HistoricoSituacaoFuncional historicoSituacaoFuncional = new HistoricoSituacaoFuncional(
				historicoSituacaoFuncionalRequest);

		setEntidades(historicoSituacaoFuncionalRequest, historicoSituacaoFuncional);
		historicoSituacaoFuncional
				.setSituacaoFuncionalAnterior(historicoSituacaoFuncional.getFuncionario().getTipoSituacaoFuncional());
		historicoSituacaoFuncionalRepository.save(historicoSituacaoFuncional);

		Funcionario funcionario = funcionarioRepository.findById(historicoSituacaoFuncional.getFuncionario().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionário", "id",
						historicoSituacaoFuncional.getFuncionario().getId()));
		funcionario.setTipoSituacaoFuncional(historicoSituacaoFuncional.getSituacaoFuncional());

		return historicoSituacaoFuncional;
	}

	public void cancelaHistoricoSituacaoFuncional(Long id) {
		HistoricoSituacaoFuncional historicoSituacaoFuncional = historicoSituacaoFuncionalRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("HistoricoSituacaoFuncional", "id", id));

		if (!Objects.nonNull(historicoSituacaoFuncional.getDataFim()))
			historicoSituacaoFuncionalRepository.deleteById(id);

	}

	private void setEntidades(HistoricoSituacaoFuncionalRequest historicoSituacaoFuncionalRequest,
			HistoricoSituacaoFuncional historicoSituacaoFuncional) {

		if (Objects.nonNull(historicoSituacaoFuncionalRequest.getFuncionarioId())) {
			Funcionario funcionario = funcionarioRepository
					.findById(historicoSituacaoFuncionalRequest.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
							historicoSituacaoFuncionalRequest.getFuncionarioId()));

			historicoSituacaoFuncional.setFuncionario(funcionario);
		}

		if (Objects.nonNull(historicoSituacaoFuncionalRequest.getFuncaoId())) {
			Funcao funcao = funcaoRepository.findById(historicoSituacaoFuncionalRequest.getFuncaoId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcao", "id",
							historicoSituacaoFuncionalRequest.getFuncaoId()));

			historicoSituacaoFuncional.setFuncao(funcao);
		}

		if (Objects.nonNull(historicoSituacaoFuncionalRequest.getNivelSalarialId())) {
			ReferenciaSalarial nivelSalarial = nivelSalarialRepository
					.findById(historicoSituacaoFuncionalRequest.getNivelSalarialId())
					.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id",
							historicoSituacaoFuncionalRequest.getNivelSalarialId()));

			historicoSituacaoFuncional.setNivelSalarial(nivelSalarial);
		}

		if (Objects.nonNull(historicoSituacaoFuncionalRequest.getMotivoId())) {
			Motivo motivo = motivoRepository.findById(historicoSituacaoFuncionalRequest.getMotivoId())
					.orElseThrow(() -> new ResourceNotFoundException("Motivo", "id",
							historicoSituacaoFuncionalRequest.getMotivoId()));

			historicoSituacaoFuncional.setMotivo(motivo);
		}

		if (Objects.nonNull(historicoSituacaoFuncionalRequest.getFilialDestinoId())) {
			EmpresaFilial filialDestino = empresaFilialRepository
					.findById(historicoSituacaoFuncionalRequest.getFilialDestinoId())
					.orElseThrow(() -> new ResourceNotFoundException("Motivo", "id",
							historicoSituacaoFuncionalRequest.getFilialDestinoId()));

			historicoSituacaoFuncional.setFilialDestino(filialDestino);
		}

		if (Objects.nonNull(historicoSituacaoFuncionalRequest.getSituacaoId())) {
			MotivoAfastamento situacao = situacaoRepository.findById(historicoSituacaoFuncionalRequest.getSituacaoId())
					.orElseThrow(() -> new ResourceNotFoundException("MotivoAfastamento", "id",
							historicoSituacaoFuncionalRequest.getSituacaoId()));

			historicoSituacaoFuncional.setSituacao(situacao);
		}

		if (Objects.nonNull(historicoSituacaoFuncionalRequest.getSituacaoFuncionalId())) {
			SituacaoFuncional situacaoFuncional = situacaoFuncionalRepository
					.findById(historicoSituacaoFuncionalRequest.getSituacaoFuncionalId())
					.orElseThrow(() -> new ResourceNotFoundException("SituacaoFuncional", "id",
							historicoSituacaoFuncionalRequest.getSituacaoFuncionalId()));

			historicoSituacaoFuncional.setSituacaoFuncional(situacaoFuncional);
		}
	}

	public Page<ProjectionSituacaoFuncional> getAllHistoricoSituacoes(int page, int size, String funcionarioNome,
			String order) {
		Utils.validatePageNumberAndSize(page, size);
		Pageable pageable = Utils.getPageRequest(page, size, order);
		Page<ProjectionSituacaoFuncional> result = null;
		if (Utils.checkStr(funcionarioNome))
			result = historicoSituacaoFuncionalRepository.findFuncionariosSituracaoAndFuncionarioNome(pageable,
					funcionarioNome);
		else
			result = historicoSituacaoFuncionalRepository.findFuncionariosSituracao(pageable);
		return result;
	}

}
