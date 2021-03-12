package com.rhlinkcon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.service.AtributoFormulaService;

@RestController
@RequestMapping("/api")
public class AtributoFormulaController {

	@Autowired
	private AtributoFormulaService atributoFormulaService;

	@GetMapping("/atributosFormula")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(atributoFormulaService.carregarAtributos());
	}

}
