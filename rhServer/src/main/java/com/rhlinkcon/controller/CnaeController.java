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

import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.cnae.CnaeRequest;
import com.rhlinkcon.payload.cnae.CnaeResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CnaeService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CnaeController {

	@Autowired
	private CnaeService cnaeService;

	@GetMapping("/cnaes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CnaeResponse> getAllCnaes(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return cnaeService.getAllCnaes(page, size, order, descricao);
	}

	@GetMapping("/cnae/{cnaeId}")
	public CnaeResponse getCnaeById(@PathVariable Long cnaeId) {
		return cnaeService.getCnaeById(cnaeId);
	}

	@PostMapping("/cnae")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCnae(@Valid @RequestBody CnaeRequest cnaeRequest) {
		cnaeService.createCnae(cnaeRequest);
		return Utils.created(true, "CNAE criado com sucesso.");
	}

	@PutMapping("/cnae")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCnae(@Valid @RequestBody CnaeRequest cnaeRequest) {
		cnaeService.updateCnae(cnaeRequest);
		return Utils.created(true, "CNAE atualizado com sucesso.");
	}

	@DeleteMapping("/cnae/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCnae(@PathVariable("id") Long id) {
		try {
			cnaeService.deleteCnae(id);
			return Utils.deleted(true, "CNAE deletado com sucesso.");
		} catch (RuntimeException ex) {
			return Utils.deleted(false, "CNAE não poder ser excluído.");
		}

	}
	
	@GetMapping("/listaCnaes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CnaeResponse> getListCnae() {
		return cnaeService.getAllCnaes();
	}
	
	@GetMapping("/cnaes/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CnaeResponse> searchAllByNomeOrCodigoClasse(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return cnaeService.searchAllByNomeOrCodigoClasse(search);
	}

}
