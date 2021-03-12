package com.rhlinkcon.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.rhlinkcon.filtro.ContrachequeFiltro;
import com.rhlinkcon.payload.contracheque.ContrachequeResponse;
import com.rhlinkcon.payload.lancamento.LancamentoResponse;
import com.rhlinkcon.service.ContrachequeService;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class ContrachequePdfReportService {

	@Autowired
	private ContrachequeService service;

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

	public ByteArrayInputStream gerarPdf(ContrachequeFiltro filtro) {

		List<ContrachequeResponse> list = service.listaTodosPorFolhaIdNaoPaginado(filtro);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		document.addTitle("Folha de pagamento");
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			Font fontTitle = new Font(Font.FontFamily.HELVETICA, 12);
			Font labelFont = new Font(Font.FontFamily.HELVETICA, 8);
			Font valueFont = new Font(Font.FontFamily.HELVETICA, 9);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

			Image imagem = Image.getInstance(
					getClass().getClassLoader().getResource("/image/brasao_prefeitura_goiania_prev_vert.png"));
			imagem.setAlignment(Image.LEFT | Image.TEXTWRAP);
			for (ContrachequeResponse contrachequeResponse : list) {
				montarCabecalhoContracheque(document, contrachequeResponse, simpleDateFormat, labelFont, valueFont,
						fontTitle, imagem);
				document.add(new Paragraph(" "));
				montarTabelaRubricas(document, contrachequeResponse, simpleDateFormat, labelFont, valueFont, fontTitle);
				document.add(new Paragraph(" "));
				montarFooterContracheque(document, contrachequeResponse, simpleDateFormat, labelFont, valueFont,
						fontTitle);
				document.newPage();
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());

	}

	public Document montarCabecalhoContracheque(Document document, ContrachequeResponse contrachequeResponse,
			SimpleDateFormat simpleDateFormat, Font labelFont, Font valueFont, Font fontTitle, Image imagem)
			throws DocumentException, MalformedURLException, IOException {
		// MONTAGEM DO CABEÇALHO DO ÓRGÃO

		PdfPTable table;
		PdfPCell hcell;

		table = new PdfPTable(2);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 2, 8 });

		hcell = new PdfPCell();
		hcell.addElement(imagem);
		hcell.setBorder(Rectangle.NO_BORDER);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraph = new Paragraph("INST.DE PREV.DOS SERV. DO MUNIC.DE GOIANIA - GOIANIAPREV", fontTitle);
		hcell.addElement(paragraph);

		paragraph = new Paragraph(contrachequeResponse.getOrgaoPagador().toUpperCase(), fontTitle);
		hcell.addElement(paragraph);
		paragraph = new Paragraph(Utils.formatarCnpj(contrachequeResponse.getFolhaPagamento().getFilial().getCnpj()),
				fontTitle);
		hcell.addElement(paragraph);
		hcell.setBorder(Rectangle.NO_BORDER);
		table.addCell(hcell);
		document.add(table);

		paragraph = new Paragraph("");
		document.add(paragraph);

		paragraph = new Paragraph("Demonstrativo de Pagamento", fontTitle);
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.SPACETABBING);

		// MONTAGEM DA TABELA INICIAL
		table = new PdfPTable(2);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 8, 2 });

		hcell = new PdfPCell();
		Paragraph paragraphNome = new Paragraph("1. Nome do Servidor", labelFont);
		paragraphNome.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphNome);
		paragraphNome = new Paragraph(contrachequeResponse.getNome(), valueFont);
		paragraphNome.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphNome);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphMatricula = new Paragraph("2. Matrícula - Dig", labelFont);
		paragraphMatricula.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphMatricula);
		paragraphMatricula = new Paragraph(Objects.nonNull(contrachequeResponse.getMatricula().toString())
				? contrachequeResponse.getMatricula().toString()
				: "", valueFont);
		paragraphMatricula.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphMatricula);
		table.addCell(hcell);

		document.add(table);

		table = new PdfPTable(3);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 4, 4, 2 });

		hcell = new PdfPCell();
		Paragraph paragraphLocalTrabalho = new Paragraph("4. Local de Trabalho", labelFont);
		paragraphLocalTrabalho.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphLocalTrabalho);
		paragraphLocalTrabalho = new Paragraph(
				Objects.nonNull(contrachequeResponse.getLotacao()) ? contrachequeResponse.getLotacao() : "", valueFont);
		paragraphLocalTrabalho.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphLocalTrabalho);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphMunicipio = new Paragraph("5. Município", labelFont);
		paragraphMunicipio.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphMunicipio);
		paragraphMunicipio = new Paragraph(
				Objects.nonNull(contrachequeResponse.getMunicipio()) ? contrachequeResponse.getMunicipio() : "",
				valueFont);
		paragraphMunicipio.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphMunicipio);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphDtAdmissao = new Paragraph("6. Data Admissão", labelFont);
		paragraphDtAdmissao.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphDtAdmissao);
		paragraphDtAdmissao = new Paragraph(Objects.nonNull(contrachequeResponse.getDataAdmissao())
				? simpleDateFormat.format(Date.from(contrachequeResponse.getDataAdmissao()))
				: "", valueFont);
		paragraphDtAdmissao.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphDtAdmissao);
		table.addCell(hcell);

		document.add(table);

		table = new PdfPTable(2);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 8, 2 });

		hcell = new PdfPCell();
		Paragraph paragraphCargoEfetivo = new Paragraph("7. Cargo Efetivo", labelFont);
		paragraphCargoEfetivo.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphCargoEfetivo);
		paragraphCargoEfetivo = new Paragraph(
				Objects.nonNull(contrachequeResponse.getCargoEfetivo()) ? contrachequeResponse.getCargoEfetivo() : "",
				valueFont);
		paragraphCargoEfetivo.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphCargoEfetivo);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphDtNascimento = new Paragraph("8. Data Nascimento", labelFont);
		paragraphDtNascimento.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphDtNascimento);
		paragraphDtNascimento = new Paragraph(Objects.nonNull(contrachequeResponse.getDataNascimento())
				? simpleDateFormat.format(Date.from(contrachequeResponse.getDataNascimento()))
				: "", valueFont);
		paragraphDtNascimento.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphDtNascimento);
		table.addCell(hcell);

		document.add(table);

		table = new PdfPTable(4);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 4, 2, 2, 2 });

		hcell = new PdfPCell();
		Paragraph paragraphVinculo = new Paragraph("12. Vínculo", labelFont);
		paragraphVinculo.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphVinculo);
		paragraphVinculo = new Paragraph(
				Objects.nonNull(contrachequeResponse.getVinculo()) ? contrachequeResponse.getVinculo() : "", valueFont);
		paragraphVinculo.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphVinculo);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphSituacao = new Paragraph("13. Situação", labelFont);
		paragraphSituacao.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphSituacao);
		paragraphSituacao = new Paragraph(
				Objects.nonNull(contrachequeResponse.getSituacao()) ? contrachequeResponse.getSituacao() : "",
				valueFont);
		paragraphSituacao.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphSituacao);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphIdentidade = new Paragraph("14. Identidade", labelFont);
		paragraphIdentidade.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphIdentidade);
		paragraphIdentidade = new Paragraph(
				Objects.nonNull(contrachequeResponse.getIdentidade()) ? contrachequeResponse.getIdentidade() : "",
				valueFont);
		paragraphIdentidade.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphIdentidade);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphCPF = new Paragraph("15. CPF", labelFont);
		paragraphCPF.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphCPF);
		paragraphCPF = new Paragraph(
				Objects.nonNull(contrachequeResponse.getCpf()) ? Utils.formatarCpf(contrachequeResponse.getCpf()) : "",
				valueFont);
		paragraphCPF.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphCPF);
		table.addCell(hcell);

		document.add(table);

		table = new PdfPTable(5);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 2, 2, 2, 2, 2 });

		hcell = new PdfPCell();
		Paragraph paragraphOrgaoPagador = new Paragraph("16. Órgão Pagador", labelFont);
		paragraphOrgaoPagador.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphOrgaoPagador);
		paragraphOrgaoPagador = new Paragraph(
				Objects.nonNull(contrachequeResponse.getOrgaoPagador()) ? contrachequeResponse.getOrgaoPagador() : "",
				valueFont);
		paragraphOrgaoPagador.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphOrgaoPagador);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphDepSF = new Paragraph("17. Dep. S.F", labelFont);
		paragraphDepSF.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphDepSF);
		paragraphDepSF = new Paragraph(
				Objects.nonNull(contrachequeResponse.getDepSf().toString()) ? contrachequeResponse.getDepSf().toString()
						: "",
				valueFont);
		paragraphDepSF.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphDepSF);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphDepIR = new Paragraph("18. Dep. I.R", labelFont);
		paragraphDepIR.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphDepIR);
		paragraphDepIR = new Paragraph(
				Objects.nonNull(contrachequeResponse.getDepIr().toString()) ? contrachequeResponse.getDepIr().toString()
						: "",
				valueFont);
		paragraphDepIR.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphDepIR);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphCargaHoraria = new Paragraph("19. Carga Horária", labelFont);
		paragraphCargaHoraria.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphCargaHoraria);
		paragraphCargaHoraria = new Paragraph(
				Objects.nonNull(contrachequeResponse.getCargaHoraria()) ? contrachequeResponse.getCargaHoraria() : "",
				valueFont);
		paragraphCargaHoraria.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphCargaHoraria);
		table.addCell(hcell);

		document.add(table);

		return document;
	}

	public Document montarTabelaRubricas(Document document, ContrachequeResponse contrachequeResponse,
			SimpleDateFormat simpleDateFormat, Font labelFont, Font valueFont, Font fontTitle)
			throws DocumentException {
		PdfPTable table;

		table = new PdfPTable(5);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 1, 3, 2, 2, 2 });

		PdfPCell hcell;
		hcell = new PdfPCell();
		Paragraph paragraphCodigo = new Paragraph("CÓDIGO", labelFont);
		paragraphCodigo.setAlignment(Element.ALIGN_CENTER);
		hcell.addElement(paragraphCodigo);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphDescricao = new Paragraph("DESCRIÇÃO", labelFont);
		paragraphDescricao.setAlignment(Element.ALIGN_CENTER);
		hcell.addElement(paragraphDescricao);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphReferencia = new Paragraph("REFERÊNCIA", labelFont);
		paragraphReferencia.setAlignment(Element.ALIGN_CENTER);
		hcell.addElement(paragraphReferencia);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphProventos = new Paragraph("PROVENTOS", labelFont);
		paragraphProventos.setAlignment(Element.ALIGN_CENTER);
		hcell.addElement(paragraphProventos);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphDescontos = new Paragraph("DESCONTOS", labelFont);
		paragraphDescontos.setAlignment(Element.ALIGN_CENTER);
		hcell.addElement(paragraphDescontos);
		table.addCell(hcell);

		for (LancamentoResponse vantagemLancamento : contrachequeResponse.getLancamentosVantagens()) {
			hcell = new PdfPCell(new Phrase(vantagemLancamento.getVerba().getCodigo(), valueFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(vantagemLancamento.getVerba().getDescricaoVerba(), valueFont));
			hcell.setBorder(Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(Objects.nonNull(vantagemLancamento.getValorReferencia())
					? vantagemLancamento.getValorReferencia().toString()
					: "", valueFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(Utils.formatarMoedaReal(vantagemLancamento.getValor()), valueFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(""));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			table.addCell(hcell);
		}

		for (LancamentoResponse descontoLancamento : contrachequeResponse.getLancamentosDescontos()) {
			boolean last = false;
			if (contrachequeResponse.getLancamentosDescontos()
					.indexOf(descontoLancamento) == contrachequeResponse.getLancamentosDescontos().size() - 1) {
				last = true;
			}
			hcell = new PdfPCell(new Phrase(descontoLancamento.getVerba().getCodigo(), valueFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(last ? (Rectangle.LEFT | Rectangle.BOTTOM) : Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(descontoLancamento.getVerba().getDescricaoVerba(), valueFont));
			hcell.setBorder(last ? (Rectangle.LEFT | Rectangle.BOTTOM) : Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(Objects.nonNull(descontoLancamento.getValorReferencia())
					? descontoLancamento.getValorReferencia().toString()
					: "", valueFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(last ? (Rectangle.LEFT | Rectangle.BOTTOM) : Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(""));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(last ? (Rectangle.LEFT | Rectangle.BOTTOM) : Rectangle.LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(Utils.formatarMoedaReal(descontoLancamento.getValor()), valueFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBorder(
					last ? (Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM) : Rectangle.LEFT | Rectangle.RIGHT);
			table.addCell(hcell);
		}

		document.add(table);

		return document;

	}

	public Document montarFooterContracheque(Document document, ContrachequeResponse contrachequeResponse,
			SimpleDateFormat simpleDateFormat, Font labelFont, Font valueFont, Font fontTitle)
			throws DocumentException {

		PdfPTable table;

		table = new PdfPTable(6);
		table.setWidthPercentage(95);
		table.setWidths(new int[] { 3, 3, 3, 3, 4, 4 });

		PdfPCell hcell;
		hcell = new PdfPCell();

		for (int i = 0; i < 4; i++) {
			hcell = montarCelulaVazia(hcell);
			hcell.setBorder((i == 0 ? Rectangle.TOP | Rectangle.LEFT : Rectangle.TOP));
			table.addCell(hcell);
		}

		hcell = new PdfPCell();
		Paragraph paragraphSalBruto = new Paragraph("29. Sal. Bruto", labelFont);
		paragraphSalBruto.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphSalBruto);
		paragraphSalBruto = new Paragraph(Objects.nonNull(contrachequeResponse.getValorBruto())
				? Utils.formatarMoedaReal(contrachequeResponse.getValorBruto())
				: "", valueFont);
		paragraphSalBruto.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphSalBruto);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphDesconto = new Paragraph("30. Desconto", labelFont);
		paragraphDesconto.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphDesconto);
		paragraphDesconto = new Paragraph(Objects.nonNull(contrachequeResponse.getValorDesconto())
				? Utils.formatarMoedaReal(contrachequeResponse.getValorDesconto())
				: "", valueFont);
		paragraphDesconto.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphDesconto);
		table.addCell(hcell);

		for (int i = 0; i < 4; i++) {

			hcell = montarCelulaVazia(hcell);
			hcell.setBorder((i == 0 ? Rectangle.LEFT : Rectangle.NO_BORDER));
			table.addCell(hcell);
		}

		hcell = montarCelulaVazia(hcell);
		table.addCell(hcell);

		hcell = new PdfPCell();
		Paragraph paragraphLiquido = new Paragraph("32. Líquido", labelFont);
		paragraphLiquido.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphLiquido);
		paragraphLiquido = new Paragraph(Objects.nonNull(contrachequeResponse.getValorLiquido())
				? Utils.formatarMoedaReal(contrachequeResponse.getValorLiquido())
				: "", valueFont);
		paragraphLiquido.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphLiquido);
		table.addCell(hcell);

		Optional<LancamentoResponse> lancamentoFgts = contrachequeResponse.getLancamentosOutros().stream()
				.filter(lancamentoOutro -> lancamentoOutro.getVerba().getCodigo().equals("xxx")).findFirst();

		hcell = new PdfPCell();
		Paragraph paragraphFgts = new Paragraph("31. FGTS", labelFont);
		paragraphFgts.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphFgts);
		paragraphFgts = new Paragraph(
				lancamentoFgts.isPresent() ? Utils.formatarMoedaReal(lancamentoFgts.get().getValor()) : "", valueFont);
		paragraphFgts.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphFgts);
		table.addCell(hcell);

		Optional<LancamentoResponse> lancamentoSalarioBase = contrachequeResponse.getLancamentosVantagens().stream()
				.filter(lancamentoVantangem -> lancamentoVantangem.getVerba().getCodigo().equals("101")).findFirst();

		hcell = new PdfPCell();
		Paragraph paragraphSalBase = new Paragraph("28. Sal. Base", labelFont);
		paragraphSalBase.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphSalBase);
		paragraphSalBase = new Paragraph(
				lancamentoSalarioBase.isPresent() ? Utils.formatarMoedaReal(lancamentoSalarioBase.get().getValor())
						: "",
				valueFont);
		paragraphSalBase.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphSalBase);
		table.addCell(hcell);

		Optional<LancamentoResponse> lancamentoBasePrev = contrachequeResponse.getLancamentosOutros().stream()
				.filter(lancamentoOutro -> lancamentoOutro.getVerba().getDescricaoVerba().equals("BASE PREV"))
				.findFirst();

		hcell = new PdfPCell();
		Paragraph paragraphBasePrev = new Paragraph("33. Base Prev", labelFont);
		paragraphBasePrev.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphBasePrev);
		paragraphBasePrev = new Paragraph(
				lancamentoBasePrev.isPresent() ? Utils.formatarMoedaReal(lancamentoBasePrev.get().getValor()) : "",
				valueFont);
		paragraphBasePrev.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphBasePrev);
		table.addCell(hcell);

		Optional<LancamentoResponse> lancamentoBaseIr = contrachequeResponse.getLancamentosOutros().stream()
				.filter(lancamentoOutro -> lancamentoOutro.getVerba().getDescricaoVerba().equals("BASE IR"))
				.findFirst();

		hcell = new PdfPCell();
		Paragraph paragraphBaseIR = new Paragraph("34. Base IRRF", labelFont);
		paragraphBaseIR.setAlignment(Element.ALIGN_LEFT);
		hcell.addElement(paragraphBaseIR);
		paragraphBaseIR = new Paragraph(
				lancamentoBaseIr.isPresent() ? Utils.formatarMoedaReal(lancamentoBaseIr.get().getValor()) : "",
				valueFont);
		paragraphBaseIR.setAlignment(Element.ALIGN_RIGHT);
		hcell.addElement(paragraphBaseIR);
		table.addCell(hcell);

		hcell = montarCelulaVazia(hcell);
		table.addCell(hcell);

		hcell = montarCelulaVazia(hcell);
		table.addCell(hcell);

		document.add(table);

		return document;
	}

	public PdfPCell montarCelulaVazia(PdfPCell hcell) {
		hcell = new PdfPCell();
		hcell.addElement(new Phrase(""));

		return hcell;
	}

}
