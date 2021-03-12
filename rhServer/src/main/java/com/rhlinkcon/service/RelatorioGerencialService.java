package com.rhlinkcon.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialFiltroDto;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioGerencialService {

	@Autowired
	private ContrachequeService contrachequeService;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private FolhaPagamentoService folhaPagamentoService;

	public PagedResponse<RelatorioGerencialDto> getPagedRelatorioGerencialList(PagedRequest pagedRequest,
			RelatorioGerencialFiltroDto filterRequest) {

		Pageable pageable = Utils.generatePegeableNoOrderGeneric(pagedRequest);

		Page<RelatorioGerencialDto> pageableResult = null;

		if (filterRequest.getAba().equals("filial")) {
			pageableResult = contrachequeService.getRelGerencialFilialByTipoProcessAndFolhaCompId(
					filterRequest.getTipoProcessamentoId(), filterRequest.getFolhaCompetenciaId(), pageable);

			if (Objects.nonNull(filterRequest.getFolhaCompetenciaComparacaoId())
					&& !filterRequest.getFolhaCompetenciaComparacaoId().equals(0L)) {
				for (RelatorioGerencialDto relGer : pageableResult) {
					RelatorioGerencialDto relGerCompare = contrachequeService
							.getRelGerencialFilialByTipoProcessAndFolhaCompIdAndFilialId(
									filterRequest.getTipoProcessamentoId(),
									filterRequest.getFolhaCompetenciaComparacaoId(), relGer.getTipo().getId());
					relGer.loadDataCompare(relGerCompare);

				}
			}

		} else {
			if (Objects.isNull(filterRequest.getFilialId()) || filterRequest.getFilialId().equals(0L))
				throw new BadRequestException("Parâmetro de Filial é obrigatório.");

			Long folhaPagamentoCompareId = null;
			if (Objects.nonNull(filterRequest.getFolhaCompetenciaComparacaoId())
					&& !filterRequest.getFolhaCompetenciaComparacaoId().equals(0L)) {
				folhaPagamentoCompareId = folhaPagamentoService
						.getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
								filterRequest.getFolhaCompetenciaComparacaoId(), filterRequest.getTipoProcessamentoId(),
								filterRequest.getFilialId());
			}

			Long folhaPagamentoId = folhaPagamentoService.getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
					filterRequest.getFolhaCompetenciaId(), filterRequest.getTipoProcessamentoId(),
					filterRequest.getFilialId());

			if (filterRequest.getAba().equals("cargo")) {
				pageableResult = contrachequeService.getRelGerencialCargoByFolhaPagamentoId(folhaPagamentoId,
						filterRequest.getLotacao(), pageable);

				if (Objects.nonNull(folhaPagamentoCompareId)) {
					for (RelatorioGerencialDto relGer : pageableResult) {
						RelatorioGerencialDto relGerCompare = contrachequeService
								.getRelGerencialCargoByFolhaPagamentoIdAndCargo(folhaPagamentoCompareId,
										filterRequest.getLotacao(), relGer.getTipo().getDescricao());
						relGer.loadDataCompare(relGerCompare);

					}
				}

			} else if (filterRequest.getAba().equals("lotacao")) {
				pageableResult = contrachequeService.getRelGerencialLotacaoByFolhaPagamentoId(folhaPagamentoId,
						pageable);

				if (Objects.nonNull(folhaPagamentoCompareId)) {
					for (RelatorioGerencialDto relGer : pageableResult) {
						RelatorioGerencialDto relGerCompare = contrachequeService
								.getRelGerencialLotacaoByFolhaPagamentoIdAndLotacao(folhaPagamentoCompareId,
										relGer.getTipo().getDescricao());
						relGer.loadDataCompare(relGerCompare);

					}
				}

			} else if (filterRequest.getAba().equals("funcao")) {
				pageableResult = contrachequeService.getRelGerencialFuncaoByFolhaPagamentoId(folhaPagamentoId,
						filterRequest.getLotacao(), pageable);

				if (Objects.nonNull(folhaPagamentoCompareId)) {
					for (RelatorioGerencialDto relGer : pageableResult) {
						RelatorioGerencialDto relGerCompare = contrachequeService
								.getRelGerencialFuncaoByFolhaPagamentoIdAndFuncao(folhaPagamentoCompareId,
										filterRequest.getLotacao(), relGer.getTipo().getDescricao());
						relGer.loadDataCompare(relGerCompare);

					}
				}

			} else if (filterRequest.getAba().equals("vinculo")) {
				pageableResult = contrachequeService.getRelGerencialVinculoByFolhaPagamentoId(folhaPagamentoId,
						filterRequest.getLotacao(), pageable);

				if (Objects.nonNull(folhaPagamentoCompareId)) {
					for (RelatorioGerencialDto relGer : pageableResult) {
						RelatorioGerencialDto relGerCompare = contrachequeService
								.getRelGerencialVinculoByFolhaPagamentoIdAndVinculo(folhaPagamentoCompareId,
										filterRequest.getLotacao(), relGer.getTipo().getDescricao());
						relGer.loadDataCompare(relGerCompare);

					}
				}

			} else if (filterRequest.getAba().equals("resumo")) {
				List<RelatorioGerencialDto> relatorioGerencialDtoList = lancamentoService
						.getRelGerencialResumoByFolhaPagamentoId(folhaPagamentoId, filterRequest.getCargoEfetivo(),
								filterRequest.getCargoFuncao(), filterRequest.getFuncionarioId(),
								filterRequest.getLotacao());

				if (Objects.nonNull(folhaPagamentoCompareId)) {
					List<RelatorioGerencialDto> relatorioGerencialDtoListCompare = lancamentoService
							.getRelGerencialResumoByFolhaPagamentoId(folhaPagamentoCompareId,
									filterRequest.getCargoEfetivo(), filterRequest.getCargoFuncao(),
									filterRequest.getFuncionarioId(), filterRequest.getLotacao());

					for (RelatorioGerencialDto relGer : relatorioGerencialDtoList) {
						for (RelatorioGerencialDto relGerCompare : relatorioGerencialDtoListCompare) {
							if (relGerCompare.getCodigoVerba().equals(relGer.getCodigoVerba())) {
								relGer.loadDataCompare(relGerCompare);
								break;
							}
						}
					}
				}

				pageableResult = new PageImpl<>(relatorioGerencialDtoList, pageable, relatorioGerencialDtoList.size());

			} else if (filterRequest.getAba().equals("funcionario")) {

				pageable = PageRequest.of(pagedRequest.getPage(), pagedRequest.getSize(), Sort.Direction.ASC,
						"funcionario.nome");

				pageableResult = contrachequeService.getRelGerencialFuncionarioByFolhaPagamentoId(folhaPagamentoId,
						filterRequest.getCargoEfetivo(), filterRequest.getCargoFuncao(), filterRequest.getLotacao(),
						pageable);

				if (Objects.nonNull(folhaPagamentoCompareId)) {
					for (RelatorioGerencialDto relGer : pageableResult) {
						RelatorioGerencialDto relGerCompare = contrachequeService
								.getRelGerencialFuncionarioByFolhaPagamentoIdAndFuncionarioId(folhaPagamentoCompareId,
										filterRequest.getCargoEfetivo(), filterRequest.getCargoFuncao(),
										filterRequest.getLotacao(), relGer.getFuncionario().getId());
						relGer.loadDataCompare(relGerCompare);

					}
				}
			}
		}

		PagedResponse<RelatorioGerencialDto> result = new PagedResponse<>(Collections.emptyList(),
				pageableResult.getNumber(), pageableResult.getSize(), pageableResult.getTotalElements(),
				pageableResult.getTotalPages(), pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<RelatorioGerencialDto> relatorioGerencialDtoList = pageableResult.map(relatorioGerencialDto -> {
				return relatorioGerencialDto;
			}).getContent();
			result.setContent(relatorioGerencialDtoList);
		}

		return result;
	}

	public List<RelatorioGerencialDto> getRelatorioGerencialList(RelatorioGerencialFiltroDto filterRequest) {

		List<RelatorioGerencialDto> relatorioGerencialDtoList = null;

		if (filterRequest.getAba().equals("filial")) {
			relatorioGerencialDtoList = contrachequeService.getRelGerencialFilialByTipoProcessAndFolhaCompId(
					filterRequest.getTipoProcessamentoId(), filterRequest.getFolhaCompetenciaId());

		} else {
			if (Objects.isNull(filterRequest.getFilialId()) || filterRequest.getFilialId().equals(0L))
				throw new BadRequestException("Parâmetro de Filial é obrigatório.");

			Long folhaPagamentoId = folhaPagamentoService.getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
					filterRequest.getFolhaCompetenciaId(), filterRequest.getTipoProcessamentoId(),
					filterRequest.getFilialId());

			if (filterRequest.getAba().equals("cargo")) {
				relatorioGerencialDtoList = contrachequeService.getRelGerencialCargoByFolhaPagamentoId(folhaPagamentoId,
						filterRequest.getLotacao());

			} else if (filterRequest.getAba().equals("lotacao")) {
				relatorioGerencialDtoList = contrachequeService
						.getRelGerencialLotacaoByFolhaPagamentoId(folhaPagamentoId);

			} else if (filterRequest.getAba().equals("funcao")) {
				relatorioGerencialDtoList = contrachequeService
						.getRelGerencialFuncaoByFolhaPagamentoId(folhaPagamentoId, filterRequest.getLotacao());

			} else if (filterRequest.getAba().equals("vinculo")) {
				relatorioGerencialDtoList = contrachequeService
						.getRelGerencialVinculoByFolhaPagamentoId(folhaPagamentoId, filterRequest.getLotacao());

			} else if (filterRequest.getAba().equals("resumo")) {
				relatorioGerencialDtoList = lancamentoService.getRelGerencialResumoByFolhaPagamentoId(folhaPagamentoId,
						filterRequest.getCargoEfetivo(), filterRequest.getCargoFuncao(),
						filterRequest.getFuncionarioId(), filterRequest.getLotacao());

			} else if (filterRequest.getAba().equals("funcionario")) {
				relatorioGerencialDtoList = contrachequeService.getRelGerencialFuncionarioByFolhaPagamentoId(
						folhaPagamentoId, filterRequest.getCargoEfetivo(), filterRequest.getCargoFuncao(),
						filterRequest.getLotacao());

			}

		}

		return relatorioGerencialDtoList;
	}

	public RelatorioGerencialDto getRelatorioGerencialSomatorio(RelatorioGerencialFiltroDto filterRequest) {

		RelatorioGerencialDto relatorioGerencialDto = null;

		if (filterRequest.getAba().equals("filial")) {
			relatorioGerencialDto = contrachequeService.getRelGerencialFilialByTipoProcessAndFolhaCompIdSomatorio(
					filterRequest.getTipoProcessamentoId(), filterRequest.getFolhaCompetenciaId());

			if (Objects.nonNull(filterRequest.getFolhaCompetenciaComparacaoId())
					&& !filterRequest.getFolhaCompetenciaComparacaoId().equals(0L)) {

				RelatorioGerencialDto relGerCompare = contrachequeService
						.getRelGerencialFilialByTipoProcessAndFolhaCompIdSomatorio(
								filterRequest.getTipoProcessamentoId(),
								filterRequest.getFolhaCompetenciaComparacaoId());
				relatorioGerencialDto.loadDataCompare(relGerCompare);

			}

		} else {
			if (Objects.isNull(filterRequest.getFilialId()) || filterRequest.getFilialId().equals(0L))
				throw new BadRequestException("Parâmetro de Filial é obrigatório.");

			Long folhaPagamentoCompareId = null;
			if (Objects.nonNull(filterRequest.getFolhaCompetenciaComparacaoId())
					&& !filterRequest.getFolhaCompetenciaComparacaoId().equals(0L)) {
				folhaPagamentoCompareId = folhaPagamentoService
						.getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
								filterRequest.getFolhaCompetenciaComparacaoId(), filterRequest.getTipoProcessamentoId(),
								filterRequest.getFilialId());
			}

			Long folhaPagamentoId = folhaPagamentoService.getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
					filterRequest.getFolhaCompetenciaId(), filterRequest.getTipoProcessamentoId(),
					filterRequest.getFilialId());

			relatorioGerencialDto = contrachequeService.getRelGerencialByFolhaPagamentoIdSomatorio(folhaPagamentoId,
					filterRequest.getCargoEfetivo(), filterRequest.getCargoFuncao(), filterRequest.getLotacao(),
					filterRequest.getVinculo());

			if (Objects.nonNull(folhaPagamentoCompareId)) {
				RelatorioGerencialDto relGerCompare = contrachequeService.getRelGerencialByFolhaPagamentoIdSomatorio(
						folhaPagamentoCompareId, filterRequest.getCargoEfetivo(), filterRequest.getCargoFuncao(),
						filterRequest.getLotacao(), filterRequest.getVinculo());
				relatorioGerencialDto.loadDataCompare(relGerCompare);

			}

		}

		return relatorioGerencialDto;
	}

}
