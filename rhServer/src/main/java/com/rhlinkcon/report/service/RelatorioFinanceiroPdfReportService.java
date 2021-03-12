package com.rhlinkcon.report.service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.rhlinkcon.model.RelatorioFinanceiroFolhaPagamento;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.ExportRelatorioFinanceiroFuncionarioPensionista;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.ExportRelatorioFinanceiroSituacaoFuncional;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.ExportRelatorioFinanceiroSomatorioGeral;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioFinanceiroPdfReportService {

	class Footer extends PdfPageEventHelper {
		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();

			SimpleDateFormat formato = new SimpleDateFormat("'ás' HH:mm 'do dia' dd/MM/yyyy");
			Phrase footer = new Phrase("Gerado por: " + Utils.getUsuarioLogado() + " " + formato.format(new Date()),
					Constantes.TIMES8);

			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		}
	}

	public byte[] gerarPdf(RelatorioFinanceiroFolhaPagamento relatorioFinanceiro,
			List<ExportRelatorioFinanceiroSituacaoFuncional> list) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPageEvent(new Footer());

			document.open();
			addContent(document, relatorioFinanceiro, list);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}

	private void addContent(Document document, RelatorioFinanceiroFolhaPagamento relatorioFinanceiro,
			List<ExportRelatorioFinanceiroSituacaoFuncional> list) throws DocumentException {

		Paragraph paragrafo = new Paragraph();

		// Adicionando o brasão
		try {
			Resource resource = new ClassPathResource(Constantes.BRASAO);
			Image imagem = Image.getInstance(resource.getURL());
			imagem.setAlignment(Image.ALIGN_CENTER);
			imagem.scalePercent(35);

			document.add(imagem);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Paragraph rh = new Paragraph("RH", Constantes.TIMES12BOLD);
		rh.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(rh);

		addEmptyLine(paragrafo, 1);

		Paragraph ctcComp = new Paragraph("Relatório Financeiro", Constantes.TIMES12BOLD);
		ctcComp.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(ctcComp);

		addEmptyLine(paragrafo, 1);
		document.add(paragrafo);

		// Somatória Geral
		ExportRelatorioFinanceiroSomatorioGeral somatorioGeral = new ExportRelatorioFinanceiroSomatorioGeral();

		// MONTAGEM DA TABELA INICIAL
		for (ExportRelatorioFinanceiroSituacaoFuncional bloco1 : list) {

			// Carregando os valores totais de todos os blocos.
			somatorioGeral.setTotalDescontos(somatorioGeral.getTotalDescontos() + bloco1.getTotalDescontos());
			somatorioGeral.setTotalProventos(somatorioGeral.getTotalProventos() + bloco1.getTotalProventos());
			somatorioGeral.setTotalLiquido(somatorioGeral.getTotalLiquido() + bloco1.getTotalLiquido());
			somatorioGeral.setTotalFuncionarios(somatorioGeral.getTotalFuncionarios() + bloco1.getTotalFuncionarios());

			montarCabecalhoContracheque(document, bloco1, true);

			montarTabelaRubricas(document, bloco1.getListProventos(), bloco1.getListDescontos(), somatorioGeral);

			montarFooterContracheque(document, bloco1.getTotalProventos(), bloco1.getTotalDescontos(),
					bloco1.getTotalLiquido(), bloco1.getTotalFuncionarios());
			document.add(new Paragraph(" "));

			for (ExportRelatorioFinanceiroFuncionarioPensionista bloco2 : bloco1.getListFuncionarioPensionistas()) {

				montarCabecalhoContracheque(document, bloco1, false);

				montarCabecalhoContracheque(document, bloco2);

				montarTabelaRubricas(document, bloco2.getListProventos(), bloco2.getListDescontos(), null);

				montarFooterContracheque(document, bloco2.getTotalProventos(), bloco2.getTotalDescontos(),
						bloco2.getTotalLiquido(), null);
				document.add(new Paragraph(" "));

			}
		}

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		PdfPCell hcell = genericCell("SOMATÓRIO GERAL", Constantes.TIMES10BOLD, Element.ALIGN_CENTER);
		hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(hcell);

		document.add(table);

		montarTabelaRubricas(document, somatorioGeral.getListProventos(), somatorioGeral.getListDescontos(), null);

		montarFooterContracheque(document, somatorioGeral.getTotalProventos(), somatorioGeral.getTotalDescontos(),
				somatorioGeral.getTotalLiquido(), somatorioGeral.getTotalFuncionarios());
	}

	private PdfPCell getCell() {
		PdfPCell hcell = new PdfPCell();
		hcell.setPaddingTop(4);
		hcell.setPaddingLeft(4);
		hcell.setPaddingBottom(4);
		hcell.setPaddingRight(4);
		return hcell;
	}

	private void montarCabecalhoContracheque(Document document, ExportRelatorioFinanceiroSituacaoFuncional bloco1,
			boolean backgroundColor) throws DocumentException {

		PdfPTable table;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		table = new PdfPTable(5);
		table.setWidthPercentage(100);
		// table.setWidths(new int[] { 8, 2 });

		String mesAnoComp = bloco1.getFolhaPagamento().getFolhaCompetencia().getMesAno();
		PdfPCell hcell = genericCell("FOLHA DE PAGAMENTO - " + mesAnoComp, Constantes.TIMES10BOLD,
				Element.ALIGN_CENTER);
		if (backgroundColor)
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		hcell.setColspan(5);
		table.addCell(hcell);

		table.addCell(genericCell("1. CNPJ"));
		table.addCell(genericCell("2. Filial"));
		table.addCell(genericCell("3. Status do Relatório"));
		table.addCell(genericCell("4. Situação Funcional"));
		table.addCell(genericCell("5. Data Atual"));

		table.addCell(genericCell(Objects.nonNull(bloco1.getCnpj()) ? bloco1.getCnpj() : ""));
		table.addCell(genericCell(Objects.nonNull(bloco1.getFilial()) ? bloco1.getFilial() : ""));
		table.addCell(genericCell(bloco1.getStatus().getLabel().toUpperCase()));
		table.addCell(genericCell(bloco1.getSituacaoFuncional()));
		table.addCell(genericCell(simpleDateFormat.format((bloco1.getDataAtual()))));

		document.add(table);

	}

	private void montarTabelaRubricas(Document document, HashMap<String, Double> listProventos,
			HashMap<String, Double> listDescontos, ExportRelatorioFinanceiroSomatorioGeral somatorioGeral)
			throws DocumentException {

		PdfPTable table;
		PdfPCell cell;

		table = new PdfPTable(2);
		table.setWidthPercentage(100);

		table.addCell(genericCell("6 - PROVENTOS", Constantes.TIMES8BOLD, Element.ALIGN_CENTER));

		table.addCell(genericCell("7 - DESCONTOS", Constantes.TIMES8BOLD, Element.ALIGN_CENTER));

		document.add(table);

		table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 2, 1, 2, 1 });

		Iterator itProventos = listProventos.entrySet().iterator();

		boolean firstLoop = true;
		while (itProventos.hasNext()) {
			Map.Entry pair = (Map.Entry) itProventos.next();

			Double valor = (Double) pair.getValue();
			String labelKey = pair.getKey().toString();

			// Juntando os valores de todos os proventos
			if (Objects.nonNull(somatorioGeral)) {
				if (somatorioGeral.getListProventos().containsKey(labelKey)) {
					somatorioGeral.getListProventos().put(labelKey,
							somatorioGeral.getListProventos().get(labelKey) + valor);
				} else {
					somatorioGeral.getListProventos().put(labelKey, valor);
				}
			}

			cell = genericCell(labelKey.toUpperCase());
			if (firstLoop)
				cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
			else
				cell.setBorder(Rectangle.LEFT);
			table.addCell(cell);

			cell = genericCell(Utils.formatarMoedaReal(valor), Element.ALIGN_RIGHT);
			if (firstLoop)
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
			else
				cell.setBorder(Rectangle.RIGHT);
			table.addCell(cell);

			cell = genericCell("");
			if (firstLoop)
				cell.setBorder(Rectangle.TOP);
			else
				cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);

			cell = genericCell("", Element.ALIGN_RIGHT);
			if (firstLoop)
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
			else
				cell.setBorder(Rectangle.RIGHT);

			table.addCell(cell);
			firstLoop = false;
			itProventos.remove();

		}

		Iterator itDescontos = listDescontos.entrySet().iterator();

		int rowIndex = 0;
		int rowsSize = table.getRows().size();
		while (itDescontos.hasNext()) {
			Map.Entry pair = (Map.Entry) itDescontos.next();

			Double valor = (Double) pair.getValue();
			String labelKey = pair.getKey().toString();

			// Juntando os valores de todos os descontos
			if (Objects.nonNull(somatorioGeral)) {
				if (somatorioGeral.getListDescontos().containsKey(labelKey)) {
					somatorioGeral.getListDescontos().put(labelKey,
							somatorioGeral.getListDescontos().get(labelKey) + valor);
				} else {
					somatorioGeral.getListDescontos().put(labelKey, valor);
				}
			}

			if (rowIndex < rowsSize) {
				table.getRow(rowIndex).getCells()[2].setPhrase(new Phrase(labelKey.toUpperCase(), Constantes.TIMES8));
				table.getRow(rowIndex).getCells()[3]
						.setPhrase(new Phrase(Utils.formatarMoedaReal(valor), Constantes.TIMES8));
				rowIndex++;
			} else {

				cell = genericCell("");
				cell.setBorder(Rectangle.LEFT);
				table.addCell(cell);

				cell = genericCell("");
				cell.setBorder(Rectangle.RIGHT);
				table.addCell(cell);

				cell = genericCell(labelKey);
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);

				cell = genericCell(Utils.formatarMoedaReal(valor), Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.RIGHT);
				table.addCell(cell);

			}

			itDescontos.remove();

		}

		document.add(table);

	}

	private PdfPCell genericCell(String value) {
		Phrase phrase = new Phrase();
		phrase.add(new Phrase(value, Constantes.TIMES8));
		PdfPCell hcell = getCell();
		hcell.setPhrase(phrase);
		return hcell;
	}

	private PdfPCell genericCell(String value, Font font, int element) {
		Phrase phrase = new Phrase();
		phrase.add(new Phrase(value, font));
		PdfPCell hcell = getCell();
		hcell.setPhrase(phrase);
		hcell.setHorizontalAlignment(element);
		return hcell;
	}

	private PdfPCell genericCell(String value, int element) {
		Phrase phrase = new Phrase();
		phrase.add(new Phrase(value, Constantes.TIMES8));
		PdfPCell hcell = getCell();
		hcell.setPhrase(phrase);
		hcell.setHorizontalAlignment(element);
		return hcell;
	}

	private void montarCabecalhoContracheque(Document document, ExportRelatorioFinanceiroFuncionarioPensionista bloco2)
			throws DocumentException {

		PdfPTable table;

		table = new PdfPTable(3);
		table.setWidthPercentage(100);

		table.addCell(genericCell("8. Matrícula"));
		table.addCell(genericCell("8. Nome"));
		table.addCell(genericCell("8. Lotação"));

		table.addCell(genericCell(bloco2.getMatricula()));
		table.addCell(genericCell(bloco2.getNome()));
		table.addCell(genericCell(bloco2.getLotacao()));

		table.addCell(genericCell("8. Função"));
		table.addCell(genericCell("8. Situação"));
		table.addCell(genericCell("8. Dados Bancários"));

		table.addCell(genericCell(bloco2.getFuncao()));
		table.addCell(genericCell(bloco2.getSituacaoFuncional()));
		table.addCell(genericCell(bloco2.getDadoBancario()));

		document.add(table);
	}

	private void montarFooterContracheque(Document document, Double totalProventos, Double totalDescontos,
			Double totalLiquido, Integer totalFuncionarios) throws DocumentException {

		PdfPTable table;

		if (Objects.nonNull(totalFuncionarios))
			table = new PdfPTable(4);
		else
			table = new PdfPTable(3);
		table.setWidthPercentage(100);

		table.addCell(genericCell("8. Total Proventos", Constantes.TIMES8BOLD, Element.ALIGN_CENTER));
		table.addCell(genericCell("9. Total Descontos", Constantes.TIMES8BOLD, Element.ALIGN_CENTER));
		table.addCell(genericCell("10. Total Líquido", Constantes.TIMES8BOLD, Element.ALIGN_CENTER));

		if (Objects.nonNull(totalFuncionarios))
			table.addCell(genericCell("11. Nº de Funcionários", Constantes.TIMES8BOLD, Element.ALIGN_CENTER));

		table.addCell(genericCell(Utils.formatarMoedaReal(totalProventos), Element.ALIGN_CENTER));
		table.addCell(genericCell(Utils.formatarMoedaReal(totalDescontos), Element.ALIGN_CENTER));
		table.addCell(genericCell(Utils.formatarMoedaReal(totalLiquido), Element.ALIGN_CENTER));

		if (Objects.nonNull(totalFuncionarios))
			table.addCell(genericCell(String.valueOf(totalFuncionarios), Element.ALIGN_CENTER));

		document.add(table);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}