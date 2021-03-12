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
import com.rhlinkcon.payload.turno.TurnoRequest;
import com.rhlinkcon.payload.turno.TurnoResponse;
import com.rhlinkcon.repository.TurnoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TurnoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TurnoController {

	@Autowired
	private TurnoRepository turnoRepository;

	@Autowired
	private TurnoService turnoService;


	@GetMapping("/turnos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TurnoResponse> getAllTurnoPage(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "codigo", defaultValue = AppConstants.DEFAULT_EMPTY) String codigo) {
		return turnoService.getAllTurnoPage(page, size, order, codigo);
	}

	@PostMapping("/turno")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTurno(@Valid @RequestBody TurnoRequest turnoRequest) {
		if (turnoRepository.existsByCodigo(turnoRequest.getCodigo())) {
			return Utils.badRequest(false, "Este Turno já existe!");
		}

		turnoService.createTurno(turnoRequest);

		return Utils.created(true, "Turno Criado com sucesso.");
	}
	
	@GetMapping("/turno/{turnoId}")
	public TurnoResponse getTurnoResponseId(@PathVariable Long turnoId) {
		return turnoService.getTurnoResponseId(turnoId);
	}
	
	@PutMapping("/turno")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTurno(@Valid @RequestBody TurnoRequest turnoRequest) {
		if (turnoRepository.existsByCodigoAndIdNot(turnoRequest.getCodigo(), turnoRequest.getId())) {
			return Utils.badRequest(false, "Este Turno já existe!");
		}

		turnoService.updateTurno(turnoRequest);

		return Utils.created(true, "Turno atualizado com sucesso.");
	}
	
	@DeleteMapping("/turno/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTurno(@PathVariable("id") Long id) {
		turnoService.deleteTurno(id);

		return Utils.deleted(true, "Turno deletado com sucesso.");
	}
	
	@GetMapping("/listaTurnos")
//	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TurnoResponse> getAllTurnos() {
		return turnoService.getAllTurnos();
	}

}
