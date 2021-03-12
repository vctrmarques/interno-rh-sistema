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
import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialRequest;
import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialResponse;
import com.rhlinkcon.repository.GrupoSalarialRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.GrupoSalarialService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class GrupoSalarialController {
	
	@Autowired
	private GrupoSalarialService grupoSalarialService;
	
	@Autowired
	private GrupoSalarialRepository grupoSalarialRepository;
	
	@GetMapping("/gruposSalariais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<GrupoSalarialResponse> getAllGruposSalariais(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return grupoSalarialService.getAllGruposSalariais(page, size, nome, order);
	}

	@GetMapping("/grupoSalarial/{id}")
	public GrupoSalarialResponse getGrupoSalarialById(@PathVariable Long id) {
		return grupoSalarialService.getGrupoSalarialById(id);
	}

	@PostMapping("/grupoSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createGrupoSalarial(@Valid @RequestBody GrupoSalarialRequest grupoSalarialRequest) {
		if (grupoSalarialRepository.existsByNome(grupoSalarialRequest.getNome())) {
			return Utils.badRequest(false, "Este Grupo Salarial já existe!");
		}
		
		grupoSalarialService.createGrupoSalarial(grupoSalarialRequest);

		return Utils.created(true, "Grupo Salarial criada com sucesso.");
	}

	@PutMapping("/grupoSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateGrupoSalarial(@Valid @RequestBody GrupoSalarialRequest grupoSalarialRequest) {
		if (grupoSalarialRepository.existsByNomeAndIdNot(grupoSalarialRequest.getNome(), grupoSalarialRequest.getId())) {
			return Utils.badRequest(false, "Este Grupo Salarial já existe!");
		}

		grupoSalarialService.updateGrupoSalarial(grupoSalarialRequest);

		return Utils.created(true, "Grupo Salarial atualizada com sucesso.");
	}


	@DeleteMapping("/grupoSalarial/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteGrupoSalarial(@PathVariable("id") Long id) {
		grupoSalarialService.deleteGrupoSalarial(id);

		return Utils.deleted(true, "Grupo Salarial deletada com sucesso.");
	}

	@GetMapping("/listaGruposSalariais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<GrupoSalarialResponse> getAllGruposSalariais() {
		return grupoSalarialService.getAllGruposSalariais();
	}
}
