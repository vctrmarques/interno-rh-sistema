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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.AuditoriaFiltroRequest;
import com.rhlinkcon.payload.auditoria.AuditLabelClassDto;
import com.rhlinkcon.payload.auditoria.AuditoriaResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.report.service.AuditoriaPdfReportService;
import com.rhlinkcon.service.AuditoriaService;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

	@Autowired
	private AuditoriaService auditoriaService;

	@Autowired
	private AuditoriaPdfReportService reportPdfService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','AUDITORIA_GESTAO')")
	public PagedResponse<AuditoriaResponse> get(PagedRequest paginacao, AuditoriaFiltroRequest filtro) {
		return auditoriaService.get(paginacao, filtro);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','AUDITORIA_GESTAO')")
	public AuditoriaResponse getById(@PathVariable Long id) {
		return auditoriaService.getById(id);
	}

	@GetMapping("/entidade/search")
	@PreAuthorize("hasAnyRole('ADMIN','AUDITORIA_GESTAO')")
	public List<AuditLabelClassDto> entitySearch(@RequestParam(value = "search", required = false) String search) {
		return auditoriaService.entitySearch(search);
	}

	@GetMapping("/relatorio/pdf")
	@PreAuthorize("hasAnyRole('ADMIN','AUDITORIA_GESTAO')")
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(
			@RequestParam(value = "order", required = false) String order, AuditoriaFiltroRequest filtro)
			throws IOException {
		try {
			ByteArrayInputStream in = reportPdfService.gerarPdf(order, filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=auditoria.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
