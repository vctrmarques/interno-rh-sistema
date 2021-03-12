package com.rhlinkcon.report.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;
import com.rhlinkcon.filtro.DirfPdfFiltro;
import com.rhlinkcon.payload.dirf.DirfEmpresaFilialDto;
import com.rhlinkcon.payload.dirf.DirfInformesDto;
import com.rhlinkcon.service.DirfService;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class DirfPdfReportService implements CrudPdfReportService<DirfPdfFiltro> {

	private static final String TITULO = "DIRF";
	private static final String SUBTITULO = "Declaração do Imposto de Renda Retido na Fonte";
	
	@Autowired
	private DirfService service;
	
	@Override
	public void personalizarDocumento(PdfWriter writer, Document document, DirfPdfFiltro filtro) throws DocumentException {
		addAtributos(document, TITULO, SUBTITULO);
		
		List<DirfInformesDto> lista =  service.getDirfFuncionario(filtro);
		
		if(Utils.checkList(lista)) {
			Iterator<DirfInformesDto> i = lista.iterator();		
			
			while(i.hasNext()) {
				mountPagina(writer, document, i.next());
				if(i.hasNext()) {
					document.newPage();
				}
			}
		} else {
			document.add(new Paragraph("Ouve um erro na geração do relatório"));
		}
		
	}
	
	private void mountPagina(PdfWriter writer, Document document, DirfInformesDto dto) throws DocumentException {
		document.add(criarCabecalho(dto.getDirf().getAnoBase(), dto.getAnoExercicio()));
		document.add(aviso());
		document.add(bloco1(dto.getDirf().getFilial()));
		document.add(bloco2(dto));
		document.add(bloco3(dto));
		document.add(bloco4(dto));
		document.add(bloco5(dto));
		document.add(bloco6(dto));
		document.add(bloco6Complemento(dto));
		document.add(bloco7(dto));
		document.add(bloco8(dto));
	}
	
	// 8. Responsável pelas informações
	private PdfPTable bloco8(DirfInformesDto dto) {
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		
		table.addCell(tituloBloco("8. Responsável pelas informações", 3));
		
		PdfPCell a = new PdfPCell(new Phrase(0, "Nome", Constantes.TIMES9));
		a.setHorizontalAlignment(Element.ALIGN_LEFT);
		a.setVerticalAlignment(Element.ALIGN_TOP);
		a.setBorderWidthBottom(0);

		table.addCell(a);
		
		PdfPCell b = new PdfPCell(new Phrase(0, "Data", Constantes.TIMES9));
		b.setHorizontalAlignment(Element.ALIGN_LEFT);
		b.setVerticalAlignment(Element.ALIGN_TOP);
		b.setBorderWidthBottom(0);

		table.addCell(b);
		
		PdfPCell c = new PdfPCell(new Phrase(0, "Assinatura", Constantes.TIMES9));
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		c.setVerticalAlignment(Element.ALIGN_TOP);
		c.setBorderWidthBottom(0);

		table.addCell(c);
		
		PdfPCell d = new PdfPCell(new Phrase(0, dto.getDirf().getResponsavelReceita().getNome(), Constantes.TIMES9BOLD));
		d.setHorizontalAlignment(Element.ALIGN_LEFT);
		d.setVerticalAlignment(Element.ALIGN_MIDDLE);
		d.setBorderWidthTop(0);
		d.setPaddingBottom(3);
		d.setPaddingLeft(3);

		table.addCell(d);
		
		PdfPCell e = new PdfPCell(new Phrase(0, dataInstant("dd/MM/yyyy", dto.getDirf().getAlteradoEm()), Constantes.TIMES9BOLD));
		e.setHorizontalAlignment(Element.ALIGN_CENTER);
		e.setVerticalAlignment(Element.ALIGN_MIDDLE);
		e.setBorderWidthTop(0);
		e.setPaddingBottom(3);
		e.setPaddingLeft(3);
		
		table.addCell(e);
		
		PdfPCell f = new PdfPCell(new Phrase(0, " ", Constantes.TIMES9BOLD));
		f.setHorizontalAlignment(Element.ALIGN_CENTER);
		f.setVerticalAlignment(Element.ALIGN_MIDDLE);
		f.setBorderWidthTop(0);
		f.setPaddingBottom(3);
		f.setPaddingLeft(3);
		
		table.addCell(f);
		
		PdfPCell g = new PdfPCell(new Phrase(0, "Aprovado pela IN RFB nº 1.682, de 28 de dezembro de 2016 ", Constantes.TIMES9));
		g.setHorizontalAlignment(Element.ALIGN_LEFT);
		g.setVerticalAlignment(Element.ALIGN_MIDDLE);
		g.setBorderWidthLeft(0);
		g.setBorderWidthRight(0);
		g.setPaddingBottom(3);
		g.setPaddingTop(3);
		g.setPaddingLeft(3);
		g.setColspan(3);
		
		table.addCell(g);
		
		return table;
	}
	
	private String dataInstant(String valor, Instant data) {
		SimpleDateFormat formato = new SimpleDateFormat(valor);
		Date result = Date.from(data);
		return formato.format(result);
	}

	// 7. Informações complementares
	private PdfPTable bloco7(DirfInformesDto dto) {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setSpacingAfter(2);
		
		
		table.addCell(tituloBloco("7. Informações Complementares", 1));
		table.addCell(textoBloco(dto.getInformacoes(), 1));
		
		return table;
	}
		
	// 6. Rendimentos Recebidos Acumuladamente
	private PdfPTable bloco6Complemento(DirfInformesDto dto) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter(2);
		table.setWidths(new float[]{8, 2});
		
		table.addCell(textoBloco("1. Total dos rendimentos tributáveis (inclusive férias e décimo terceiro salário)", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("2. Exclusão: Despesas com a ação judicial", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("3. Dedução: Contribuição previdenciária oficial", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("4. Dedução: Pensão alimentícia (preencher também o quadro 7)", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("5. Imposto sobre a renda retido na fonte", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("6. Rendimentos isentos de pensão, proventos de aposentadoria ou reforma por moléstia grave ou aposentadoria ou reforma por acidente em serviço", 1));
		table.addCell(valorBloco(0.00));
		
		return table;
	}
	
	private PdfPTable bloco6(DirfInformesDto dto) throws DocumentException {
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingAfter(3);
		table.setWidths(new float[]{5, 2, 1, 2});
		
		table.addCell(tituloBloco("6. Rendimentos Recebidos Acumuladamente - Art. 12-A da Lei nº 7.713, de 1988 (Sujeitos à tributação exclusiva)", 4));
		
		table.addCell(textoBloco("6.1. Número do processo: (especificar)", 1));
		table.addCell(textoBloco("Quantidade de meses", 1));
		table.addCell(textoBloco("0", 1));
		
		PdfPCell a = new PdfPCell(new Phrase(0, "Valores em reais", Constantes.TIMES10));
		a.setHorizontalAlignment(Element.ALIGN_CENTER);
		a.setVerticalAlignment(Element.ALIGN_MIDDLE);
		a.setBorderWidth(PdfCell.NO_BORDER);
		a.setRowspan(2);
		
		table.addCell(a);
		
		table.addCell(textoBloco("Natureza do rendimento: (especificar)", 3));
		
		return table;
	}
	
	// 5. Rendimentos Sujeitos à Tributação Exclusiva (rendimento líquido)
	private PdfPTable bloco5(DirfInformesDto dto) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter(2);
		table.setWidths(new float[]{8, 2});
		
		table.addCell(tituloBloco("5. Rendimentos Sujeitos à Tributação Exclusiva (rendimento líquido)", 1));
		table.addCell(valoresTituloBloco());
		
		table.addCell(textoBloco("1. Décimo terceiro salário", 1));
		table.addCell(valorBloco(dto.getDecimoTerceiro()));
		
		table.addCell(textoBloco("2. Imposto sobre a renda retido na fonte sobre 13º salário", 1));
		table.addCell(valorBloco(dto.getImpostoSobreDecimoTerceiro()));
		
		table.addCell(textoBloco("3. Outros: " + dto.getTextoOutrosDecimoTerceiro(), 1));
		table.addCell(valorBloco(dto.getOutrosDecimoTerceiro()));
		
		return table;
	}
	
	// 4. Rendimentos Isentos e Não Tributáveis
	private PdfPTable bloco4(DirfInformesDto dto) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter(2);
		table.setWidths(new float[]{8, 2});
		
		table.addCell(tituloBloco("4. Rendimentos Isentos e Não Tributáveis", 1));
		table.addCell(valoresTituloBloco());
		
		table.addCell(textoBloco("1. Parcela isenta dos proventos da aposentadoria, reserva remunerada, reforma e pensão (65 anos ou mais)", 1));
		table.addCell(valorBloco(dto.getParcelaIsenta()));
		
		table.addCell(textoBloco("2. Diárias e ajuda de custo", 1));
		table.addCell(valorBloco(dto.getDiariasAjudaCusto()));
		
		table.addCell(textoBloco("3. Pensão e proventos de aposentadoria ou reforma por moléstia grave; proventos de aposentadoria ou reforma por acidente em serviço", 1));
		table.addCell(valorBloco(dto.getPensaoProventosAposentadoria()));
		
		table.addCell(textoBloco("4. Lucros e dividendos, apurados a partir de 1996, pagos por pessoa jurídica (lucro real, presumido ou arbitrado)", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("5. Valores pagos ao titular ou sócio da microempresa ou empresa de pequeno porte, exceto pro labore, aluguéis ou serviços prestados", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("6. Indenizações por rescisão de contrato de trabalho, inclusive a título de PDV e por acidente de trabalho", 1));
		table.addCell(valorBloco(0.00));
		
		table.addCell(textoBloco("7. Outros: " + dto.getTextoOutros(), 1));
		table.addCell(valorBloco(dto.getValorOutros()));
		
		return table;
	}
	
	// 3. Rendimentos Tributáveis, Deduções e Imposto sobre a Renda Retido na Fonte
	private PdfPTable bloco3(DirfInformesDto dto) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter(2);
		table.setWidths(new float[]{8, 2});
		
		table.addCell(tituloBloco("3. Rendimentos Tributáveis, Deduções e Imposto sobre a Renda Retido na Fonte", 1));
		table.addCell(valoresTituloBloco());
		
		table.addCell(textoBloco("1. Total dos rendimentos (inclusive férias)", 1));
		table.addCell(valorBloco(dto.getTotalRendimentos()));
		
		table.addCell(textoBloco("2. Contribuição previdenciária oficial", 1));
		table.addCell(valorBloco(dto.getPrevidenciaOficial()));
		
		table.addCell(textoBloco("3. Contribuição a entidades de previdência complementar, pública ou privada, e a fundos de aposentadoria programada individual (Fapi)(preencher também o quadro 7)", 1));
		table.addCell(valorBloco(dto.getPrevidenciaComplementar()));
		
		table.addCell(textoBloco("4. Pensão alimentícia (preencher também o quadro 7)", 1));
		table.addCell(valorBloco(dto.getPensaoAlimenticia()));
		
		table.addCell(textoBloco("5. Imposto sobre a renda retido na fonte", 1));
		table.addCell(valorBloco(dto.getImpostoRetido()));
		
		return table;
	}

	// 2. Pessoa Física Beneficiário dos Rendimentos
	private PdfPTable bloco2(DirfInformesDto dto) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter(2);
		table.setWidths(new float[]{1, 4});
		
		table.addCell(tituloBloco("2. Pessoa Física Beneficiário dos Rendimentos", 2));
		
		PdfPCell b = new PdfPCell(new Phrase(0, "CPF", Constantes.TIMES9));
		b.setHorizontalAlignment(Element.ALIGN_LEFT);
		b.setVerticalAlignment(Element.ALIGN_TOP);
		b.setBorderWidthBottom(0);

		table.addCell(b);
		
		PdfPCell c = new PdfPCell(new Phrase(0, "Nome Completo", Constantes.TIMES9));
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		c.setVerticalAlignment(Element.ALIGN_TOP);
		c.setBorderWidthBottom(0);

		table.addCell(c);
		
		PdfPCell d = new PdfPCell(new Phrase(0, Utils.formatarCpf(dto.getCpf()), Constantes.TIMES9BOLD));
		d.setHorizontalAlignment(Element.ALIGN_CENTER);
		d.setVerticalAlignment(Element.ALIGN_MIDDLE);
		d.setBorderWidthTop(0);
		d.setPaddingBottom(3);

		table.addCell(d);
		
		PdfPCell e = new PdfPCell(new Phrase(0, dto.getNome(), Constantes.TIMES9BOLD));
		e.setHorizontalAlignment(Element.ALIGN_LEFT);
		e.setVerticalAlignment(Element.ALIGN_MIDDLE);
		e.setBorderWidthTop(0);
		e.setPaddingBottom(3);
		e.setPaddingLeft(3);

		table.addCell(e);
		
		PdfPCell f = new PdfPCell(new Phrase(0, "Natureza do Rendimento", Constantes.TIMES9));
		f.setHorizontalAlignment(Element.ALIGN_LEFT);
		f.setVerticalAlignment(Element.ALIGN_TOP);
		f.setBorderWidthBottom(0);
		f.setColspan(2);

		table.addCell(f);
		
		PdfPCell g = new PdfPCell(new Phrase(0, "Rendimentos do trabalho assalariado", Constantes.TIMES9BOLD));
		g.setHorizontalAlignment(Element.ALIGN_LEFT);
		g.setVerticalAlignment(Element.ALIGN_MIDDLE);
		g.setBorderWidthTop(0);
		g.setColspan(2);
		g.setPaddingBottom(3);
		g.setPaddingLeft(3);

		table.addCell(g);
		
		return table;
	}
	
	// 1. Fonte pagadora Pessoa Jurídica
	private PdfPTable bloco1(DirfEmpresaFilialDto dto) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter(2);
		table.setWidths(new float[]{1, 4});
		
		table.addCell(tituloBloco("1. Fonte Pagadora Pessoa Jurídica", 2));
		
		PdfPCell b = new PdfPCell(new Phrase(0, "CNPJ", Constantes.TIMES9));
		b.setHorizontalAlignment(Element.ALIGN_LEFT);
		b.setVerticalAlignment(Element.ALIGN_TOP);
		b.setBorderWidthBottom(0);

		table.addCell(b);
		
		PdfPCell c = new PdfPCell(new Phrase(0, "Nome Empresarial", Constantes.TIMES9));
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		c.setVerticalAlignment(Element.ALIGN_TOP);
		c.setBorderWidthBottom(0);

		table.addCell(c);
		
		PdfPCell d = new PdfPCell(new Phrase(0, Utils.formatarCnpj(dto.getCnpj()), Constantes.TIMES9BOLD));
		d.setHorizontalAlignment(Element.ALIGN_CENTER);
		d.setVerticalAlignment(Element.ALIGN_MIDDLE);
		d.setBorderWidthTop(0);
		d.setPaddingBottom(3);
		

		table.addCell(d);
		
		PdfPCell e = new PdfPCell(new Phrase(0, dto.getNome().toUpperCase(), Constantes.TIMES9BOLD));
		e.setHorizontalAlignment(Element.ALIGN_LEFT);
		e.setVerticalAlignment(Element.ALIGN_MIDDLE);
		e.setBorderWidthTop(0);
		e.setPaddingBottom(3);
		e.setPaddingLeft(3);

		table.addCell(e);
		
		return table;
	}

	private PdfPTable aviso() {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setSpacingAfter(5);
		
		table.addCell(textoBloco("Verifique as condições e o prazo para a apresentação da Declaração do Imposto de Renda da Pessoa Física para este ano-calendário "
				+ " no sítio da Secretaria Especial da Receita Federal do Brasil na internet, no endereço receita.economia.gov.br", 1));
		
		return table;
	}

	private PdfPTable criarCabecalho(Integer anoBase, Integer anoExercicio) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingAfter(5);
		table.setWidths(new float[]{4, 2});
		
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		table1.setWidths(new float[]{2, 4});

		try {
			Resource resource = new ClassPathResource(Constantes.BRASAODIRF);
			Image imagem = Image.getInstance(resource.getURL());
			imagem.setAlignment(Image.ALIGN_CENTER);
			imagem.scalePercent(20);

			PdfPCell a = new PdfPCell(imagem);
			a.setHorizontalAlignment(Element.ALIGN_CENTER);
			a.setVerticalAlignment(Element.ALIGN_MIDDLE);
			a.setBorderWidth(PdfCell.NO_BORDER);
			a.setRowspan(4);
			
			table1.addCell(a);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		table1.addCell(celulaUpper("Ministério da economia", Element.ALIGN_CENTER));
		table1.addCell(celula("Secretaria Especial da Receita Federal do Brasil", Element.ALIGN_CENTER));
		table1.addCell(celula("Imposto sobre a Renda da Pessoa Física", Element.ALIGN_CENTER));
		table1.addCell(celula("Exercício " + anoExercicio.toString(), Element.ALIGN_CENTER));
		
		table.addCell(table1);
		
		PdfPTable table2 = new PdfPTable(1);
		table2.setWidthPercentage(100);
		
		table2.addCell(celulaUpper("COMPROVANTE DE RENDIMENTOS PAGOS E DE RETENÇÃO DE IMPOSTO DE RENDA NA FONTE", Element.ALIGN_CENTER));
		table2.addCell(celula("Ano-calendário de " + anoBase.toString(), Element.ALIGN_CENTER));
		
		table.addCell(table2);
		
        return table;
	}
	
	private PdfPCell tituloBloco(String texto, int colspan) {
		PdfPCell a = new PdfPCell(new Phrase(0, texto, Constantes.TIMES10));
		a.setHorizontalAlignment(Element.ALIGN_LEFT);
		a.setVerticalAlignment(Element.ALIGN_BOTTOM);
		a.setBorderWidth(PdfCell.NO_BORDER);
		a.setColspan(colspan);
		a.setPaddingBottom(3);
		a.setPaddingTop(3);
		a.setPaddingLeft(3);
		a.setPaddingRight(3);
		return a;
	}
	
	private PdfPCell valoresTituloBloco() {
		PdfPCell a = new PdfPCell(new Phrase(0, "Valores em reais", Constantes.TIMES10));
		a.setHorizontalAlignment(Element.ALIGN_CENTER);
		a.setVerticalAlignment(Element.ALIGN_BOTTOM);
		a.setBorderWidth(PdfCell.NO_BORDER);
		a.setPaddingBottom(3);
		a.setPaddingTop(3);
		a.setPaddingLeft(3);
		a.setPaddingRight(3);
		return a;
	}
	
	private PdfPCell textoBloco(String texto, int colspan) {
		PdfPCell a = new PdfPCell(new Phrase(0, texto, Constantes.TIMES9));
		a.setHorizontalAlignment(Element.ALIGN_LEFT);
		a.setVerticalAlignment(Element.ALIGN_CENTER);
		a.setColspan(colspan);
		a.setPaddingBottom(3);
		a.setPaddingTop(3);
		a.setPaddingLeft(3);
		a.setPaddingRight(3);
		return a;
	}
	
	private PdfPCell valorBloco(Double valor) {

		DecimalFormat df = new DecimalFormat("###,##0.00");

		Phrase v = new Phrase();
		if(Objects.nonNull(valor)) {
			
			v = new Phrase(0, df.format(valor), Constantes.TIMES9);
		} else {
			v = new Phrase(0, df.format(0.00), Constantes.TIMES9);
		}
		PdfPCell a = new PdfPCell(v);
		a.setHorizontalAlignment(Element.ALIGN_RIGHT);
		a.setVerticalAlignment(Element.ALIGN_CENTER);
		a.setPaddingBottom(3);
		a.setPaddingTop(3);
		a.setPaddingLeft(3);
		a.setPaddingRight(3);
		
		return a;
	}

}
