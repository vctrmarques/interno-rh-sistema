package com.rhlinkcon.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialFiltroDto;
import com.rhlinkcon.service.EmpresaFilialService;
import com.rhlinkcon.service.FuncionarioService;
import com.rhlinkcon.service.RelatorioGerencialService;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioGerencialExcelReportService {

	@Autowired
	private RelatorioGerencialService service;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaFilialService empresaFilialService;

	public ByteArrayInputStream gerarExcel(RelatorioGerencialFiltroDto filtro) {

		List<RelatorioGerencialDto> lista = service.getRelatorioGerencialList(filtro);
		RelatorioGerencialDto somatorio = service.getRelatorioGerencialSomatorio(filtro);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Relatório Gerencial");

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

		CellStyle borderStyle = workbook.createCellStyle();
		borderStyle.setBorderBottom(BorderStyle.THIN);
		borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		borderStyle.setBorderLeft(BorderStyle.THIN);
		borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		borderStyle.setBorderRight(BorderStyle.THIN);
		borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		borderStyle.setBorderTop(BorderStyle.THIN);
		borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		int rownum = 0;

		String finalCollumn = ":F";

		if (filtro.getAba().equals("resumo")) {
			finalCollumn = ":D";
		} else if (filtro.getAba().equals("filial") || filtro.getAba().equals("funcionario")) {
			finalCollumn = ":G";
		}

		Row row = sheet.createRow(rownum++);
		Cell cell = row.createCell(0);
		cell.setCellValue("RH");
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellValue("Relatório Gerencial");
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellValue(filtro.getAbaLabel());
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Ano da Competência:");
		cell = row.createCell(1);
		cell.setCellValue(filtro.getAnoCompetencia() + ".");
		cell.setCellStyle(borderStyle);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Mês da Competência:");
		cell = row.createCell(1);
		cell.setCellValue(filtro.getMesCompetencia() + ".");
		cell.setCellStyle(borderStyle);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Tipo de Processamento:");
		cell = row.createCell(1);
		cell.setCellValue(filtro.getTipoProcessamento() + ".");
		cell.setCellStyle(borderStyle);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));

		if (Objects.nonNull(filtro.getFilialId())) {
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellStyle(borderBoldStyle);
			cell.setCellValue("Filial:");
			cell = row.createCell(1);
			EmpresaFilialResponse emp = empresaFilialService.getEmpresaFilialById(filtro.getFilialId());
			cell.setCellValue(emp.getNomeFilial() + ".");
			cell.setCellStyle(borderStyle);
			sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));

		}

		if (Utils.checkStr(filtro.getCargoEfetivo())) {
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellStyle(borderBoldStyle);
			cell.setCellValue("Cargo:");
			cell = row.createCell(1);
			cell.setCellValue(filtro.getCargoEfetivo() + ".");
			cell.setCellStyle(borderStyle);
			sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		}

		if (Utils.checkStr(filtro.getCargoFuncao())) {
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellStyle(borderBoldStyle);
			cell.setCellValue("Função:");
			cell = row.createCell(1);
			cell.setCellValue(filtro.getCargoFuncao() + ".");
			cell.setCellStyle(borderStyle);
			sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		}

		if (Objects.nonNull(filtro.getFuncionarioId())) {
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellStyle(borderBoldStyle);
			cell.setCellValue("Funcionário:");
			cell = row.createCell(1);
			FuncionarioResponse func = funcionarioService.getFuncionarioById(filtro.getFuncionarioId());
			cell.setCellValue(func.getNome() + ".");
			cell.setCellStyle(borderStyle);
			sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		}

		if (Utils.checkStr(filtro.getLotacao())) {
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellStyle(borderBoldStyle);
			cell.setCellValue("Lotação:");
			cell = row.createCell(1);
			cell.setCellValue(filtro.getLotacao() + ".");
			cell.setCellStyle(borderStyle);
			sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		}

		if (Utils.checkStr(filtro.getVinculo())) {
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellStyle(borderBoldStyle);
			cell.setCellValue("Vínculo:");
			cell = row.createCell(1);
			cell.setCellValue(filtro.getVinculo() + ".");
			cell.setCellStyle(borderStyle);
			sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownum + finalCollumn + rownum));
		}

		row = sheet.createRow(rownum++);

		if (lista.isEmpty()) {

			// Mensagem de feedback para busca retornada sem dados
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellValue("A busca não retornou dados para os filtros aplicados.");
			sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		} else {

			// Cabeçalho dos dados principais da tabela 2

			if (filtro.getAba().equals("funcionario")) {

				row = sheet.createRow(rownum++);

				int cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue("Nome");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Matrícula");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Lotação");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Situação Atual");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Proventos");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Descontos");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Líquido");
				cell.setCellStyle(backgroundBorderStyle);

				for (RelatorioGerencialDto item : lista) {

					cellnum = 0;

					row = sheet.createRow(rownum++);

					cell = row.createCell(cellnum++);
					cell.setCellValue(item.getFuncionario().getNome());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(item.getFuncionario().getMatricula().toString());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(item.getFuncionario().getLotacao());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(item.getFuncionario().getTipoSituacaoFuncional());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(Utils.formatarMoedaReal(item.getTotalProvento()));
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(Utils.formatarMoedaReal(item.getTotalDesconto()));
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(Utils.formatarMoedaReal(item.getTotalLiquido()));
					cell.setCellStyle(borderStyle);

				}

				cellnum = 4;

				row = sheet.createRow(rownum++);

				cell = row.createCell(cellnum++);
				cell.setCellValue(Utils.formatarMoedaReal(somatorio.getTotalProvento()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(Utils.formatarMoedaReal(somatorio.getTotalDesconto()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(Utils.formatarMoedaReal(somatorio.getTotalLiquido()));

			} else if (filtro.getAba().equals("resumo")) {

				row = sheet.createRow(rownum++);

				int cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue("Código");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Descrição");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Qtd. Funcionários");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Valor");
				cell.setCellStyle(backgroundBorderStyle);

				Double vantagens = 0.0;
				for (RelatorioGerencialDto item : lista) {

					if (item.getTipoVerba().equals("VANTAGEM")) {

						cellnum = 0;

						row = sheet.createRow(rownum++);

						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getCodigoVerba());
						cell.setCellStyle(borderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getDescricaoVerba());
						cell.setCellStyle(borderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellValue(String.valueOf(item.getQtdFuncionario()));
						cell.setCellStyle(borderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellValue(Utils.formatarMoedaReal(item.getValor()));
						cell.setCellStyle(borderStyle);

						vantagens += item.getValor() != null ? item.getValor() : 0.0;

					}

				}

				row = sheet.createRow(rownum++);

				cell = row.createCell(1);
				cell.setCellValue("TOTAL DE VANTAGENS");

				cell = row.createCell(3);
				cell.setCellValue(Utils.formatarMoedaReal(vantagens));

				Double descontos = 0.0;
				for (RelatorioGerencialDto item : lista) {

					if (item.getTipoVerba().equals("DESCONTO")) {

						cellnum = 0;

						row = sheet.createRow(rownum++);

						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getCodigoVerba());
						cell.setCellStyle(borderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getDescricaoVerba());
						cell.setCellStyle(borderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellValue(String.valueOf(item.getQtdFuncionario()));
						cell.setCellStyle(borderStyle);

						cell = row.createCell(cellnum++);
						cell.setCellValue(Utils.formatarMoedaReal(item.getValor()));
						cell.setCellStyle(borderStyle);

						descontos += item.getValor() != null ? item.getValor() : 0.0;
					}

				}

				row = sheet.createRow(rownum++);

				cell = row.createCell(1);
				cell.setCellValue("TOTAL DE DESCONTOS");

				cell = row.createCell(3);
				cell.setCellValue(Utils.formatarMoedaReal(descontos));

				row = sheet.createRow(rownum++);

				cell = row.createCell(1);
				cell.setCellValue("TOTAL LÍQUIDO");

				cell = row.createCell(3);
				cell.setCellValue(Utils.formatarMoedaReal(vantagens - descontos));

			} else {

				row = sheet.createRow(rownum++);

				int cellnum = 0;

				if (filtro.getAba().equals("filial")) {
					cell = row.createCell(cellnum++);
					cell.setCellValue("Código");
					cell.setCellStyle(backgroundBorderStyle);
				}

				cell = row.createCell(cellnum++);
				cell.setCellValue("Descrição");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Qtd. Funcionários");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Total Proventos");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Total Descontos");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Total Líquido");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue("Valor Médio");
				cell.setCellStyle(backgroundBorderStyle);

				for (RelatorioGerencialDto item : lista) {
					cellnum = 0;

					row = sheet.createRow(rownum++);

					if (filtro.getAba().equals("filial")) {
						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getTipo().getId().toString());
						cell.setCellStyle(borderStyle);
					}

					cell = row.createCell(cellnum++);
					cell.setCellValue(item.getTipo().getDescricao());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(item.getQtdFuncionario()));
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(Utils.formatarMoedaReal(item.getTotalProvento()));
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(Utils.formatarMoedaReal(item.getTotalDesconto()));
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(Utils.formatarMoedaReal(item.getTotalLiquido()));
					cell.setCellStyle(borderStyle);

					cell = row.createCell(cellnum++);
					cell.setCellValue(Utils.formatarMoedaReal(item.getValorMedio()));
					cell.setCellStyle(borderStyle);

				}

				row = sheet.createRow(rownum++);
				cellnum = 0;

				if (filtro.getAba().equals("filial")) {
					cell = row.createCell(cellnum++);
				}

				cell = row.createCell(cellnum++);

				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(somatorio.getQtdFuncionario()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(Utils.formatarMoedaReal(somatorio.getTotalProvento()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(Utils.formatarMoedaReal(somatorio.getTotalDesconto()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(Utils.formatarMoedaReal(somatorio.getTotalLiquido()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(Utils.formatarMoedaReal(somatorio.getValorMedio()));

			}

		}

		row = sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);

		// Mensagem de feedback para busca retornada sem dados
		SimpleDateFormat formato = new SimpleDateFormat("'ás' HH:mm 'do dia' dd/MM/yyyy");
		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellValue("Gerado por: " + Utils.getUsuarioLogado() + " " + formato.format(new Date()));
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownum + finalCollumn + rownum));

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);

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

}
