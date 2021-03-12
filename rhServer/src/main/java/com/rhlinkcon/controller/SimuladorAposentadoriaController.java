package com.rhlinkcon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.ApiResponse;
import com.rhlinkcon.payload.simuladorAposentadoria.SimuladorAposentadoria;
import com.rhlinkcon.service.SimuladorAposentadoriaService;

@RestController
@RequestMapping("/api/publico")
public class SimuladorAposentadoriaController {

	@Autowired
	private SimuladorAposentadoriaService simuladorAposentadoriaService;

	@PostMapping("/simulador/aposentadoria")
	public ResponseEntity<?> createSimuladorAposentadoria(
			@Valid @RequestBody SimuladorAposentadoria simuladorAposentadoria) {
		return ResponseEntity.ok(new ApiResponse(true, "Simulação efetuada com sucesso.",
				simuladorAposentadoriaService.simular(simuladorAposentadoria)));
	}

}
