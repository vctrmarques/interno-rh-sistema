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

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaRequest;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.UnidadeFederativaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class UnidadeFederativaController {

	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	@Autowired
	private UnidadeFederativaService unidadeFederativaService;

	@GetMapping("/unidadeFederativa/search")
	public List<DadoBasicoDto> getDadoBasicoList() {
		return unidadeFederativaService.getDadoBasicoList();
	}

	@GetMapping("/unidadesFederativas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<UnidadeFederativaResponse> getAllUnidadesFederativas(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "sigla", defaultValue = AppConstants.DEFAULT_EMPTY) String sigla) {
		return unidadeFederativaService.getAllUnidadesFederativas(page, size, sigla, order);
	}

	@GetMapping("/listaUnidadesFederativas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<UnidadeFederativaResponse> getListUnidadeFederativa() {
		return unidadeFederativaService.getAllUnidadesFederativas();
	}

	@GetMapping("/unidadeFederativa/{unidadeFederativaId}")
	public UnidadeFederativaResponse getUnidadeFederativaById(@PathVariable Long unidadeFederativaId) {
		return unidadeFederativaService.getUnidadeFederativaById(unidadeFederativaId);
	}

	@PostMapping("/unidadeFederativa")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createUnidadeFederativa(
			@Valid @RequestBody UnidadeFederativaRequest unidadeFederativaRequest) {
		if (unidadeFederativaRepository.existsBySigla(unidadeFederativaRequest.getSigla())) {
			return Utils.badRequest(false, "Esta Unidade Federativa já existe!");
		}

		unidadeFederativaService.createUnidadeFederativa(unidadeFederativaRequest);

		return Utils.created(true, "Unidade Federativa criada com sucesso.");
	}

	@PutMapping("/unidadeFederativa")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateUnidadeFederativa(
			@Valid @RequestBody UnidadeFederativaRequest unidadeFederativaRequest) {
		if (unidadeFederativaRepository.existsBySiglaAndIdNot(unidadeFederativaRequest.getSigla(),
				unidadeFederativaRequest.getId())) {
			return Utils.badRequest(false, "Esta Unidade Federativa já existe!");
		}

		unidadeFederativaService.updateUnidadeFederativa(unidadeFederativaRequest);

		return Utils.created(true, "Unidade Federativa atualizada com sucesso.");
	}

	@DeleteMapping("/unidadeFederativa/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteUnidadeFederativa(@PathVariable("id") Long id) {
		unidadeFederativaService.deleteUnidadeFederativa(id);

		return Utils.deleted(true, "Unidade Federativa deletada com sucesso.");
	}

	@GetMapping("/listaUfs")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<UnidadeFederativaResponse> getListUf() {
		return unidadeFederativaService.getAllUnidadesFederativas();
	}
}
