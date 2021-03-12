package com.rhlinkcon.report.service;

import java.util.List;
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
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialFiltroDto;
import com.rhlinkcon.service.EmpresaFilialService;
import com.rhlinkcon.service.FuncionarioService;
import com.rhlinkcon.service.RelatorioGerencialService;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioGerencialPdfReportService implements CrudPdfReportService<RelatorioGerencialFiltroDto> {

	private static final String TITULO = "Relatório Gerencial";
	private static final String SUBTITULO = "Relatório baseado nas folhas de pagamentos por filiais, cargo, função, funcionários, entre outras opções.";

	@Autowired
	private RelatorioGerencialService service;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaFilialService empresaFilialService;

	@Override
	public void personalizarDocumento(PdfWriter writer, Document document, RelatorioGerencialFiltroDto valor)
			throws DocumentException {
		addAtributos(document, TITULO, SUBTITULO);
		addDefaultLogoOnTop(document);
		addContentFiltros(document, valor);

		addContentFilial(document, valor);

	}

	private void addContentFiltros(Document document, RelatorioGerencialFiltroDto filtro) throws DocumentException {

		Paragraph paragrafo = new Paragraph();

		Paragraph rh = new Paragraph("RH", Constantes.TIMES12BOLD);
		rh.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(rh);

		addEmptyLine(paragrafo, 1);

		Paragraph relatStat = new Paragraph("Relatório Gerencial", Constantes.TIMES12BOLD);
		relatStat.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(relatStat);

		addEmptyLine(paragrafo, 1);

		Paragraph abaLabel = new Paragraph(filtro.getAbaLabel(), Constantes.TIMES12BOLD);
		abaLabel.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(abaLabel);

		addEmptyLine(paragrafo, 1);

		document.add(paragrafo);

		// Tabela 1
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);

		Phrase anoPhrase = new Phrase();
		anoPhrase.add(new Phrase("Ano da Competência: ", Constantes.TIMES10BOLD));
		anoPhrase.add(new Phrase(filtro.getAnoCompetencia() + ".", Constantes.TIMES10));
		PdfPCell anoCell = new PdfPCell();
		anoCell.setPhrase(anoPhrase);
		anoCell.setPaddingBottom(4);
		anoCell.setPaddingLeft(4);
		table1.addCell(anoCell);

		Phrase mesPhrase = new Phrase();
		mesPhrase.add(new Phrase("Mês da Competência: ", Constantes.TIMES10BOLD));
		mesPhrase.add(new Phrase(filtro.getMesCompetencia() + ".", Constantes.TIMES10));
		PdfPCell mesCell = new PdfPCell();
		mesCell.setPhrase(mesPhrase);
		mesCell.setPaddingBottom(4);
		mesCell.setPaddingLeft(4);
		table1.addCell(mesCell);

		Phrase tipoProcPhrase = new Phrase();
		tipoProcPhrase.add(new Phrase("Tipo de Processamento: ", Constantes.TIMES10BOLD));
		tipoProcPhrase.add(new Phrase(filtro.getTipoProcessamento() + ".", Constantes.TIMES10));
		PdfPCell tipoProcCell = new PdfPCell();
		tipoProcCell.setPhrase(tipoProcPhrase);
		tipoProcCell.setColspan(2);
		tipoProcCell.setPaddingBottom(4);
		tipoProcCell.setPaddingLeft(4);
		table1.addCell(tipoProcCell);

		if (Objects.nonNull(filtro.getFilialId())) {
			Phrase cargoPhrase = new Phrase();
			cargoPhrase.add(new Phrase("Filial: ", Constantes.TIMES10BOLD));
			EmpresaFilialResponse emp = empresaFilialService.getEmpresaFilialById(filtro.getFilialId());
			cargoPhrase.add(new Phrase(emp.getNomeFilial() + ".", Constantes.TIMES10));
			PdfPCell cargoCell = new PdfPCell();
			cargoCell.setPhrase(cargoPhrase);
			cargoCell.setColspan(2);
			cargoCell.setPaddingBottom(4);
			cargoCell.setPaddingLeft(4);
			table1.addCell(cargoCell);
		}

		if (Utils.checkStr(filtro.getCargoEfetivo())) {
			Phrase cargoPhrase = new Phrase();
			cargoPhrase.add(new Phrase("Cargo: ", Constantes.TIMES10BOLD));
			cargoPhrase.add(new Phrase(filtro.getCargoEfetivo() + ".", Constantes.TIMES10));
			PdfPCell cargoCell = new PdfPCell();
			cargoCell.setPhrase(cargoPhrase);
			cargoCell.setColspan(2);
			cargoCell.setPaddingBottom(4);
			cargoCell.setPaddingLeft(4);
			table1.addCell(cargoCell);
		}

		if (Utils.checkStr(filtro.getCargoFuncao())) {
			Phrase cargoPhrase = new Phrase();
			cargoPhrase.add(new Phrase("Função: ", Constantes.TIMES10BOLD));
			cargoPhrase.add(new Phrase(filtro.getCargoFuncao() + ".", Constantes.TIMES10));
			PdfPCell cargoCell = new PdfPCell();
			cargoCell.setPhrase(cargoPhrase);
			cargoCell.setColspan(2);
			cargoCell.setPaddingBottom(4);
			cargoCell.setPaddingLeft(4);
			table1.addCell(cargoCell);
		}

		if (Objects.nonNull(filtro.getFuncionarioId())) {
			Phrase cargoPhrase = new Phrase();
			cargoPhrase.add(new Phrase("Funcionário: ", Constantes.TIMES10BOLD));
			FuncionarioResponse func = funcionarioService.getFuncionarioById(filtro.getFuncionarioId());
			cargoPhrase.add(new Phrase(func.getNome() + ".", Constantes.TIMES10));
			PdfPCell cargoCell = new PdfPCell();
			cargoCell.setPhrase(cargoPhrase);
			cargoCell.setColspan(2);
			cargoCell.setPaddingBottom(4);
			cargoCell.setPaddingLeft(4);
			table1.addCell(cargoCell);
		}

		if (Utils.checkStr(filtro.getLotacao())) {
			Phrase cargoPhrase = new Phrase();
			cargoPhrase.add(new Phrase("Lotação: ", Constantes.TIMES10BOLD));
			cargoPhrase.add(new Phrase(filtro.getLotacao() + ".", Constantes.TIMES10));
			PdfPCell cargoCell = new PdfPCell();
			cargoCell.setPhrase(cargoPhrase);
			cargoCell.setColspan(2);
			cargoCell.setPaddingBottom(4);
			cargoCell.setPaddingLeft(4);
			table1.addCell(cargoCell);
		}

		if (Utils.checkStr(filtro.getVinculo())) {
			Phrase cargoPhrase = new Phrase();
			cargoPhrase.add(new Phrase("Vínculo: ", Constantes.TIMES10BOLD));
			cargoPhrase.add(new Phrase(filtro.getVinculo() + ".", Constantes.TIMES10));
			PdfPCell cargoCell = new PdfPCell();
			cargoCell.setPhrase(cargoPhrase);
			cargoCell.setColspan(2);
			cargoCell.setPaddingBottom(4);
			cargoCell.setPaddingLeft(4);
			table1.addCell(cargoCell);
		}

		// Adição da Tabela 1
		document.add(table1);

		document.add(new Paragraph(" "));

	}

	private void addContentFilial(Document document, RelatorioGerencialFiltroDto filtro) throws DocumentException {

		List<RelatorioGerencialDto> lista = service.getRelatorioGerencialList(filtro);
		RelatorioGerencialDto somatorio = service.getRelatorioGerencialSomatorio(filtro);

		document.add(new Paragraph(" "));

		if (lista.isEmpty()) {

			// Mensagem de feedback para busca retornada sem dados
			document.add(new Phrase("A busca não retornou dados para os filtros aplicados."));

		} else {

			if (filtro.getAba().equals("funcionario")) {

				PdfPTable table2 = new PdfPTable(7);
				table2.setWidthPercentage(100);

				Stream.of("Nome", "Matrícula", "Lotação", "Situação Atual", "Proventos", "Descontos", "Líquido")
						.forEach(columnTitle -> {
							PdfPCell header = new PdfPCell();
							header.setBackgroundColor(BaseColor.LIGHT_GRAY);
							header.setPaddingBottom(4);
							header.setPaddingLeft(4);
							header.setPhrase(new Phrase(columnTitle, Constantes.TIMES10));
							table2.addCell(header);
						});

				for (RelatorioGerencialDto item : lista) {

					PdfPCell dataCell = new PdfPCell();
					dataCell.setPaddingBottom(4);
					dataCell.setPaddingLeft(4);

					dataCell.setPhrase(new Phrase(item.getFuncionario().getNome(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(item.getFuncionario().getMatricula().toString(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(item.getFuncionario().getLotacao(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(
							new Phrase(item.getFuncionario().getTipoSituacaoFuncional(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(
							new Phrase(Utils.formatarMoedaReal(item.getTotalProvento()), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(
							new Phrase(Utils.formatarMoedaReal(item.getTotalDesconto()), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(item.getTotalLiquido()), Constantes.TIMES10));
					table2.addCell(dataCell);

				}

				PdfPCell dataCell = new PdfPCell();
				dataCell.setPaddingBottom(4);
				dataCell.setPaddingLeft(4);

				table2.addCell(dataCell);
				table2.addCell(dataCell);
				table2.addCell(dataCell);
				table2.addCell(dataCell);

				dataCell.setPhrase(
						new Phrase(Utils.formatarMoedaReal(somatorio.getTotalProvento()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				dataCell.setPhrase(
						new Phrase(Utils.formatarMoedaReal(somatorio.getTotalDesconto()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				dataCell.setPhrase(
						new Phrase(Utils.formatarMoedaReal(somatorio.getTotalLiquido()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				document.add(table2);

			} else if (filtro.getAba().equals("resumo")) {
				PdfPTable table2 = new PdfPTable(4);
				table2.setWidthPercentage(100);

				Stream.of("Código", "Descrição", "Qtd. Funcionários", "Valor").forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setBackgroundColor(BaseColor.LIGHT_GRAY);
					header.setPaddingBottom(4);
					header.setPaddingLeft(4);
					header.setPhrase(new Phrase(columnTitle, Constantes.TIMES10));
					table2.addCell(header);
				});

				Double vantagens = 0.0;
				for (RelatorioGerencialDto item : lista) {

					if (item.getTipoVerba().equals("VANTAGEM")) {
						PdfPCell dataCell = new PdfPCell();
						dataCell.setPaddingBottom(4);
						dataCell.setPaddingLeft(4);

						dataCell.setPhrase(new Phrase(item.getCodigoVerba(), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(item.getDescricaoVerba(), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(String.valueOf(item.getQtdFuncionario()), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(item.getValor()), Constantes.TIMES10));
						table2.addCell(dataCell);

						vantagens += item.getValor() != null ? item.getValor() : 0.0;

					}

				}

				PdfPCell dataCell = new PdfPCell();
				dataCell.setPaddingBottom(4);
				dataCell.setPaddingLeft(4);

				table2.addCell(dataCell);

				dataCell.setPhrase(new Phrase("TOTAL DE VANTAGENS", Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				table2.addCell(new PdfPCell());

				dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(vantagens), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				Double descontos = 0.0;
				for (RelatorioGerencialDto item : lista) {

					if (item.getTipoVerba().equals("DESCONTO")) {
						dataCell = new PdfPCell();
						dataCell.setPaddingBottom(4);
						dataCell.setPaddingLeft(4);

						dataCell.setPhrase(new Phrase(item.getCodigoVerba(), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(item.getDescricaoVerba(), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(String.valueOf(item.getQtdFuncionario()), Constantes.TIMES10));
						table2.addCell(dataCell);

						dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(item.getValor()), Constantes.TIMES10));
						table2.addCell(dataCell);

						descontos += item.getValor() != null ? item.getValor() : 0.0;
					}

				}

				dataCell = new PdfPCell();
				dataCell.setPaddingBottom(4);
				dataCell.setPaddingLeft(4);

				table2.addCell(new PdfPCell());

				dataCell.setPhrase(new Phrase("TOTAL DE DESCONTOS", Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				table2.addCell(new PdfPCell());

				dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(descontos), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				dataCell = new PdfPCell();
				dataCell.setPaddingBottom(4);
				dataCell.setPaddingLeft(4);

				table2.addCell(dataCell);

				dataCell.setPhrase(new Phrase("TOTAL LÍQUIDO", Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				table2.addCell(new PdfPCell());

				dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(vantagens - descontos), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				document.add(table2);

			} else {

				PdfPTable table2 = new PdfPTable(7);
				table2.setWidthPercentage(100);

				if (filtro.getAba().equals("filial")) {
					table2.addCell(getCellGeneric("Código"));
				} else {
					table2 = new PdfPTable(6);
					table2.setWidthPercentage(100);
				}

				table2.addCell(getCellGeneric("Descrição"));
				table2.addCell(getCellGeneric("Qtd. Funcionários"));
				table2.addCell(getCellGeneric("Proventos"));
				table2.addCell(getCellGeneric("Descontos"));
				table2.addCell(getCellGeneric("Líquido"));
				table2.addCell(getCellGeneric("Valor Médio"));

				for (RelatorioGerencialDto item : lista) {

					PdfPCell dataCell = new PdfPCell();
					dataCell.setPaddingBottom(4);
					dataCell.setPaddingLeft(4);

					if (filtro.getAba().equals("filial")) {
						dataCell.setPhrase(new Phrase(item.getTipo().getId().toString(), Constantes.TIMES10));
						table2.addCell(dataCell);
					}

					dataCell.setPhrase(new Phrase(item.getTipo().getDescricao(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(String.valueOf(item.getQtdFuncionario()), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(
							new Phrase(Utils.formatarMoedaReal(item.getTotalProvento()), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(
							new Phrase(Utils.formatarMoedaReal(item.getTotalDesconto()), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(item.getTotalLiquido()), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(Utils.formatarMoedaReal(item.getValorMedio()), Constantes.TIMES10));
					table2.addCell(dataCell);

				}

				PdfPCell dataCell = new PdfPCell();
				dataCell.setPaddingBottom(4);
				dataCell.setPaddingLeft(4);

				if (filtro.getAba().equals("filial")) {
					table2.addCell(dataCell);
				}
				table2.addCell(dataCell);

				dataCell.setPhrase(new Phrase(String.valueOf(somatorio.getQtdFuncionario()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				dataCell.setPhrase(
						new Phrase(Utils.formatarMoedaReal(somatorio.getTotalProvento()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				dataCell.setPhrase(
						new Phrase(Utils.formatarMoedaReal(somatorio.getTotalDesconto()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				dataCell.setPhrase(
						new Phrase(Utils.formatarMoedaReal(somatorio.getTotalLiquido()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				dataCell.setPhrase(
						new Phrase(Utils.formatarMoedaReal(somatorio.getValorMedio()), Constantes.TIMES10BOLD));
				table2.addCell(dataCell);

				document.add(table2);
			}

			document.add(new Paragraph(" "));
		}
	}

	private PdfPCell getCellGeneric(String columnTitle) {
		PdfPCell header = new PdfPCell();
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPaddingBottom(4);
		header.setPaddingLeft(4);
		header.setPhrase(new Phrase(columnTitle, Constantes.TIMES10));
		return header;
	}
}
