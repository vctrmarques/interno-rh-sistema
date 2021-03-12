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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoDto;
import com.rhlinkcon.report.service.RelatorioGerencialExcelReportService;
import com.rhlinkcon.report.service.RelatorioServidorPagBloqueadoPdfReportService;
import com.rhlinkcon.service.EmpresaFilialService;
import com.rhlinkcon.service.RelatorioServidorPagBloqueadoService;
import com.rhlinkcon.service.SituacaoFuncionalService;

@RestController
@RequestMapping("/api/relatorioServidorPagBloqueado")
public class RelatorioServidorPagBloqueadoController {

	@Autowired
	private RelatorioServidorPagBloqueadoService service;

	@Autowired
	private EmpresaFilialService empresaFilialService;

	@Autowired
	private RelatorioServidorPagBloqueadoPdfReportService reportPdfService;

	@Autowired
	private RelatorioGerencialExcelReportService reportExcelService;

	@Autowired
	private SituacaoFuncionalService situacaoFuncionalService;

	@GetMapping("/relatorio/pdf")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_SERV_PAG_BLOQUEADO_GESTAO')")
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(ServidorPagBloqueadoDto filtro) throws IOException {
		try {
			ByteArrayInputStream in = reportPdfService.gerarPdf(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=Rel. Servidor Com Pag Bloqueado.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/relatorio/excel")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_SERV_PAG_BLOQUEADO_GESTAO')")
	public ResponseEntity<InputStreamResource> getAllRelatorioExcel(ServidorPagBloqueadoDto filtro) throws IOException {
		try {
			ByteArrayInputStream in = reportExcelService.gerarExcel(null);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=Rel. Servidor Com Pag Bloqueado.xlsx");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/servidor/search")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_SERV_PAG_BLOQUEADO_GESTAO')")
	public List<DadoBasicoDto> getServidorSearchList(@RequestParam(value = "search") String search) {
		return service.getServidorSearchList(search);
	}

	@GetMapping("/empresa/filial/search")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_SERV_PAG_BLOQUEADO_GESTAO')")
	public List<DadoBasicoDto> getEmpresaFilialSearchList() {
		return empresaFilialService.getEmpresaFilialSearchList();
	}

	@GetMapping("/situacao/funcional/search")
	@PreAuthorize("hasAnyRole('ADMIN','RELATORIO_SERV_PAG_BLOQUEADO_GESTAO')")
	public List<DadoBasicoDto> getSituacoesFuncionaisList(
			@RequestParam(value = "search", required = false) String search) {
		return situacaoFuncionalService.getSituacoesFuncionaisList(search, null);
	}

}
