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

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoRequest;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoResponse;
import com.rhlinkcon.repository.TipoProcessamentoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TipoProcessamentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TipoProcessamentoController {

	@Autowired
	private TipoProcessamentoRepository tipoProcessamentoRepository;

	@Autowired
	private TipoProcessamentoService tipoProcessamentoService;


	@GetMapping("/tipoProcessamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TipoProcessamentoResponse> getAllTipoProcessamentoPage(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return tipoProcessamentoService.getAllTipoProcessamentoPage(page, size, order, descricao);
	}

	@GetMapping("/tipoProcessamento/{tipoProcessamentoId}")
	public TipoProcessamentoResponse getTipoProcessamentoById(@PathVariable Long tipoProcessamentoId) {
		return tipoProcessamentoService.getTipoProcessamentoById(tipoProcessamentoId);
	}

	@PostMapping("/tipoProcessamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTipoProcessamento(@Valid @RequestBody TipoProcessamentoRequest tipoProcessamentoRequest) {
		if (tipoProcessamentoRepository.existsByDescricao(tipoProcessamentoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Tipo de Processamento já existe!");
		}

		tipoProcessamentoService.createTipoProcessamento(tipoProcessamentoRequest);

		return Utils.created(true, "Tipo de Processamento criado com sucesso.");
	}

	@PutMapping("/tipoProcessamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTipoProcessamento(@Valid @RequestBody TipoProcessamentoRequest tipoProcessamentoRequest) {
		if (tipoProcessamentoRepository.existsByDescricaoAndIdNot(tipoProcessamentoRequest.getDescricao(),
				tipoProcessamentoRequest.getId())) {
			return Utils.badRequest(false, "Este Tipo de Processamento já existe!");
		}

		tipoProcessamentoService.updateTipoProcessamento(tipoProcessamentoRequest);

		return Utils.created(true, "Tipo de Processamento atualizado com sucesso.");
	}

	@DeleteMapping("/tipoProcessamento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTipoProcessamento(@PathVariable("id") Long id) {
		tipoProcessamentoService.deleteTipoProcessamento(id);

		return Utils.deleted(true, "Tipo de Processamento deletado com sucesso.");
	}
	
	@GetMapping("/listaTiposProcessamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TipoProcessamentoResponse> getAllTiposProcessamentos() {
		return tipoProcessamentoService.getAllTiposProcessamentos();
	}
	
	@GetMapping("/tipoProcessamento/folha/pagamento/competencia/{competenciaId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TipoProcessamentoResponse> getTipoProcessListFolhaPagByCompetenciaId(@PathVariable("competenciaId") Long competenciaId) {
		return tipoProcessamentoService.getTipoProcessListFolhaPagByCompetenciaId(competenciaId);
	}
	
	@GetMapping("/tipoProcessamento/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TipoProcessamentoResponse> getAllTiposProcessamentoByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return tipoProcessamentoService.getAllTiposProcessamentoByDescricao(search);
	}

}
