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

import com.rhlinkcon.payload.causaAfastamento.CausaAfastamentoRequest;
import com.rhlinkcon.payload.causaAfastamento.CausaAfastamentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CausaAfastamentoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CausaAfastamentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CausaAfastamentoController {

	@Autowired
	private CausaAfastamentoRepository causaAfastamentoRepository;

	@Autowired
	private CausaAfastamentoService causaAfastamentoService;
	
	@GetMapping("/listaCausasAfastamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CausaAfastamentoResponse> getAllCausasAfastamento() {
		return causaAfastamentoService.getAllCausasAfastamento();
	}
	
	@GetMapping("/causasAfastamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CausaAfastamentoResponse> getAllCausasAfastamento(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return causaAfastamentoService.getAllCausasAfastamento(page, size, descricao, order);
	}

	@GetMapping("/causaAfastamento/{causaAfastamentoId}")
	public CausaAfastamentoResponse getCausaAfastamentoById(@PathVariable Long causaAfastamentoId) {
		return causaAfastamentoService.getCausaAfastamentoById(causaAfastamentoId);
	}

	@PostMapping("/causaAfastamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCausaAfastamento(@Valid @RequestBody CausaAfastamentoRequest causaAfastamentoRequest) {
		if (causaAfastamentoRepository.existsByDescricao(causaAfastamentoRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Causa de Afastamento já existe!");
		}

		causaAfastamentoService.createCausaAfastamento(causaAfastamentoRequest);

		return Utils.created(true, "Causa de Afastamento criada com sucesso.");
	}

	@PutMapping("/causaAfastamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCausaAfastamento(@Valid @RequestBody CausaAfastamentoRequest causaAfastamentoRequest) {
		if (causaAfastamentoRepository.existsByDescricaoAndIdNot(causaAfastamentoRequest.getDescricao(), causaAfastamentoRequest.getId())) {
			return Utils.badRequest(false, "Esta Causa de Afastamento já existe!");
		}

		causaAfastamentoService.updateCausaAfastamento(causaAfastamentoRequest);

		return Utils.created(true, "Causa de Afastamento atualizado com sucesso.");
	}

	@DeleteMapping("/causaAfastamento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCausaAfastamento(@PathVariable("id") Long id) {
		causaAfastamentoService.deleteCausaAfastamento(id);

		return Utils.deleted(true, "Classificação de Causa de Afastamento deletada com sucesso.");
	}

}
