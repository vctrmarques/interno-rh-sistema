package com.rhlinkcon.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoFiltroDto;
import com.rhlinkcon.report.service.RelatorioFolhaPagamentoExcelReportService;
import com.rhlinkcon.report.service.RelatorioFolhaPagamentoPdfReportService;

@RestController
@RequestMapping("/api/relatorioFolhaPagamento")
public class RelatorioFolhaPagamentoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private RelatorioFolhaPagamentoPdfReportService reportPdfService;
	
	@Autowired
	private RelatorioFolhaPagamentoExcelReportService reportExcelService;
	

	
	@GetMapping("/relatorio/pdf")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(RelatorioFolhaPagamentoFiltroDto filtro) 
			throws IOException {
		try {
			ByteArrayInputStream in = reportPdfService.gerarPdf(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=teste-folha-pagamento.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/relatorio/excel")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_GERENCIAL_GESTAO')")
	public ResponseEntity<InputStreamResource> getAllRelatorioExcel(RelatorioFolhaPagamentoFiltroDto filtro)
			throws IOException {
		try {
			ByteArrayInputStream in = reportExcelService.gerarExcel(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=relatorioGerencial.xlsx");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
