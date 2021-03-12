package com.rhlinkcon.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.rhlinkcon.filtro.DirfFiltro;
import com.rhlinkcon.filtro.DirfPdfFiltro;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.dirf.DirfBeneficiarioDto;
import com.rhlinkcon.payload.dirf.DirfDto;
import com.rhlinkcon.payload.dirf.DirfEmpresaFilialDto;
import com.rhlinkcon.payload.dirf.DirfResponsavelReceitaDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.report.service.DirfPdfReportService;
import com.rhlinkcon.service.DirfService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/dirf")
public class DirfController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','DIRF_GESTAO')";
	private static final String AUTHORIZECADASTRAR = "hasAnyRole('ADMIN','DIRF_CADASTRAR')";
	private static final String AUTHORIZEATUALIZAR = "hasAnyRole('ADMIN','DIRF_ATUALIZAR')";
	private static final String AUTHORIZEEXCLUIR = "hasAnyRole('ADMIN','DIRF_EXCLUIR')";
	private static final String AUTHORIZEVISUALIZAR = "hasAnyRole('ADMIN','DIRF_VISUALIZAR')";
	
	@Autowired
	private DirfService service;
	
	@Autowired
	private DirfPdfReportService pdfReportService;
	
	@GetMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<DirfDto> getPagedList(PagedRequest pagedRequest,
			DirfFiltro filtro) {
		return service.getPagedList(pagedRequest, filtro);
	}
	
	@PostMapping()
	@PreAuthorize(AUTHORIZECADASTRAR)
	public ResponseEntity<?> create(@Valid @RequestBody DirfDto request) {
		service.create(request);
		return Utils.created(true, "Dirf criada com sucesso.");
	}
	
	@PutMapping()
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public ResponseEntity<?> update(@Valid @RequestBody DirfDto request) {
		service.update(request);
		return Utils.created(true, "Dirf atualizada com sucesso.");
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize(AUTHORIZEEXCLUIR)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Dirf removida com sucesso.");
	}
	
	@GetMapping("/{id}")
	@PreAuthorize(AUTHORIZEVISUALIZAR)
	public DirfDto getById(@PathVariable Long id) {
		return service.getById(id);
	}
	
	@GetMapping("/filiais")
	public PagedResponse<DirfEmpresaFilialDto> getPagedListFilial(PagedRequest pagedRequest) {
		return service.getPagedListFilial(pagedRequest);
	}
	
	@GetMapping("/beneficiarios")
	public PagedResponse<DirfBeneficiarioDto> getPagedListBeneficiarios(PagedRequest pagedRequest, Long dirfId, String query) {
		return service.getPagedListBeneficiario(pagedRequest, dirfId, query);
	}
	
	@GetMapping("/responsavel/search")
	public List<DirfResponsavelReceitaDto> getAllResponsavelReceitaByCpf(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return service.getAllResponsavelReceitaByCpf(search);
	}
	
	@GetMapping("/relatorio/pdf")
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<InputStreamResource> getAllRelatorioPdf(
			DirfPdfFiltro filtro) throws IOException {
		try {
			ByteArrayInputStream in = pdfReportService.gerarPdf(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=dirf.pdf");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/arquivo")
	@PreAuthorize(AUTHORIZEGESTAO)
	public List<AnexoResponse> getAllOrCreate(Long dirfId) {
		try {
			return service.getAllOrCreateByDirfId(dirfId);
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	
	
	
	
	
}
