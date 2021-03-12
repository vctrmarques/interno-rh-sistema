package com.rhlinkcon.report.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jrimum.utilix.text.Strings;
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
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.lowagie.text.pdf.PdfCell;
import com.rhlinkcon.filtro.BatimentoFolhaCustomizacaoFiltro;
import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoDadosFolhaDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFuncionarioCountDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoResumoDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoVerbaDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.RelatorioBatimentoFolhaPagamentoDto;
import com.rhlinkcon.service.BatimentoFolhaPagamentoService;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class BatimentoFolhaPagamentoPdfReportService implements CrudPdfReportService<BatimentoFolhaCustomizacaoFiltro> {
	
	private static final String TITULO = "Relatório batimento da folha de pagamento";
	private static final String SUBTITULO = "Relatório baseado nos valores totais de vantagens, descontos, líquidos, dentre outros";
	
	@Autowired
	private BatimentoFolhaPagamentoService service;
	
	@Override
	public void personalizarDocumento(PdfWriter writer, Document document, BatimentoFolhaCustomizacaoFiltro valor) throws DocumentException {
		addAtributos(document, TITULO, SUBTITULO);
		String dataHoraCabecalho = dataAtual("'HORA - 'HH:mm:ss' | DATA - 'dd/MM/yyyy");

		List<RelatorioBatimentoFolhaPagamentoDto> listaInfo = service.get(valor);
		
		if (Utils.checkList(listaInfo)) {
			for(RelatorioBatimentoFolhaPagamentoDto info : listaInfo) {
				if(valor.possuiContracheque()) {
					montaBlocoContracheque(document, info, valor, writer, dataHoraCabecalho);
					document.newPage();
				}
				
				if(valor.possuiSituacao()) {
					for(BatimentoResumoDto s : info.getResumoSituacoes()) {
						document.add(criarCabecalho(info, writer.getPageNumber(), dataHoraCabecalho));
						montaBlocoSituacaoLotacao(document, info, s, valor);
						document.newPage();
					}
				}
				
				if(valor.possuiLotacao()) {
					for(BatimentoResumoDto s : info.getResumoLotacoes()) {
						document.add(criarCabecalho(info, writer.getPageNumber(), dataHoraCabecalho));
						montaBlocoSituacaoLotacao(document, info, s, valor);
						document.newPage();
					}
				}
				
				if(valor.possuiFilial()) {
					document.add(criarCabecalho(info, writer.getPageNumber(), dataHoraCabecalho));
					montaBlocoFilial(document, info, valor);
					document.newPage();
				}
				
			}
			
			if(valor.possuiEmpresa()) {
				document.add(criarCabecalho(listaInfo.get(0), writer.getPageNumber(), dataHoraCabecalho));
				montaBlocoEmpresa(document, listaInfo.get(0), valor);
			}
		}

	}
	

	private void montaBlocoContracheque(Document document, RelatorioBatimentoFolhaPagamentoDto info, BatimentoFolhaCustomizacaoFiltro filtro, PdfWriter writer, String dataHoraCabecalho) throws DocumentException {
		
		document.add(criarCabecalho(info, writer.getPageNumber(), dataHoraCabecalho));
		
		document.add(getTitulo("CONTRACHEQUES"));
		
		int count = 0;
		for(BatimentoDadosFolhaDto f : info.getContracheque()) {
			if(count == 4) {
				count = 0;
				document.newPage();
				document.add(criarCabecalho(info, writer.getPageNumber(), dataHoraCabecalho));
				document.add(getTitulo("CONTRACHEQUES"));
			}
			if(filtro.isDadosBancariosContracheque()) {
				PdfPTable linha = new PdfPTable(3);
				linha.setWidthPercentage(100);
				linha.setHorizontalAlignment(Element.ALIGN_LEFT);
				linha.setWidths(new float[]{2, 4, 4});
				linha.addCell(cUpper("cod. banco: " + f.getCodigoBanco(), Element.ALIGN_LEFT, false));
				linha.addCell(cUpper("agência: " + f.getAgencia(), Element.ALIGN_LEFT, false));
				linha.addCell(cUpper("conta: " + f.getNumeroConta() + "-" + f.getDigitoConta(), Element.ALIGN_LEFT, false));
	        	document.add(linha);
	        }
			if(filtro.isMatriculaContracheque() || filtro.isFuncionarioContracheque() || filtro.isLotacaoContracheque()) {
				PdfPTable linha = new PdfPTable(3);
				linha.setWidthPercentage(100);
				linha.setWidths(new float[]{2, 4, 4});
				linha.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				int contaColuna = 0;
				
				if(filtro.isMatriculaContracheque()) {
					linha.addCell(cUpper("matrícula: " + f.getMatricula(), Element.ALIGN_LEFT, true));
					contaColuna += 1;
				}
				if(filtro.isFuncionarioContracheque()) {
					linha.addCell(cUpper("nome: " + f.getNome(), Element.ALIGN_LEFT, true));
					contaColuna += 1;
				}
				if(filtro.isLotacaoContracheque()) {
					linha.addCell(cUpper("lotação: " + f.getLotacao(), Element.ALIGN_LEFT, true));
					contaColuna += 1;
				}
				
				for (int i = contaColuna; i<3; i++) {
					linha.addCell(cUpper(" ", Element.ALIGN_LEFT, false));
				}
				
				document.add(linha);
			}
			if(filtro.isFuncaoContracheque() || filtro.isDependenteIrContracheque() || filtro.isDependenteSalarioFamiliaContracheque() || 
					filtro.isDataAdmissaoContracheque() || filtro.isSituacaoFuncionalContracheque() || filtro.isCargaHorariaContracheque()) {
				PdfPTable linha = new PdfPTable(4);
				linha.setWidthPercentage(100);
				linha.setWidths(new float[]{4, 2, 2, 4});
				
				int contaColuna = 0;
				
				if(filtro.isFuncaoContracheque()) {
					linha.addCell(cUpper("Cargo: " + f.getCargo(), Element.ALIGN_LEFT, false));
					contaColuna += 1;
				}
				if(filtro.isDependenteIrContracheque()) {
					linha.addCell(cUpper("Dep. IR: " + "00", Element.ALIGN_LEFT, false));//dependente de IR
					contaColuna += 1;
				}
				if(filtro.isDependenteSalarioFamiliaContracheque()) {
					linha.addCell(cUpper("Dep. SF: " + "00", Element.ALIGN_LEFT, false));//dependente de salario Familia
					contaColuna += 1;
				}
				if(filtro.isDataAdmissaoContracheque()) {
					linha.addCell(cUpper("Admissão: " + f.getDataAdmissao(), Element.ALIGN_LEFT, false));//data adimissão
					contaColuna += 1;
				}
			
				for (int i = contaColuna; i<4; i++) {
					linha.addCell(cUpper(" ", Element.ALIGN_LEFT, false));
				}
				
				document.add(linha);
			}
			
			if(filtro.isSituacaoFuncionalContracheque() || filtro.isCargaHorariaContracheque()) {
				PdfPTable linha = new PdfPTable(2);
				linha.setWidthPercentage(100);
				linha.setWidths(new float[]{4, 2});
				
				int contaColuna = 0;

				if(filtro.isSituacaoFuncionalContracheque()) {
					linha.addCell(cUpper("Situação: " + f.getSituacaoFuncional(), Element.ALIGN_LEFT, false));
					contaColuna += 1;
				}
				if(filtro.isCargaHorariaContracheque()) {
					linha.addCell(cUpper("Carga Horária: " + "", Element.ALIGN_LEFT, false));
					contaColuna += 1;
				}
				
				for (int i = contaColuna; i<2; i++) {
					linha.addCell(cUpper(" ", Element.ALIGN_LEFT, false));
				}
				
				document.add(linha);
			}
			
			
			if(filtro.isVantagensContracheque() || filtro.isDescontosContracheque()) {
				PdfPTable linha = new PdfPTable(2);
				linha.setWidthPercentage(100);
				linha.setWidths(new float[]{2, 2});
				linha.addCell(cValueCustom("VANTAGENS", Element.ALIGN_CENTER, 0, 1, 0, 0));
				linha.addCell(cValueCustom("DESCONTOS", Element.ALIGN_CENTER, 0, 1, 0, 0));
				document.add(linha);
				
				PdfPTable tableV = new PdfPTable(2);
				tableV.setWidthPercentage(100);
				tableV.setWidths(new float[]{4, 2});
				tableV.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
				tableV.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));

				if(filtro.isVantagensContracheque()) {
					Iterator itp = f.getListProventos().entrySet().iterator();
					while(itp.hasNext()) {
						Map.Entry pair = (Map.Entry) itp.next();
						
						Double valor = (Double) pair.getValue();
						String labelKey = pair.getKey().toString();
						
						tableV.addCell(cUpper(labelKey, Element.ALIGN_LEFT, false));
						tableV.addCell(valorBloco(valor));
					}
				}
				
				PdfPTable tableD = new PdfPTable(2);
				tableD.setWidthPercentage(100);
				tableD.setWidths(new float[]{4, 2});
				tableD.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
				tableD.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));
				
				if(filtro.isDescontosContracheque()) {
					Iterator itd = f.getListDescontos().entrySet().iterator();
					
					while(itd.hasNext()) {
						Map.Entry pair = (Map.Entry) itd.next();
						
						Double valor = (Double) pair.getValue();
						String labelKey = pair.getKey().toString();
						
						tableD.addCell(celulaUpper(labelKey, Element.ALIGN_LEFT));
						tableD.addCell(valorBloco(valor));
					}
				}
				
				PdfPTable linha2 = new PdfPTable(2);
				linha2.setWidthPercentage(100);
				linha2.setWidths(new float[]{4, 4});
				if(!filtro.isTotalizadoresContracheque())
					linha2.setSpacingAfter(10);
				
				PdfPCell c = new PdfPCell(tableV);
				c.setBorderWidthTop(0);
				c.setBorderWidthBottom(0);
				c.setBorderWidthLeft(1);
				c.setBorderWidthRight(0);
				c.setPaddingBottom(0);
				c.setPaddingTop(0);
				c.setPaddingLeft(0);
				c.setPaddingRight(0);
				
				linha2.addCell(c);
				
				PdfPCell c2 = new PdfPCell(tableD);
				c2.setBorderWidthTop(0);
				c2.setBorderWidthBottom(0);
				c2.setBorderWidthLeft(0);
				c2.setBorderWidthRight(1);
				c2.setPaddingBottom(0);
				c2.setPaddingTop(0);
				c2.setPaddingLeft(0);
				c2.setPaddingRight(0);
				
				linha2.addCell(c2);
				
				document.add(linha2);
			}
			
			if(filtro.isTotalizadoresContracheque()) {
				PdfPTable linha = new PdfPTable(6);
				linha.setWidthPercentage(100);
				linha.setSpacingAfter(10);
				linha.setWidths(new float[]{4, 2, 4, 2, 4, 2});
				
				linha.addCell(cValueCustom("TOTAL DE PROVENTOS:", Element.ALIGN_LEFT, 1, 1, 1, 0));
				linha.addCell(valorBlocoCustom(f.getTotalProventos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
				linha.addCell(cValueCustom("TOTAL DE DESCONTOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
				linha.addCell(valorBlocoCustom(f.getTotalDescontos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
				linha.addCell(cValueCustom("TOTAL LIQUIDOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
				linha.addCell(valorBlocoCustom(f.getTotalLiquido(), Element.ALIGN_LEFT, 1, 1, 0, 1));
				document.add(linha);
			}
	        count += 1;
		}
		
	}


	private Paragraph getTitulo(String valor) {
		Paragraph titulo = new Paragraph(valor, Constantes.TIMES10BOLD);
		titulo.setAlignment(Element.ALIGN_CENTER);
		titulo.setSpacingAfter(8);
		
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-5);
		dottedline.setGap(2f);
		titulo.add(dottedline);
		
		return titulo;
	}


	private void montaBlocoSituacaoLotacao(Document document, RelatorioBatimentoFolhaPagamentoDto info, BatimentoResumoDto resumo, BatimentoFolhaCustomizacaoFiltro filtro) throws DocumentException {
		document.add(getTitulo(check(info.getEmpresaFilial().getNomeFilial(), true) + " (" + check(resumo.getDescricao(), true) + ")"));
		
		PdfPTable linha = new PdfPTable(2);
		linha.setWidthPercentage(100);
		linha.setWidths(new float[]{2, 2});
		linha.addCell(cValueCustom("VANTAGENS", Element.ALIGN_CENTER, 0, 1, 0, 0));
		linha.addCell(cValueCustom("DESCONTOS", Element.ALIGN_CENTER, 0, 1, 0, 0));
		document.add(linha);
		
		PdfPTable tableV = new PdfPTable(2);
		tableV.setWidthPercentage(100);
		tableV.setWidths(new float[]{4, 2});
		tableV.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
		tableV.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));

		if(filtro.isVantagensSituacao() || filtro.isVantagensLotacao()) {
			for(BatimentoVerbaDto f : resumo.getVantagens()) {
				tableV.addCell(cUpper(f.getRubrica() + " - " + f.getDescricao(), Element.ALIGN_LEFT, false));
				tableV.addCell(valorBloco(f.getValor()));
			}
		}
		
		PdfPTable tableD = new PdfPTable(2);
		tableD.setWidthPercentage(100);
		tableD.setWidths(new float[]{4, 2});
		tableD.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
		tableD.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));
		
		if(filtro.isDescontosSituacao() || filtro.isDescontosLotacao()) {
			for(BatimentoVerbaDto f : resumo.getDescontos()) {
				tableD.addCell(cUpper(f.getRubrica() + " - " + f.getDescricao(), Element.ALIGN_LEFT, false));
				tableD.addCell(valorBloco(f.getValor()));
			}
		}
		
		PdfPTable linha2 = new PdfPTable(2);
		linha2.setWidthPercentage(100);
		linha2.setWidths(new float[]{4, 4});
		if(!filtro.isTotalizadoresSituacao())
			linha2.setSpacingAfter(10);
		
		PdfPCell c = new PdfPCell(tableV);
		c.setBorderWidthTop(0);
		c.setBorderWidthBottom(0);
		c.setBorderWidthLeft(1);
		c.setBorderWidthRight(0);
		c.setPaddingBottom(0);
		c.setPaddingTop(0);
		c.setPaddingLeft(0);
		c.setPaddingRight(0);
		
		linha2.addCell(c);
		
		PdfPCell c2 = new PdfPCell(tableD);
		c2.setBorderWidthTop(0);
		c2.setBorderWidthBottom(0);
		c2.setBorderWidthLeft(0);
		c2.setBorderWidthRight(1);
		c2.setPaddingBottom(0);
		c2.setPaddingTop(0);
		c2.setPaddingLeft(0);
		c2.setPaddingRight(0);
		
		linha2.addCell(c2);
		
		document.add(linha2);
		
		if(filtro.isTotalizadoresSituacao() || filtro.isTotalizadoresLotacao()) {
			PdfPTable linha3 = new PdfPTable(6);
			linha3.setWidthPercentage(100);
			if(!filtro.isTotaisFuncionariosSituacao() || !filtro.isTotaisFuncionariosLotacao())
				linha3.setSpacingAfter(10);
			linha3.setWidths(new float[]{4, 2, 4, 2, 4, 2});
			
			linha3.addCell(cValueCustom("TOTAL DE PROVENTOS:", Element.ALIGN_LEFT, 1, 1, 1, 0));
			linha3.addCell(valorBlocoCustom(resumo.getTotalProventos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
			linha3.addCell(cValueCustom("TOTAL DE DESCONTOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
			linha3.addCell(valorBlocoCustom(resumo.getTotalDescontos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
			linha3.addCell(cValueCustom("TOTAL LIQUIDOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
			linha3.addCell(valorBlocoCustom(resumo.getTotalLiquido(), Element.ALIGN_LEFT, 1, 1, 0, 1));
			linha3.addCell(cValueCustom("NÚMERO FUNCIONÁRIOS:", Element.ALIGN_LEFT, 0, 0, 0, 0));
			linha3.addCell(cValueCustom(resumo.getTotalFuncionarios().getTotal(), Element.ALIGN_LEFT, 0, 0, 0, 0));
			document.add(linha3);
		}
		
		if(filtro.isTotaisFuncionariosSituacao() || filtro.isTotaisFuncionariosLotacao()) {
			
			Paragraph subtitulo = new Paragraph("TOTAL DE FUNCIONÁRIOS");
			subtitulo.setAlignment(Element.ALIGN_LEFT);
			subtitulo.setFont(Constantes.TIMES9BOLD);
			subtitulo.setSpacingAfter(10);
			subtitulo.setSpacingBefore(10);
			
			document.add(subtitulo);
			
			PdfPTable linha3 = new PdfPTable(4);
			linha3.setWidthPercentage(100);
			linha3.setSpacingAfter(10);
			linha3.setWidths(new float[]{4, 2, 4, 2});
			
			linha3.addCell(cUpper("FUNCIONÁRIOS", Element.ALIGN_LEFT, false));
			linha3.addCell(cUpper(resumo.getTotalFuncionarios().getTotal().toString(), Element.ALIGN_RIGHT, false));
			
			for(BatimentoFuncionarioCountDto e : resumo.getTotalFuncionarios().getLista()) {
				linha3.addCell(cUpper(e.getDescricao(), Element.ALIGN_LEFT, false));
				linha3.addCell(cUpper(e.getCount().toString(), Element.ALIGN_RIGHT, false));
			}
			
			if(resumo.getTotalFuncionarios().getLista().size() % 2 == 0) {
				linha3.addCell(cUpper(" ", Element.ALIGN_LEFT, false));
				linha3.addCell(cUpper(" ", Element.ALIGN_RIGHT, false));
			}
			
			document.add(linha3);
		}
		
	}

	private void montaBlocoFilial(Document document, RelatorioBatimentoFolhaPagamentoDto info, BatimentoFolhaCustomizacaoFiltro filtro) throws DocumentException {
		document.add(getTitulo("TOTAIS DA " + check(info.getEmpresaFilial().getNomeFilial(), true)));
		
		PdfPTable linha = new PdfPTable(2);
		linha.setWidthPercentage(100);
		linha.setWidths(new float[]{2, 2});
		linha.addCell(cValueCustom("VANTAGENS", Element.ALIGN_CENTER, 0, 1, 0, 0));
		linha.addCell(cValueCustom("DESCONTOS", Element.ALIGN_CENTER, 0, 1, 0, 0));
		document.add(linha);
		
		PdfPTable tableV = new PdfPTable(2);
		tableV.setWidthPercentage(100);
		tableV.setWidths(new float[]{4, 2});
		tableV.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
		tableV.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));

		if(filtro.isVantagensFilial()) {
			for(BatimentoVerbaDto f : info.getResumoFilial().getVantagens()) {
				tableV.addCell(cUpper(f.getRubrica() + " - " + f.getDescricao(), Element.ALIGN_LEFT, false));
				tableV.addCell(valorBloco(f.getValor()));
			}
		}
		
		PdfPTable tableD = new PdfPTable(2);
		tableD.setWidthPercentage(100);
		tableD.setWidths(new float[]{4, 2});
		tableD.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
		tableD.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));
		
		if(filtro.isDescontosFilial()) {
			for(BatimentoVerbaDto f : info.getResumoFilial().getDescontos()) {
				tableD.addCell(cUpper(f.getRubrica() + " - " + f.getDescricao(), Element.ALIGN_LEFT, false));
				tableD.addCell(valorBloco(f.getValor()));
			}
		}
		
		PdfPTable linha2 = new PdfPTable(2);
		linha2.setWidthPercentage(100);
		linha2.setWidths(new float[]{4, 4});
		if(!filtro.isTotalizadoresFilial())
			linha2.setSpacingAfter(10);
		
		PdfPCell c = new PdfPCell(tableV);
		c.setBorderWidthTop(0);
		c.setBorderWidthBottom(0);
		c.setBorderWidthLeft(1);
		c.setBorderWidthRight(0);
		c.setPaddingBottom(0);
		c.setPaddingTop(0);
		c.setPaddingLeft(0);
		c.setPaddingRight(0);
		
		linha2.addCell(c);
		
		PdfPCell c2 = new PdfPCell(tableD);
		c2.setBorderWidthTop(0);
		c2.setBorderWidthBottom(0);
		c2.setBorderWidthLeft(0);
		c2.setBorderWidthRight(1);
		c2.setPaddingBottom(0);
		c2.setPaddingTop(0);
		c2.setPaddingLeft(0);
		c2.setPaddingRight(0);
		
		linha2.addCell(c2);
		
		document.add(linha2);
		
		if(filtro.isTotalizadoresFilial()) {
			PdfPTable linha3 = new PdfPTable(6);
			linha3.setWidthPercentage(100);
			if(!filtro.isTotaisFuncionariosFilial())
				linha3.setSpacingAfter(10);
			linha3.setWidths(new float[]{4, 2, 4, 2, 4, 2});
			
			linha3.addCell(cValueCustom("TOTAL DE PROVENTOS:", Element.ALIGN_LEFT, 1, 1, 1, 0));
			linha3.addCell(valorBlocoCustom(info.getResumoFilial().getTotalProventos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
			linha3.addCell(cValueCustom("TOTAL DE DESCONTOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
			linha3.addCell(valorBlocoCustom(info.getResumoFilial().getTotalDescontos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
			linha3.addCell(cValueCustom("TOTAL LIQUIDOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
			linha3.addCell(valorBlocoCustom(info.getResumoFilial().getTotalLiquido(), Element.ALIGN_LEFT, 1, 1, 0, 1));
			linha3.addCell(cValueCustom("NÚMERO FUNCIONÁRIOS:", Element.ALIGN_LEFT, 0, 0, 0, 0));
			linha3.addCell(cValueCustom(info.getResumoFilial().getTotalFuncionarios().getTotal(), Element.ALIGN_LEFT, 0, 0, 0, 0));
			document.add(linha3);
		}
		
		if(filtro.isTotaisFuncionariosSituacao()) {
			
			Paragraph subtitulo = new Paragraph("Total de Funcionários");
			subtitulo.setAlignment(Element.ALIGN_LEFT);
			subtitulo.setFont(Constantes.TIMES9BOLD);
			subtitulo.setSpacingAfter(10);
			subtitulo.setSpacingBefore(10);
			
			document.add(subtitulo);
			
			PdfPTable linha4 = new PdfPTable(4);
			linha4.setWidthPercentage(100);
			linha4.setSpacingAfter(10);
			linha4.setWidths(new float[]{4, 2, 4, 2});
			
			linha4.addCell(cUpper("FUNCIONÁRIOS", Element.ALIGN_LEFT, false));
			linha4.addCell(cUpper(info.getResumoFilial().getTotalFuncionarios().getTotal().toString(), Element.ALIGN_RIGHT, false));
			
			for(BatimentoFuncionarioCountDto e : info.getResumoFilial().getTotalFuncionarios().getLista()) {
				linha4.addCell(cUpper(e.getDescricao(), Element.ALIGN_LEFT, false));
				linha4.addCell(cUpper(e.getCount().toString(), Element.ALIGN_RIGHT, false));
			}
			
			if(info.getResumoFilial().getTotalFuncionarios().getLista().size() % 2 == 0) {
				linha4.addCell(cUpper(" ", Element.ALIGN_LEFT, false));
				linha4.addCell(cUpper(" ", Element.ALIGN_RIGHT, false));
			}
			
			document.add(linha4);
		}
	}


	private void montaBlocoEmpresa(Document document, RelatorioBatimentoFolhaPagamentoDto info, BatimentoFolhaCustomizacaoFiltro filtro) throws DocumentException {
		document.add(getTitulo(check(info.getEmpresaFilial().getNomeOrgao(), true)));
		
		PdfPTable linha = new PdfPTable(2);
		linha.setWidthPercentage(100);
		linha.setWidths(new float[]{2, 2});
		linha.addCell(cValueCustom("VANTAGENS", Element.ALIGN_CENTER, 0, 1, 0, 0));
		linha.addCell(cValueCustom("DESCONTOS", Element.ALIGN_CENTER, 0, 1, 0, 0));
		document.add(linha);
		
		PdfPTable tableV = new PdfPTable(2);
		tableV.setWidthPercentage(100);
		tableV.setWidths(new float[]{4, 2});
		tableV.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
		tableV.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));

		if(filtro.isVantagensEmpresa()) {
			for(BatimentoVerbaDto f : info.getResumoEmpresa().getVantagens()) {
				tableV.addCell(cUpper(f.getRubrica() + " - " + f.getDescricao(), Element.ALIGN_LEFT, false));
				tableV.addCell(valorBloco(f.getValor()));
			}
		}
		
		PdfPTable tableD = new PdfPTable(2);
		tableD.setWidthPercentage(100);
		tableD.setWidths(new float[]{4, 2});
		tableD.addCell(cUpper("Rubrica - Descrição", Element.ALIGN_LEFT, false));
		tableD.addCell(cUpper("Valor", Element.ALIGN_RIGHT, false));
		
		if(filtro.isDescontosEmpresa()) {
			for(BatimentoVerbaDto f : info.getResumoEmpresa().getDescontos()) {
				tableD.addCell(cUpper(f.getRubrica() + " - " + f.getDescricao(), Element.ALIGN_LEFT, false));
				tableD.addCell(valorBloco(f.getValor()));
			}
		}
		
		PdfPTable linha2 = new PdfPTable(2);
		linha2.setWidthPercentage(100);
		linha2.setWidths(new float[]{4, 4});
		if(!filtro.isTotalizadoresEmpresa())
			linha2.setSpacingAfter(10);
		
		PdfPCell c = new PdfPCell(tableV);
		c.setBorderWidthTop(0);
		c.setBorderWidthBottom(0);
		c.setBorderWidthLeft(1);
		c.setBorderWidthRight(0);
		c.setPaddingBottom(0);
		c.setPaddingTop(0);
		c.setPaddingLeft(0);
		c.setPaddingRight(0);
		
		linha2.addCell(c);
		
		PdfPCell c2 = new PdfPCell(tableD);
		c2.setBorderWidthTop(0);
		c2.setBorderWidthBottom(0);
		c2.setBorderWidthLeft(0);
		c2.setBorderWidthRight(1);
		c2.setPaddingBottom(0);
		c2.setPaddingTop(0);
		c2.setPaddingLeft(0);
		c2.setPaddingRight(0);
		
		linha2.addCell(c2);
		
		document.add(linha2);
		
		if(filtro.isTotalizadoresEmpresa()) {
			PdfPTable linha3 = new PdfPTable(6);
			linha3.setWidthPercentage(100);
			if(!filtro.isTotaisFuncionariosEmpresa())
				linha3.setSpacingAfter(10);
			linha3.setWidths(new float[]{4, 2, 4, 2, 4, 2});
			
			linha3.addCell(cValueCustom("TOTAL DE PROVENTOS:", Element.ALIGN_LEFT, 1, 1, 1, 0));
			linha3.addCell(valorBlocoCustom(info.getResumoEmpresa().getTotalProventos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
			linha3.addCell(cValueCustom("TOTAL DE DESCONTOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
			linha3.addCell(valorBlocoCustom(info.getResumoEmpresa().getTotalDescontos(), Element.ALIGN_LEFT, 1, 1, 0, 0));
			linha3.addCell(cValueCustom("TOTAL LIQUIDOS:", Element.ALIGN_RIGHT, 1, 1, 0, 0));
			linha3.addCell(valorBlocoCustom(info.getResumoEmpresa().getTotalLiquido(), Element.ALIGN_LEFT, 1, 1, 0, 1));
			linha3.addCell(cValueCustom("NÚMERO FUNCIONÁRIOS:", Element.ALIGN_LEFT, 0, 0, 0, 0));
			linha3.addCell(cValueCustom(info.getResumoEmpresa().getTotalFuncionarios().getTotal(), Element.ALIGN_LEFT, 0, 0, 0, 0));
			document.add(linha3);
		}
		
		if(filtro.isTotaisFuncionariosEmpresa()) {
			
			Paragraph subtitulo = new Paragraph("TOTAL DE FUNCIONÁRIOS");
			subtitulo.setAlignment(Element.ALIGN_LEFT);
			subtitulo.setFont(Constantes.TIMES9BOLD);
			subtitulo.setSpacingAfter(10);
			subtitulo.setSpacingBefore(10);
			
			document.add(subtitulo);
			
			PdfPTable linha3 = new PdfPTable(4);
			linha3.setWidthPercentage(100);
			linha3.setSpacingAfter(10);
			linha3.setWidths(new float[]{4, 2, 4, 2});
			
			linha3.addCell(cUpper("FUNCIONÁRIOS", Element.ALIGN_LEFT, false));
			linha3.addCell(cUpper(info.getResumoEmpresa().getTotalFuncionarios().getTotal().toString(), Element.ALIGN_RIGHT, false));
			
			for(BatimentoFuncionarioCountDto e : info.getResumoEmpresa().getTotalFuncionarios().getLista()) {
				linha3.addCell(cUpper(e.getDescricao(), Element.ALIGN_LEFT, false));
				linha3.addCell(cUpper(e.getCount().toString(), Element.ALIGN_RIGHT, false));
			}
			
			if(info.getResumoEmpresa().getTotalFuncionarios().getLista().size() % 2 == 0) {
				linha3.addCell(cUpper(" ", Element.ALIGN_LEFT, false));
				linha3.addCell(cUpper(" ", Element.ALIGN_RIGHT, false));
			}
			
			document.add(linha3);
		}
	}


	private PdfPTable criarCabecalho(RelatorioBatimentoFolhaPagamentoDto info, int pagina, String dataHoraCabecalho) throws DocumentException {
		PdfPTable tableBorder = new PdfPTable(1);
		tableBorder.setWidthPercentage(100);
		tableBorder.setSpacingAfter(3);
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 4, 3});
		

        table.addCell(cUpper(info.getEmpresaFilial().getCodigoOrgao() + " - " + info.getEmpresaFilial().getNomeOrgao(), Element.ALIGN_LEFT, false));
        table.addCell(cUpper("FOLHA DE PAGAMENTO (" + MesEnum.getEnumByInteger(info.getCompetencia().getMesCompetencia()).getLabel() + "/" + info.getCompetencia().getAnoCompetencia() + ")", Element.ALIGN_CENTER, true));
        table.addCell(cUpper("FOLHA - " + Strings.fillWithZeroLeft(pagina, 3), Element.ALIGN_RIGHT, false));
        
        table.addCell(cUpper("CNPJ: " + Utils.formatarCnpj(info.getEmpresaFilial().getCnpjOrgao()), Element.ALIGN_LEFT, false));
        table.addCell(cUpper("PERÍODO - " + data("dd/MM/yyyy", info.getCompetencia().getInicioCompetencia()) + " A " + data("dd/MM/yyyy", info.getCompetencia().getFimCompetencia()), Element.ALIGN_CENTER, false));
        table.addCell(cUpper(dataHoraCabecalho, Element.ALIGN_RIGHT, false));
        
        table.addCell(cUpper(info.getEmpresaFilial().getCodigoFilial() + " - " + info.getEmpresaFilial().getNomeFilial(), Element.ALIGN_LEFT, false));
        table.addCell(cUpper(info.getTipoProcessamento() + " (DEFINITIVO)", Element.ALIGN_CENTER, false));
        table.addCell(cUpper("", Element.ALIGN_CENTER, false));
        
        tableBorder.addCell(table);
       
        return tableBorder;
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
		a.setVerticalAlignment(Element.ALIGN_TOP);
		a.setBorderWidth(PdfCell.NO_BORDER);
		a.setPaddingBottom(3);
		a.setPaddingTop(3);
		a.setPaddingLeft(3);
		a.setPaddingRight(3);
		
		return a;
	}
	
	private PdfPCell valorBlocoCustom(Double valor, int alinhamento, int top, int bottom, int left, int right) {

		DecimalFormat df = new DecimalFormat("###,##0.00");

		Phrase v = new Phrase();
		if(Objects.nonNull(valor)) {
			
			v = new Phrase(0, df.format(valor), Constantes.TIMES9);
		} else {
			v = new Phrase(0, df.format(0.00), Constantes.TIMES9);
		}
		PdfPCell a = new PdfPCell(v);
		a.setHorizontalAlignment(Element.ALIGN_RIGHT);
		a.setVerticalAlignment(Element.ALIGN_TOP);
		a.setBorderWidthTop(top);
		a.setBorderWidthBottom(bottom);
		a.setBorderWidthLeft(left);
		a.setBorderWidthRight(right);
		a.setPaddingBottom(3);
		a.setPaddingTop(3);
		a.setPaddingLeft(3);
		a.setPaddingRight(3);
		
		return a;
	}
	
	private PdfPCell cUpper(String texto, int alinhamento, boolean bold) {
		Phrase valor;
		if(Objects.nonNull(texto)) {
			valor = new Phrase(0, texto.toUpperCase(), bold ? Constantes.TIMES9BOLD : Constantes.TIMES9);
		} else {
			valor = new Phrase(0, " ", bold ? Constantes.TIMES9BOLD : Constantes.TIMES9);
		}
		PdfPCell c = new PdfPCell(valor);
		c.setHorizontalAlignment(alinhamento);
		c.setVerticalAlignment(Element.ALIGN_TOP);
		c.setBorderWidth(PdfCell.NO_BORDER);
		c.setPaddingBottom(3);
		c.setPaddingTop(3);
		c.setPaddingLeft(3);
		c.setPaddingRight(3);
		return c;
	}
	
	private PdfPCell cValue(String texto, int alinhamento, boolean bold) {
		Phrase valor;
		if(Objects.nonNull(texto)) {
			valor = new Phrase(0, texto.toUpperCase(), bold ? Constantes.TIMES9BOLD : Constantes.TIMES9);
		} else {
			valor = new Phrase(0, " ", bold ? Constantes.TIMES9BOLD : Constantes.TIMES9);
		}
		PdfPCell c = new PdfPCell(valor);
		c.setHorizontalAlignment(alinhamento);
		c.setVerticalAlignment(Element.ALIGN_TOP);
		c.setBorderWidth(PdfCell.NO_BORDER);
		c.setPaddingBottom(3);
		c.setPaddingTop(3);
		c.setPaddingLeft(3);
		c.setPaddingRight(3);
		return c;
	}
	
	private PdfPCell cValueCustom(String texto, int alinhamento, int top, int bottom, int left, int right) {
		Phrase valor;
		if(Objects.nonNull(texto)) {
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
		c.setPaddingBottom(3);
		c.setPaddingTop(3);
		c.setPaddingLeft(3);
		c.setPaddingRight(3);
		return c;
	}
	
	private PdfPCell cValueCustom(Integer total, int alinhamento, int top, int bottom, int left, int right) {
		Phrase valor;
		if(Objects.nonNull(total)) {
			valor = new Phrase(0, total.toString().toUpperCase(), Constantes.TIMES9);
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
		c.setPaddingBottom(3);
		c.setPaddingTop(3);
		c.setPaddingLeft(3);
		c.setPaddingRight(3);
		return c;
	}
	
	private String dataInstant(String valor, Instant data) {
		SimpleDateFormat formato = new SimpleDateFormat(valor);
		Date result = Date.from(data);
		return formato.format(result);
	}
	
	private Paragraph addDottedLine() {
		Paragraph a = new Paragraph();
		a.setSpacingAfter(10);
		
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-2);
		dottedline.setGap(2f);
		a.add(dottedline);
		
		return a;
	}
	
	private String check(String valor, boolean upperCase) {
		return Objects.nonNull(valor) ? (upperCase ? valor.toUpperCase() : valor): " ";
	}
	
}
