package com.rhlinkcon.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.RelatorioDadosAposentadoPensionistaFiltro;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.report.service.RelatorioDadosAposentadoPensionistaPdfService;
import com.rhlinkcon.service.RelatorioDadosAposentadoPensionistaService;

@RestController
@RequestMapping("/api/relatorioDadosAposentadoPensionista")
public class RelatorioDadosAposentadoPensionistaController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ROLE_RELATORIO_APOSENTADO_PENSAO_GESTAO')";
	
	@Autowired
	private RelatorioDadosAposentadoPensionistaPdfService pdfReportService;
	
	@Autowired
	private RelatorioDadosAposentadoPensionistaService service;
	
	@GetMapping("/relatorio/pdf")
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(
			RelatorioDadosAposentadoPensionistaFiltro filtro) throws IOException {
		try {
			ByteArrayInputStream in = pdfReportService.gerarPdf(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=relatorio-dados-aposentado-pensionista.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/filiais")
	public List<DadoBasicoDto> getPagedListFilial() {
		return service.getPagedListFilial();
	}
	
	@GetMapping("/lotacoes/{id}")
	public List<DadoBasicoDto> getPagedListFilial(@PathVariable Long id) {
		return service.getPagedListLotacao(id);
	}
}
