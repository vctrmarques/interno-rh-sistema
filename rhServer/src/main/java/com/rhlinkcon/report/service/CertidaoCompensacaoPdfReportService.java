package com.rhlinkcon.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.rhlinkcon.filtro.CertidaoCompensacaoFiltro;
import com.rhlinkcon.model.CertidaoCompensacao;
import com.rhlinkcon.model.StatusCertidaoCompensacaoEnum;
import com.rhlinkcon.repository.certidaoCompensacao.CertidaoCompensacaoRepository;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class CertidaoCompensacaoPdfReportService {

	@Autowired
	private CertidaoCompensacaoRepository repository;

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

	public ByteArrayInputStream gerarPdf(CertidaoCompensacaoFiltro filtro) {

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

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPageEvent(new Footer());

			document.open();
			addContent(document, filtro, listaMap);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	private static void addContent(Document document, CertidaoCompensacaoFiltro filtro,
			Map<StatusCertidaoCompensacaoEnum, List<CertidaoCompensacao>> listaMap) throws DocumentException {

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

		Paragraph ctcComp = new Paragraph("CTC - Compensação", Constantes.TIMES12BOLD);
		ctcComp.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(ctcComp);

		Paragraph relatStat = new Paragraph("(Relatório por Status)", Constantes.TIMES12BOLD);
		relatStat.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(relatStat);

		addEmptyLine(paragrafo, 1);

		document.add(paragrafo);

		String statusListStr = "";
		if (Objects.nonNull(filtro.getStatusList()) && !filtro.getStatusList().isEmpty()) {
			for (String status : filtro.getStatusList()) {
				if (statusListStr.isEmpty()) {
					statusListStr = " " + StatusCertidaoCompensacaoEnum.valueOf(status).getLabel();
				} else {
					statusListStr += ", " + StatusCertidaoCompensacaoEnum.valueOf(status).getLabel();
				}
			}
		} else {
			statusListStr = " Todos";
		}

		Phrase statusPhrase = new Phrase();
		statusPhrase.add(new Phrase("Status:", Constantes.TIMES10BOLD));
		statusPhrase.add(new Phrase(statusListStr + ".", Constantes.TIMES10));

		// Célula Status da Tabela 1
		PdfPCell statusCell = new PdfPCell();
		statusCell.setPhrase(statusPhrase);
		statusCell.setColspan(2);
		statusCell.setPaddingBottom(4);
		statusCell.setPaddingLeft(4);

		// Tabela 1
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		table1.addCell(statusCell);

		String periodoStr = " ";
		if (Objects.nonNull(filtro.getDataInicialStr())) {
			periodoStr += filtro.getDataInicialStr() + " à ";
		} else {
			periodoStr += "Não definido à ";
		}
		if (Objects.nonNull(filtro.getDataFinalStr())) {
			periodoStr += filtro.getDataFinalStr() + ".";
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

		String fundoListStr = "";
		if (Objects.nonNull(filtro.getFundoList()) && !filtro.getFundoList().isEmpty()) {
			for (String fundo : filtro.getFundoList()) {
				if (fundoListStr.isEmpty()) {
					fundoListStr = " " + fundo;
				} else {
					fundoListStr += ", " + fundo;
				}
			}
		} else {
			fundoListStr = " Todos";
		}

		Phrase fundosPhrase = new Phrase();
		fundosPhrase.add(new Phrase("Fundos:", Constantes.TIMES10BOLD));
		fundosPhrase.add(new Phrase(fundoListStr + ".", Constantes.TIMES10));

		// Célula Fundo da Tabela 1
		PdfPCell fundoCell = new PdfPCell();
		fundoCell.setPhrase(fundosPhrase);
		fundoCell.setPaddingBottom(4);
		fundoCell.setPaddingLeft(4);

		table1.addCell(fundoCell);

		// Adição da Tabela 1
		document.add(table1);

		document.add(new Paragraph(" "));

		if (listaMap.isEmpty()) {

			// Mensagem de feedback para busca retornada sem dados
			document.add(new Phrase("A busca não retornou dados para os filtros aplicados."));

		} else {

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			// Iterando no hasMap de CTC.
			for (Map.Entry<StatusCertidaoCompensacaoEnum, List<CertidaoCompensacao>> entrada : listaMap.entrySet()) {

				int sizeList = entrada.getValue().size();
				String labelKey = entrada.getKey().getLabel();

				// Célula de Cabeçalho da Tabela 2
				PdfPCell statusCell2 = new PdfPCell();
				statusCell2.setPhrase(new Phrase(labelKey + " (" + sizeList + ")", Constantes.TIMES10BOLD));
				statusCell2.setPaddingBottom(4);
				statusCell2.setPaddingLeft(4);
				statusCell2.setColspan(5);

				// Tabela 2 - Tabela agrupada por Status
				PdfPTable table2 = new PdfPTable(5);
				table2.setWidthPercentage(100);
				table2.addCell(statusCell2);
				table2.setWidths(new float[] { 2, 2, 4, 2, 2 });

				// Cabeçalho dos dados principais da tabela 2
				Stream.of("PIS / PASEP", "Matrícula", "Funcionário", "Nº do Processo", "Data do Status")
						.forEach(columnTitle -> {
							PdfPCell header = new PdfPCell();
							header.setBackgroundColor(BaseColor.LIGHT_GRAY);
							header.setPaddingBottom(4);
							header.setPaddingLeft(4);
							header.setPhrase(new Phrase(columnTitle, Constantes.TIMES10));
							table2.addCell(header);
						});

				// Celula de dados da tabela 2
				PdfPCell dataCell = new PdfPCell();
				dataCell.setPaddingBottom(4);
				dataCell.setPaddingLeft(4);

				for (CertidaoCompensacao ctcCompensacao : entrada.getValue()) {

					dataCell.setPhrase(new Phrase(ctcCompensacao.getFuncionario().getPisPasep(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(
							new Phrase(ctcCompensacao.getFuncionario().getMatricula().toString(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(ctcCompensacao.getFuncionario().getNome(), Constantes.TIMES10));
					table2.addCell(dataCell);

					dataCell.setPhrase(new Phrase(ctcCompensacao.getProcesso(), Constantes.TIMES10));
					table2.addCell(dataCell);

					String dateStatus = "";
					if (Objects.nonNull(Date.from(ctcCompensacao.getUpdatedAt())))
						dateStatus = formato.format(Date.from(ctcCompensacao.getUpdatedAt()));

					dataCell.setPhrase(new Phrase(dateStatus, Constantes.TIMES10));
					table2.addCell(dataCell);

				}

				// Adição da Tabela 2
				document.add(table2);

				document.add(new Paragraph(" "));

			}
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}