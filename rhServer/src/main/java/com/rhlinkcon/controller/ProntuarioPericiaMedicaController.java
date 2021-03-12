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

import com.rhlinkcon.filtro.ProntuarioPericiaMedicaFiltro;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.periciaMedica.PericiaMedicaResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ProntuarioPericiaMedicaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ProntuarioPericiaMedicaController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ROLE_PRONTUARIO_PERICIA_MEDICA_GESTAO')";
	private static final String AUTHORIZECADASTRAR = "hasAnyRole('ADMIN','ROLE_PRONTUARIO_PERICIA_MEDICA_CADASTRAR')";
	private static final String AUTHORIZEATUALIZAR = "hasAnyRole('ADMIN','ROLE_PRONTUARIO_PERICIA_MEDICA_ATUALIZAR')";
	private static final String AUTHORIZEEXCLUIR = "hasAnyRole('ADMIN','ROLE_PRONTUARIO_PERICIA_EXCLUIR')";
	private static final String AUTHORIZEVISUALIZAR = "hasAnyRole('ADMIN','ROLE_PRONTUARIO_PERICIA_VISUALIZAR')";

	@Autowired
	private ProntuarioPericiaMedicaService service;

	@GetMapping("/prontuariosPericiasMedicas")
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<PericiaMedicaResponse> getAllProntuarioPericiaMedica(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			ProntuarioPericiaMedicaFiltro prontuarioPericiaMedicaFiltro) {
		return null;
	}

	@PostMapping("/prontuarioPericiaMedica")
	@PreAuthorize(AUTHORIZECADASTRAR)
	public ResponseEntity<?> create(@Valid @RequestBody ProntuarioPericiaMedicaDto dto) {
		service.create(dto);
		return Utils.created(true, "Prontuário Perícia Médica criada com sucesso!");
	}

	@GetMapping("/prontuarioPericiaMedica/{id}")
	@PreAuthorize(AUTHORIZEVISUALIZAR)
	public ProntuarioPericiaMedicaDto getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@GetMapping("/prontuarioPericiaMedica/ByIdPacientePerica/{id}")
	@PreAuthorize(AUTHORIZEVISUALIZAR)
	public List<ProntuarioPericiaMedicaDto> getByIdPacientePerica(@PathVariable Long id) {
		return service.getByIdPacientePericaMedica(id);
	}

	@GetMapping("/prontuarioPericiaMedica/ByIdConsultaPericia/{id}")
	@PreAuthorize(AUTHORIZEVISUALIZAR)
	public ProntuarioPericiaMedicaDto getByIdConsultaPericia(@PathVariable Long id) {
		return service.getByIdConsultaPericiaMedica(id);
	}

	@PutMapping("/prontuarioPericiaMedica")
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public ResponseEntity<?> update(@Valid @RequestBody ProntuarioPericiaMedicaDto dto) {
		service.update(dto);
		return Utils.created(true, "Prontuário Perícia Médica alterado com sucesso!");
	}

	@DeleteMapping("/prontuarioPericiaMedica/{id}")
	@PreAuthorize(AUTHORIZEEXCLUIR)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Prontuário Perícia Médica deletada com sucesso.");
	}

}
