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

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.naturezaJuridica.NaturezaJuridicaRequest;
import com.rhlinkcon.payload.naturezaJuridica.NaturezaJuridicaResponse;
import com.rhlinkcon.repository.NaturezaJuridicaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.NaturezaJuridicaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class NaturezaJuridicaController {

	@Autowired
	private NaturezaJuridicaRepository naturezaJuridicaRepository;

	@Autowired
	private NaturezaJuridicaService naturezaJuridicaService;

	@GetMapping("/naturezasJuridicas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<NaturezaJuridicaResponse> getAllNaturezasJuridicas(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "codigo", defaultValue = AppConstants.DEFAULT_EMPTY) String codigo) {
		return naturezaJuridicaService.getAllNaturezasJuridicas(page, size, nome, codigo, order);
	}

	@GetMapping("/naturezaJuridica/{naturezaJuridicaId}")
	public NaturezaJuridicaResponse getNaturezaJuridicaById(@PathVariable Long naturezaJuridicaId) {
		return naturezaJuridicaService.getNaturezaJuridicaById(naturezaJuridicaId);
	}

	@PostMapping("/naturezaJuridica")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createNaturezaJuridica(@Valid @RequestBody NaturezaJuridicaRequest naturezaJuridicaRequest) {
		if (naturezaJuridicaRepository.existsByNome(naturezaJuridicaRequest.getNome())) {
			return Utils.badRequest(false, "Esta Natureza Jurídica já existe!");
		}
		
		if (naturezaJuridicaRepository.existsByCodigo(naturezaJuridicaRequest.getCodigo())) {
			return Utils.badRequest(false, "O código já está em uso!");
		}

		naturezaJuridicaService.createNaturezaJuridica(naturezaJuridicaRequest);

		return Utils.created(true, "Natureza Jurídica criada com sucesso.");
	}

	@PutMapping("/naturezaJuridica")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateNaturezaJuridica(@Valid @RequestBody NaturezaJuridicaRequest naturezaJuridicaRequest) {
		if (naturezaJuridicaRepository.existsByNomeAndIdNot(naturezaJuridicaRequest.getNome(), naturezaJuridicaRequest.getId())) {
			return Utils.badRequest(false, "Esta Natureza Jurídica já existe!");
		}
		
		if (naturezaJuridicaRepository.existsByCodigoAndIdNot(naturezaJuridicaRequest.getCodigo(),naturezaJuridicaRequest.getId())) {
			return Utils.badRequest(false, "O código já está em uso!");
		}

		naturezaJuridicaService.updateNaturezaJuridica(naturezaJuridicaRequest);

		return Utils.created(true, "Natureza Jurídica atualizada com sucesso.");
	}


	@DeleteMapping("/naturezaJuridica/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteNaturezaJuridica(@PathVariable("id") Long id) {
		naturezaJuridicaService.deleteNaturezaJuridica(id);

		return Utils.deleted(true, "Natureza Jurídica deletada com sucesso.");
	}

}
