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
import com.rhlinkcon.payload.tipoFolha.TipoFolhaRequest;
import com.rhlinkcon.payload.tipoFolha.TipoFolhaResponse;
import com.rhlinkcon.repository.TipoFolhaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TipoFolhaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TipoFolhaController {

	@Autowired
	private TipoFolhaRepository tipoFolhaRepository;

	@Autowired
	private TipoFolhaService tipoFolhaService;

	@GetMapping("/tiposFolhas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TipoFolhaResponse> getAllTiposFolhas(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return tipoFolhaService.getAllTiposFolhas(page, size, descricao, order);
	}
	
	@GetMapping("/tipoFolha/{tipoFolhaId}")
	public TipoFolhaResponse getTipoFolhaById(@PathVariable Long tipoFolhaId) {
		return tipoFolhaService.getTipoFolhaById(tipoFolhaId);
	}

	@PostMapping("/tipoFolha")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTipoFolha(@Valid @RequestBody TipoFolhaRequest tipoFolhaRequest) {
		if (tipoFolhaRepository.existsByDescricao(tipoFolhaRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Tipo de Folha já existe!");
		}

		tipoFolhaService.createTipoFolha(tipoFolhaRequest);

		return Utils.created(true, "Tipo de Folha criado com sucesso.");
	}

	@PutMapping("/tipoFolha")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTipoFolha(@Valid @RequestBody TipoFolhaRequest tipoFolhaRequest) {
		if (tipoFolhaRepository.existsByDescricaoAndIdNot(tipoFolhaRequest.getDescricao(), tipoFolhaRequest.getId())) {
			return Utils.badRequest(false, "Este Tipo de Folha já existe!");
		}

		tipoFolhaService.updateTipoFolha(tipoFolhaRequest);
		
		return Utils.created(true, "Tipo de Folha atualizado com sucesso.");
	}


	@DeleteMapping("/tipoFolha/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTipoFolha(@PathVariable("id") Long id) {
		tipoFolhaService.deleteTipoFolha(id);
		
		return Utils.deleted(true, "Tipo de Folha deletado com sucesso.");
	}
	
	@GetMapping("/listaTiposFolhas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TipoFolhaResponse> getAllTiposFolhas() {
		return tipoFolhaService.getAllTiposFolhas();
	}


}
