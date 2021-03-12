package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.rhlinkcon.filtro.BatimentoFolhaCustomizacaoFiltro;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoDadosFolhaDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFolhaPagamentoResponse;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFuncionarioCountDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoResumoDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoTotalFuncionarioDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoVerbaDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.ProjecaoSomatorioValores;
import com.rhlinkcon.payload.batimentoFolhaPagamento.ProjecaoSomatorioValoresFiliais;
import com.rhlinkcon.payload.batimentoFolhaPagamento.RelatorioBatimentoFolhaPagamentoDto;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoResponse;

@Service
public class BatimentoFolhaPagamentoService {

	@Autowired
	private EmpresaFilialService empresaService;

	@Autowired
	private FolhaPagamentoService folhaPagamentoService;

	@Autowired
	private ContrachequeService contrachequeService;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private FolhaCompetenciaService folhaCompetenciaService;

	@Autowired
	private TipoProcessamentoService tipoProcessamentoService;

	public PagedResponse<BatimentoFolhaPagamentoResponse> getAllbyFiltros(Integer competencia, Long tipoProcessamentoId,
			Integer ano) {
		MesEnum mes = MesEnum.getEnumByInteger(competencia);

		List<ProjecaoSomatorioValoresFiliais> lista = contrachequeService.getSomatorioValoresFiliais(mes.getValue(),
				ano, tipoProcessamentoId);

		Page<ProjecaoSomatorioValoresFiliais> listaPage = new PageImpl<>(lista);

		if (listaPage.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), listaPage.getNumber(), listaPage.getSize(),
					listaPage.getTotalElements(), listaPage.getTotalPages(), listaPage.isLast());
		}

		List<BatimentoFolhaPagamentoResponse> listaResponse = listaPage.map(item -> {
			EmpresaFilialResponse emp = empresaService.getEmpresaFilialById(item.getEmpresa());
			return new BatimentoFolhaPagamentoResponse(item, emp);
		}).getContent();

		return new PagedResponse<>(listaResponse, listaPage.getNumber(), listaPage.getSize(),
				listaPage.getTotalElements(), listaPage.getTotalPages(), listaPage.isLast());
	}

	public BatimentoFolhaPagamentoResponse getOrgaobyFiltros(Integer competencia, Long tipoProcessamentoId,
			Integer ano) {
		EmpresaFilial empresa = empresaService.getEmpresaMatriz();

		MesEnum mes = MesEnum.getEnumByInteger(competencia);

		ProjecaoSomatorioValores valores = contrachequeService.getSomatorioValoresOrgao(mes.getValue(), ano,
				tipoProcessamentoId);

		return new BatimentoFolhaPagamentoResponse(empresa, valores);
	}

	public List<RelatorioBatimentoFolhaPagamentoDto> get(BatimentoFolhaCustomizacaoFiltro customizacao) {

		EmpresaFilial orgao = empresaService.getEmpresaMatriz();
		MesEnum mes = MesEnum.getEnumByInteger(customizacao.getCompetencia());
		FolhaCompetenciaResponse competencia = folhaCompetenciaService
				.findByMesCompetenciaAndAnoCompetencia(mes.getValue(), customizacao.getAno());
		TipoProcessamentoResponse tipoProcessamento = tipoProcessamentoService
				.getTipoProcessamentoById(customizacao.getTipoProcessamentoId());

		List<EmpresaFilialResponse> filiais = new ArrayList<>();

		if (customizacao.isOrgao()) {
			filiais = folhaPagamentoService.getAllEmpresasFiliaisDaCompetencia(competencia.getId());
		} else {
			EmpresaFilialResponse filial = empresaService.getEmpresaFilialById(customizacao.getId());
			filiais.add(filial);
		}

		BatimentoResumoDto empresa = new BatimentoResumoDto();

		if (customizacao.possuiEmpresa()) {
			List<BatimentoFuncionarioCountDto> countFuncionario = contrachequeService
					.getCountTotalFuncionariosPorSituacao(competencia.getId(), customizacao.getTipoProcessamentoId(),
							null);
			BatimentoTotalFuncionarioDto totalFuncionario = new BatimentoTotalFuncionarioDto(countFuncionario);

			List<BatimentoVerbaDto> vantagensE = lancamentoService.getBatimentoVerba(competencia.getId(),
					customizacao.getTipoProcessamentoId(), null, TipoVerba.VANTAGEM, null, null);
			List<BatimentoVerbaDto> descontosE = lancamentoService.getBatimentoVerba(competencia.getId(),
					customizacao.getTipoProcessamentoId(), null, TipoVerba.DESCONTO, null, null);

			empresa = new BatimentoResumoDto(vantagensE, descontosE, totalFuncionario);
		}

		List<RelatorioBatimentoFolhaPagamentoDto> lista = new ArrayList<>();

		for (EmpresaFilialResponse ef : filiais) {

			List<BatimentoDadosFolhaDto> listaDto = new ArrayList<>();
			if (customizacao.possuiContracheque()) {
				List<Contracheque> folhas = contrachequeService.getAllByFiltroCompetenciaNoResponse(competencia.getId(),
						customizacao.getTipoProcessamentoId(), ef.getId());
				for (Contracheque e : folhas) {
					listaDto.add(new BatimentoDadosFolhaDto(e));
				}
			}

			List<BatimentoResumoDto> listaSituacoesDto = new ArrayList<>();
			List<BatimentoFuncionarioCountDto> countSituacoesFuncionario = contrachequeService
					.getCountTotalFuncionariosPorSituacao(competencia.getId(), customizacao.getTipoProcessamentoId(),
							ef.getId());

			BatimentoTotalFuncionarioDto totalFuncionarioSituacao = new BatimentoTotalFuncionarioDto(
					countSituacoesFuncionario);

			if (customizacao.possuiSituacao()) {

				for (BatimentoFuncionarioCountDto e : totalFuncionarioSituacao.getLista()) {
					List<BatimentoVerbaDto> vantagens = lancamentoService.getBatimentoVerba(competencia.getId(),
							customizacao.getTipoProcessamentoId(), ef.getId(), TipoVerba.VANTAGEM, e.getEntitiId(),
							null);
					List<BatimentoVerbaDto> descontos = lancamentoService.getBatimentoVerba(competencia.getId(),
							customizacao.getTipoProcessamentoId(), ef.getId(), TipoVerba.DESCONTO, e.getEntitiId(),
							null);

					BatimentoResumoDto situacao = new BatimentoResumoDto(vantagens, descontos, e);

					listaSituacoesDto.add(situacao);
				}
			}

			BatimentoResumoDto filial = new BatimentoResumoDto();
			if (customizacao.possuiFilial()) {
				List<BatimentoVerbaDto> vantagensF = lancamentoService.getBatimentoVerba(competencia.getId(),
						customizacao.getTipoProcessamentoId(), ef.getId(), TipoVerba.VANTAGEM, null, null);
				List<BatimentoVerbaDto> descontosF = lancamentoService.getBatimentoVerba(competencia.getId(),
						customizacao.getTipoProcessamentoId(), ef.getId(), TipoVerba.DESCONTO, null, null);

				filial = new BatimentoResumoDto(vantagensF, descontosF, totalFuncionarioSituacao);
			}

			List<BatimentoResumoDto> listaLotacoesDto = new ArrayList<>();

			if (customizacao.possuiLotacao()) {

				List<BatimentoFuncionarioCountDto> countLotacoesFuncionario = contrachequeService
						.getCountTotalFuncionariosPorLotacao(competencia.getId(), customizacao.getTipoProcessamentoId(),
								ef.getId());

				BatimentoTotalFuncionarioDto totalFuncionarioLotacao = new BatimentoTotalFuncionarioDto(
						countLotacoesFuncionario);

				for (BatimentoFuncionarioCountDto e : totalFuncionarioLotacao.getLista()) {
					List<BatimentoVerbaDto> vantagens = lancamentoService.getBatimentoVerba(competencia.getId(),
							customizacao.getTipoProcessamentoId(), ef.getId(), TipoVerba.VANTAGEM, null,
							e.getEntitiId());
					List<BatimentoVerbaDto> descontos = lancamentoService.getBatimentoVerba(competencia.getId(),
							customizacao.getTipoProcessamentoId(), ef.getId(), TipoVerba.DESCONTO, null,
							e.getEntitiId());

					BatimentoResumoDto lotacao = new BatimentoResumoDto(vantagens, descontos, e);

					listaLotacoesDto.add(lotacao);
				}
			}

			RelatorioBatimentoFolhaPagamentoDto dto = new RelatorioBatimentoFolhaPagamentoDto(orgao, ef, competencia,
					tipoProcessamento, listaDto, listaSituacoesDto, filial, listaLotacoesDto, empresa);
			lista.add(dto);
		}

		return lista;
	}

}
