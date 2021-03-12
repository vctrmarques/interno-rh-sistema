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

import com.rhlinkcon.payload.experienciaProfissional.ExperienciaProfissionalRequest;
import com.rhlinkcon.payload.experienciaProfissional.ExperienciaProfissionalResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ExperienciaProfissionalRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ExperienciaProfissionalService;
import com.rhlinkcon.service.FuncionarioService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ExperienciaProfissionalController {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private ExperienciaProfissionalService experienciaProfissionalService;

	@Autowired
	private ExperienciaProfissionalRepository experienciaProfissionalRepository;

	@GetMapping("/experienciasProfissionais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncionarioResponse> getAllFaixasSalariais(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return funcionarioService.getAllFuncionariosComExperiencia(page, size, search, order);
	}

	@GetMapping("/experienciaProfissional/{experienciaProfissionalId}")
	public ExperienciaProfissionalResponse getExperienciaProfissionalById(
			@PathVariable Long experienciaProfissionalId) {
		return experienciaProfissionalService.getExperienciaProfissionalById(experienciaProfissionalId);
	}

	@PostMapping("/experienciaProfissional")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createFuncao(
			@Valid @RequestBody ExperienciaProfissionalRequest experienciaProfissionalRequest) {
		if (experienciaProfissionalRepository.existsExperienciaProfissionalByFuncaoIdAndFuncionarioId(
				experienciaProfissionalRequest.getFuncaoId(), experienciaProfissionalRequest.getFuncionarioId())) {
			return Utils.badRequest(false, "O funcionário já possui experiência nesta função!");
		}

		experienciaProfissionalService.createExperienciaProfissional(experienciaProfissionalRequest);

		return Utils.created(true, "Função criada com sucesso.");
	}

	@PutMapping("/experienciaProfissional")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFuncao(
			@Valid @RequestBody ExperienciaProfissionalRequest experienciaProfissionalRequest) {
		if (experienciaProfissionalRepository.existsExperienciaProfissionalByIdNotAndFuncaoIdAndFuncionarioId(
				experienciaProfissionalRequest.getId(), experienciaProfissionalRequest.getFuncaoId(),
				experienciaProfissionalRequest.getFuncionarioId())) {
			return Utils.badRequest(false, "O funcionário já possui experiência nesta função!");
		}

		experienciaProfissionalService.updateExperienciaProfissional(experienciaProfissionalRequest);

		return Utils.created(true, "Função atualizada com sucesso.");
	}

	@DeleteMapping("/experienciaProfissional/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteFuncao(@PathVariable("id") Long id) {
		experienciaProfissionalService.deleteExperienciaProfissional(id);

		return Utils.deleted(true, "Função deletada com sucesso.");
	}
}
