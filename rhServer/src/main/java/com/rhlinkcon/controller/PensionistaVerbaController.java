package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.PensionistaFiltro;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.pensionista.PensionistaResponse;
import com.rhlinkcon.payload.pensionistaVerba.PensionistaVerbaRequest;
import com.rhlinkcon.payload.pensionistaVerba.PensionistaVerbaResponse;
import com.rhlinkcon.service.PensionistaService;
import com.rhlinkcon.service.PensionistaVerbaService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class PensionistaVerbaController {
	@Autowired
	private PensionistaVerbaService pensionistaVerbaService;

	@Autowired
	private PensionistaService pensionistaService;

	@GetMapping("/pensionistaVerbas/{pensionistaId}")
	public List<PensionistaVerbaResponse> getVerbasPensionistaById(@PathVariable Long pensionistaId) {
		return pensionistaVerbaService.getVerbasPensionistaByPensionistaId(pensionistaId);
	}

	@PostMapping("/pensionistaVerbas/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody PensionistaVerbaRequest request) {

		pensionistaVerbaService.createPensionistaVerba(request);

		return Utils.created(true, "Verbas do Funcionário criada com sucesso.");
	}

	@GetMapping("/pensionistaVerbas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<PensionistaResponse> getAll(PagedRequest pagedRequest, PensionistaFiltro pensionistaFiltro) {
		return pensionistaService.getAllPensionisaToVerbasPensionista(pagedRequest, pensionistaFiltro);
	}
}