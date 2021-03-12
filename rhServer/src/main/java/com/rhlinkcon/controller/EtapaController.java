package com.rhlinkcon.controller;

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

import com.rhlinkcon.payload.etapa.EtapaRequest;
import com.rhlinkcon.payload.etapa.EtapaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EtapaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.EtapaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class EtapaController {

	@Autowired
	private EtapaRepository etapaRepository;

	@Autowired
	private EtapaService etapaService;

	@GetMapping("/etapas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EtapaResponse> getAllEtapas(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return etapaService.getAllEtapas(page, size, descricao, order);
	}

	@GetMapping("/etapa/{etapaId}")
	public EtapaResponse getEtapaById(@PathVariable Long etapaId) {
		return etapaService.getEtapaById(etapaId);
	}

	@PostMapping("/etapa")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createEtapa(@Valid @RequestBody EtapaRequest etapaRequest) {
		if (etapaRepository.existsByDescricao(etapaRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Etapa já existe!");
		}

		etapaService.createEtapa(etapaRequest);

		return Utils.created(true, "Etapa criada com sucesso.");
	}

	@PutMapping("/etapa")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateEtapa(@Valid @RequestBody EtapaRequest etapaRequest) {
		if (etapaRepository.existsByDescricaoAndIdNot(etapaRequest.getDescricao(), etapaRequest.getId())) {
			return Utils.badRequest(false, "Esta Etapa já existe!");
		}

		etapaService.updateEtapa(etapaRequest);
		
		return Utils.created(true, "Etapa atualizada com sucesso.");
	}


	@DeleteMapping("/etapa/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteEtapa(@PathVariable("id") Long id) {
		etapaService.deleteEtapa(id);
		
		return Utils.deleted(true, "Classificação de Etapa deletada com sucesso.");
	}

}
