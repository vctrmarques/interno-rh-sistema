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

import com.rhlinkcon.payload.crmCrea.CrmCreaRequest;
import com.rhlinkcon.payload.crmCrea.CrmCreaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CrmCreaRepository;
import com.rhlinkcon.service.CrmCreaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CrmCreaController {

	@Autowired
	private CrmCreaRepository crmCreaRepository;

	@Autowired
	private CrmCreaService crmCreaService;

	@GetMapping("/crmCreas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CrmCreaResponse> getAllCrmCreas(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nomeConveniado", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeConveniado) {
		return crmCreaService.getAllCrmCreas(page, size, order, nomeConveniado);
	}

	@GetMapping("/listaCrmCreas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CrmCreaResponse> getAllCrmCreas() {
		return crmCreaService.getAllCrmCreas();
	}

	@GetMapping("/crmCrea/{crmCreaId}")
	public CrmCreaResponse getCrmCreaById(@PathVariable Long crmCreaId) {
		return crmCreaService.getCrmCreaById(crmCreaId);
	}

	@PostMapping("/crmCrea")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCrmCrea(@Valid @RequestBody CrmCreaRequest crmCreaRequest) {
		if (crmCreaRepository.existsByNomeConveniado(crmCreaRequest.getNomeConveniado())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		crmCreaService.createCrmCrea(crmCreaRequest);

		return Utils.created(true, "CRM / CREA criado com sucesso.");
	}

	@PutMapping("/crmCrea")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCrmCrea(@Valid @RequestBody CrmCreaRequest crmCreaRequest) {
		if (crmCreaRepository.existsByNomeConveniadoAndIdNot(crmCreaRequest.getNomeConveniado(),
				crmCreaRequest.getId())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		crmCreaService.updateCrmCrea(crmCreaRequest);

		return Utils.created(true, "CRM / CREA atualizado com sucesso.");
	}

	@DeleteMapping("/crmCrea/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCrmCrea(@PathVariable("id") Long id) {
		crmCreaService.deleteCrmCrea(id);

		return Utils.deleted(true, "CRM / CREA deletado com sucesso.");
	}
	
	@GetMapping("/crmCreas/porTipo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> getAllCrmCreasPorTipo(String tipo) {
		return ResponseEntity.ok(crmCreaService.getAllCrmCreasPorTipo(tipo));
	}

}
