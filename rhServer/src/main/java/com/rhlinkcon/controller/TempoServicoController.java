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

import com.rhlinkcon.payload.cargo.CargoRequest;
import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.payload.tempoServico.TempoServicoRequest;
import com.rhlinkcon.payload.tempoServico.TempoServicoResponse;
import com.rhlinkcon.repository.CargoRepository;
import com.rhlinkcon.service.TempoServicoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TempoServicoController {

	@Autowired
	private TempoServicoService tempoServicoService;

	@GetMapping("/tempoServico/{tempoServicoId}")
	public TempoServicoResponse getTempoServicoById(@PathVariable Long tempoServicoId) {
		return tempoServicoService.getTempoServicoById(tempoServicoId);
	}

	@PostMapping("/tempoServico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTempoServico(@Valid @RequestBody TempoServicoRequest tempoServicoRequest) {

		tempoServicoService.createTempoServico(tempoServicoRequest);

		return Utils.created(true, "Tempo de Serviço criado com sucesso.");
	}

	@PutMapping("/tempoServico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTempoServico(@Valid @RequestBody TempoServicoRequest tempoServicoRequest) {
		
		tempoServicoService.updateTempoServico(tempoServicoRequest);

		return Utils.created(true, "Tempo de Serviço atualizado com sucesso.");
	}

	@DeleteMapping("/tempoServico/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTermpoServico(@PathVariable("id") Long id) {
		tempoServicoService.deleteTempoServico(id);

		return Utils.deleted(true, "Tempo de Serviço deletado com sucesso.");
	}

}
