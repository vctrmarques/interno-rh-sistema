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
import com.rhlinkcon.payload.tipoAposentadoria.TipoAposentadoriaRequest;
import com.rhlinkcon.payload.tipoAposentadoria.TipoAposentadoriaResponse;
import com.rhlinkcon.payload.tipoFolha.TipoFolhaRequest;
import com.rhlinkcon.payload.tipoFolha.TipoFolhaResponse;
import com.rhlinkcon.repository.TipoAposentadoriaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TipoAposentadoriaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TipoAposentadoriaController {
	
	@Autowired
	private TipoAposentadoriaService tipoAposentadoriaService;
	
	@GetMapping("/tiposAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TipoAposentadoriaResponse> getAll(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return tipoAposentadoriaService.getAllTiposAposentadoria(page, size, descricao, order);
	}
	
	@GetMapping("/tiposAposentadoria/{tiposAposentadoriaId}")
	public TipoAposentadoriaResponse getTipoAposentadoriaById(@PathVariable Long tiposAposentadoriaId) {
		return tipoAposentadoriaService.getTipoAposentadoriaById(tiposAposentadoriaId);
	}
	
	@PostMapping("/tiposAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTipoAposentadoria(@Valid @RequestBody TipoAposentadoriaRequest tipoAposentadoriaRequest) {
		if (tipoAposentadoriaService.existsByDescricao(tipoAposentadoriaRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Tipo de Aposentadoria já existe!");
		}

		tipoAposentadoriaService.createTipoAposentadoria(tipoAposentadoriaRequest);
		return Utils.created(true, "Tipo de Aposentadoria criado com sucesso.");
	}	
	
	@PutMapping("/tiposAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTipoAposentadoria(@Valid @RequestBody TipoAposentadoriaRequest tipoAposentadoriaRequest) {
		if (tipoAposentadoriaService.existsByDescricaoAndIdNot(tipoAposentadoriaRequest.getDescricao(), tipoAposentadoriaRequest.getId())) {
			return Utils.badRequest(false, "Este Tipo de Aposentadoria já existe!");
		}

		tipoAposentadoriaService.updateTipoAposentadoria(tipoAposentadoriaRequest);
		return Utils.created(true, "Tipo de Aposentadoria atualizado com sucesso.");
	}


	@DeleteMapping("/tiposAposentadoria/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTipoAposentadoria(@PathVariable("id") Long id) {
		tipoAposentadoriaService.deleteTipoAposentadoria(id);
		return Utils.deleted(true, "Tipo de Aposentadoria deletado com sucesso.");
	}
	
	@GetMapping("/listaTiposAposentadorias")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TipoAposentadoriaResponse> getAllTiposAposentadorias() {
		return tipoAposentadoriaService.getAllTiposAposentadorias();
	}
	

}
