package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.rhlinkcon.filtro.LegislacaoFiltroRequest;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.legislacao.DetalhamentoNormaResponse;
import com.rhlinkcon.payload.legislacao.LegislacaoRequest;
import com.rhlinkcon.payload.legislacao.LegislacaoResponse;
import com.rhlinkcon.payload.legislacao.NormaResponse;
import com.rhlinkcon.service.LegislacaoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/legislacao")
public class LegislacaoController {

	@Autowired
	private LegislacaoService legislacaoService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public PagedResponse<LegislacaoResponse> get(PagedRequest pagedRequest,
			LegislacaoFiltroRequest legislacaoFiltro) {
		return legislacaoService.get(pagedRequest, legislacaoFiltro);
	}

	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public List<DadoBasicoDto> getDadoBasicoList() {
		return legislacaoService.getDadoBasicoList();
	}

	@GetMapping("/norma/search")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public List<NormaResponse> getNormaResponseList(@RequestParam(value = "search", required = false) String search) {
		return legislacaoService.getNormaResponseList(search);
	}

	@GetMapping("/assuntoNorma/search")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public List<DadoBasicoDto> getDadoBasicoListAssuntoNorma(
			@RequestParam(value = "search", required = false) String search) {
		return legislacaoService.getDadoBasicoListAssuntoNorma(search);
	}

	@GetMapping("/detalhamentoNorma/search")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public List<DetalhamentoNormaResponse> searchDetalhamentoNorma(
			@RequestParam(value = "search", required = false) String search) {
		return legislacaoService.searchDetalhamentoNorma(search);
	}

	@GetMapping("/enteFederado/search")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public List<DadoBasicoDto> getDadoBasicoListEnteFederado(
			@RequestParam(value = "search", required = false) String search) {
		return legislacaoService.getDadoBasicoListEnteFederado(search);
	}

	@GetMapping("/unidadeGestora/search")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public List<DadoBasicoDto> getDadoBasicoListUnidadeGestora(
			@RequestParam(value = "search", required = false) String search) {
		return legislacaoService.getDadoBasicoListUnidadeGestora(search);
	}

	@GetMapping("/textoDocumento/search")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public List<DadoBasicoDto> getDadoBasicoListTextoDocumento(
			@RequestParam(value = "search", required = false) String search) {
		return legislacaoService.getDadoBasicoListTextoDocumento(search);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_VISUALIZAR','LEGISLACAO_CADASTRAR','LEGISLACAO_ATUALIZAR','LEGISLACAO_EXCLUIR')")
	public LegislacaoResponse getById(@PathVariable Long id) {
		return legislacaoService.getById(id);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_CADASTRAR')")
	public ResponseEntity<?> create(@Valid @RequestBody LegislacaoRequest legislacaoRequest) {
		legislacaoService.create(legislacaoRequest);
		return Utils.created(true, "Legislação inserida com sucesso.");
	}

	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_ATUALIZAR')")
	public ResponseEntity<?> update(@Valid @RequestBody LegislacaoRequest legislacaoRequest) {
		legislacaoService.update(legislacaoRequest);
		return Utils.created(true, "Legislação atualizada com sucesso.");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','LEGISLACAO_EXCLUIR')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		legislacaoService.delete(id);

		return Utils.deleted(true, "Legislação excluída com sucesso.");
	}

}
