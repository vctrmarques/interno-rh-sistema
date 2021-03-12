package com.rhlinkcon.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoFiltroDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoFilialDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoProventosDto;
import com.rhlinkcon.service.RelatorioFolhaPagamentoService;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioFolhaPagamentoExcelReportService {

	LinkedHashMap<String, Double> resumoTotalProventos = new LinkedHashMap<>();

	@Autowired
	private RelatorioFolhaPagamentoService service;

	public ByteArrayInputStream gerarExcel(RelatorioFolhaPagamentoFiltroDto filtro) {

		RelatorioFolhaPagamentoDto dto = service.getRelatorioFolhaPagamento(filtro);
		RelatorioFolhaPagamentoDto dtoResumoProventos = null;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Relatório de Folha de Pagamento");

		CellStyle styleCenterBold = workbook.createCellStyle();
		styleCenterBold.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		styleCenterBold.setFont(font);

		CellStyle borderBoldStyle = workbook.createCellStyle();
		borderBoldStyle.setFont(font);
		borderBoldStyle.setBorderBottom(BorderStyle.THIN);
		borderBoldStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		borderBoldStyle.setBorderLeft(BorderStyle.THIN);
		borderBoldStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		borderBoldStyle.setBorderRight(BorderStyle.THIN);
		borderBoldStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		borderBoldStyle.setBorderTop(BorderStyle.THIN);
		borderBoldStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle backgroundBorderStyle = workbook.createCellStyle();
		backgroundBorderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		backgroundBorderStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.index);
		backgroundBorderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		backgroundBorderStyle.setBorderBottom(BorderStyle.THIN);
		backgroundBorderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		backgroundBorderStyle.setBorderLeft(BorderStyle.THIN);
		backgroundBorderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		backgroundBorderStyle.setBorderRight(BorderStyle.THIN);
		backgroundBorderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		backgroundBorderStyle.setBorderTop(BorderStyle.THIN);
		backgroundBorderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle borderStyleRight = workbook.createCellStyle();
		borderStyleRight.setBorderBottom(BorderStyle.THIN);
		borderStyleRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleRight.setBorderLeft(BorderStyle.THIN);
		borderStyleRight.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleRight.setBorderRight(BorderStyle.THIN);
		borderStyleRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleRight.setBorderTop(BorderStyle.THIN);
		borderStyleRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleRight.setAlignment(HorizontalAlignment.RIGHT);

		CellStyle borderStyleLeft = workbook.createCellStyle();
		borderStyleLeft.setBorderBottom(BorderStyle.THIN);
		borderStyleLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleLeft.setBorderLeft(BorderStyle.THIN);
		borderStyleLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleLeft.setBorderRight(BorderStyle.THIN);
		borderStyleLeft.setRightBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleLeft.setBorderTop(BorderStyle.THIN);
		borderStyleLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleLeft.setAlignment(HorizontalAlignment.LEFT);

		CellStyle borderStyleCenter = workbook.createCellStyle();
		borderStyleCenter.setBorderBottom(BorderStyle.THIN);
		borderStyleCenter.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleCenter.setBorderLeft(BorderStyle.THIN);
		borderStyleCenter.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleCenter.setBorderRight(BorderStyle.THIN);
		borderStyleCenter.setRightBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleCenter.setBorderTop(BorderStyle.THIN);
		borderStyleCenter.setTopBorderColor(IndexedColors.BLACK.getIndex());
		borderStyleCenter.setAlignment(HorizontalAlignment.CENTER);

		CellStyle backgroundBorderStyleCenter = workbook.createCellStyle();
		backgroundBorderStyleCenter.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		backgroundBorderStyleCenter.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.index);
		backgroundBorderStyleCenter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		backgroundBorderStyleCenter.setBorderBottom(BorderStyle.THIN);
		backgroundBorderStyleCenter.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		backgroundBorderStyleCenter.setBorderLeft(BorderStyle.THIN);
		backgroundBorderStyleCenter.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		backgroundBorderStyleCenter.setBorderRight(BorderStyle.THIN);
		backgroundBorderStyleCenter.setRightBorderColor(IndexedColors.BLACK.getIndex());
		backgroundBorderStyleCenter.setBorderTop(BorderStyle.THIN);
		backgroundBorderStyleCenter.setTopBorderColor(IndexedColors.BLACK.getIndex());
		backgroundBorderStyleCenter.setAlignment(HorizontalAlignment.CENTER);

		int rownum = 0;

		String finalCollumn = ":J";

		Row row = sheet.createRow(rownum++);
		Cell cell = row.createCell(0);
		cell.setCellValue("RH");
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellValue("RELATÓRIO FOLHA PAGAMENTO");
		cell.setCellStyle(styleCenterBold);
		sheet.setColumnWidth(0, 8000);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellValue("FILIAIS");
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Ano da Competência:");

		cell = row.createCell(1);
		cell.setCellValue(filtro.getAno() + ".");
		cell.setCellStyle(borderStyleLeft);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		cell = row.createCell(2);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(3);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(4);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(5);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(6);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(7);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(8);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(9);
		cell.setCellStyle(borderStyleLeft);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Mês da Competência:");
		cell = row.createCell(1);
		cell.setCellValue(Utils.getMesCompetenciaString(filtro.getCompetencia()).toUpperCase() + ".");
		cell.setCellStyle(borderStyleLeft);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		cell = row.createCell(2);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(3);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(4);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(5);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(6);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(7);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(8);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(9);
		cell.setCellStyle(borderStyleLeft);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Tipo de Processamento:");
		cell = row.createCell(1);
		cell.setCellValue(filtro.getTipoProcessamento() + ".");
		cell.setCellStyle(borderStyleLeft);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		cell = row.createCell(2);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(3);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(4);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(5);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(6);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(7);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(8);
		cell.setCellStyle(borderStyleLeft);
		cell = row.createCell(9);
		cell.setCellStyle(borderStyleLeft);

		row = sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);

		int cellnum = 0;
		rownum = 11;

		cell = row.createCell(cellnum++);
		cell.setCellValue("RESUMO GERAL DOS ORGÃOS");
		cell.setCellStyle(backgroundBorderStyle);

		cell = row.createCell(cellnum++);
		cell.setCellValue("REFERENTE A:");
		cell.setCellStyle(backgroundBorderStyle);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(backgroundBorderStyle);

		cellnum = 0;
		row = sheet.createRow(rownum++);
		cell = row.createCell(0);

		cell = row.createCell(cellnum++);
		cell.setCellValue("FILIAIS");
		cell.setCellStyle(backgroundBorderStyleCenter);

		cell = row.createCell(cellnum++);
		cell.setCellValue("Nº FUNCIONÁRIOS");
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + ":D" + rownum));
		cell.setCellStyle(backgroundBorderStyleCenter);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellValue("LÍQUIDO");
		sheet.addMergedRegion(CellRangeAddress.valueOf("E" + rownum + ":G" + rownum));
		cell.setCellStyle(backgroundBorderStyleCenter);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellValue("BRUTO");
		sheet.addMergedRegion(CellRangeAddress.valueOf("H" + rownum + finalCollumn + rownum));
		cell.setCellStyle(backgroundBorderStyleCenter);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		// Monta Primeira Tabela
		for (RelatorioFolhaPagamentoResumoFilialDto e : dto.getResumoFilial()) {

			cellnum = 0;
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);

			cell = row.createCell(cellnum++);
			cell.setCellValue(e.getNomeFilial());
			cell.setCellStyle(borderStyleLeft);

			cell = row.createCell(cellnum++);
			cell.setCellValue(e.getCountFuncionarios().toString());
			sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + ":D" + rownum));
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellValue(Utils.formatarMoedaReal(e.getLiquidos()));
			sheet.addMergedRegion(CellRangeAddress.valueOf("E" + rownum + ":G" + rownum));
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellValue(Utils.formatarMoedaReal(e.getBruto()));
			sheet.addMergedRegion(CellRangeAddress.valueOf("H" + rownum + finalCollumn + rownum));
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(borderStyleRight);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(borderStyleRight);
		}

		cellnum = 0;
		row = sheet.createRow(rownum++);
		cell = row.createCell(0);

		cell = row.createCell(cellnum++);
		cell.setCellValue("TOTAL GERAL DOS ORGÃOS ");
		cell.setCellStyle(borderStyleLeft);

		cell = row.createCell(cellnum++);
		cell.setCellValue(dto.getTotalFuncionarios().toString());
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + ":D" + rownum));
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellValue(Utils.formatarMoedaReal(dto.getTotalLiquido()));
		sheet.addMergedRegion(CellRangeAddress.valueOf("E" + rownum + ":G" + rownum));
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellValue(Utils.formatarMoedaReal(dto.getTotalBruto()));
		sheet.addMergedRegion(CellRangeAddress.valueOf("H" + rownum + finalCollumn + rownum));
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(borderStyleRight);

		for (int j = 0; j < filtro.getFilialList().size(); j++) {

			Long filialId = filtro.getFilialList().get(j);
			String situaçãoFuncional = null;

			if (filtro.getSituacaoFuncionalList() != null) {
				for (int l = 0; l < filtro.getSituacaoFuncionalList().size(); l++) {
					situaçãoFuncional = filtro.getSituacaoFuncionalList().get(l);

					dtoResumoProventos = service.getRelatorioFolhaPagamentoResumoProventos(filialId, filtro,
							situaçãoFuncional);

					if (!dtoResumoProventos.getResumoProventosNormal().isEmpty()) {

						row = sheet.createRow(rownum++);
						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellValue("RESUMO DOS PROVENTOS E DESCONTOS - " + situaçãoFuncional + " / "
								+ filtro.getTipoProcessamento());
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellValue("ORGÃOS : " + filtro.getFilialString().get(j));
						cell.setCellStyle(backgroundBorderStyle);
						sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("NORMAL");
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + ":D" + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("DIFERENÇA");
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("E" + rownum + ":G" + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("DEVOLUÇÃO");
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("H" + rownum + finalCollumn + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellValue("VERBAS");
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("Quant.");
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("VALOR");
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("C" + rownum + ":D" + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("Quant.");
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("VALOR");
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("F" + rownum + ":G" + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("Quant.");
						cell.setCellStyle(backgroundBorderStyleCenter);

						cell = row.createCell(cellnum++);
						cell.setCellValue("VALOR");
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("I" + rownum + finalCollumn + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyleCenter);

						// Preenche Segunda Tabela
						for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos
								.getResumoProventosNormal()) {
							row = sheet.createRow(rownum++);
							cellnum = 0;

							cell = row.createCell(cellnum++);
							cell.setCellValue(e.getVerba().getDescricaoVerba());
							cell.setCellStyle(borderStyleLeft);

							cell = row.createCell(cellnum++);
							cell.setCellValue(e.getCountQuantidade().toString());
							cell.setCellStyle(borderStyleRight);

							cell = row.createCell(cellnum++);
							cell.setCellValue(Utils.formatarMoedaReal(e.getValor()));
							cell.setCellStyle(borderStyleRight);
							sheet.addMergedRegion(CellRangeAddress.valueOf("C" + rownum + ":D" + rownum));

							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleRight);

							String quant = "0";
							Double valor = 0.0;
							for (RelatorioFolhaPagamentoResumoProventosDto e2 : dtoResumoProventos
									.getResumoProventosDiferenca()) {
								if (e2.getVerba().getVerbaAssociada().getDescricao()
										.equals(e.getVerba().getDescricaoVerba())) {
									quant = e2.getCountQuantidade().toString();
									valor = e2.getValor();
									break;
								}
							}
							cell = row.createCell(cellnum++);
							cell.setCellValue(quant);
							cell.setCellStyle(borderStyleRight);

							cell = row.createCell(cellnum++);
							cell.setCellValue(Utils.formatarMoedaReal(valor));
							cell.setCellStyle(borderStyleRight);
							sheet.addMergedRegion(CellRangeAddress.valueOf("F" + rownum + ":G" + rownum));

							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleRight);

							String quant2 = "0";
							Double valor2 = 0.0;
							for (RelatorioFolhaPagamentoResumoProventosDto e2 : dtoResumoProventos
									.getResumoProventosDevolucao()) {
								if (e2.getVerba().getVerbaAssociada().getDescricao()
										.equals(e.getVerba().getDescricaoVerba())) {
									quant2 = e2.getCountQuantidade().toString();
									valor2 = e2.getValor();
									break;
								}
							}
							cell = row.createCell(cellnum++);
							cell.setCellValue(quant2);
							cell.setCellStyle(borderStyleRight);

							cell = row.createCell(cellnum++);
							cell.setCellValue(Utils.formatarMoedaReal(valor2));
							cell.setCellStyle(borderStyleRight);
							sheet.addMergedRegion(CellRangeAddress.valueOf("I" + rownum + finalCollumn + rownum));

							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleRight);
						}

						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellValue("RESUMO DOS PROVENTOS E DESCONTOS (TOTALIZADORES E AFINS)");
						cell.setCellStyle(backgroundBorderStyleCenter);
						sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(backgroundBorderStyle);

						sumarizadorTotalProventos(dtoResumoProventos);

						// Monta a tabela no Excel
						for (Entry<String, Double> entry : resumoTotalProventos.entrySet()) {
							row = sheet.createRow(rownum++);
							cellnum = 0;

							cell = row.createCell(cellnum++);
							cell.setCellValue(entry.getKey());
							cell.setCellStyle(borderStyleLeft);
							sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + ":E" + rownum));
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleLeft);
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleLeft);
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleLeft);
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleLeft);
							cell = row.createCell(cellnum++);
							cell.setCellValue(Utils.formatarMoedaReal(entry.getValue()));
							cell.setCellStyle(borderStyleRight);
							sheet.addMergedRegion(CellRangeAddress.valueOf("F" + rownum + finalCollumn + rownum));
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleLeft);
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleLeft);
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleLeft);
							cell = row.createCell(cellnum++);
							cell.setCellStyle(borderStyleRight);

						}
					}
				}
			} else {
				dtoResumoProventos = service.getRelatorioFolhaPagamentoResumoProventos(filialId, filtro,
						situaçãoFuncional);

				if (!dtoResumoProventos.getResumoProventosNormal().isEmpty()) {
					row = sheet.createRow(rownum++);
					row = sheet.createRow(rownum++);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellValue("RESUMO DOS PROVENTOS E DESCONTOS / " + filtro.getTipoProcessamento());
					cell.setCellStyle(backgroundBorderStyleCenter);
					sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);

					row = sheet.createRow(rownum++);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);

					row = sheet.createRow(rownum++);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellValue("ORGÃOS : " + filtro.getFilialString().get(j));
					cell.setCellStyle(backgroundBorderStyle);
					sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);
					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);

					row = sheet.createRow(rownum++);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("NORMAL");
					cell.setCellStyle(backgroundBorderStyleCenter);
					sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + ":D" + rownum));

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("DIFERENÇA");
					cell.setCellStyle(backgroundBorderStyleCenter);
					sheet.addMergedRegion(CellRangeAddress.valueOf("E" + rownum + ":G" + rownum));

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("DEVOLUÇÃO");
					cell.setCellStyle(backgroundBorderStyleCenter);
					sheet.addMergedRegion(CellRangeAddress.valueOf("H" + rownum + finalCollumn + rownum));

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyle);

					row = sheet.createRow(rownum++);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellValue("VERBAS");
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("Quant.");
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("VALOR");
					cell.setCellStyle(backgroundBorderStyleCenter);
					sheet.addMergedRegion(CellRangeAddress.valueOf("C" + rownum + ":D" + rownum));

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("Quant.");
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("VALOR");
					cell.setCellStyle(backgroundBorderStyleCenter);
					sheet.addMergedRegion(CellRangeAddress.valueOf("F" + rownum + ":G" + rownum));

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("Quant.");
					cell.setCellStyle(backgroundBorderStyleCenter);

					cell = row.createCell(cellnum++);
					cell.setCellValue("VALOR");
					cell.setCellStyle(backgroundBorderStyleCenter);
					sheet.addMergedRegion(CellRangeAddress.valueOf("I" + rownum + finalCollumn + rownum));

					cell = row.createCell(cellnum++);
					cell.setCellStyle(backgroundBorderStyleCenter);

					for (RelatorioFolhaPagamentoResumoProventosDto e : dtoResumoProventos.getResumoProventosNormal()) {
						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellValue(e.getVerba().getDescricaoVerba());
						cell.setCellStyle(borderStyleLeft);

						cell = row.createCell(cellnum++);
						cell.setCellValue(e.getCountQuantidade().toString());
						cell.setCellStyle(borderStyleRight);

						cell = row.createCell(cellnum++);
						cell.setCellValue(Utils.formatarMoedaReal(e.getValor()));
						cell.setCellStyle(borderStyleRight);
						sheet.addMergedRegion(CellRangeAddress.valueOf("C" + rownum + ":D" + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleRight);

						String quant = "0";
						Double valor = 0.0;
						for (RelatorioFolhaPagamentoResumoProventosDto e2 : dtoResumoProventos
								.getResumoProventosDiferenca()) {
							if (e2.getVerba().getVerbaAssociada().getDescricao()
									.equals(e.getVerba().getDescricaoVerba())) {
								quant = e2.getCountQuantidade().toString();
								valor = e2.getValor();
								break;
							}
						}
						cell = row.createCell(cellnum++);
						cell.setCellValue(quant);
						cell.setCellStyle(borderStyleRight);

						cell = row.createCell(cellnum++);
						cell.setCellValue(Utils.formatarMoedaReal(valor));
						cell.setCellStyle(borderStyleRight);
						sheet.addMergedRegion(CellRangeAddress.valueOf("F" + rownum + ":G" + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleRight);

						String quant2 = "0";
						Double valor2 = 0.0;
						for (RelatorioFolhaPagamentoResumoProventosDto e2 : dtoResumoProventos
								.getResumoProventosDevolucao()) {
							if (e2.getVerba().getVerbaAssociada().getDescricao()
									.equals(e.getVerba().getDescricaoVerba())) {
								quant2 = e2.getCountQuantidade().toString();
								valor2 = e2.getValor();
								break;
							}
						}
						cell = row.createCell(cellnum++);
						cell.setCellValue(quant2);
						cell.setCellStyle(borderStyleRight);

						cell = row.createCell(cellnum++);
						cell.setCellValue(Utils.formatarMoedaReal(valor2));
						cell.setCellStyle(borderStyleRight);
						sheet.addMergedRegion(CellRangeAddress.valueOf("I" + rownum + finalCollumn + rownum));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleRight);
					}
				}

				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue("RESUMO DOS PROVENTOS E DESCONTOS (TOTALIZADORES E AFINS)");
				cell.setCellStyle(backgroundBorderStyleCenter);
				sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(backgroundBorderStyle);

				if (!dtoResumoProventos.getResumoProventosNormal().isEmpty()) {
					sumarizadorTotalProventos(dtoResumoProventos);

					// Monta a tabela no Excel
					for (Entry<String, Double> entry : resumoTotalProventos.entrySet()) {
						row = sheet.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellValue(entry.getKey());
						cell.setCellStyle(borderStyleLeft);
						sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + ":E" + rownum));
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleLeft);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleLeft);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleLeft);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleLeft);
						cell = row.createCell(cellnum++);
						cell.setCellValue(Utils.formatarMoedaReal(entry.getValue()));
						cell.setCellStyle(borderStyleRight);
						sheet.addMergedRegion(CellRangeAddress.valueOf("F" + rownum + finalCollumn + rownum));
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleLeft);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleLeft);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleLeft);
						cell = row.createCell(cellnum++);
						cell.setCellStyle(borderStyleRight);

					}
				}
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());

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
