package com.rhlinkcon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.IdentificacaoVerbaEnum;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoFiltroDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoFilialDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoProventosDto;

@Service
public class RelatorioFolhaPagamentoService {

	@Autowired
	private ContrachequeService contrachequeService;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private FolhaPagamentoService folhaPagamentoService;

	@Autowired
	private FolhaCompetenciaService folhaCompetenciaService;

	public RelatorioFolhaPagamentoDto getRelatorioFolhaPagamentoResumoProventos(Long filialId,
			RelatorioFolhaPagamentoFiltroDto filtro, String situaçãoFuncional) {

		FolhaCompetenciaResponse folhaCompetenciaResponse = folhaCompetenciaService
				.findByMesCompetenciaAndAnoCompetencia(filtro.getCompetencia(), filtro.getAno());

		Long folhaPagamentoId = folhaPagamentoService.getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
				folhaCompetenciaResponse.getId(), filtro.getTipoProcessamentoId(), filialId);

		List<RelatorioFolhaPagamentoResumoProventosDto> normalDtoList = lancamentoService
				.listContrachequesByVerba(folhaPagamentoId, situaçãoFuncional, null);
		List<RelatorioFolhaPagamentoResumoProventosDto> diferencaDtoList = lancamentoService
				.listContrachequesByVerba(folhaPagamentoId, situaçãoFuncional, IdentificacaoVerbaEnum.DIFERENCA);
		List<RelatorioFolhaPagamentoResumoProventosDto> devolucaoDtoList = lancamentoService
				.listContrachequesByVerba(folhaPagamentoId, situaçãoFuncional, IdentificacaoVerbaEnum.DEVOLUCAO);

		// List<RelatorioFolhaPagamentoResumoProventosDto> normalDtoList =
		// lancamentoService.listContrachequesByVerba(filialId,
		// situacaoFuncional, null , filtro.getAno(), filtro.getCompetencia(),
		// filtro.getTipoProcessamentoId())
		// List<RelatorioFolhaPagamentoResumoProventosDto> diferencaDtoList =
		// lancamentoService.listContrachequesByVerba(filialId,
		// situacaoFuncional, IdentificacaoVerbaEnum.DEVOLUCAO, filtro.getAno(),
		// filtro.getCompetencia(), filtro.getTipoProcessamentoId());
		// List<RelatorioFolhaPagamentoResumoProventosDto> devolucaoDtoList =
		// lancamentoService.listContrachequesByVerba(filialId,
		// situacaoFuncional, IdentificacaoVerbaEnum.DIFERENCA, filtro.getAno(),
		// filtro.getCompetencia(), filtro.getTipoProcessamentoId());

		return new RelatorioFolhaPagamentoDto(normalDtoList, diferencaDtoList, devolucaoDtoList);
	}

	public RelatorioFolhaPagamentoDto getRelatorioFolhaPagamento(RelatorioFolhaPagamentoFiltroDto filtro) {

		List<RelatorioFolhaPagamentoResumoFilialDto> listaContracheques = contrachequeService
				.getDadosContrachequeByFiltros(filtro.getFilialList(), filtro.getSituacaoFuncionalList(),
						filtro.getAno(), filtro.getCompetencia(), filtro.getTipoProcessamentoId());

		return new RelatorioFolhaPagamentoDto(filtro.getCompetencia(), filtro.getAno(), listaContracheques);
	}

}
