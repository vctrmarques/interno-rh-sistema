package com.rhlinkcon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.model.PeriodoCertidaoExSegurado;
import com.rhlinkcon.payload.certidaoExSegurado.PeriodoCertidaoExSeguradoRequest;
import com.rhlinkcon.service.PeriodoCertidaoExSeguradoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class PeriodoCertidaoExSeguradoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private PeriodoCertidaoExSeguradoService periodoCertidaoExSeguradoService;
	
	
	@PostMapping("/periodoCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody PeriodoCertidaoExSeguradoRequest periodoCertidaoExSeguradoRequest) {
		PeriodoCertidaoExSegurado periodoCertidaoExSegurado = new PeriodoCertidaoExSegurado( periodoCertidaoExSeguradoRequest);
		periodoCertidaoExSeguradoService.create(periodoCertidaoExSegurado);
		return Utils.created(true, "Criado com sucesso.");
	}
	
	@PutMapping("/periodoCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody PeriodoCertidaoExSeguradoRequest  periodoCertidaoExSeguradoRequest) {
		PeriodoCertidaoExSegurado periodoCertidaoExSegurado = new PeriodoCertidaoExSegurado( periodoCertidaoExSeguradoRequest);
		periodoCertidaoExSeguradoService.create(periodoCertidaoExSegurado);
		return Utils.created(true, "Editado com sucesso.");
	}
	
	@DeleteMapping("/periodoCertidaoExSegurado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		periodoCertidaoExSeguradoService.delete(id);
		return Utils.deleted(true, "deletado com sucesso.");
	}
	
	
}