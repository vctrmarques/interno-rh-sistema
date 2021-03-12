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
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.funcionarioExercicio.FuncionarioExercicioRequest;
import com.rhlinkcon.payload.funcionarioExercicio.FuncionarioExercicioResponse;
import com.rhlinkcon.service.FuncionarioExercicioService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FuncionarioExercicioController {
		

	@Autowired
	private FuncionarioExercicioService funcionarioExercicioService;
	
	
	@GetMapping("/funcionarioExercicio/{funcionarioExercicioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public FuncionarioExercicioResponse getFuncionarioExercicioById(@PathVariable Long funcionarioExercicioId) { 
		return funcionarioExercicioService.getFuncionarioExercicioById(funcionarioExercicioId);
	}

	@PostMapping("/funcionarioExercicio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createFuncionarioExercicio(@Valid @RequestBody FuncionarioExercicioRequest funcionarioExercicioRequest) {

		funcionarioExercicioService.createFuncionarioExercicio(funcionarioExercicioRequest);
		
		return Utils.created(true, "Exercício criado com sucesso.");
	}

	@PutMapping("/funcionarioExercicio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFuncionarioExercicio(@Valid @RequestBody FuncionarioExercicioRequest funcionarioExercicioRequest) {

		funcionarioExercicioService.updateFuncionarioExercicio(funcionarioExercicioRequest);

		return Utils.created(true, "Exercício atualizado com sucesso.");
	}

	@DeleteMapping("/funcionarioExercicio/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteFuncionarioExercicio(@PathVariable("id") Long id) {
		funcionarioExercicioService.deleteFuncionarioExercicio(id);

		return Utils.deleted(true, "Exercício deletado com sucesso.");
	}
	

}
