package com.rhlinkcon.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.CertidaoCompensacaoFiltro;
import com.rhlinkcon.payload.BasicRequest;
import com.rhlinkcon.payload.certidaoCompensacao.CertidaoCompensacaoRequest;
import com.rhlinkcon.payload.certidaoCompensacao.CertidaoCompensacaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.report.service.CertidaoCompensacaoExcelReportService;
import com.rhlinkcon.report.service.CertidaoCompensacaoPdfReportService;
import com.rhlinkcon.service.CertidaoCompensacaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CertidaoCompensacaoController {

	@Autowired
	private CertidaoCompensacaoService service;
	@Autowired
	private CertidaoCompensacaoPdfReportService reportPdfService;
	@Autowired
	private CertidaoCompensacaoExcelReportService reportExcelService;

	@GetMapping("/certidoesCompensacao")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_VISUALIZAR','CTC_COMPENSACAO_CADASTRAR','CTC_COMPENSACAO_ATUALIZAR','CTC_COMPENSACAO_EXCLUIR')")
	public PagedResponse<CertidaoCompensacaoResponse> getAll(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			CertidaoCompensacaoFiltro certidaoCompensacaoFiltro) {
		return service.getAll(page, size, order, certidaoCompensacaoFiltro);
	}

	@GetMapping("/certidoesCompensacao/relatorio/pdf")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_VISUALIZAR','CTC_COMPENSACAO_CADASTRAR','CTC_COMPENSACAO_ATUALIZAR','CTC_COMPENSACAO_EXCLUIR')")
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro)
			throws IOException {
		try {
			ByteArrayInputStream in = reportPdfService.gerarPdf(certidaoCompensacaoFiltro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=certidao_compensacao_por_status.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/certidoesCompensacao/relatorio/excel")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_VISUALIZAR','CTC_COMPENSACAO_CADASTRAR','CTC_COMPENSACAO_ATUALIZAR','CTC_COMPENSACAO_EXCLUIR')")
	public ResponseEntity<InputStreamResource> getAllRelatorioExcel(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro)
			throws IOException {
		try {
			ByteArrayInputStream in = reportExcelService.gerarExcel(certidaoCompensacaoFiltro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=certidao_compensacao_por_status.xlsx");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/certidaoCompensacao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_VISUALIZAR','CTC_COMPENSACAO_CADASTRAR','CTC_COMPENSACAO_ATUALIZAR','CTC_COMPENSACAO_EXCLUIR')")
	public CertidaoCompensacaoResponse getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@PostMapping("/certidaoCompensacao")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_CADASTRAR')")
	public ResponseEntity<?> create(@Valid @RequestBody CertidaoCompensacaoRequest request) {
		CertidaoCompensacaoResponse objResponse = service.create(request);
		return Utils.created(true, "Declaração criada com sucesso.", objResponse);
	}

	@PutMapping("/certidaoCompensacao")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_ATUALIZAR')")
	public ResponseEntity<?> update(@Valid @RequestBody CertidaoCompensacaoRequest request) {
		CertidaoCompensacaoResponse objResponse = service.update(request);
		return Utils.created(true, "Declaração atualizada com sucesso.", objResponse);
	}

	@PostMapping("/certidaoCompensacao/retificar")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_CADASTRAR','CTC_COMPENSACAO_ATUALIZAR')")
	public ResponseEntity<?> retificar(@Valid @RequestBody BasicRequest basicRequest) {
		CertidaoCompensacaoResponse objResponse = service.retificar(basicRequest.getId());
		return Utils.created(true, "Declaração retificada com sucesso.", objResponse);
	}

	@PutMapping("/certidaoCompensacao/status")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_ATUALIZAR')")
	public ResponseEntity<?> alterarStatusCertidao(@RequestBody CertidaoCompensacaoRequest request) {
		service.alteraStatus(request.getId(), request.getStatusAtual());
		return Utils.created(true, "Status Certidão atualizada com sucesso.");
	}

	@DeleteMapping("/certidaoCompensacao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_EXCLUIR')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Certidão deletada com sucesso.");
	}

	@DeleteMapping("/certidaoCompensacao/remover-anexo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_COMPENSACAO_EXCLUIR')")
	public ResponseEntity<?> deleteAnexo(@PathVariable("id") Long id) {
		service.deleteAnexo(id);
		return Utils.deleted(true, "Anexo deletadoo com sucesso.");
	}

}
