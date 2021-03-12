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
import com.rhlinkcon.payload.grauAcademico.GrauAcademicoRequest;
import com.rhlinkcon.payload.grauAcademico.GrauAcademicoResponse;
import com.rhlinkcon.repository.GrauAcademicoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.GrauAcademicoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class GrauAcademicoController {

	@Autowired
	private GrauAcademicoRepository grauAcademicoRepository;

	@Autowired
	private GrauAcademicoService grauAcademicoService;

	@GetMapping("/grausAcademicos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<GrauAcademicoResponse> getAllgrausAcademicos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return grauAcademicoService.getAllGrausAcademicos(page, size, nome, order);
	}
	
	@GetMapping("/listaGrausAcademicos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<GrauAcademicoResponse> getListGrausAcademicos() {
		return grauAcademicoService.getAllGrausAcademicos();
	}
	
	@GetMapping("/grauAcademico/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<GrauAcademicoResponse> searchByNome(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return grauAcademicoService.searchByNome(search);
	}

	@GetMapping("/grauAcademico/{grauAcademicoId}")
	public GrauAcademicoResponse getGrauAcademicoById(@PathVariable Long grauAcademicoId) {
		return grauAcademicoService.getGrauAcademicoById(grauAcademicoId);
	}

	@PostMapping("/grauAcademico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createGrauAcademico(@Valid @RequestBody GrauAcademicoRequest grauAcademicoRequest) {
		if (grauAcademicoRepository.existsByNome(grauAcademicoRequest.getNome())) {
			return Utils.badRequest(false, "Este Grau Acadêmico já existe!");
		}

		grauAcademicoService.createGrauAcademico(grauAcademicoRequest);

		return Utils.created(true, "Grau Acadêmico criado com sucesso.");
	}

	@PutMapping("/grauAcademico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateGrauAcademico(@Valid @RequestBody GrauAcademicoRequest grauAcademicoRequest) {
		if (grauAcademicoRepository.existsByNomeAndIdNot(grauAcademicoRequest.getNome(), grauAcademicoRequest.getId())) {
			return Utils.badRequest(false, "Este Grau Acadêmico já existe!");
		}

		grauAcademicoService.updateGrauAcademico(grauAcademicoRequest);
		
		return Utils.created(true, "Grau Acadêmico atualizado com sucesso.");
	}


	@DeleteMapping("/grauAcademico/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteGrauAcademico(@PathVariable("id") Long id) {
		grauAcademicoService.deleteGrauAcademico(id);
		
		return Utils.deleted(true, "Classificação de Grau Acadêmico deletada com sucesso.");
	}

}
