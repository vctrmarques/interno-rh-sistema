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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.BatimentoFolhaCustomizacaoFiltro;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFolhaPagamentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.report.service.BatimentoFolhaPagamentoPdfReportService;
import com.rhlinkcon.service.BatimentoFolhaPagamentoService;
import com.rhlinkcon.util.AppConstants;

@RestController
@RequestMapping("/api/batimentoFolhaPagamento")
public class BatimentoFolhaPagamentoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";

	@Autowired
	private BatimentoFolhaPagamentoService service;

	@Autowired
	private BatimentoFolhaPagamentoPdfReportService pdfReportService;

	@GetMapping()
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<BatimentoFolhaPagamentoResponse> getAllByFiltros(
			@RequestParam(value = "competencia", defaultValue = AppConstants.DEFAULT_EMPTY) Integer competencia,
			@RequestParam(value = "tipoProcessamento", defaultValue = AppConstants.DEFAULT_EMPTY) Long tipoProcessamentoId,
			@RequestParam(value = "ano", defaultValue = AppConstants.DEFAULT_EMPTY) Integer ano) {
		return service.getAllbyFiltros(competencia, tipoProcessamentoId, ano);
	}

	@GetMapping("/orgao")
	@PreAuthorize(AUTHORIZE)
	public BatimentoFolhaPagamentoResponse getOrgaoByFiltros(
			@RequestParam(value = "competencia", defaultValue = AppConstants.DEFAULT_EMPTY) Integer competencia,
			@RequestParam(value = "tipoProcessamento", defaultValue = AppConstants.DEFAULT_EMPTY) Long tipoProcessamentoId,
			@RequestParam(value = "ano", defaultValue = AppConstants.DEFAULT_EMPTY) Integer ano) {
		return service.getOrgaobyFiltros(competencia, tipoProcessamentoId, ano);
	}

	@GetMapping("/relatorio/pdf")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(
			BatimentoFolhaCustomizacaoFiltro filtro) throws IOException {
		try {
			ByteArrayInputStream in = pdfReportService.gerarPdf(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=batimento-folha-pagamento.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
