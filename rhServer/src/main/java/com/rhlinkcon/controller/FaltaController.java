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

import com.rhlinkcon.payload.falta.FaltaRequest;
import com.rhlinkcon.payload.falta.FaltaResponse;
import com.rhlinkcon.payload.processo.ProcessoResponse;
import com.rhlinkcon.service.FaltaService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FaltaController {
	@Autowired
	private FaltaService faltaService;
	
	@PostMapping("/falta")
	public ResponseEntity<?> create(@Valid @RequestBody FaltaRequest falta){
		faltaService.create(falta);		
		return Utils.created(true, "Falta registrada com sucesso");
	}
	
	@GetMapping("/falta/{faltaId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public FaltaResponse getFaltaById(@PathVariable Long faltaId) {
		return faltaService.getFaltaById(faltaId);
	}
	
	@PutMapping("/falta")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody FaltaRequest falta) {
		faltaService.create(falta);		
		return Utils.created(true, "Falta atualizada com sucesso");
	}
	
	@DeleteMapping("/falta/remover-anexo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteAnexo(@PathVariable("id") Long id) {
		faltaService.deleteAnexo(id);
		return Utils.deleted(true, "Anexo deletadoo com sucesso.");
	}
}
