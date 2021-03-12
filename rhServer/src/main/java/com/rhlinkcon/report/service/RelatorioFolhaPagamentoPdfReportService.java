package com.rhlinkcon.report.service;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoFiltroDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoFilialDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoProventosDto;
import com.rhlinkcon.service.RelatorioFolhaPagamentoService;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioFolhaPagamentoPdfReportService implements CrudPdfReportService<RelatorioFolhaPagamentoFiltroDto> {

	private static final String TITULO = "Relatórios da folha de pagamentos";
	private static final String SUBTITULO = "";

	LinkedHashMap<String, Double> resumoTotalProventos = new LinkedHashMap<>();

	@Autowired
	private RelatorioFolhaPagamentoService service;

	@Override
	public void personalizarDocumento(PdfWriter writer, Document document, RelatorioFolhaPagamentoFiltroDto filtro)
			throws DocumentException {

		RelatorioFolhaPagamentoDto dto = service.getRelatorioFolhaPagamento(filtro);

		addAtributos(document, TITULO, SUBTITULO);
		addDefaultLogoOnTop(document);
		addContentCabeçalho(document, filtro);

		if (!dto.getResumoFilial().isEmpty()) {
			addContentResumoProventoseDescontos(document, dto);
		}

		// MONTA O RELATÓRIO POR FILIAL
		for (int j = 0; j < filtro.getFilialList().size(); j++) {

			Long filialId = filtro.getFilialList().get(j);
			String situaçãoFuncional = null;

			if (filtro.getSituacaoFuncionalList() != null) {
				for (int l = 0; l < filtro.getSituacaoFuncionalList().size(); l++) {
					situaçãoFuncional = filtro.getSituacaoFuncionalList().get(l);

					RelatorioFolhaPagamentoDto dtoResumoProventos = service
							.getRelatorioFolhaPagamentoResumoProventos(filialId, filtro, situaçãoFuncional);

					addContentCabecalhoResumoProventoseDescontos(writer.getPageNumber(), document, filtro,
							filtro.getFilialString().get(j), situaçãoFuncional, dtoResumoProventos);
					addContentResumoProventoTotalizadores(document, dtoResumoProventos);
					addContentTotalizadores(document, dtoResumoProventos);
				}
			} else {

				RelatorioFolhaPagamentoDto dtoResumoProventos = service
						.getRelatorioFolhaPagamentoResumoProventos(filialId, filtro, situaçãoFuncional);

				addContentCabecalhoResumoProventoseDescontos(writer.getPageNumber(), document, filtro,
						filtro.getFilialString().get(j), situaçãoFuncional, dtoResumoProventos);
				addContentResumoProventoTotalizadores(document, dtoResumoProventos);
				addContentTotalizadores(document, dtoResumoProventos);
			}
		}
	}

	private void addContentCabecalhoResumoProventoseDescontos(int pagina, Document document,
			RelatorioFolhaPagamentoFiltroDto filtro, String nomeFilial, String situaçãoFuncional,
			RelatorioFolhaPagamentoDto dtoResumoProventos) throws DocumentException {

		if (!dtoResumoProventos.getResumoProventosNormal().isEmpty()) {
			document.add(new Paragraph(" "));

			PdfPTable linhaNew = new PdfPTable(1);
			linhaNew.setWidthPercentage(100);
			linhaNew.setHorizontalAlignment(Element.ALIGN_CENTER);
			linhaNew.setWidths(new float[] { 2 });
			if (situaçãoFuncional != null) {
				linhaNew.addCell(cUpper("RESUMO DOS PROVENTOS E DESCONTOS - " + situaçãoFuncional + " / "
						+ filtro.getTipoProcessamento(), Element.ALIGN_CENTER, false));
			} else {
				linhaNew.addCell(cUpper("RESUMO DOS PROVENTOS E DESCONTOS / " + filtro.getTipoProcessamento(),
						Element.ALIGN_CENTER, false));
			}
			document.add(linhaNew);

			PdfPTable linhaNew2 = new PdfPTable(2);
			linhaNew2.setWidthPercentage(100);
			linhaNew2.setHorizontalAlignment(Element.ALIGN_LEFT);
			linhaNew2.setWidths(new float[] { 30, 4 });

			Phrase paginaPhrase = new Phrase();
			paginaPhrase.add(new Phrase("REFERENTE: ", Constantes.TIMES10));
			paginaPhrase.add(new Phrase(
					Utils.getMesCompetenciaString(filtro.getCompetencia()).toUpperCase() + "/" + filtro.getAno(),
					Constantes.TIMES10));
			PdfPCell celula = new PdfPCell(paginaPhrase);
			celula.setHorizontalAlignment(Element.ALIGN_LEFT);
			celula.setVerticalAlignment(Element.ALIGN_TOP);
			celula.setBorderWidth(PdfCell.NO_BORDER);
			celula.setPaddingBottom(4);
			celula.setPaddingTop(4);
			celula.setPaddingLeft(4);
			celula.setPaddingRight(4);
			linhaNew2.addCell(celula);

			Phrase paginaPhrase2 = new Phrase();
			paginaPhrase2.add(new Phrase("PÁGINA: ", Constantes.TIMES8));
			paginaPhrase2.add(new Phrase(pagina + ".", Constantes.TIMES8));
			PdfPCell celulaN = new PdfPCell(paginaPhrase2);
			celulaN.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celulaN.setVerticalAlignment(Element.ALIGN_TOP);
			celulaN.setBorderWidth(PdfCell.NO_BORDER);
			celulaN.setPaddingBottom(4);
			celulaN.setPaddingTop(4);
			celulaN.setPaddingLeft(4);
			celulaN.setPaddingRight(4);
			linhaNew2.addCell(celulaN);

			document.add(linhaNew2);

			PdfPTable linhaNew3 = new PdfPTable(1);
			linhaNew3.setWidthPercentage(100);
			linhaNew3.setHorizontalAlignment(Element.ALIGN_CENTER);
			linhaNew3.setWidths(new float[] { 2 });
			Phrase paginaPhrase3 = new Phrase();
			paginaPhrase3.add(new Phrase("ORGÃOS: ", Constantes.TIMES10));
			paginaPhrase3.add(new Phrase(nomeFilial.toUpperCase(), Constantes.TIMES10));
			PdfPCell celulaN1 = new PdfPCell(paginaPhrase3);
			celulaN1.setHorizontalAlignment(Element.ALIGN_LEFT);
			celulaN1.setVerticalAlignment(Element.ALIGN_TOP);
			celulaN1.setBorderWidth(PdfCell.NO_BORDER);
			celulaN1.setPaddingBottom(4);
			celulaN1.setPaddingTop(4);
			celulaN1.setPaddingLeft(4);
			celulaN1.setPaddingRight(4);
			linhaNew3.addCell(celulaN1);

			document.add(linhaNew3);
		}
	}

	private void addContentCabeçalho(Document document, RelatorioFolhaPagamentoFiltroDto filtro)
			throws DocumentException {

		Paragraph paragrafo = new Paragraph();

		Paragraph rh = new Paragraph("RH", Constantes.TIMES12BOLD);
		rh.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(rh);

		addEmptyLine(paragrafo, 1);

		Paragraph relatStat = new Paragraph("Relatório Folha de Pagamento", Constantes.TIMES12BOLD);
		relatStat.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(relatStat);

		addEmptyLine(paragrafo, 2);
		document.add(paragrafo);

		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);

		Phrase anoPhrase = new Phrase();
		anoPhrase.add(new Phrase("Ano da Competência: ", Constantes.TIMES10BOLD));
		anoPhrase.add(new Phrase(filtro.getAno() + ".", Constantes.TIMES10));
		PdfPCell anoCell = new PdfPCell();
		anoCell.setPhrase(anoPhrase);
		anoCell.setPaddingBottom(4);
		anoCell.setPaddingLeft(4);
		table1.addCell(anoCell);

		Phrase mesPhrase = new Phrase();
		mesPhrase.add(new Phrase("Mês da Competência: ", Constantes.TIMES10BOLD));
		mesPhrase.add(new Phrase(Utils.getMesCompetenciaString(filtro.getCompetencia()).toUpperCase() + ".",
				Constantes.TIMES10));
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

		document.add(table1);
		document.add(new Paragraph(" "));
	}

	private void addContentResumoProventoseDescontos(Document document, RelatorioFolhaPagamentoDto dto)
			throws DocumentException {

		PdfPTable linha = new PdfPTable(2);
		linha.setWidthPercentage(100);
		linha.setWidths(new float[] { 2, 2 });
		linha.addCell(cValueCustom("RESUMO GERAL DOS ORGÃOS ", Element.ALIGN_CENTER, 0, 0, 0, 0));
		linha.addCell(cValueCustom("REFERENTE A: " + dto.getMesAnoCompetencia().toUpperCase(), Element.ALIGN_CENTER, 0,
				0, 0, 0));
		document.add(linha);

		PdfPTable tableV = new PdfPTable(4);
		tableV.setWidthPercentage(100);

		tableV.addCell(cValueCustom("Filial", Element.ALIGN_CENTER, 1, 1, 1, 1));
		tableV.addCell(cValueCustom("Nº Funcionarios", Element.ALIGN_CENTER, 1, 1, 0, 1));
		tableV.addCell(cValueCustom("Liquido", Element.ALIGN_CENTER, 1, 1, 0, 1));
		tableV.addCell(cValueCustom("Bruto", Element.ALIGN_CENTER, 1, 1, 0, 1));

		for (RelatorioFolhaPagamentoResumoFilialDto e : dto.getResumoFilial()) {
			tableV.addCell(cValueCustom(e.getNomeFilial(), Element.ALIGN_LEFT, 0, 1, 1, 1));
			tableV.addCell(cValueCustom(e.getCountFuncionarios().toString(), Element.ALIGN_RIGHT, 0, 1, 0, 1));
			tableV.addCell(cValueCustom(Utils.formatarMoedaReal(e.getLiquidos()), Element.ALIGN_RIGHT, 0, 1, 0, 1));
			tableV.addCell(cValueCustom(Utils.formatarMoedaReal(e.getBruto()), Element.ALIGN_RIGHT, 0, 1, 0, 1));
		}

		document.add(tableV);

		PdfPTable tableD = new PdfPTable(4);
		tableD.setWidthPercentage(100);
		tableD.addCell(cValueCustom("TOTAL GERAL DOS ORGÃOS", Element.ALIGN_LEFT, 0, 1, 1, 1));
		tableD.addCell(cValueCustom(dto.getTotalFuncionarios().toString(), Element.ALIGN_RIGHT, 0, 1, 0, 1));
		tableD.addCell(cValueCustom(Utils.formatarMoedaReal(dto.getTotalLiquido()), Element.ALIGN_RIGHT, 0, 1, 0, 1));
		tableD.addCell(cValueCustom(Utils.formatarMoedaReal(dto.getTotalBruto()), Element.ALIGN_RIGHT, 0, 1, 0, 1));

		document.add(tableD);

	}

	private void addContentResumoProventoTotalizadores(Document document, RelatorioFolhaPagamentoDto dtoResumoProventos)
			throws DocumentException {

		if (!dtoResumoProventos.getResumoProventosNormal().isEmpty()) {

			montarCabecalhoResumoProventos(document);

			for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosNormal()) {
				PdfPTable linhaV = new PdfPTable(7);
				linhaV.setWidthPercentage(100);
				linhaV.setWidths(new float[] { 4, 2, 2, 2, 2, 2, 2 });

				PdfPCell verbaCell2 = new PdfPCell();
				verbaCell2.setPhrase(new Phrase(e.getVerba().getDescricaoVerba(), Constantes.TIMES9));
				verbaCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				verbaCell2.setPaddingBottom(4);
				verbaCell2.setPaddingLeft(4);
				linhaV.addCell(verbaCell2);

				Phrase normalPhrase2 = new Phrase();
				normalPhrase2.add(new Phrase(e.getCountQuantidade().toString(), Constantes.TIMES8));
				PdfPCell normalCell2 = new PdfPCell(normalPhrase2);
				normalCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				normalCell2.setPaddingBottom(4);
				normalCell2.setPaddingLeft(4);
				linhaV.addCell(normalCell2);

				Phrase diferencaPhrase2 = new Phrase();
				diferencaPhrase2.add(new Phrase(Utils.formatarMoedaReal(e.getValor()), Constantes.TIMES8));
				PdfPCell diferencaCell2 = new PdfPCell(diferencaPhrase2);
				diferencaCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				diferencaCell2.setPaddingBottom(4);
				diferencaCell2.setPaddingLeft(4);
				linhaV.addCell(diferencaCell2);

				String quant = "0";
				Double valor = 0.0;
				for (RelatorioFolhaPagamentoResumoProventosDto e2 : dtoResumoProventos.getResumoProventosDiferenca()) {
					if (e2.getVerba().getVerbaAssociada().getDescricao()
							.equals(e.getVerba().getDescricaoVerba())) {
						quant = e2.getCountQuantidade().toString();
						valor = e2.getValor();
						break;
					}
				}

				Phrase normalPhrase3 = new Phrase();
				normalPhrase3.add(new Phrase(quant, Constantes.TIMES8));
				PdfPCell normalCell3 = new PdfPCell(normalPhrase3);
				normalCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				normalCell3.setPaddingBottom(4);
				normalCell3.setPaddingLeft(4);
				linhaV.addCell(normalCell3);

				Phrase diferencaPhrase3 = new Phrase();
				diferencaPhrase3.add(new Phrase(Utils.formatarMoedaReal(valor), Constantes.TIMES8));
				PdfPCell diferencaCell3 = new PdfPCell(diferencaPhrase3);
				diferencaCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				diferencaCell3.setPaddingBottom(4);
				diferencaCell3.setPaddingLeft(4);
				linhaV.addCell(diferencaCell3);

				String quant2 = "0";
				Double valor2 = 0.0;
				for (RelatorioFolhaPagamentoResumoProventosDto e3 : dtoResumoProventos.getResumoProventosDevolucao()) {
					if (e3.getVerba().getVerbaAssociada().getDescricao()
							.equals(e.getVerba().getDescricaoVerba())) {
						quant2 = e3.getCountQuantidade().toString();
						valor2 = e3.getValor();
						break;
					}
				}

				Phrase normalPhrase4 = new Phrase();
				normalPhrase4.add(new Phrase(quant2, Constantes.TIMES8));
				PdfPCell normalCell4 = new PdfPCell(normalPhrase4);
				normalCell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				normalCell4.setPaddingBottom(4);
				normalCell4.setPaddingLeft(4);
				linhaV.addCell(normalCell4);

				Phrase diferencaPhrase4 = new Phrase();
				diferencaPhrase4.add(new Phrase(Utils.formatarMoedaReal(valor2), Constantes.TIMES8));
				PdfPCell diferencaCell4 = new PdfPCell(diferencaPhrase4);
				diferencaCell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				diferencaCell4.setPaddingBottom(4);
				diferencaCell4.setPaddingLeft(4);
				linhaV.addCell(diferencaCell4);

				document.add(linhaV);
			}
		}
	}

	private void addContentTotalizadores(Document document, RelatorioFolhaPagamentoDto dtoResumoProventos) {

		try {
			if (!dtoResumoProventos.getResumoProventosNormal().isEmpty()) {

				sumarizadorTotalProventos(dtoResumoProventos);

				document.add(new Paragraph(" "));

				PdfPTable linha0 = new PdfPTable(1);
				linha0.setWidthPercentage(100);
				linha0.setWidths(new float[] { 2 });
				linha0.addCell(cValueCustom("RESUMO DOS PROVENTOS E DESCONTOS (TOTALIZADORES E AFINS)",
						Element.ALIGN_CENTER, 1, 1, 1, 1));
				document.add(linha0);

				// Monta a tabela de Resumo dos Proventos
				for (Entry<String, Double> entry : resumoTotalProventos.entrySet()) {
					PdfPTable linha1 = new PdfPTable(2);
					linha1.setWidthPercentage(100);
					linha1.setWidths(new float[] { 2, 2 });
					linha1.addCell(cValueCustom(entry.getKey(), Element.ALIGN_LEFT, 0, 1, 1, 1));
					linha1.addCell(
							cValueCustom(Utils.formatarMoedaReal(entry.getValue()), Element.ALIGN_RIGHT, 0, 1, 0, 1));
					document.add(linha1);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void montarCabecalhoResumoProventos(Document doc) {

		try {
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);

			Phrase verbaPhrase = new Phrase();
			verbaPhrase.add(new Phrase("VERBA", Constantes.TIMES8BOLD));
			PdfPCell verbaCell = new PdfPCell(verbaPhrase);
			verbaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			verbaCell.setPaddingBottom(4);
			verbaCell.setPaddingLeft(4);
			table.addCell(verbaCell);

			Phrase normalPhrase = new Phrase();
			normalPhrase.add(new Phrase("NORMAL", Constantes.TIMES8BOLD));
			PdfPCell normalCell = new PdfPCell(normalPhrase);
			normalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			normalCell.setPaddingBottom(4);
			normalCell.setPaddingLeft(4);
			table.addCell(normalCell);

			Phrase diferencaPhrase = new Phrase();
			diferencaPhrase.add(new Phrase("DIFERENÇA", Constantes.TIMES8BOLD));
			PdfPCell diferencaCell = new PdfPCell(diferencaPhrase);
			diferencaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			diferencaCell.setPaddingBottom(4);
			diferencaCell.setPaddingLeft(4);
			table.addCell(diferencaCell);

			Phrase devolucaoPhrase = new Phrase();
			devolucaoPhrase.add(new Phrase("DEVOLUÇÃO", Constantes.TIMES8BOLD));
			PdfPCell devolucaoCell = new PdfPCell(devolucaoPhrase);
			devolucaoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			devolucaoCell.setPaddingBottom(4);
			devolucaoCell.setPaddingLeft(4);
			table.addCell(devolucaoCell);

			doc.add(table);

			PdfPTable table2 = new PdfPTable(7);
			table2.setWidthPercentage(100);
			table2.setWidths(new float[] { 4, 2, 2, 2, 2, 2, 2 });

			PdfPCell verbaCell2 = new PdfPCell();
			verbaCell2.setPhrase(new Phrase("", Constantes.TIMES9));
			verbaCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			verbaCell2.setPaddingBottom(4);
			verbaCell2.setPaddingLeft(4);
			table2.addCell(verbaCell2);

			Phrase normalPhrase2 = new Phrase();
			normalPhrase2.add(new Phrase("QUANTIDADE", Constantes.TIMES8BOLD));
			PdfPCell normalCell2 = new PdfPCell(normalPhrase2);
			normalCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			normalCell2.setPaddingBottom(4);
			normalCell2.setPaddingLeft(4);
			table2.addCell(normalCell2);

			Phrase diferencaPhrase2 = new Phrase();
			diferencaPhrase2.add(new Phrase("VALOR", Constantes.TIMES8BOLD));
			PdfPCell diferencaCell2 = new PdfPCell(diferencaPhrase2);
			diferencaCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			diferencaCell2.setPaddingBottom(4);
			diferencaCell2.setPaddingLeft(4);
			table2.addCell(diferencaCell2);

			Phrase normalPhrase3 = new Phrase();
			normalPhrase3.add(new Phrase("QUANTIDADE", Constantes.TIMES8BOLD));
			PdfPCell normalCell3 = new PdfPCell(normalPhrase3);
			normalCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			normalCell3.setPaddingBottom(4);
			normalCell3.setPaddingLeft(4);
			table2.addCell(normalCell3);

			Phrase diferencaPhrase3 = new Phrase();
			diferencaPhrase3.add(new Phrase("VALOR", Constantes.TIMES8BOLD));
			PdfPCell diferencaCell3 = new PdfPCell(diferencaPhrase3);
			diferencaCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			diferencaCell3.setPaddingBottom(4);
			diferencaCell3.setPaddingLeft(4);
			table2.addCell(diferencaCell3);

			Phrase normalPhrase4 = new Phrase();
			normalPhrase4.add(new Phrase("QUANTIDADE", Constantes.TIMES8BOLD));
			PdfPCell normalCell4 = new PdfPCell(normalPhrase4);
			normalCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			normalCell4.setPaddingBottom(4);
			normalCell4.setPaddingLeft(4);
			table2.addCell(normalCell4);

			Phrase diferencaPhrase4 = new Phrase();
			diferencaPhrase4.add(new Phrase("VALOR", Constantes.TIMES8BOLD));
			PdfPCell diferencaCell4 = new PdfPCell(diferencaPhrase4);
			diferencaCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			diferencaCell4.setPaddingBottom(4);
			diferencaCell4.setPaddingLeft(4);
			table2.addCell(diferencaCell4);

			doc.add(table2);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private PdfPCell cUpper(String texto, int alinhamento, boolean bold) {
		Phrase valor;
		if (Objects.nonNull(texto)) {
			valor = new Phrase(0, texto.toUpperCase(), bold ? Constantes.TIMES9BOLD : Constantes.TIMES9);
		} else {
			valor = new Phrase(0, " ", bold ? Constantes.TIMES9BOLD : Constantes.TIMES9);
		}
		PdfPCell c = new PdfPCell(valor);
		c.setHorizontalAlignment(alinhamento);
		c.setVerticalAlignment(Element.ALIGN_TOP);
		c.setBorderWidth(PdfCell.NO_BORDER);
		c.setPaddingBottom(4);
		c.setPaddingTop(4);
		c.setPaddingLeft(4);
		c.setPaddingRight(4);
		return c;
	}

	private PdfPCell cValueCustom(String texto, int alinhamento, int top, int bottom, int left, int right) {
		Phrase valor;
		if (Objects.nonNull(texto)) {
			valor = new Phrase(0, texto.toUpperCase(), Constantes.TIMES9);
		} else {
			valor = new Phrase(0, " ", Constantes.TIMES9);
		}
		PdfPCell c = new PdfPCell(valor);
		c.setHorizontalAlignment(alinhamento);
		c.setVerticalAlignment(Element.ALIGN_TOP);
		c.setBorderWidthTop(top);
		c.setBorderWidthBottom(bottom);
		c.setBorderWidthLeft(left);
		c.setBorderWidthRight(right);
		c.setPaddingBottom(4);
		c.setPaddingTop(4);
		c.setPaddingLeft(4);
		c.setPaddingRight(4);
		return c;
	}

	/**
	 * SUMARIZA AS VERBAS NORMAL, DIFERENÇA E DEVOLUÇÃO
	 * 
	 * @param dtoResumoProventos
	 */
	private void sumarizadorTotalProventos(RelatorioFolhaPagamentoDto dtoResumoProventos) {

		Double totalProventosNormal = 0.0;
		Double totalDescontoNormal = 0.0;

		Double totalProventosDevolucao = 0.0;
		Double totalDescontoDevolucao = 0.0;

		Double totalProventosDiferenca = 0.0;
		Double totalDescontoDiferenca = 0.0;

		// Soma todas as verbas Normal
		for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosNormal()) {

			if (e.getVerba().getTipoVerba().equals("VANTAGEM")) {
				totalProventosNormal = totalProventosNormal + e.getValor();
			} else if (e.getVerba().getTipoVerba().equals("DESCONTO")) {
				totalDescontoNormal = totalDescontoNormal + e.getValor();
			}
		}

		// Soma todas as verbas Debvolução
		for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosDevolucao()) {

			if (e.getVerba().getTipoVerba().equals("VANTAGEM")) {
				totalProventosDevolucao = totalProventosDevolucao + e.getValor();
			} else if (e.getVerba().getTipoVerba().equals("DESCONTO")) {
				totalDescontoDevolucao = totalDescontoDevolucao + e.getValor();
			}
		}

		// Soma todas as verbas Diferença
		for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosDiferenca()) {

			if (e.getVerba().getTipoVerba().equals("VANTAGEM")) {
				totalProventosDiferenca = totalProventosDiferenca + e.getValor();
			} else if (e.getVerba().getTipoVerba().equals("DESCONTO")) {
				totalDescontoDiferenca = totalDescontoDiferenca + e.getValor();
			}
		}

		// Monta a formula para cada campo.
		Double campo27 = totalProventosNormal + totalProventosDiferenca + totalProventosDiferenca;
		Double campo28 = (totalProventosNormal + totalProventosDiferenca - totalProventosDevolucao)
				- totalDescontoNormal - totalDescontoDiferenca + totalDescontoDevolucao;
		Double campo29 = sumarizarTotaisdeVerbas(dtoResumoProventos, "FGTS");
		Double campo30 = sumarizarTotaisdeVerbas(dtoResumoProventos, "IPSM");
		Double campo31 = sumarizarTotaisdeVerbas(dtoResumoProventos, "ISM SAUDE");
		Double campo32 = sumarizarTotaisdeVerbas(dtoResumoProventos, "INSS");
		Double campo33 = sumarizarTotaisdeVerbas(dtoResumoProventos, "SALÁRIO FAMILIA(INSS)");
		Double campo34 = campo27 + campo28 + campo29 + campo30 + campo31 + campo32 + campo33;

		// Adiciona no LinkedHashMap para interar na hora da montgem da tabela Excel
		resumoTotalProventos.put("VALOR DO ORGÃOS PARA EMPENHO:", campo27);
		resumoTotalProventos.put("TOTAL LÍQUIDO DOS ORGÃOS:", campo28);
		resumoTotalProventos.put("TOTAL DO FGTS:", campo29);
		resumoTotalProventos.put("CONTRIBUIÇÃO PATRONAL PARA O RPPS:", campo30);
		resumoTotalProventos.put("TOTAL ISM SAÚDE PATRONAL:", campo31);
		resumoTotalProventos.put("CONTRIBUIÇÃO PATRONAL PARA O INSS:", campo32);
		resumoTotalProventos.put("SALÁRIO FAMILIA(INSS):", campo33);
		resumoTotalProventos.put("TOTAL GERAL COM ENCARGOS:", campo34);

	}

	/**
	 * @param dtoResumoProventos
	 * @param descricaoVerba
	 * 
	 * @formula - Total de verbas “normais” com ":descricaoVerba" no nome + total de
	 *          verbas de “diferença” com ":descricaoVerba" no nome - total de
	 *          verbas de “devolução” com ":descricaoVerba" no nome.
	 * 
	 * @return Double
	 */
	private Double sumarizarTotaisdeVerbas(RelatorioFolhaPagamentoDto dtoResumoProventos, String descricaoVerba) {

		Double totalverbaNormal = 0.0;
		Double totalverbaDevolucao = 0.0;
		Double totalverbaDiferenca = 0.0;

		totalverbaNormal = totalizadorCampoDescricaoVerbaNormal(dtoResumoProventos, descricaoVerba);
		totalverbaDevolucao = totalizadorCampoDescricaoVerbaDevolucao(dtoResumoProventos, descricaoVerba);
		totalverbaDiferenca = totalizadorCampoDescricaoVerbaDiferenca(dtoResumoProventos, descricaoVerba);

		return totalverbaNormal + totalverbaDiferenca - totalverbaDevolucao;

	}

	/**
	 * @param dtoResumoProventos
	 * @param descricaoVerba
	 * 
	 * @formula - realiza um SUM no campo 'valor' nas Verbas do tipo Normal, onde a
	 *          descricao_verba é igual ao parametro :descricaoVerba
	 * 
	 * @return
	 */
	private Double totalizadorCampoDescricaoVerbaNormal(RelatorioFolhaPagamentoDto dtoResumoProventos,
			String descricaoVerba) {
		Double totalVerbaNormal = 0.0;

		for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosNormal()) {

			if (e.getVerba().getDescricaoVerba().contains(descricaoVerba)) {
				totalVerbaNormal = totalVerbaNormal + e.getValor();
			}
		}

		return totalVerbaNormal;
	}

	/**
	 * @param dtoResumoProventos
	 * @param descricaoVerba
	 * 
	 * @formula - realiza um SUM no campo 'valor' nas Verbas do tipo Devolucao, onde
	 *          a descricao_verba é igual ao parametro :descricaoVerba
	 * 
	 * @return
	 */
	private Double totalizadorCampoDescricaoVerbaDevolucao(RelatorioFolhaPagamentoDto dtoResumoProventos,
			String descricaoVerba) {
		Double totalVerbaDevolucao = 0.0;

		for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosDevolucao()) {
			if (e.getVerba().getDescricaoVerba().contains(descricaoVerba)) {
				totalVerbaDevolucao = totalVerbaDevolucao + e.getValor();
			}
		}

		return totalVerbaDevolucao;
	}

	/**
	 * @param dtoResumoProventos
	 * @param descricaoVerba
	 * 
	 * @formula - realiza um SUM no campo 'valor' nas Verbas do tipo Diferenca, onde
	 *          a descricao_verba é igual ao parametro :descricaoVerba
	 * 
	 * @return
	 */
	private Double totalizadorCampoDescricaoVerbaDiferenca(RelatorioFolhaPagamentoDto dtoResumoProventos,
			String descricaoVerba) {
		Double totalVerbaDiferenca = 0.0;

		for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosDiferenca()) {
			if (e.getVerba().getDescricaoVerba().contains(descricaoVerba)) {
				totalVerbaDiferenca = totalVerbaDiferenca + e.getValor();
			}
		}

		return totalVerbaDiferenca;
	}

}
