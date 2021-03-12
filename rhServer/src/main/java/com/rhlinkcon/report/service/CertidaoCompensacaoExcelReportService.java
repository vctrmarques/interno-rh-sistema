package com.rhlinkcon.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.rhlinkcon.filtro.CertidaoCompensacaoFiltro;
import com.rhlinkcon.model.CertidaoCompensacao;
import com.rhlinkcon.model.StatusCertidaoCompensacaoEnum;
import com.rhlinkcon.repository.certidaoCompensacao.CertidaoCompensacaoRepository;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class CertidaoCompensacaoExcelReportService {

	@Autowired
	private CertidaoCompensacaoRepository repository;

	public ByteArrayInputStream gerarExcel(CertidaoCompensacaoFiltro filtro) {

		List<CertidaoCompensacao> lista = repository.filtro(filtro);

		Map<StatusCertidaoCompensacaoEnum, List<CertidaoCompensacao>> listaMap = new HashMap<>();

		// Carrega um hashMap da lista de certidões por status para serem usados no
		// relatório.
		for (CertidaoCompensacao certidaoCompensacao : lista) {
			if (listaMap.containsKey(certidaoCompensacao.getStatusAtual())) {
				listaMap.get(certidaoCompensacao.getStatusAtual()).add(certidaoCompensacao);
			} else {
				List<CertidaoCompensacao> listaByStatus = new ArrayList<CertidaoCompensacao>();
				listaByStatus.add(certidaoCompensacao);
				listaMap.put(certidaoCompensacao.getStatusAtual(), listaByStatus);
			}
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Certidão Compensação por Status");

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

		int rownumStatus = 10;

		Row row = sheet.createRow(rownumStatus++);
		Cell cell = row.createCell(0);
		cell.setCellValue("RH");
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownumStatus + ":E" + rownumStatus));

		row = sheet.createRow(rownumStatus++);

		row = sheet.createRow(rownumStatus++);
		cell = row.createCell(0);
		cell.setCellValue("CTC - Compensação");
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownumStatus + ":E" + rownumStatus));

		row = sheet.createRow(rownumStatus++);
		cell = row.createCell(0);
		cell.setCellValue("(Relatório por Status)");
		cell.setCellStyle(styleCenterBold);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownumStatus + ":E" + rownumStatus));

		row = sheet.createRow(rownumStatus++);

		row = sheet.createRow(rownumStatus++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Status:");

		String statusListStr = "";
		if (Objects.nonNull(filtro.getStatusList()) && !filtro.getStatusList().isEmpty()) {
			for (String status : filtro.getStatusList()) {
				if (statusListStr.isEmpty()) {
					statusListStr = StatusCertidaoCompensacaoEnum.valueOf(status).getLabel();
				} else {
					statusListStr += ", " + StatusCertidaoCompensacaoEnum.valueOf(status).getLabel();
				}
			}
		} else {
			statusListStr = "Todos";
		}
		cell = row.createCell(1);
		cell.setCellValue(statusListStr + ".");
		cell.setCellStyle(borderStyle);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownumStatus + ":E" + rownumStatus));

		row = sheet.createRow(rownumStatus++);
		cell = row.createCell(0);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Períodos:");

		String periodoStr = "";
		if (Objects.nonNull(filtro.getDataInicialStr())) {
			periodoStr += filtro.getDataInicialStr() + " à ";
		} else {
			periodoStr += "Não definido à ";
		}
		if (Objects.nonNull(filtro.getDataFinalStr())) {
			periodoStr += filtro.getDataFinalStr();
		} else {
			periodoStr += "Não definido";
		}

		cell = row.createCell(1);
		cell.setCellValue(periodoStr + ".");
		cell.setCellStyle(borderStyle);
		sheet.addMergedRegion(CellRangeAddress.valueOf("B" + rownumStatus + ":C" + rownumStatus));

		cell = row.createCell(3);
		cell.setCellStyle(borderBoldStyle);
		cell.setCellValue("Fundos:");

		String fundoListStr = "";
		if (Objects.nonNull(filtro.getFundoList()) && !filtro.getFundoList().isEmpty()) {
			for (String fundo : filtro.getFundoList()) {
				if (fundoListStr.isEmpty()) {
					fundoListStr += fundo;
				} else {
					fundoListStr += ", " + fundo;
				}
			}
		} else {
			fundoListStr = "Todos";
		}
		cell = row.createCell(4);
		cell.setCellValue(fundoListStr + ".");
		cell.setCellStyle(borderStyle);

		row = sheet.createRow(rownumStatus++);

		if (listaMap.isEmpty()) {

			// Mensagem de feedback para busca retornada sem dados
			row = sheet.createRow(rownumStatus++);
			cell = row.createCell(0);
			cell.setCellValue("A busca não retornou dados para os filtros aplicados.");
			sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownumStatus + ":E" + rownumStatus));

		} else {

			// Iterando no hasMap de CTC.

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			for (Map.Entry<StatusCertidaoCompensacaoEnum, List<CertidaoCompensacao>> entrada : listaMap.entrySet()) {

				int sizeList = entrada.getValue().size();
				String labelKey = entrada.getKey().getLabel();

				// Célula de Cabeçalho da Tabela 2
				row = sheet.createRow(rownumStatus++);
				cell = row.createCell(0);
				cell.setCellValue(labelKey + " (" + sizeList + ")");
				cell.setCellStyle(borderBoldStyle);
				sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownumStatus + ":E" + rownumStatus));

				// Cabeçalho dos dados principais da tabela 2

				row = sheet.createRow(rownumStatus++);

				cell = row.createCell(0);
				cell.setCellValue("PIS / PASEP");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(1);
				cell.setCellValue("Matrícula");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(2);
				cell.setCellValue("Funcionário");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(3);
				cell.setCellValue("Nº do Processo");
				cell.setCellStyle(backgroundBorderStyle);

				cell = row.createCell(4);
				cell.setCellValue("Data do Status");
				cell.setCellStyle(backgroundBorderStyle);

				for (CertidaoCompensacao ctcCompensacao : entrada.getValue()) {

					row = sheet.createRow(rownumStatus++);

					cell = row.createCell(0);
					cell.setCellValue(ctcCompensacao.getFuncionario().getPisPasep());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(1);
					cell.setCellValue(ctcCompensacao.getFuncionario().getMatricula().toString());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(2);
					cell.setCellValue(ctcCompensacao.getFuncionario().getNome());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(3);
					cell.setCellValue(ctcCompensacao.getProcesso());
					cell.setCellStyle(borderStyle);

					cell = row.createCell(4);
					String dateStatus = "";
					if (Objects.nonNull(Date.from(ctcCompensacao.getUpdatedAt())))
						dateStatus = formato.format(Date.from(ctcCompensacao.getUpdatedAt()));
					cell.setCellValue(dateStatus);
					cell.setCellStyle(borderStyle);
				}

				row = sheet.createRow(rownumStatus++);

			}
		}

		row = sheet.createRow(rownumStatus++);
		row = sheet.createRow(rownumStatus++);
		row = sheet.createRow(rownumStatus++);

		// Mensagem de feedback para busca retornada sem dados
		SimpleDateFormat formato = new SimpleDateFormat("'ás' HH:mm 'do dia' dd/MM/yyyy");
		row = sheet.createRow(rownumStatus++);
		cell = row.createCell(0);
		cell.setCellValue("Gerado por: " + Utils.getUsuarioLogado() + " " + formato.format(new Date()));
		sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rownumStatus + ":E" + rownumStatus));

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);

		try {
			// Resource file
			Resource resource = new ClassPathResource(Constantes.BRASAO);
			// FileInputStream obtains input bytes from the image file
			InputStream inputStream = resource.getInputStream();
			// Get the contents of an InputStream as a byte[].
			byte[] bytes = IOUtils.toByteArray(inputStream);
			// Adds a picture to the workbook
			int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			// close the input stream
			inputStream.close();

			// Returns an object that handles instantiating concrete classes
			CreationHelper helper = workbook.getCreationHelper();

			// Creates the top-level drawing patriarch.
			Drawing drawing = sheet.createDrawingPatriarch();

			// Create an anchor that is attached to the worksheet
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner for the image
			anchor.setCol1(2);
			anchor.setDx1(240);

			// Creates a picture
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			// Reset the image to the original size
			pict.resize();
		} catch (Exception e) {
			// TODO: handle exception
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

}
