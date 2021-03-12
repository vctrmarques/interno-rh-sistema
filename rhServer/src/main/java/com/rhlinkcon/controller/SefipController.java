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

import com.rhlinkcon.model.TipoSefipEnum;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.sefip.SefipRequest;
import com.rhlinkcon.payload.sefip.SefipResponse;
import com.rhlinkcon.repository.SefipRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.SefipService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class SefipController {

	@Autowired
	private SefipRepository sefipRepository;

	@Autowired
	private SefipService sefipService;

	@GetMapping("/sefips")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<SefipResponse> getAllSefips(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return sefipService.getAllSefips(page, size, descricao, order);
	}

	@GetMapping("/sefip/{sefipId}")
	public SefipResponse getSefipById(@PathVariable Long sefipId) {
		return sefipService.getSefipById(sefipId);
	}

	@GetMapping("/sefip/categoria/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SefipResponse> searchAllByDescricaoAndTipoCategoria(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return sefipService.searchAllByDescricaoAndTipo(search, TipoSefipEnum.CATEGORIA);
	}
	
	@GetMapping("/listaSefips")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SefipResponse> getAllSefips() {
		return sefipService.getAllSefips();
	}

	@GetMapping("/sefip/ocorrencia/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SefipResponse> searchAllByDescricaoAndTipoOcorrencia(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return sefipService.searchAllByDescricaoAndTipo(search, TipoSefipEnum.OCORRENCIA);
	}

	@PostMapping("/sefip")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createSefip(@Valid @RequestBody SefipRequest sefipRequest) {
		if (sefipRepository.existsByDescricao(sefipRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Sefip já existe!");
		}

		sefipService.createSefip(sefipRequest);

		return Utils.created(true, "Sefip criada com sucesso.");
	}

	@PutMapping("/sefip")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateSefip(@Valid @RequestBody SefipRequest sefipRequest) {
		if (sefipRepository.existsByDescricaoAndIdNot(sefipRequest.getDescricao(), sefipRequest.getId())) {
			return Utils.badRequest(false, "Esta Sefip já existe!");
		}

		sefipService.updateSefip(sefipRequest);

		return Utils.created(true, "Sefip atualizada com sucesso.");
	}

	@DeleteMapping("/sefip/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteSefip(@PathVariable("id") Long id) {
		sefipService.deleteSefip(id);

		return Utils.deleted(true, "Sefip deletada com sucesso.");
	}

}
