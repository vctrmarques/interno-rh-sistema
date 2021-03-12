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

import com.rhlinkcon.payload.centroCusto.CentroCustoRequest;
import com.rhlinkcon.payload.evento.EventoRequest;
import com.rhlinkcon.payload.evento.EventoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EventoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.EventoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private EventoService eventoService;

	@GetMapping("/eventos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EventoResponse> getCentrosCustos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return eventoService.getAllEventos(page, size, descricao, order);
	}
	
	@GetMapping("/listaEventos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EventoResponse> getListCentrosCustos() {
		return eventoService.getAllEventos();
	}

	@GetMapping("/evento/{eventoId}")
	public EventoResponse getEventoById(@PathVariable Long eventoId) {
		return eventoService.getEventoById(eventoId);
	}

	@PostMapping("/evento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createEvento(@Valid @RequestBody EventoRequest eventoRequest) {
		if (eventoRepository.existsByDescricao(eventoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este evento já existe!");
		}

		eventoService.createEvento(eventoRequest);

		return Utils.created(true, "Evento criado com sucesso.");
	}

	@PutMapping("/evento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateEvento(@Valid @RequestBody EventoRequest eventoRequest) {
		if (eventoRepository.existsByDescricaoAndIdNot(eventoRequest.getDescricao(), eventoRequest.getId())) {
			return Utils.badRequest(false, "Este Centro de Custo já existe!");
		}

		eventoService.updateEvento(eventoRequest);
		
		return Utils.created(true, "Centro de Custo atualizado com sucesso.");
	}


	@DeleteMapping("/evento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteEvento(@PathVariable("id") Long id) {
		eventoService.deleteEvento(id);
		
		return Utils.deleted(true, "Evento deletado com sucesso.");
	}

}
