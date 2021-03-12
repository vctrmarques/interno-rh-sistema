package com.rhlinkcon.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

public interface CrudPdfReportService<T> {

	void personalizarDocumento(PdfWriter writer, Document document, T valor) throws DocumentException;

	default ByteArrayInputStream gerarPdf(T valor) {

		Document document = new Document(PageSize.A4, 15, 15, 15, 15);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPageEvent(new HeaderFooterPageEvent());
			document.open();
			personalizarDocumento(writer, document, valor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	class HeaderFooterPageEvent extends PdfPageEventHelper {
		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			super.onStartPage(writer, document);
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			super.onEndPage(writer, document);
		}
	}

	/*
	 * Adiciona informações nas propriedades do arquivo
	 */

	default void addAtributos(Document document, String titulo, String subTitulo) {
		document.addAuthor(Utils.getUsuarioLogado());
		document.addCreationDate();
		document.addCreator("Sistema de Administração de Recursos Humanos");
		document.addTitle(titulo);
		document.addSubject(subTitulo);

	}

	default void addDefaultLogoOnTop(Document document) {
		try {
			Resource resource = new ClassPathResource(Constantes.BRASAO);
			Image imagem = Image.getInstance(resource.getURL());
			imagem.setAlignment(Image.ALIGN_CENTER);
			imagem.scalePercent(35);

			document.add(imagem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	default void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	default PdfPCell celulaUpper(String texto, int alinhamento) {
		Phrase valor;
		if (Objects.nonNull(texto)) {
			valor = new Phrase(0, texto.toUpperCase(), Constantes.TIMES9);
		} else {
			valor = new Phrase(0, " ", Constantes.TIMES9);
		}
		PdfPCell c = new PdfPCell(valor);
		c.setHorizontalAlignment(alinhamento);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(PdfCell.NO_BORDER);
		c.setPaddingBottom(4);
		c.setPaddingTop(4);
		c.setPaddingLeft(4);
		c.setPaddingRight(4);
		return c;
	}

	default PdfPCell celula(String texto, int alinhamento) {
		Phrase valor;
		if (Objects.nonNull(texto)) {
			valor = new Phrase(0, texto.toUpperCase(), Constantes.TIMES9);
		} else {
			valor = new Phrase(0, " ", Constantes.TIMES9);
		}
		PdfPCell c = new PdfPCell(valor);
		c.setHorizontalAlignment(alinhamento);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(PdfCell.NO_BORDER);
		c.setPaddingBottom(4);
		c.setPaddingTop(4);
		c.setPaddingLeft(4);
		c.setPaddingRight(4);
		return c;
	}

	default String dataAtual(String valor) {
		if (Objects.isNull(valor))
			return "";
		SimpleDateFormat formato = new SimpleDateFormat(valor);
		return formato.format(new Date());
	}

	default String data(String valor, Date data) {
		if (Objects.isNull(valor) || Objects.isNull(data))
			return "";
		SimpleDateFormat formato = new SimpleDateFormat(valor);
		return formato.format(data);
	}

}
