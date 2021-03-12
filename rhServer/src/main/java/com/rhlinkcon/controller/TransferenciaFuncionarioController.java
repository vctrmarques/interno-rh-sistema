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

import com.rhlinkcon.payload.tranferenciaFuncionario.TransferenciaFuncionarioRequest;
import com.rhlinkcon.payload.tranferenciaFuncionario.TransferenciaFuncionarioResponse;
import com.rhlinkcon.service.TransferenciaFuncionarioService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TransferenciaFuncionarioController {
	
	@Autowired
	private TransferenciaFuncionarioService transferenciaFuncionarioService;

	@GetMapping("/transferencias/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TransferenciaFuncionarioResponse> getAllTransferenciaByFuncionario(@PathVariable Long funcionarioId) {
		return transferenciaFuncionarioService.getAllTransferenciaByFuncionario(funcionarioId);
	}
	
	@PostMapping("/transferenciaFuncionario")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTransferenciaFuncionario(@Valid @RequestBody TransferenciaFuncionarioRequest transferenciaFuncionarioRequest) {

		transferenciaFuncionarioService.createTransferenciaFuncionario(transferenciaFuncionarioRequest);

		return Utils.created(true, "Trasferência realizada com sucesso.");
	}

}
