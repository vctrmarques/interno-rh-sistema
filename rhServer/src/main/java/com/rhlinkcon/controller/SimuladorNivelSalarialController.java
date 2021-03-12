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
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;
import com.rhlinkcon.payload.simuladorNivelSalarial.SimuladorNivelSalarialRequest;
import com.rhlinkcon.payload.simuladorNivelSalarial.SimuladorNivelSalarialResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.SimuladorNivelSalarialService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class SimuladorNivelSalarialController {

	@Autowired
	private SimuladorNivelSalarialService simuladorNivelSalarialService;

	@GetMapping("/simulardorNivelSalarials")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<SimuladorNivelSalarialResponse> getAllNivelSalarialPage(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return simuladorNivelSalarialService.getAllNivelSalarialPage(page, size, order, descricao);
	}

	@GetMapping("/simulardorNivelSalarials/listaNiveis")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ReferenciaSalarialResponse> getNiveisCompetencia(
			@RequestParam(value = "simuladorNivelSalarialId", defaultValue = AppConstants.DEFAULT_EMPTY) Long simuladorNivelSalarialId,
			@RequestParam(value = "mes", defaultValue = AppConstants.DEFAULT_EMPTY) String mes,
			@RequestParam(value = "ano", defaultValue = AppConstants.DEFAULT_EMPTY) String ano) {
		return simuladorNivelSalarialService.getNiveisCompetencia(simuladorNivelSalarialId, mes, ano);
	}

	@GetMapping("/simuladorNivelSalarial/{id}")
	public SimuladorNivelSalarialResponse getSimuladorNivelSalarialById(@PathVariable Long id) {
		return simuladorNivelSalarialService.getSimuladorNivelSalarialById(id);
	}

	@PostMapping("/simulardorNivelSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createSimuladorNivelSalarial(
			@Valid @RequestBody SimuladorNivelSalarialRequest simuladorNivelSalarialRequest) {
		simuladorNivelSalarialService.createSimuladorNivelSalarial(simuladorNivelSalarialRequest);

		return Utils.created(true, "Simulador Nível Salarial criado com sucesso.");
	}

	@PutMapping("/simulardorNivelSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateSimuladorNivelSalarial(
			@Valid @RequestBody SimuladorNivelSalarialRequest simuladorNivelSalarialRequest) {
		simuladorNivelSalarialService.updateSimuladorNivelSalarial(simuladorNivelSalarialRequest);

		return Utils.created(true, "Nível Salarial atualizado com sucesso.");
	}

	@DeleteMapping("/simulardorNivelSalarial/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteSimuladorNivelSalarial(@PathVariable("id") Long id) {
		simuladorNivelSalarialService.deleteSimuladorNivelSalarial(id);

		return Utils.deleted(true, "Nível Salarial deletado com sucesso.");
	}

}
