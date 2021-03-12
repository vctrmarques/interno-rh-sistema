package com.rhlinkcon.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.rhlinkcon.audit.AuditoriaDescricaoItemDto;
import com.rhlinkcon.filtro.AuditoriaFiltroRequest;
import com.rhlinkcon.payload.auditoria.AuditoriaResponse;
import com.rhlinkcon.service.AuditoriaService;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class AuditoriaPdfReportService {

	@Autowired
	private AuditoriaService service;

	class Footer extends PdfPageEventHelper {
		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();

			SimpleDateFormat formato = new SimpleDateFormat("'ás' HH:mm 'do dia' dd/MM/yyyy");
			Phrase footer = new Phrase("Gerado por: " + Utils.getUsuarioLogado() + " " + formato.format(new Date()),
					Constantes.TIMES10);

			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		}
	}

	public ByteArrayInputStream gerarPdf(String order, AuditoriaFiltroRequest filtro) {

		List<AuditoriaResponse> lista = service.get(order, filtro);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPageEvent(new Footer());

			document.open();
			addContent(document, lista, filtro);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	private static void addContent(Document document, List<AuditoriaResponse> lista, AuditoriaFiltroRequest filtro)
			throws DocumentException {

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

		Paragraph relatStat = new Paragraph("Relatório de Auditoria", Constantes.TIMES12BOLD);
		relatStat.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(relatStat);

		addEmptyLine(paragrafo, 1);

		document.add(paragrafo);

		String entityListStr = "";
		if (Objects.nonNull(filtro.getEntidadeNomeList()) && !filtro.getEntidadeNomeList().isEmpty()) {
			for (String entityName : filtro.getEntidadeNomeList()) {
				try {
					Class<?> aClass = Class.forName(entityName);
					for (Annotation annotation : aClass.getDeclaredAnnotations()) {
						if (annotation instanceof AuditLabelClass) {
							AuditLabelClass auditLabelClass = (AuditLabelClass) annotation;
							if (entityListStr.isEmpty()) {
								entityListStr = " " + auditLabelClass.label();
							} else {
								entityListStr += ", " + auditLabelClass.label();
							}
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} else {
			entityListStr = " Todas";
		}

		Phrase entitiesPhrase = new Phrase();
		entitiesPhrase.add(new Phrase("Tabelas:", Constantes.TIMES10BOLD));
		entitiesPhrase.add(new Phrase(entityListStr + ".", Constantes.TIMES10));

		// Célula Tabelas da Tabela 1
		PdfPCell entitiesCell = new PdfPCell();
		entitiesCell.setPhrase(entitiesPhrase);
		entitiesCell.setColspan(2);
		entitiesCell.setPaddingBottom(4);
		entitiesCell.setPaddingLeft(4);

		// Tabela 1
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		table1.addCell(entitiesCell);

		String periodoStr = " ";
		if (Objects.nonNull(filtro.getPeriodoInicialStr())) {
			periodoStr += filtro.getPeriodoInicialStr() + " à ";
		} else {
			periodoStr += "Não definido à ";
		}
		if (Objects.nonNull(filtro.getPeriodoFinalStr())) {
			periodoStr += filtro.getPeriodoFinalStr() + ".";
		} else {
			periodoStr += "Não definido.";
		}

		Phrase periodoPhrase = new Phrase();
		periodoPhrase.add(new Phrase("Períodos:", Constantes.TIMES10BOLD));
		periodoPhrase.add(new Phrase(periodoStr, Constantes.TIMES10));

		// Célula Período da Tabela 1
		PdfPCell periodoCell = new PdfPCell();
		periodoCell.setPhrase(periodoPhrase);
		periodoCell.setPaddingBottom(4);
		periodoCell.setPaddingLeft(4);

		table1.addCell(periodoCell);

		String usuarioStr = "";
		if (Utils.checkStr(filtro.getNome())) {
			usuarioStr = " " + filtro.getNome();
		} else {
			usuarioStr = " Todos";
		}

		Phrase usuarioPhrase = new Phrase();
		usuarioPhrase.add(new Phrase("Usuário:", Constantes.TIMES10BOLD));
		usuarioPhrase.add(new Phrase(usuarioStr + ".", Constantes.TIMES10));

		// Célula Fundo da Tabela 1
		PdfPCell usuarioCell = new PdfPCell();
		usuarioCell.setPhrase(usuarioPhrase);
		usuarioCell.setPaddingBottom(4);
		usuarioCell.setPaddingLeft(4);

		table1.addCell(usuarioCell);

		// Adição da Tabela 1
		document.add(table1);

		document.add(new Paragraph(" "));

		if (lista.isEmpty()) {

			// Mensagem de feedback para busca retornada sem dados
			document.add(new Phrase("A busca não retornou dados para os filtros aplicados."));

		} else {

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			// Tabela 2 - Tabela agrupada por Status
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100);
//			table2.setWidths(new float[] { 2, 2, 4, 2, 2 });

			// Cabeçalho dos dados principais da tabela 2
			Stream.of("Usuário", "Operação", "Tabela", "Data - Hora").forEach(columnTitle -> {
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setPaddingBottom(4);
				header.setPaddingLeft(4);
				header.setPhrase(new Phrase(columnTitle, Constantes.TIMES10));
				table2.addCell(header);
			});

			// Iterando a lista de resultado da busca
			for (AuditoriaResponse auditoria : lista) {

				// Celula de dados da tabela 2
				PdfPCell dataCell = new PdfPCell();
				dataCell.setPaddingBottom(4);
				dataCell.setPaddingLeft(4);

				dataCell.setPhrase(new Phrase(auditoria.getCriadoPor(), Constantes.TIMES10));
				table2.addCell(dataCell);

				dataCell.setPhrase(new Phrase(auditoria.getAcao(), Constantes.TIMES10));
				table2.addCell(dataCell);

				dataCell.setPhrase(new Phrase(auditoria.getEntidade().getLabel(), Constantes.TIMES10));
				table2.addCell(dataCell);

				String dateStatus = "";
				if (Objects.nonNull(Date.from(auditoria.getCriadoEm())))
					dateStatus = formato.format(Date.from(auditoria.getCriadoEm()));

				dataCell.setPhrase(new Phrase(dateStatus, Constantes.TIMES10));
				table2.addCell(dataCell);

				String auditoriaDescricaoStr = "";
				for (AuditoriaDescricaoItemDto item : auditoria.getAuditoriaDescricaoDto().getItemList()) {
					auditoriaDescricaoStr += item.getCampo() + ": ";

					if (item.getValor() instanceof List) {

						List valorList = (List) item.getValor();
						for (Object itemValor : valorList) {
							auditoriaDescricaoStr += itemValor.toString() + " - ";
						}
						auditoriaDescricaoStr = auditoriaDescricaoStr.substring(0, auditoriaDescricaoStr.length() - 3);
						auditoriaDescricaoStr += ", ";
					} else {
						auditoriaDescricaoStr += item.getValor() + ", ";
					}
				}
				auditoriaDescricaoStr = auditoriaDescricaoStr.substring(0, auditoriaDescricaoStr.length() - 2);
				auditoriaDescricaoStr += ".";

				// Celula de dados da tabela 2
				PdfPCell dataDescricaoCell = new PdfPCell();
				dataDescricaoCell.setPhrase(new Phrase(auditoriaDescricaoStr, Constantes.TIMES10));
				dataDescricaoCell.setPaddingBottom(4);
				dataDescricaoCell.setPaddingLeft(4);
				dataDescricaoCell.setColspan(4);
				table2.addCell(dataDescricaoCell);

			}
			// Adição da Tabela 2
			document.add(table2);

			document.add(new Paragraph(" "));
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
