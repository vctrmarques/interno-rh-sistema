package com.rhlinkcon.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialFiltroDto;
import com.rhlinkcon.report.service.RelatorioGerencialExcelReportService;
import com.rhlinkcon.report.service.RelatorioGerencialPdfReportService;
import com.rhlinkcon.service.RelatorioGerencialService;

@RestController
@RequestMapping("/api/relatorioGerencial")
public class RelatorioGerencialController {

	@Autowired
	private RelatorioGerencialService service;

	@Autowired
	private RelatorioGerencialPdfReportService reportPdfService;

	@Autowired
	private RelatorioGerencialExcelReportService reportExcelService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_GERENCIAL_GESTAO')")
	public PagedResponse<RelatorioGerencialDto> getPagedRelatorioGerencialList(PagedRequest pagedRequest,
			@Valid RelatorioGerencialFiltroDto filterRequest) {
		return service.getPagedRelatorioGerencialList(pagedRequest, filterRequest);
	}

	@GetMapping("/somatorio")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_GERENCIAL_GESTAO')")
	public RelatorioGerencialDto getRelatorioGerencialSomatorio(@Valid RelatorioGerencialFiltroDto filterRequest) {
		return service.getRelatorioGerencialSomatorio(filterRequest);
	}

	@GetMapping("/relatorio/pdf")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_GERENCIAL_GESTAO')")
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(RelatorioGerencialFiltroDto filtro)
			throws IOException {
		try {
			ByteArrayInputStream in = reportPdfService.gerarPdf(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=relatorioGerencial.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/relatorio/excel")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_GERENCIAL_GESTAO')")
	public ResponseEntity<InputStreamResource> getAllRelatorioExcel(RelatorioGerencialFiltroDto filtro)
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
