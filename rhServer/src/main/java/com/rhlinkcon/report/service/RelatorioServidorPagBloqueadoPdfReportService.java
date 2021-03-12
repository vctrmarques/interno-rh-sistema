package com.rhlinkcon.report.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoDto;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoTipoEnum;
import com.rhlinkcon.payload.simuladorAposentadoria.TempoTotal;
import com.rhlinkcon.payload.simuladorAposentadoria.UtilsSimulador;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.service.RelatorioServidorPagBloqueadoService;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioServidorPagBloqueadoPdfReportService implements CrudPdfReportService<ServidorPagBloqueadoDto> {

	private static final String TITULO = "Relatório de Servidores com Pagamento Bloqueado";
	private static final String SUBTITULO = "Relatório baseado nos servidores cuja situação funcional não entra na folha e/ou estão com pendências de recadastramento.";

	@Autowired
	private RelatorioServidorPagBloqueadoService service;

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	@Override
	public void personalizarDocumento(PdfWriter writer, Document document, ServidorPagBloqueadoDto valor)
			throws DocumentException {
		addAtributos(document, TITULO, SUBTITULO);
		addDefaultLogoOnTop(document);

		List<ServidorPagBloqueadoDto> lista = service.getRelatorioServidorPagBloqueadoList(valor);

		addContentFiltros(document, valor, lista);

		addContentFilial(document, lista);

	}

	private void addContentFiltros(Document document, ServidorPagBloqueadoDto filtro,
			List<ServidorPagBloqueadoDto> lista) throws DocumentException {

		Paragraph paragrafo = new Paragraph();

		Paragraph rh = new Paragraph("RH", Constantes.TIMES12BOLD);
		rh.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(rh);

		addEmptyLine(paragrafo, 1);

		Paragraph relatStat = new Paragraph(TITULO, Constantes.TIMES12BOLD);
		relatStat.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(relatStat);

		addEmptyLine(paragrafo, 1);

		document.add(paragrafo);

		PdfPTable table1 = new PdfPTable(1);
		table1.setWidthPercentage(100);

		Phrase fundoPhrase = new Phrase();
		fundoPhrase.add(new Phrase("Fundos Selecionados: ", Constantes.TIMES10BOLD));
		String fundosSelec = "";
		List<EmpresaFilial> fundos = empresaFilialRepository.findByIdIn(filtro.getFundosSelecionados());
		for (EmpresaFilial empresaFilial : fundos) {
			if (fundosSelec.isEmpty())
				fundosSelec += empresaFilial.getLabel();
			else
				fundosSelec += ", " + empresaFilial.getLabel();
		}
		fundoPhrase.add(new Phrase(fundosSelec + ".", Constantes.TIMES10));

		PdfPCell fundoCell = new PdfPCell();
		fundoCell.setPhrase(fundoPhrase);
		fundoCell.setPaddingBottom(4);
		fundoCell.setPaddingLeft(4);
		table1.addCell(fundoCell);

		if (Objects.nonNull(filtro.getSituacaoFuncionalSelecionadas())
				&& !filtro.getSituacaoFuncionalSelecionadas().isEmpty()) {
			Phrase sitFuncPhrase = new Phrase();
			sitFuncPhrase.add(new Phrase("Situações Funcionais Selecionadas: ", Constantes.TIMES10BOLD));
			String sitFuncSelec = "";
			List<SituacaoFuncional> sitFuncs = situacaoFuncionalRepository
					.findByIdIn(filtro.getSituacaoFuncionalSelecionadas());
			for (SituacaoFuncional empresaFilial : sitFuncs) {
				if (sitFuncSelec.isEmpty())
					sitFuncSelec += empresaFilial.getLabel();
				else
					sitFuncSelec += ", " + empresaFilial.getLabel();
			}
			sitFuncPhrase.add(new Phrase(sitFuncSelec + ".", Constantes.TIMES10));

			PdfPCell sitFuncCell = new PdfPCell();
			sitFuncCell.setPhrase(sitFuncPhrase);
			sitFuncCell.setPaddingBottom(4);
			sitFuncCell.setPaddingLeft(4);
			table1.addCell(sitFuncCell);
		}

		Phrase mesPhrase = new Phrase();
		mesPhrase.add(new Phrase("Total de Servidores: ", Constantes.TIMES10BOLD));
		mesPhrase.add(new Phrase(lista.size() + ".", Constantes.TIMES10));
		PdfPCell mesCell = new PdfPCell();
		mesCell.setPhrase(mesPhrase);
		mesCell.setPaddingBottom(4);
		mesCell.setPaddingLeft(4);
		table1.addCell(mesCell);

//		Phrase tipoProcPhrase = new Phrase();
//		tipoProcPhrase.add(new Phrase("Tipo de Processamento: ", Constantes.TIMES10BOLD));
//		tipoProcPhrase.add(new Phrase(filtro.getTipoProcessamento() + ".", Constantes.TIMES10));
//		PdfPCell tipoProcCell = new PdfPCell();
//		tipoProcCell.setPhrase(tipoProcPhrase);
//		tipoProcCell.setColspan(2);
//		tipoProcCell.setPaddingBottom(4);
//		tipoProcCell.setPaddingLeft(4);
//		table1.addCell(tipoProcCell);
//
//		if (Objects.nonNull(filtro.getFilialId())) {
//			Phrase cargoPhrase = new Phrase();
//			cargoPhrase.add(new Phrase("Filial: ", Constantes.TIMES10BOLD));
//			EmpresaFilialResponse emp = empresaFilialService.getEmpresaFilialById(filtro.getFilialId());
//			cargoPhrase.add(new Phrase(emp.getNomeFilial() + ".", Constantes.TIMES10));
//			PdfPCell cargoCell = new PdfPCell();
//			cargoCell.setPhrase(cargoPhrase);
//			cargoCell.setColspan(2);
//			cargoCell.setPaddingBottom(4);
//			cargoCell.setPaddingLeft(4);
//			table1.addCell(cargoCell);
//		}
//
//		if (Utils.checkStr(filtro.getCargoEfetivo())) {
//			Phrase cargoPhrase = new Phrase();
//			cargoPhrase.add(new Phrase("Cargo: ", Constantes.TIMES10BOLD));
//			cargoPhrase.add(new Phrase(filtro.getCargoEfetivo() + ".", Constantes.TIMES10));
//			PdfPCell cargoCell = new PdfPCell();
//			cargoCell.setPhrase(cargoPhrase);
//			cargoCell.setColspan(2);
//			cargoCell.setPaddingBottom(4);
//			cargoCell.setPaddingLeft(4);
//			table1.addCell(cargoCell);
//		}
//
//		if (Utils.checkStr(filtro.getCargoFuncao())) {
//			Phrase cargoPhrase = new Phrase();
//			cargoPhrase.add(new Phrase("Função: ", Constantes.TIMES10BOLD));
//			cargoPhrase.add(new Phrase(filtro.getCargoFuncao() + ".", Constantes.TIMES10));
//			PdfPCell cargoCell = new PdfPCell();
//			cargoCell.setPhrase(cargoPhrase);
//			cargoCell.setColspan(2);
//			cargoCell.setPaddingBottom(4);
//			cargoCell.setPaddingLeft(4);
//			table1.addCell(cargoCell);
//		}
//
//		if (Objects.nonNull(filtro.getFuncionarioId())) {
//			Phrase cargoPhrase = new Phrase();
//			cargoPhrase.add(new Phrase("Funcionário: ", Constantes.TIMES10BOLD));
//			FuncionarioResponse func = funcionarioService.getFuncionarioById(filtro.getFuncionarioId());
//			cargoPhrase.add(new Phrase(func.getNome() + ".", Constantes.TIMES10));
//			PdfPCell cargoCell = new PdfPCell();
//			cargoCell.setPhrase(cargoPhrase);
//			cargoCell.setColspan(2);
//			cargoCell.setPaddingBottom(4);
//			cargoCell.setPaddingLeft(4);
//			table1.addCell(cargoCell);
//		}
//
//		if (Utils.checkStr(filtro.getLotacao())) {
//			Phrase cargoPhrase = new Phrase();
//			cargoPhrase.add(new Phrase("Lotação: ", Constantes.TIMES10BOLD));
//			cargoPhrase.add(new Phrase(filtro.getLotacao() + ".", Constantes.TIMES10));
//			PdfPCell cargoCell = new PdfPCell();
//			cargoCell.setPhrase(cargoPhrase);
//			cargoCell.setColspan(2);
//			cargoCell.setPaddingBottom(4);
//			cargoCell.setPaddingLeft(4);
//			table1.addCell(cargoCell);
//		}
//
//		if (Utils.checkStr(filtro.getVinculo())) {
//			Phrase cargoPhrase = new Phrase();
//			cargoPhrase.add(new Phrase("Vínculo: ", Constantes.TIMES10BOLD));
//			cargoPhrase.add(new Phrase(filtro.getVinculo() + ".", Constantes.TIMES10));
//			PdfPCell cargoCell = new PdfPCell();
//			cargoCell.setPhrase(cargoPhrase);
//			cargoCell.setColspan(2);
//			cargoCell.setPaddingBottom(4);
//			cargoCell.setPaddingLeft(4);
//			table1.addCell(cargoCell);
//		}
//
		document.add(table1);

		document.add(new Paragraph(" "));

	}

	private void addContentFilial(Document document, List<ServidorPagBloqueadoDto> lista) throws DocumentException {

		if (lista.isEmpty()) {

			document.add(new Paragraph(" "));
			// Mensagem de feedback para busca retornada sem dados
			document.add(new Phrase("A busca não retornou dados para os filtros aplicados."));

		} else {

			// Agrupando por Fundo
			Map<String, List<ServidorPagBloqueadoDto>> servPagBloqDtoMapByFundo = new HashMap<>();
			for (ServidorPagBloqueadoDto servidorPagBloqueadoDto : lista) {
				if (servPagBloqDtoMapByFundo.containsKey(servidorPagBloqueadoDto.getFundo())) {
					List<ServidorPagBloqueadoDto> value = servPagBloqDtoMapByFundo
							.get(servidorPagBloqueadoDto.getFundo());
					value.add(servidorPagBloqueadoDto);
					servPagBloqDtoMapByFundo.put(servidorPagBloqueadoDto.getFundo(), value);
				} else {
					List<ServidorPagBloqueadoDto> value = new ArrayList<ServidorPagBloqueadoDto>();
					value.add(servidorPagBloqueadoDto);
					servPagBloqDtoMapByFundo.put(servidorPagBloqueadoDto.getFundo(), value);
				}

			}

			// Gerando a lista do map para retorno.
			for (Map.Entry<String, List<ServidorPagBloqueadoDto>> pairFundo : servPagBloqDtoMapByFundo.entrySet()) {

				PdfPTable table1 = new PdfPTable(1);
				table1.setWidthPercentage(100);

				Phrase mesPhrase = new Phrase();
				mesPhrase.add(new Phrase("Fundo: ", Constantes.TIMES10BOLD));
				mesPhrase.add(new Phrase(pairFundo.getKey() + ".", Constantes.TIMES10));
				PdfPCell mesCell = new PdfPCell();
				mesCell.setPhrase(mesPhrase);
				mesCell.setPaddingBottom(4);
				mesCell.setPaddingLeft(4);
				table1.addCell(mesCell);

				// Agrupando por Situação Funcional
				Map<String, List<ServidorPagBloqueadoDto>> servPagBloqDtoMapBySitFunc = new HashMap<>();
				for (ServidorPagBloqueadoDto servidorPagBloqueadoDto : pairFundo.getValue()) {
					if (servPagBloqDtoMapBySitFunc.containsKey(servidorPagBloqueadoDto.getSituacaoFuncional())) {
						List<ServidorPagBloqueadoDto> value = servPagBloqDtoMapBySitFunc
								.get(servidorPagBloqueadoDto.getSituacaoFuncional());
						value.add(servidorPagBloqueadoDto);
						servPagBloqDtoMapBySitFunc.put(servidorPagBloqueadoDto.getSituacaoFuncional(), value);
					} else {
						List<ServidorPagBloqueadoDto> value = new ArrayList<ServidorPagBloqueadoDto>();
						value.add(servidorPagBloqueadoDto);
						servPagBloqDtoMapBySitFunc.put(servidorPagBloqueadoDto.getSituacaoFuncional(), value);
					}

				}

				// Gerando a lista do map para retorno.
				for (Map.Entry<String, List<ServidorPagBloqueadoDto>> pairSitFunc : servPagBloqDtoMapBySitFunc
						.entrySet()) {

					document.add(new Paragraph(" "));
					document.add(table1);
					PdfPTable table3 = new PdfPTable(1);
					table3.setWidthPercentage(100);

					Phrase mesPhrase3 = new Phrase();
					mesPhrase3.add(new Phrase("Situação Funcional: ", Constantes.TIMES10BOLD));
					mesPhrase3.add(new Phrase(pairSitFunc.getKey() + ".", Constantes.TIMES10));
					PdfPCell mesCell3 = new PdfPCell();
					mesCell3.setPhrase(mesPhrase3);
					mesCell3.setPaddingBottom(4);
					mesCell3.setPaddingLeft(4);
					table3.addCell(mesCell3);

					document.add(table3);

					PdfPTable table2 = new PdfPTable(new float[] { 16, 48, 16, 16, 16, 16 });
					table2.setWidthPercentage(100);

					Stream.of("Matrícula", "Nome", "CPF", "Valor Líquido", "Mês de Nascimento", "Telefone de Contato")
							.forEach(columnTitle -> {
								PdfPCell header = new PdfPCell();
								header.setBackgroundColor(BaseColor.LIGHT_GRAY);
								header.setPaddingBottom(4);
								header.setPaddingLeft(4);
								header.setHorizontalAlignment(Element.ALIGN_CENTER);
								header.setPhrase(new Phrase(columnTitle, Constantes.TIMES10));
								table2.addCell(header);
							});

					Collections.sort(pairSitFunc.getValue());
					for (ServidorPagBloqueadoDto item : pairSitFunc.getValue()) {

						PdfPCell dataCell = new PdfPCell();
						dataCell.setPaddingBottom(4);
						dataCell.setPaddingLeft(4);

						dataCell.setPhrase(new Phrase(item.getMatricula(), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(Utils.snakeToCamel(item.getNome()), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(Utils.formatarCpf(item.getCpf()), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(
								new Phrase(Utils.formatarMoedaReal(item.getValorLiquido()), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(item.getMesNascimento(), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(item.getTelefoneContato(), Constantes.TIMES10));
						table2.addCell(dataCell);

						String problemas = "";
						for (ServidorPagBloqueadoTipoEnum problema : item.getProblemas()) {
							if (problemas.isEmpty())
								problemas += problema.getLabel();
							else
								problemas += ", " + problema.getLabel();
						}
						Phrase mesPhrase6 = new Phrase();
						mesPhrase6.add(new Phrase("Motivos: ", Constantes.TIMES10));
						mesPhrase6.add(new Phrase(problemas + ".", Constantes.TIMES10));
						dataCell.setColspan(3);
						dataCell.setPhrase(mesPhrase6);
						table2.addCell(dataCell);

						String totalDiasPendentes = "Não disponível.";
						if (Objects.nonNull(item.getDataBaseRecadastramento())) {
							TempoTotal tempoTotal = UtilsSimulador.periodoContribuicao(Date.from(
									item.getDataBaseRecadastramento().atStartOfDay(ZoneId.systemDefault()).toInstant()),
									new Date());
							totalDiasPendentes = tempoTotal.getAnos() + " ano(s), " + tempoTotal.getMeses()
									+ " mes(es) e " + tempoTotal.getDias() + " dia(s).";
						}
						Phrase mesPhrase7 = new Phrase();
						mesPhrase7.add(new Phrase(" Dias Pendentes: ", Constantes.TIMES10));
						mesPhrase7.add(new Phrase(totalDiasPendentes, Constantes.TIMES10));
						dataCell.setColspan(3);
						dataCell.setPhrase(mesPhrase7);
						table2.addCell(dataCell);

					}

					document.add(table2);
				}

			}

			document.add(new Paragraph(" "));
		}
	}

}
